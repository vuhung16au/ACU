# Custom Stack Implementation with JUnit 5 (Aussie Edition)

This is a Maven project demonstrating JUnit 5 testing framework with a custom Stack data structure implementation. The project is designed to help students learn how to use JUnit for testing custom data structures, featuring Australian-themed data and examples.

## Project Structure

```
src/
├── main/java/com/acu/datastructure/stack/
│   ├── MyStack.java    # Custom Stack implementation
│   └── Main.java       # Demo application
└── test/java/com/acu/datastructure/stack/
    └── MyStackTest.java  # Comprehensive JUnit 5 test suite
```

## Features

- **Custom Stack Implementation**: A generic stack data structure with basic operations
- **JUnit 5 Tests**: Comprehensive test suite demonstrating various JUnit 5 features:
  - `@Test` annotations with `@DisplayName` for readable test names
  - `@BeforeEach` for test setup
  - `@Nested` classes for organizing tests by functionality
  - `@ParameterizedTest` with `@ValueSource` for data-driven tests
  - Exception testing with `assertThrows`
  - Various assertion methods (`assertEquals`, `assertTrue`, `assertFalse`, `assertNull`)

## Stack Operations

The `MyStack<E>` class provides the following operations:

- **`push(E element)`**: Adds an element to the top of the stack
- **`pop()`**: Removes and returns the element at the top of the stack
- **`peek()`**: Returns the element at the top without removing it
- **`isEmpty()`**: Checks if the stack is empty
- **`size()`**: Returns the number of elements in the stack
- **`clear()`**: Removes all elements from the stack

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
mvn exec:java -Dexec.mainClass="com.acu.datastructure.stack.Main"
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

### Phase 1: Setup and Initialization
- New stack creation and initialization
- Constructor validation (negative capacity)
- Default and custom capacity handling

### Phase 2: Basic Functionality
- Push operations and size increment
- Pop operations and size decrement
- Peek operations (without removal)
- Element ordering (LIFO - Last In, First Out)

### Phase 3: State Verification
- Stack emptiness after operations
- Size accuracy after multiple operations
- Mixed operation sequences
- LIFO behavior verification

### Phase 4: Edge Cases and Exceptions
- Empty stack operations (`pop`, `peek`)
- Null element handling
- Multiple operations on empty stack
- Exception throwing verification

### Additional Test Categories
- **Capacity and Performance**: Dynamic capacity expansion, clear operations
- **Generic Types**: Testing with different data types (Integer, Double, custom objects)
- **Parameterized Tests**: Data-driven testing with various inputs

## JUnit 5 Features Demonstrated

1. **Annotations**: `@Test`, `@DisplayName`, `@Nested`, `@BeforeEach`, `@ParameterizedTest`
2. **Assertions**: `assertEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertThrows`
3. **Parameterized Tests**: Using `@ParameterizedTest` and `@ValueSource`
4. **Test Organization**: Nested test classes for better structure and readability
5. **Exception Testing**: Verifying that exceptions are thrown correctly
6. **Setup Methods**: Using `@BeforeEach` for test initialization

## Package Structure

All classes use the package `com.acu.datastructure.stack` as specified in the project plan.

## Learning Objectives

This project helps students understand:

- How to structure unit tests for data structures
- Testing both "happy path" and edge cases
- Using JUnit 5 annotations effectively
- Writing comprehensive test suites
- Testing exception handling
- Organizing tests with nested classes
- Using parameterized tests for data-driven testing

## Example Usage

```java
// Create a new stack for Australian cities
MyStack<String> stack = new MyStack<>();

// Push Australian cities
stack.push("Sydney");
stack.push("Melbourne");
stack.push("Brisbane");

// Check size
System.out.println("Size: " + stack.size()); // Output: 3

// Peek at top element
System.out.println("Top: " + stack.peek()); // Output: Brisbane

// Pop elements (LIFO order)
System.out.println("Popped: " + stack.pop()); // Output: Brisbane
System.out.println("Popped: " + stack.pop()); // Output: Melbourne
System.out.println("Popped: " + stack.pop()); // Output: Sydney

// Check if empty
System.out.println("Empty: " + stack.isEmpty()); // Output: true
```

## Australian Data Examples

The project includes various Australian-themed data for testing:

- **Cities**: Sydney, Melbourne, Brisbane, Perth, Adelaide, Canberra, Darwin, Hobart, Gold Coast, Newcastle
- **States/Territories**: NSW, VIC, QLD, WA, SA, TAS, NT, ACT
- **Landmarks**: Sydney Opera House, Uluru, Great Barrier Reef, Bondi Beach, Twelve Apostles
- **Population Data**: Real population numbers for major Australian cities
- **Wildlife**: Kangaroo, Koala, Emu

## Contributing

This project is designed for educational purposes. Feel free to extend the implementation with additional features or test cases to further your understanding of JUnit testing and data structures. G'day mate! 🦘

