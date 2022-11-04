package de.samples.todos.domain;

import java.util.Collection;
import java.util.Optional;

public interface TodosSink {
    long getCount();

    Collection<Todo> findAll();

    Optional<Todo> findById(long id);

    void save(Todo item);

    boolean exists(long id);

    void delete(long id);
}
