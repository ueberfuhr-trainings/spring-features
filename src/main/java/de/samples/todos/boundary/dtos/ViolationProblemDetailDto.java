package de.samples.todos.boundary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@Schema(name = "ViolationProblemDetail",
  description = "A RFC-7807 problem detail containing validation constraint violation details.")
@Getter
public class ViolationProblemDetailDto extends ProblemDetail {

    @Schema(name = "ViolationDescription",
      description = "A description of a single constraint violation.")
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ViolationDescriptionDto {

        @Schema(description = "The path of the payload that was invalid.")
        private final String path;
        @Schema(description = "The invalid value.")
        private final Object value;
        @Schema(description = "The violation message.")
        private final String message;

        public static ViolationDescriptionDto forViolation(ConstraintViolation<?> violation) {
            return new ViolationDescriptionDto(
              violation.getPropertyPath().toString(),
              violation.getInvalidValue(),
              violation.getMessage()
            );
        }

    }

    @Schema(description = "The constraint violation details.")
    private final ViolationDescriptionDto[] violations;

    protected ViolationProblemDetailDto(int rawStatusCode, ViolationDescriptionDto... violations) {
        super(rawStatusCode);
        this.violations = violations;
    }

    public static ViolationProblemDetailDto forStatusAndDescriptions(
      HttpStatus status,
      ViolationDescriptionDto... violations
    ) {
        return new ViolationProblemDetailDto(status.value(), violations);
    }

}
