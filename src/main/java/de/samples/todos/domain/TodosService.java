package de.samples.todos.domain;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

@Service
public class TodosService {

    private final Map<Long, Todo> todos = new TreeMap<>();

    // TODO replace later
    @PostConstruct
    public void initialize() {
        Stream.of(
            Todo.builder()
              .title("Staubsaugen")
              .build(),
            Todo.builder()
              .title("Aufräumen")
              .dueDate(LocalDate.now().plusDays(14))
              .build(),
            Todo.builder()
              .title("Spring Boot lernen")
              .status(Todo.TodoStatus.PROGRESS)
              .build()
          )
          .forEach(this::insert);
    }

    public Collection<Todo> findAll() {
        return Collections.unmodifiableCollection(todos.values());
    }

    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(todos.get(id));
    }

    public void insert(Todo item) {
        final var newId = todos.keySet().stream()
          .max(Comparator.naturalOrder())
          .orElse(0L)
          + 1L;
        // wird später wieder ersetzt
        item.setId(newId);
        todos.put(newId, item);
    }

    public void replace(Todo item) {
        todos.put(item.getId(), item);
    }

    public void delete(long id) {
        todos.remove(id);
    }

}
