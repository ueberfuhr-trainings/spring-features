# Spring Boot Native Support

Since Spring Boot 3 (Milestone 5), Spring's formerly [Native initiative](https://www.baeldung.com/spring-native-intro)
moved into the core.

- (Spring Native Hints)[https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#native-hints]
   moved to
  (Spring Core Hints)[https://docs.spring.io/spring-framework/docs/6.0.0-M6/javadoc-api/org/springframework/aot/hint/package-summary.html]
- Spring AOT Maven/Gradle Plugin's functionality moven to
  (Spring Boot Plugin)[https://docs.spring.io/spring-boot/docs/3.0.0-RC1/maven-plugin/reference/htmlsingle/#aot]

## Motivation

In some scenarios like "Function as a Service" (FaaS), it is extremely important for an app
to fastly startup and have low memory footprint. This can be reached e.g. by

- ahead-of-time (AOT) compilation
- native image building, i.e.
    - run user code (e.g. static initialization)
    - linking compiled user code, parts of the Java runtime (gc, threading support) and the results of the code execution
    - produce a standalone, platform-dependent executable or shared library

[GraalVM](https://www.graalvm.org/) is a Java VM and JDK based on HotSpot/OpenJDK, implemented in Java.
It provides a tool named `native-image` builder, that can build native images as described upon.
A short example by code is documented [here](native-image-sample.md).

## Build Native Image (Maven)

If we are using `spring-boot-starter-parent`, there is a pre-configured `native` profile, that we can use:

```bash
mvn clean package -Pnative
```

By default, Spring Boot Maven Plugin only prepares the JAR for building a native image, i.e. it invokes AOT compiling
and generates the
[Native Image Build Configuration](https://www.graalvm.org/22.0/reference-manual/native-image/BuildConfiguration/)
If we want to get the native image itself, we need to add `native-maven-plugin` explicitly
(see [pom.xml](../../pom.xml)). Then, we must be aware, that we

- have to run the build using our platform's native command line
- have to be sure that the platform's native compiler is available (see [Installation Instructions](native-image-sample.md))

For Windows 64-bit, we have to invoke

```bash
"C:\Program Files (x86)\Microsoft Visual Studio\2019\BuildTools\VC\Auxiliary\Build\vcvars64.bat"
mvn clean package -Pnative
```

This will also build a native test image and runs the tests in native mode.

## Sample issues

### Static Fields (Constants)

Unfortunately, with the current libraries, starting the generated executable will result in an error:

```bash
Caused by: java.lang.IllegalArgumentException: Constant named 'SNAKE_CASE' not found
  at org.springframework.util.Assert.notNull(Assert.java:219) ~[na:na]
  at org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$Jackson2ObjectMapperBuilderCustomizerConfiguration$StandardJackson2ObjectMapperBuilderCustomizer.configurePropertyNamingStrategyField(JacksonAutoConfiguration.java:287) ~[spring-features.exe:na]
```

This is because we use this setting in `application.yml`:

```yaml
spring:
  jackson:
    property-naming-strategy: SNAKE_CASE
```

`SNAKE_CASE` is one of the constants of `com.fasterxml.jackson.databind.PropertyNamingStrategies`:

```java
public abstract class PropertyNamingStrategies
  implements java.io.Serializable {
    // ...
    public static final PropertyNamingStrategy SNAKE_CASE = new SnakeCaseStrategy();
}
```

Static initializations are the domain of AOT compiling. Obviously, this constant is ignored during build time
because of any direct usage. So, we have to give the AOT compiler a so-called _Native Hint_.

```java

@Configuration // any Spring component
@ImportRuntimeHints(JacksonHints.class)
public class BoundaryConfiguration {

}

public class JacksonHints implements RuntimeHintsRegistrar {

    @Override
    @SneakyThrows
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints
          .reflection()
          .registerField(PropertyNamingStrategies.class.getField("SNAKE_CASE"));
    }
}
```

This will lead to the following entry in the file `META-INF/native-image/de.sample/spring-features/reflect-config.json`
during AOT compiling:

```json
[
  {
    "name": "com.fasterxml.jackson.databind.PropertyNamingStrategies",
    "fields": [
      {
        "name": "SNAKE_CASE"
      }
    ]
  }
]
```

This file configures the `native-image` builder to compile the field into the native code.

### Incompatibilities

#### Mockito
Currently, there is an  [issue](https://github.com/spring-projects/spring-boot/issues/32195) with Mockito running in
native test execution mode. At the moment, we could avoid this by disabling native tests:

```xml

<plugin>
    <groupId>org.graalvm.buildtools</groupId>
    <artifactId>native-maven-plugin</artifactId>
    <configuration>
        <skipNativeTests>true</skipNativeTests>
    </configuration>
</plugin>
```

#### GraphQL

GraphQL does not declare any native hints that would be necessary to run in native apps.
An [issue](https://github.com/spring-projects/spring-graphql/issues/495) exists.
