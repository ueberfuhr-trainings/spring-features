package de.samples.todos.boundary.graphql;

import de.samples.todos.boundary.graphql.fake_domain.AssigneeService;
import de.samples.todos.boundary.graphql.types.QlAssignee;
import de.samples.todos.domain.Todo;
import de.samples.todos.domain.TodosService;
import graphql.ErrorType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Samples: https://github.com/hantsy/spring-graphql-sample

@GraphQlTest(QlTodosController.class)
// we need to register the mapper, the exception handler, the Date scalar...
@ComponentScan(basePackageClasses = QlTodosController.class)
public class QlTodosControllerTest {

    private static final String QUERY_FIND_BY_ID_WITH_ASSIGNEE = """
      query($id: ID!) {
          findById(id: $id){
              id,
              title,
              assignee {
                  id
                  name
              }
          }
      }
      """;
    private static final String QUERY_FIND_BY_ID_WITHOUT_ASSIGNEE = """
      query($id: ID!) {
          findById(id: $id){
              id,
              title
          }
      }
      """;

    private static final String MUTATION_CREATE_TODO_TITLE_ONLY = """
      mutation ($title: String!) {
          createTodo(input: {title: $title}) {
              id
          }
      }
      """;

    @Autowired
    GraphQlTester graphQl;
    @MockBean
    TodosService service;
    @MockBean
    AssigneeService assigneeService;

    @Test
    void shouldReturnTodoWithAssignee() {
        final var id = 5L;
        Todo todo = Todo.builder().id(5L).title("test").build();
        when(service.findById(5L)).thenReturn(Optional.of(todo));
        QlAssignee assignee = new QlAssignee();
        assignee.setId(1L);
        assignee.setName("test-assignee");
        when(assigneeService.findAssignee(any())).thenReturn(assignee);
        // test
        graphQl
          .document(QUERY_FIND_BY_ID_WITH_ASSIGNEE)
          .variable("id", id)
          .execute()
          .path("$.data.findById.assignee")
          .entity(QlAssignee.class)
          .isEqualTo(assignee);

    }

    @Test
    void shouldNotInvokeAssigneeServiceWhenQueryNotIncludingAssignee() {
        final var id = 5L;
        Todo todo = Todo.builder().id(5L).title("test").build();
        when(service.findById(5L)).thenReturn(Optional.of(todo));
        // test
        graphQl
          .document(QUERY_FIND_BY_ID_WITHOUT_ASSIGNEE)
          .variable("id", id)
          .execute()
          .path("$.data.findById.title")
          .entity(String.class)
          .isEqualTo(todo.getTitle());
        verify(assigneeService, never()).findAssignee(any());
    }

    @Test
    void shouldReturnValidationErrorWhenCreateTodoWithShortTitle() {
        graphQl
          .document(MUTATION_CREATE_TODO_TITLE_ONLY)
          .variable("title", "x") // too short
          .execute()
          .errors()
          .expect(err -> err.getErrorType() == ErrorType.ValidationError)
          .verify();
        verify(service, never()).insert(any());
    }

}
