package de.samples.todos.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public @Data class TodoDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    private LocalDate dueDate;
    @JsonProperty(defaultValue = "new")
    private String status;

}
