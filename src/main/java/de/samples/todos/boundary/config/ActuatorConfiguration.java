package de.samples.todos.boundary.config;

import de.samples.todos.persistence.TodosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ActuatorConfiguration {

    // -------------------------------------

    private final JdbcTemplate db;
    private final TodosRepository todosRepository;

    private Health databaseHealth() {
        try {
            // test query
            db.query("select 1", (rs, num) -> 1);
            return Health.up()
              .build();
        } catch (DataAccessException ex) {
            // see application.yml for activation of details
            return Health.down()
              .withDetail("cause", "database query does not work")
              .withException(ex)
              .build();
        }
    }

    // URL: .../actuator/health
    // we could also write a @Component annotated class
    @Bean()
    public HealthIndicator databaseQueryWorks() {
        return this::databaseHealth;
    }

    // -------------------------------------

    // URL: .../actuator/info
    // further information: https://codeboje.de/spring-boot-info-actuator

    @Bean
    public InfoContributor provideTodosInfos() {
        return builder ->
          builder
            .withDetail("todos", Map.of("count", todosRepository.count()));

    }

    // -------------------------------------

    // URL: .../actuator/metrics
    // URL: .../actuator/metrics/system.cpu.usage
    /* URL: .../actuator/prometheus
    <!-- Micrometer Prometheus registry  -->
    <dependency>
	    <groupId>io.micrometer</groupId>
	    <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
     */

}
