package de.samples.todos.shared.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Maps a pageable object by copying all pageable fields,
 * but allows renaming properties
 */
@Component
public class PageableMapper {

    /**
     * Maps the pageable by replacing property names. Use this to avoid passing through
     * boundary names to the persistence layer.
     * @param source the incoming pageable
     * @param propertyNameMapper a mapper that maps the incoming property names to outgoing ones
     *                           (or empty optional, if the property is not allowed)
     * @return the mapped pageable
     * @throws UnknownPropertyException if the pageable contains a property name that does not exist
     */
    public Pageable map(Pageable source, Function<String, Optional<String>> propertyNameMapper) throws UnknownPropertyException {
        return null == source ? null : PageRequest.of(
          source.getPageNumber(),
          source.getPageSize(),
          mapSort(source.getSort(), propertyNameMapper)
        );
    }

    private Sort mapSort(Sort sort, Function<String, Optional<String>> propertyNameMapper) {
        return sort == null || sort.isUnsorted() ? sort : Sort.by(
          sort.stream()
            .map(
              order -> Sort.Order
                .by(
                  propertyNameMapper.apply(order.getProperty())
                    .orElseThrow(() -> new UnknownPropertyException(order.getProperty()))
                )
                .with(order.getDirection())
            )
            .collect(Collectors.toList())
        );
    }

}
