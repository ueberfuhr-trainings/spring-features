# Spring Features

[![CI Build](https://github.com/ueberfuhr-trainings/spring-features/actions/workflows/ci.yml/badge.svg)](https://github.com/ueberfuhr-trainings/spring-features/actions/workflows/ci.yml)

## About this repository

This repository contains a Spring Boot application that demonstrates the most important features.

 - Spring MVC [REST Controller](src/main/java/de/samples/todos/boundary/TodosController.java)
   incl. OpenAPI descriptions
   (run the app and test it with [Swagger-UI](http://localhost:9080/swagger-ui.html))
 - [MapStruct-Mappers](src/main/java/de/samples/todos/boundary/TodoDtoMapper.java) (out of Spring's scope)
 - Bean Validation in Controller and Service
 - [Global Exception Handling](src/main/java/de/samples/todos/boundary/GlobalExceptionHandler.java)
   incl. RFC-7807 Problem Details
 - [JPA Repository](src/main/java/de/samples/todos/persistence/TodosRepository.java)
 - Event [Broadcasting](src/main/java/de/samples/todos/domain/TodosService.java)
   and [Handling](src/main/java/de/samples/todos/domain/TodoChangedEventLogger.java)
   incl. [Test](src/test/java/de/samples/todos/domain/TodosChangedEventPublishingTest.java)
 - Advanced Bean Configuration, e.g.
   - Using [`@ConditionalOnMissingBean`](src/main/java/de/samples/todos/domain/TodosSinkInMemoryImpl.java)
   - Using [Profiles](src/main/java/de/samples/todos/boundary/HelloWorldController.java)
   - Using [YAML-Extension](src/main/java/de/samples/todos/shared/config/ApplicationConfiguration.java)
 - [Aspect](src/main/java/de/samples/todos/shared/aspects/LogOnInvocationAspect.java)
   for automatic logging on method invocation
   ([usage1](src/main/java/de/samples/todos/persistence/TodosSinkJpaImpl.java),
    [usage2](src/main/java/de/samples/todos/domain/TodosSinkInMemoryImpl.java))
 - [Actuator Health and Info Extensions](src/main/java/de/samples/todos/boundary/config/ActuatorConfiguration.java)
   - Run the app and test it with [Swagger-UI](http://localhost:9080/swagger-ui.html)
   - Invoke the database health extension directly
     ([built-in](http://localhost:9080/actuator/health/db),
      [custom](http://localhost:9080/actuator/health/databaseQueryWorks))
 - Spring's Native Support (see separate [documentation](docs/native/index.md))
 - Spring Security
   - We need to run the app with the `security` profile (e.g. with parameter `-Dspring.profiles.active=security`) to enable
     security.
   - There are 2 user accounts in-memory with a simple password and role:
     - `user` / `password` (role `USER`)
     - `admin` / `password` (role `ADMIN`)
   - We'll detect a `/api/v1/user` REST resource that we can call. **Only the `USER` role is allowed to run in - not the `ADMIN` role.**
   - Only the `ADMIN` role is allowed to delete a todo.

## Run the app
To directly run the app from Maven, use

```bash
mvn spring-boot:run
```

To build and run the app, use

```bash
mvn clean package
java -jar target/spring-features-0.0.1-SNAPSHOT.jar
```

## Sample Data Intialization

There's a Spring Boot Profile that leads to automatically creating sample data on startup.
Just run the application additionally with the parameter

```bash
-Dspring.profiles.active=dev
```

## Use the app

Open `http://localhost:9080/` in the browser.
