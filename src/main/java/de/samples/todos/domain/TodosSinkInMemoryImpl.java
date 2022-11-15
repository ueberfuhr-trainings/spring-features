package de.samples.todos.domain;

import de.samples.todos.shared.aspects.LogOnInvocation;
import org.slf4j.event.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

/*
 * We need a factory here, because @ConditionalOnMissingBean only works with that!
 */

@Configuration
public class TodosSinkInMemoryImpl {

    @Bean
    @ConditionalOnMissingBean(TodosSink.class)
    @LogOnInvocation(value = "Using In-Memory-Implementation. This is not scalable!", level = Level.WARN)
    TodosSink createSinkForThis() {
        return new TodosSink() {

            private final Map<Long, Todo> todos = new TreeMap<>();

            @Override
            public long getCount() {
                return todos.size();
            }

            @Override
            public Stream<Todo> findAll() {
                return todos.values().stream();
            }

            @Override
            public Optional<Todo> findById(long id) {
                return Optional.ofNullable(todos.get(id));
            }

            @Override
            public void save(Todo item) {
                if (null == item.getId()) {
                    item.setId(
                      todos.keySet().stream()
                        .max(Comparator.naturalOrder())
                        .orElse(0L)
                        + 1L
                    );
                }
                todos.put(item.getId(), item);
            }

            @Override
            public boolean exists(long id) {
                return todos.containsKey(id);
            }

            @Override
            public void delete(long id) {
                todos.remove(id);
            }
        };
    }

}
