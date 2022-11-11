package de.samples.todos.boundary.graphql.types;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Configuration
public class DateScalar {

    private static class DateCoercing implements Coercing<LocalDate, String> {

        @Override
        public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
            if (dataFetcherResult instanceof LocalDate date) {
                return date.toString(); // ISO-8601 format yyyy-MM-dd
            } else {
                throw new CoercingSerializeException("Expected a LocalDate object.");
            }
        }

        @Override
        public LocalDate parseValue(Object input) throws CoercingParseValueException {
            try {
                if (input instanceof String s) {
                    return LocalDate.parse(s);
                } else {
                    throw new CoercingParseValueException("Expected a String");
                }
            } catch (DateTimeParseException e) {
                throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e);
            }
        }

        @Override
        public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
            if (input instanceof StringValue s) {
                return parseValue(s.getValue());
            } else {
                throw new CoercingParseLiteralException("Expected a StringValue.");
            }
        }

    }

    @Bean
    RuntimeWiringConfigurer registerDateScalar() {
        return builder -> builder.scalar(
          GraphQLScalarType
            .newScalar()
            .name("Date")
            .description("Java LocalDate Scalar")
            .coercing(new DateCoercing())
            .build()
        );
    }
}
