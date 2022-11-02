package de.samples.todos.boundary;

import de.samples.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoDtoMapper {

    TodoDto map(Todo todo);
    Todo map(TodoDto todo);

    default String mapStatus(Todo.TodoStatus status) {
        return null == status ? null : switch (status) {
            case NEW -> "new";
            case PROGRESS -> "in_progress";
            case CANCELLED -> "cancelled";
            case COMPLETED -> "completed";
        };
    }

    default Todo.TodoStatus mapStatus(String status) {
        return null == status ? null : switch (status) {
            case "new" -> Todo.TodoStatus.NEW;
            case "in_progress" -> Todo.TodoStatus.PROGRESS;
            case "cancelled" -> Todo.TodoStatus.CANCELLED;
            case "completed" -> Todo.TodoStatus.COMPLETED;
            default -> null;
        };
    }

}
