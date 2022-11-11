package de.samples.todos.boundary.graphql.types;

import lombok.Data;

import java.time.LocalDate;

public @Data class QlTodo {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private QlTodoStatus status;
    // no assignee here to allow lazy retrieving!
    private Long assigneeId; // simulate foreign key

}
