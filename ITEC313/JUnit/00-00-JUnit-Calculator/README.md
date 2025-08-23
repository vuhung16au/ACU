# JUnit 5 Calculator Example

This is a simple Maven project demonstrating JUnit 5 testing framework with a basic Calculator class.

## Project Structure

```
src/
├── main/java/com/acu/junit/
│   ├── Calculator.java    # Main calculator class with arithmetic operations
│   └── Main.java         # Demo application
└── test/java/com/acu/junit/
    └── CalculatorTests.java  # JUnit 5 test cases
```

## Features

- **Calculator Class**: Provides basic arithmetic operations (add, subtract, multiply, divide)
- **JUnit 5 Tests**: Comprehensive test suite demonstrating various JUnit 5 features:
  - `@Test` annotations
  - `@DisplayName` for readable test names
  - `@Nested` classes for organizing tests
  - `@BeforeEach` for test setup
  - `@ParameterizedTest` with `@CsvSource` for data-driven tests
  - Exception testing with `assertThrows`
  - Various assertion methods

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Building and Running

### Clean and Build
```bash
mvn clean compile
```

### Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.acu.junit.Main"
```

### Run Tests
```bash
mvn test
```

### Clean, Build, and Test (All-in-one)
```bash
mvn clean test
```

## Test Coverage

The test suite covers:
- Basic arithmetic operations with positive and negative numbers
- Edge cases (zero, maximum/minimum integer values)
- Exception handling (division by zero)
- Parameterized tests with multiple input combinations

## JUnit 5 Features Demonstrated

1. **Annotations**: `@Test`, `@DisplayName`, `@Nested`, `@BeforeEach`
2. **Assertions**: `assertEquals`, `assertNotNull`, `assertThrows`
3. **Parameterized Tests**: Using `@ParameterizedTest` and `@CsvSource`
4. **Test Organization**: Nested test classes for better structure
5. **Exception Testing**: Verifying that exceptions are thrown correctly

## Package Structure

All classes use the package `com.acu.junit` as specified in the requirements.
