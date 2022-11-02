package de.samples.todos.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TodosInitializer {

    private final TodosService service;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeTodos() {
        if(this.service.getCount()<1) {
            Stream.of(
                Todo.builder()
                  .title("Staubsaugen")
                  .build(),
                Todo.builder()
                  .title("Aufräumen")
                  .dueDate(LocalDate.now().plusDays(14))
                  .build(),
                Todo.builder()
                  .title("Spring Boot lernen")
                  .status(Todo.TodoStatus.PROGRESS)
                  .build()
              )
              .forEach(this.service::insert);
        }
    }
}
