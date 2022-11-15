package de.samples.todos.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface TodosSink {
    long getCount();

    Stream<Todo> findAll();

    Optional<Todo> findById(long id);

    void save(Todo item);

    boolean exists(long id);

    void delete(long id);
}
