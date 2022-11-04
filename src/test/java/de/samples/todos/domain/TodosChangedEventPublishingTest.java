package de.samples.todos.domain;

import de.samples.todos.config.EnableBeanValidation;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = { TodosService.class, EnableBeanValidation.class })
@RecordApplicationEvents
class TodosChangedEventPublishingTest {

    @MockBean
    TodosSink sink;
    @Autowired
    TodosService service;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ApplicationEvents events;

    @Test
    void shouldRecordEventOnCreateTodo() {
        // Arrange
        final var todo = Todo.builder()
          .title("test")
          .build();
        // Act && Assert
        service.insert(todo);
        assertThat(events.stream(TodoChangedEvent.class))
          .asList()
          .hasSize(1)
          .first()
          .isEqualTo(new TodoChangedEvent(todo, TodoChangedEvent.ChangeType.CREATED));
    }

    @Test
    void shouldNotRecordEventOnCreateInvalidTodo() {
        // Arrange
        final var todo = Todo.builder()
          .title("x") // less that 3 chars
          .build();
        // Act && Assert
        assertThatThrownBy(() -> service.insert(todo))
          .isInstanceOf(ValidationException.class);
        assertThat(events.stream(TodoChangedEvent.class))
          .isEmpty();
    }

}
