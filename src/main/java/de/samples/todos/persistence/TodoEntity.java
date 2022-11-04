package de.samples.todos.persistence;

import de.samples.todos.domain.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "Todo")
@Table(name = "todos")
public @Data class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Enumerated
    private Todo.TodoStatus status = Todo.TodoStatus.NEW;

}
