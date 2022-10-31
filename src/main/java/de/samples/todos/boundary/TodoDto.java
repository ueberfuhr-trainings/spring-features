package de.samples.todos.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

public @Data class TodoDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    @JsonProperty(defaultValue = "new")
    private String status;

}
