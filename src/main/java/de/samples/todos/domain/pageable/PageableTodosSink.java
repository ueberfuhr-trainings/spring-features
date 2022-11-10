package de.samples.todos.domain.pageable;

import de.samples.todos.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableTodosSink {

    Page<Todo> findAll(Pageable params);

}
