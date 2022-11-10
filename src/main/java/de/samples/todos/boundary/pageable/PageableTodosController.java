package de.samples.todos.boundary.pageable;

import de.samples.todos.boundary.dtos.TodoDto;
import de.samples.todos.domain.pageable.PageableTodosService;
import de.samples.todos.shared.pageable.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * This is the official Spring supported way.
 * This will lead to an OpenAPI documentation that is vendor-specific (and does not look good).
 * Maybe, the boundary should declare a custom type for that?
 * Also, we have to check the order property names (otherwise, we'll get a 500 because of a database mapping error)
 */

@RestController
@RequestMapping("/api/v1/pageable/todos")
@Tag(name = "todos")
@RequiredArgsConstructor
public class PageableTodosController {

    private final PageableTodosService service;
    private final PageableTodoDtoMapper mapper;
    private final PageableMapper pageableMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all todos the pageable way.")
    @ApiResponse(
      responseCode = "200",
      description = "The todos were found and returned."
    )
    Page<TodoDto> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return service
          .findAll(pageableMapper.map(pageable, mapper::mapPropertyNameFromDto))
          .map(mapper::map);
    }

}
