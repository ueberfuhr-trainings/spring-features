package de.samples.todos.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloWorldController.class)
class HelloWorldApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldNotProvideHello() throws Exception {
        mvc.perform(get("/hello"))
          .andExpect(status().isNotFound());
    }

}
