package de.samples.todos.persistence.pageable;

import de.samples.todos.persistence.TodoEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PageableTodoEntityMapper {

    private static final Collection<String> ALLOWED_PROPERTIES = Set.of(
      "dueDate", "title", "description", "status", "id"
    );

    @Delegate
    private final TodoEntityMapper delegate;

    public Optional<String> mapPropertyNameToEntity(String property) {
        return Optional.ofNullable(ALLOWED_PROPERTIES.contains(property) ? property : null);
    }

}
