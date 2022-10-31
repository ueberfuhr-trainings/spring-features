package de.samples.todos.boundary;

import de.samples.todos.domain.Todo;
import de.samples.todos.domain.TodosService;
import lombok.RequiredArgsConstructor;
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

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/todos")
@RequiredArgsConstructor
public class TodosController {

    /*
     * TODO maybe, it is a good idea to have a custom TodoDto type in the boundary
     *  to customize HTTP mappings
     */

    private final TodosService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Todo> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Todo findById(@PathVariable("id") long id) {
        return service.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Todo> create(@RequestBody Todo todo) {
        service.insert(todo);
        final var locationHeader = linkTo(methodOn(TodosController.class)
          .findById(todo.getId())).toUri(); // HATEOAS
        return ResponseEntity.created(locationHeader).body(todo);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void replace(@PathVariable("id") long id, @RequestBody Todo todo) {
        todo.setId(id);
        service.replace(todo);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

}
