package de.samples.todos.boundary.graphql.types;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public @Data class QlCreateTodoInput {

    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    private LocalDate dueDate;

}
