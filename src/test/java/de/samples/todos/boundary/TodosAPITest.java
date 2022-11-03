package de.samples.todos.boundary;

import de.samples.todos.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    void shouldReturnNoContentOnDelete() throws Exception {
        // Arrange
        // nothing to instrument
        // Act && Assert
        mvc.perform(delete("/api/v1/todos/10"))
            .andExpect(status().isNoContent());
        Mockito.verify(service).delete(10);
    }

}
