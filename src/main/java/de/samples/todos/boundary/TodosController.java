package de.samples.todos.boundary;

import de.samples.todos.boundary.dtos.TodoDto;
import de.samples.todos.boundary.dtos.ViolationProblemDetailDto;
import de.samples.todos.domain.NotFoundException;
import de.samples.todos.domain.TodosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
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
@RequestMapping("/api/v1/todos")
@Tag(name = "todos", description = "Todo Management")
@RequiredArgsConstructor
public class TodosController {

    private final TodosService service;
    private final TodoDtoMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all todos.")
    @ApiResponse(
      responseCode = "200",
      description = "The todos were found and returned."
    )
    Collection<TodoDto> findAll() {
        return service.findAll()
          .stream()
          .map(mapper::map)
          .toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a single todo by id.")
    @ApiResponse(
      responseCode = "200",
      description = "The todo was found and returned."
    )
    @ApiResponse(
      responseCode = "404",
      description = "A todo with the given id could not be found.",
      content = @Content(schema = @Schema(implementation = ProblemDetail.class))
    )
    TodoDto findById(
      @Parameter(description = "The id of the todo.")
      @PathVariable("id")
      long id
    ) {
        return service.findById(id)
          .map(mapper::map)
          .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a single todo.")
    @ApiResponse(
      responseCode = "201",
      description = "The todo was created and returned.",
      headers = @Header(name = "Location", description = "The URL of the created todo.")
    )
    @ApiResponse(
      responseCode = "422",
      description = "The given entity was invalid.",
      content = @Content(schema = @Schema(implementation = ViolationProblemDetailDto.class))
    )
    ResponseEntity<TodoDto> create(
      @Valid
      @RequestBody
      TodoDto todo
    ) {
        final var newTodo = mapper.map(todo);
        service.insert(newTodo);
        final var locationHeader = linkTo(methodOn(TodosController.class)
          .findById(newTodo.getId())).toUri(); // HATEOAS
        return ResponseEntity.created(locationHeader).body(mapper.map(newTodo));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Replace a single todo.")
    @ApiResponse(responseCode = "204",
      description = "The todo was replaced."
    )
    @ApiResponse(responseCode = "422",
      description = "The given entity was invalid.",
      content = @Content(schema = @Schema(implementation = ViolationProblemDetailDto.class)))
    @ApiResponse(responseCode = "404",
      description = "A todo with the given id could not be found.",
      content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    void replace(
      @Parameter(description = "The id of the todo.")
      @PathVariable("id")
      long id,
      @Valid
      @RequestBody
      TodoDto todo
    ) {
        todo.setId(id);
        service.replace(mapper.map(todo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a single todo by id.")
    @ApiResponse(responseCode = "204",
      description = "The todo was deleted.")
    @ApiResponse(responseCode = "404",
      description = "A todo with the given id could not be found.",
      content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    void delete(
      @Parameter(description = "The id of the todo.")
      @PathVariable("id")
      long id
    ) {
        service.delete(id);
    }

}
