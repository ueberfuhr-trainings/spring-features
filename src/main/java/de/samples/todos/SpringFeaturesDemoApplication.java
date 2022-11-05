package de.samples.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class SpringFeaturesDemoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SpringFeaturesDemoApplication.class, args);
		// Necessary for Actuator Startup Endpoint
		final var app = new SpringApplication(SpringFeaturesDemoApplication.class);
		app.setApplicationStartup(new BufferingApplicationStartup(2048));
		app.run(args);

	}

}
