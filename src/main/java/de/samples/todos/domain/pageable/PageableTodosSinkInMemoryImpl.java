package de.samples.todos.domain.pageable;

import de.samples.todos.domain.TodosSink;
import de.samples.todos.shared.aspects.LogOnInvocation;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

/*
 * We need a factory here, because @ConditionalOnMissingBean only works with that!
 */

@Configuration
@RequiredArgsConstructor
public class PageableTodosSinkInMemoryImpl {

    private final TodosSink sink;

    @Bean
    @ConditionalOnMissingBean(PageableTodosSink.class)
    @LogOnInvocation(value = "Using In-Memory-Implementation. This is not scalable!", level = Level.WARN)
    PageableTodosSink createSinkForThis() {
        // TODO ordering is not supported currently
        return params -> {
            final var all = sink.findAll();
            final var pageEntries = all.stream()
              .skip((long) params.getPageSize() * params.getPageNumber())
              .limit(params.getPageSize())
              .collect(Collectors.toList());
            return new PageImpl<>(pageEntries, params, all.size());
        };
    }

}
