# Spring Features

[![CI Build](https://github.com/ueberfuhr-trainings/spring-features/actions/workflows/ci.yml/badge.svg)](https://github.com/ueberfuhr-trainings/spring-features/actions/workflows/ci.yml)

## About this repository

This repository contains a Spring Boot application that demonstrates the most important features.

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
