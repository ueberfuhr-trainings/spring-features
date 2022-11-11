package de.samples.todos.boundary.graphql.fake_domain;

import de.samples.todos.boundary.graphql.types.QlAssignee;
import de.samples.todos.boundary.graphql.types.QlTodo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
 * This service is used for testing purposes.
 */
@Service
@Slf4j
public class AssigneeService {

    // a real domain would return a domain Assignee
    public QlAssignee findAssignee(QlTodo todo) {
        log.info("Retrieving assignee for todo with id " + todo.getId());
        // we could make any database query here
        QlAssignee result = new QlAssignee();
        result.setId(todo.getAssigneeId());
        result.setName(String.format("Assignee for '%s'", todo.getTitle()));
        result.setDepartment("ACME Department");
        return result;
    }

}
