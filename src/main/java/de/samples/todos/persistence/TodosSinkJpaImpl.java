package de.samples.todos.persistence;

import de.samples.todos.domain.Todo;
import de.samples.todos.domain.TodosSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TodosSinkJpaImpl implements TodosSink {

    private final TodosRepository repo;
    private final TodoEntityMapper mapper;

    @Override
    public long getCount() {
        return repo.count();
    }

    @Override
    public Collection<Todo> findAll() {
        return repo.findAll()
          .stream()
          .map(mapper::map)
          .toList();
    }

    @Override
    public Optional<Todo> findById(long id) {
        return repo.findById(id)
          .map(mapper::map);
    }

    @Override
    public void save(Todo item) {
        final var entity = mapper.map(item);
        repo.save(entity);
        mapper.map(entity, item);
    }

    @Override
    public boolean exists(long id) {
        return repo.existsById(id);
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
