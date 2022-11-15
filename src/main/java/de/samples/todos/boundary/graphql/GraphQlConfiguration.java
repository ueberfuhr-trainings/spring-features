package de.samples.todos.boundary.graphql;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(GraphQlConfiguration.GraphQlRuntimeHints.class)
@Configuration
public class GraphQlConfiguration {

    //    @Value(value="classpath:graphiql/index.html", )
    //    Resource graphiqlWelcomePage;

    static class GraphQlRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources()
              .registerPattern("graphql/**/")
              .registerPattern("graphiql/index.html");
        }
    }

}
