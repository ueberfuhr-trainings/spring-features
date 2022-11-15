package de.samples.todos.boundary.graphql;

import de.samples.todos.boundary.graphql.fake_domain.AssigneeService;
import de.samples.todos.boundary.graphql.types.QlAssignee;
import de.samples.todos.boundary.graphql.types.QlCreateTodoInput;
import de.samples.todos.boundary.graphql.types.QlTodo;
import de.samples.todos.domain.TodosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Optional;

// we could also implement this by using GraphQl

@Controller
@RequiredArgsConstructor
@Slf4j
public class QlTodosController {

    // custom mapper
    private final TodosService service;
    private final AssigneeService assigneeService;
    private final QlTypesMapper mapper;

    @QueryMapping("findTodos")
    public Collection<QlTodo> findAll() {
        return service.findAll()
          .map(mapper::map)
          .toList();
    }

    // lazy retrieving, if wanted
    @SuppressWarnings("unused") // invoked by GraphQL
    @SchemaMapping(typeName = "Todo", field = "assignee")
    public QlAssignee findAssignee(QlTodo todo) {
        return assigneeService.findAssignee(todo);
    }

    @SuppressWarnings("unused") // invoked by GraphQL
    @QueryMapping("findById")
    public Optional<QlTodo> findById(@Argument("id") long id) {
        return service.findById(id)
          .map(mapper::map);
    }

    @SuppressWarnings("unused") // invoked by GraphQL
    @MutationMapping("createTodo")
    public QlTodo create(@Valid @Argument("input") QlCreateTodoInput input) {
        final var newTodo = mapper.map(input);
        service.insert(newTodo);
        return mapper.map(newTodo);
    }

}
