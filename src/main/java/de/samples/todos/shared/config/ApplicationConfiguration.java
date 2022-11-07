package de.samples.todos.shared.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * We need to
 *  - add the spring-boot-configuration-processor to pom.xml
 *  - IntelliJ: Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
 *    - Enable Annotation Processors from classpath (check the project's profile too)
 */

/**
 * The application-specific configuration. To enable sample data initialization on startup,
 * add the following lines to your application.yml files:
 *
 * <pre>
 * application:
 *   initialize-sample-data-on-startup: true
 * </pre>
 */
@Component
@ConfigurationProperties("application")
public @Data class ApplicationConfiguration {

    private boolean initializeSampleDataOnStartup;

}
