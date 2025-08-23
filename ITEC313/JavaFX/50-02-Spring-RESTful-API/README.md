# Spring Boot Hello World

A simple Spring Boot application demonstrating a basic REST endpoint.

## Quick Start

### Run the application
```bash
mvn spring-boot:run
```

### Test the endpoint
```bash
curl http://localhost:8080/hello
# Returns: Hello World!

curl "http://localhost:8080/hello?name=Spring"
# Returns: Hello Spring!
```

### Build
```bash
mvn clean compile
```

### Package
```bash
mvn clean package
```

## API

- **GET** `/hello`
  - Query parameter: `name` (optional, defaults to "World")
  - Returns: `Hello {name}!`

## Project Structure

- `src/main/java/com/acu/hellospring/HellospringApplication.java` - Main application class with REST endpoint
- `src/test/java/com/acu/hellospring/HellospringApplicationTests.java` - Tests for the endpoint
- `pom.xml` - Maven build configuration

## Requirements

- Java 21
- Maven 3.6+
- Internet access for first build

