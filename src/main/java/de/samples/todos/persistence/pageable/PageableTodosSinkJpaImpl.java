package de.samples.todos.persistence.pageable;

import de.samples.todos.domain.Todo;
import de.samples.todos.domain.pageable.PageableTodosSink;
import de.samples.todos.persistence.TodosRepository;
import de.samples.todos.shared.aspects.LogOnInvocation;
import de.samples.todos.shared.pageable.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@LogOnInvocation("Using JPA Implementation 👍")
public class PageableTodosSinkJpaImpl implements PageableTodosSink {

    private final TodosRepository repo;
    private final PageableTodoEntityMapper mapper;
    private final PageableMapper pageableMapper;

    @Override
    public Page<Todo> findAll(Pageable params) {
        return repo
          .findAll(pageableMapper.map(params, mapper::mapPropertyNameToEntity))
          .map(mapper::map);
    }
}
