package de.samples.todos.persistence;

import de.samples.todos.domain.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TodoEntityMapper {

    Todo map(TodoEntity entity);

    TodoEntity map(Todo todo);

    void map(TodoEntity source, @MappingTarget Todo target);

}
