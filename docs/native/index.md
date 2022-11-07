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

If we are using `spring-boot-starter-parent`, this execution is automatically configured if we enable the `native` profile. 

```bash
mvn clean package -Pnative
```
