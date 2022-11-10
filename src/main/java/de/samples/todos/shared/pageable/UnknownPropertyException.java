package de.samples.todos.shared.pageable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnknownPropertyException extends RuntimeException {

    @Getter
    private final String property;

}
