package de.samples.todos.domain;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.stream.Stream;

@Validated
@Service
@RequiredArgsConstructor
public class TodosService {

    private final ApplicationEventPublisher eventPublisher;
    private final TodosSink sink;

    long getCount() {
        return sink.getCount();
    }

    public Stream<Todo> findAll() {
        return sink.findAll();
    }

    public Optional<Todo> findById(long id) {
        return sink.findById(id);
    }

    public void insert(@Valid Todo item) {
        item.setId(null);
        sink.save(item);
        eventPublisher.publishEvent(new TodoChangedEvent(item, TodoChangedEvent.ChangeType.CREATED));
    }

    public void replace(@Valid Todo item) {
        final var id = item.getId();
        this.checkExistingId(id);
        sink.save(item);
        eventPublisher.publishEvent(new TodoChangedEvent(item, TodoChangedEvent.ChangeType.REPLACED));
    }

    public void delete(long id) {
        this.checkExistingId(id);
        final var item = this.findById(id)
          .orElseThrow(NotFoundException::new);
        sink.delete(id);
        eventPublisher.publishEvent(new TodoChangedEvent(item, TodoChangedEvent.ChangeType.REMOVED));
    }

    private void checkExistingId(long id) {
        if (!sink.exists(id)) {
            throw new NotFoundException();
        }
    }

}
