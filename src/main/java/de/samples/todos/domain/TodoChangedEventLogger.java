package de.samples.todos.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class TodoChangedEventLogger {

    @EventListener(TodoChangedEvent.class)
    public void logEvent(TodoChangedEvent event) {
        log.info(event.toString());
    }

}
