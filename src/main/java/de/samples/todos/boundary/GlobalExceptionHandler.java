package de.samples.todos.boundary;

import de.samples.todos.boundary.dtos.ViolationProblemDetailDto;
import de.samples.todos.boundary.dtos.ViolationProblemDetailDto.ViolationDescriptionDto;
import de.samples.todos.domain.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static ConstraintViolation<?> map(ObjectError error) {
        try {
            return error.unwrap(ConstraintViolation.class);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Exception thrown by controller validation.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
          .unprocessableEntity()
          .body(
            ViolationProblemDetailDto.forStatusAndDescriptions(
              UNPROCESSABLE_ENTITY,
              ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(GlobalExceptionHandler::map)
                .filter(Objects::nonNull)
                .map(ViolationDescriptionDto::forViolation)
                .toArray(ViolationDescriptionDto[]::new)
            )
          );
    }

    // Exception thrown by service (or any other) validation.
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = UNPROCESSABLE_ENTITY)
    protected ProblemDetail handleValidationException() {
        return ProblemDetail.forStatus(UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = UNPROCESSABLE_ENTITY)
    protected ViolationProblemDetailDto handleValidationException(ConstraintViolationException ex) {
        return ViolationProblemDetailDto.forStatusAndDescriptions(
          UNPROCESSABLE_ENTITY,
          ex.getConstraintViolations()
            .stream()
            .map(ViolationDescriptionDto::forViolation)
            .toArray(ViolationDescriptionDto[]::new)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = NOT_FOUND)
    @ResponseBody
    protected ProblemDetail handleNotFoundException() {
        return ProblemDetail.forStatus(NOT_FOUND);
    }

}
