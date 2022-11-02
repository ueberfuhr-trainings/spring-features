package de.samples.todos.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
public @Data class Todo {

    /*
     * maybe, we could make this aJava record, but we have to check
     * the mapping to the HTTP response
     */

    public enum TodoStatus {
        NEW, PROGRESS, COMPLETED, CANCELLED
    }

    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    private LocalDate dueDate;
    @Builder.Default
    private TodoStatus status = TodoStatus.NEW;

}
