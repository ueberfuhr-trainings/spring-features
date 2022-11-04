package de.samples.todos.boundary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Schema(name = "Todo", description = "A todo that is managed by the service.")
public @Data class TodoDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "The unique key.")
    private Long id;
    @NotNull
    @Size(min = 3, max=100)
    @Schema(description = "The short title.")
    private String title;
    @Size(max=255)
    @Schema(description = "The longer description.")
    private String description;
    @Schema(description = "The date when the todo has to be completed.")
    private LocalDate dueDate;
    @JsonProperty(defaultValue = "new")
    @Pattern(regexp = "new|progress|completed|archived")
    @Schema(description = "The status of the todo.")
    private String status;

}
