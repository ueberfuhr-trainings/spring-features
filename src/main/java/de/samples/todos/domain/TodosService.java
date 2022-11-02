package de.samples.todos.domain;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Validated
@Service
@RequiredArgsConstructor
public class TodosService {

    private final ApplicationEventPublisher eventPublisher;
    private final Map<Long, Todo> todos = new TreeMap<>();

    long getCount() {
        return todos.size();
    }

    public Collection<Todo> findAll() {
        return Collections.unmodifiableCollection(todos.values());
    }

    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(todos.get(id));
    }

    public void insert(@Valid Todo item) {
        final var newId = todos.keySet().stream()
          .max(Comparator.naturalOrder())
          .orElse(0L)
          + 1L;
        // wird später wieder ersetzt
        item.setId(newId);
        todos.put(newId, item);
        eventPublisher.publishEvent(new TodoChangedEvent(item, TodoChangedEvent.ChangeType.CREATED));
    }

    public void replace(@Valid Todo item) {
        final var id = item.getId();
        this.checkExistingId(id);
        todos.put(id, item);
        eventPublisher.publishEvent(new TodoChangedEvent(item, TodoChangedEvent.ChangeType.REPLACED));
    }

    public void delete(long id) {
        this.checkExistingId(id);
        final var item = todos.remove(id);
        eventPublisher.publishEvent(new TodoChangedEvent(item, TodoChangedEvent.ChangeType.REMOVED));
    }

    private void checkExistingId(long id) {
        if (!todos.containsKey(id)) {
            throw new NotFoundException();
        }
    }

}
