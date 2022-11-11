package de.samples.todos.boundary.graphql;

import de.samples.todos.boundary.graphql.types.QlCreateTodoInput;
import de.samples.todos.boundary.graphql.types.QlTodo;
import de.samples.todos.boundary.graphql.types.QlTodoStatus;
import de.samples.todos.domain.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QlTypesMapper {

    @Mapping(target = "assigneeId", constant = "1L")
    QlTodo map(Todo todo);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    Todo map(QlCreateTodoInput input);

    default QlTodoStatus mapStatus(Todo.TodoStatus status) {
        return null == status ? QlTodoStatus.NEW : switch (status) {
            case NEW -> QlTodoStatus.NEW;
            case PROGRESS -> QlTodoStatus.PROGRESS;
            case CANCELLED -> QlTodoStatus.CANCELLED;
            case COMPLETED -> QlTodoStatus.COMPLETED;
        };
    }



}
