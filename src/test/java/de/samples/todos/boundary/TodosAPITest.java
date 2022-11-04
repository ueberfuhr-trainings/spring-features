package de.samples.todos.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.samples.todos.boundary.dtos.TodoDto;
import de.samples.todos.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test the boundary concerning REST API behavior.
 */
@WebMvcTest
public class TodosAPITest {

    @MockBean
    TodosService service;
    @MockBean
    TodoDtoMapper mapper;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper jsonMapper;

    @Test
    void shouldReturnNoContentOnDelete() throws Exception {
        // Arrange
        // nothing to instrument
        // Act && Assert
        mvc.perform(delete("/api/v1/todos/10"))
          .andExpect(status().isNoContent());
        verify(service).delete(10);
    }

    @Test
    void shouldReturnUnprocessableContentOnPostInvalidData() throws Exception {
        // Arrange
        final var invalidTodo = new TodoDto();
        invalidTodo.setTitle("x"); // less than 3 chars
        final var json = this.jsonMapper.writeValueAsString(invalidTodo);
        // nothing to instrument
        // Act && Assert
        mvc
          .perform(
            post("/api/v1/todos")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json)
          )
          .andExpect(status().isUnprocessableEntity());
        verify(service, never()).insert(any());
    }

}
