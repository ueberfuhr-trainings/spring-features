package de.samples.todos.domain.pageable;

import de.samples.todos.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/*
 * This makes the service Spring-specific.
 */
@Service
@RequiredArgsConstructor
public class PageableTodosService {

    private final PageableTodosSink sink;

    public Page<Todo> findAll(Pageable params) {
        return sink.findAll(params);
    }

}
