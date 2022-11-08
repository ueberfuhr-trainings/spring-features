package de.samples.todos.boundary.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class JacksonHints implements RuntimeHintsRegistrar {

    @Override
    @SneakyThrows
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        final var reflection = hints.reflection();
        Arrays.stream(PropertyNamingStrategies.class.getFields())
          .filter(f -> Modifier.isStatic(f.getModifiers()))
          .filter(f -> f.getType().isAssignableFrom(PropertyNamingStrategy.class))
          .forEach(reflection::registerField);
    }
}
