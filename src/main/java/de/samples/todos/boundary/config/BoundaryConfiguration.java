package de.samples.todos.boundary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.CONTENT_LANGUAGE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.IF_MATCH;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;
import static org.springframework.http.HttpHeaders.LINK;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpHeaders.ORIGIN;

@Configuration
@RequiredArgsConstructor
// Merged into Spring Boot 3.0.0-RC2
// @ImportRuntimeHints(JacksonRuntimeHints.class)
public class BoundaryConfiguration {

    private final CorsConfigurationData allowed;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry
                  .addViewController("/")
                  .setViewName("redirect:/index.html");
            }

            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                  .exposedHeaders(LOCATION, LINK)
                  // allow all HTTP request methods
                  .allowedMethods(stream(RequestMethod.values()).map(Enum::name).toArray(String[]::new)) //
                  // allow the commonly used headers
                  .allowedHeaders(ORIGIN, CONTENT_TYPE, CONTENT_LANGUAGE, ACCEPT, ACCEPT_LANGUAGE, IF_MATCH, IF_NONE_MATCH) //
                  // this is stage specific
                  .allowedOrigins(allowed.getOrigins())
                  .allowCredentials(allowed.isCredentials());
            }

        };
    }


    /*
    class JacksonRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            if (ClassUtils.isPresent("com.fasterxml.jackson.databind.PropertyNamingStrategy", classLoader)) {
                registerHints(hints.reflection());
            }
        }

        private void registerHints(ReflectionHints reflection) {
            var fieldsOfStrategies = PropertyNamingStrategies.class.getDeclaredFields();
            // Jackson 2.12 pre
            var fieldsOfStrategy = PropertyNamingStrategy.class.getDeclaredFields();
            // Find all static fields that provide a PropertyNamingStrategy
            // (this way we automatically support new constants
            // that may be added by Jackson in the future)
            Stream.concat(Stream.of(fieldsOfStrategies), Stream.of(fieldsOfStrategy))
              .filter(f -> Modifier.isStatic(f.getModifiers()))
              .filter(f -> f.getType().isAssignableFrom(PropertyNamingStrategy.class))
              .forEach(reflection::registerField);
        }

    }
    */

}
