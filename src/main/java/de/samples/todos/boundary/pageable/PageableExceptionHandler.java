package de.samples.todos.boundary.pageable;

import de.samples.todos.shared.pageable.UnknownPropertyException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class PageableExceptionHandler {

    @ExceptionHandler(UnknownPropertyException.class)
    @ResponseStatus(code = BAD_REQUEST)
    ProblemDetail handleUnknownPropertyException(UnknownPropertyException ex) {
        final var result = ProblemDetail.forStatus(BAD_REQUEST);
        result.setDetail(String.format("Unknown property: '%s'", ex.getProperty()));
        return result;
    }

}

