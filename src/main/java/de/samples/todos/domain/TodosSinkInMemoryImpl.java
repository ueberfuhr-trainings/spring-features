package de.samples.todos.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/*
 * We need a factory here, because @ConditionalOnMissingBean only works with that!
 */

@Configuration
@Slf4j
public class TodosSinkInMemoryImpl {

    private void warn() {
        log.warn("Using In-Memory-Implementation. This is not scalable!");
    }

    @Bean
    @ConditionalOnMissingBean(TodosSink.class)
    TodosSink createSinkForThis() {
        return new TodosSink() {

            private final Map<Long, Todo> todos = new TreeMap<>();

            @Override
            public long getCount() {
                warn();
                return todos.size();
            }

            @Override
            public Collection<Todo> findAll() {
                warn();
                return Collections.unmodifiableCollection(todos.values());
            }

            @Override
            public Optional<Todo> findById(long id) {
                warn();
                return Optional.ofNullable(todos.get(id));
            }

            @Override
            public void save(Todo item) {
                warn();
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
                warn();
                return todos.containsKey(id);
            }

            @Override
            public void delete(long id) {
                warn();
                todos.remove(id);
            }
        };
    }

}
