package de.samples.todos.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/todos")
public class TodosController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Todo> findAll() {
        return List.of(
          Todo.builder()
            .id(1L)
            .title("Staubsaugen")
            .build(),
          Todo.builder()
            .id(2L)
            .title("Aufräumen")
            .dueDate(LocalDate.now().plusDays(14))
            .build(),
          Todo.builder()
            .id(3L)
            .title("Spring Boot lernen")
            .status(Todo.TodoStatus.PROGRESS)
            .build()
        );
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Todo findById(@PathVariable("id") long id) {
        return Todo.builder()
          .id(id)
          .title("Datenhaltung implementieren")
          .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Todo> create(@RequestBody Todo todo) {
        todo.setId(4);
        final var locationHeader = linkTo(methodOn(TodosController.class)
          .findById(todo.getId())).toUri(); // HATEOAS
        return ResponseEntity.created(locationHeader).body(todo);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void replace(@PathVariable("id") long id, @RequestBody Todo todo) {
        // nothing to implement here
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        // nothing to implement here
    }

}
