# Spring Boot Hello World

A simple Spring Boot application demonstrating a basic REST endpoint.

## Quick Start

### Run the application
```bash
./gradlew bootRun
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
./gradlew build
```

## API

- **GET** `/hello`
  - Query parameter: `name` (optional, defaults to "World")
  - Returns: `Hello {name}!`

## Project Structure

- `src/main/java/com/acu/hellospring/HellospringApplication.java` - Main application class with REST endpoint
- `src/test/java/com/acu/hellospring/HellospringApplicationTests.java` - Tests for the endpoint
- `build.gradle` - Gradle build configuration

## Requirements

- Java 21 (automatically provisioned by Gradle toolchain)
- Internet access for first build