package de.samples.todos.boundary.pageable;

import de.samples.todos.boundary.TodoDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PageableTodoDtoMapper {

    private static final Collection<String> ALLOWED_PROPERTIES = Set.of(
      "due_date", "title", "description", "status", "id"
    );

    @Delegate
    private final TodoDtoMapper delegate;

    public Optional<String> mapPropertyNameFromDto(String property) {
        if (ALLOWED_PROPERTIES.contains(property)) {
            return Optional.of(
              switch (property) {
                  case "due_date" -> "dueDate";
                  default -> property;
              }
            );
        } else {
            return Optional.empty();
        }
    }

}
