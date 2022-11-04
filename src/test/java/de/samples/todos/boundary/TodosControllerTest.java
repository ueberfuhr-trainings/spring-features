package de.samples.todos.boundary;

import de.samples.todos.boundary.dtos.TodoDto;
import de.samples.todos.domain.Todo;
import de.samples.todos.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * This is a simple test of the controller using
 *  - JUnit 5
 *  - Mockito
 *  - AssertJ
 */
@ExtendWith(MockitoExtension.class)
class TodosControllerTest {

    @Mock
    TodosService service;
    @Mock
    TodoDtoMapper mapper;
    @InjectMocks
    TodosController controller;

    @Test
    void shouldMapAndReturnTodos() {
        // Arrange
        final var todo = Todo.builder().id(1L).title("test").build();
        final var dto = new TodoDto();
        dto.setId(1L);
        dto.setTitle("test");
        when(service.findAll()).thenReturn(List.of(todo));
        when(mapper.map(todo)).thenReturn(dto);
        // Act
        Collection<TodoDto> result = controller.findAll();
        // Assert
        assertThat(result).containsExactly(dto);
        verify(mapper).map(todo);
    }

}
