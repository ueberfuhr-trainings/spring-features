package de.samples.todos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * We need to
 *  - add the spring-boot-configuration-processor to pom.xml
 *  - IntelliJ: Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
 *    - Enable Annotation Processors from classpath (check the project's profile too)
 */
@Component
@ConfigurationProperties("application")
public @Data class ApplicationConfiguration {

    private boolean initializeSampleDataOnStartup;

}
