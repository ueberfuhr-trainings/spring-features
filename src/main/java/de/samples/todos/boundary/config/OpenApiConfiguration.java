package de.samples.todos.boundary.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
          .components(new Components())
          .info(
            new Info()
              .title("Todos Management Service")
              .description("An API to manages todos.")
              .version("1.0")
          );
    }

}
