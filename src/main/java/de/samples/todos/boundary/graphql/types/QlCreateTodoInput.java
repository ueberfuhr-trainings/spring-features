package de.samples.todos.boundary.graphql.types;

import lombok.Data;

import java.time.LocalDate;

public @Data class QlCreateTodoInput {

    private String title;
    private String description;
    private LocalDate dueDate;

}
