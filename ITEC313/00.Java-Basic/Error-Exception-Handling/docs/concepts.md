# Java Exception Handling Concepts

## Overview
Exception handling in Java is a mechanism to handle runtime errors and exceptional conditions that may occur during program execution. Unlike Python, Java has a more structured approach with checked and unchecked exceptions.

## Key Concepts

### 1. Exception Hierarchy
Java has a well-defined exception hierarchy:
- `Throwable` (root class)
  - `Error` (unchecked - system errors)
  - `Exception` (checked - application errors)
    - `RuntimeException` (unchecked)
    - `IOException` (checked)
    - Custom exceptions

### 2. Checked vs Unchecked Exceptions

#### Checked Exceptions
- Must be declared or caught
- Extend `Exception` (not `RuntimeException`)
- Examples: `IOException`, `FileNotFoundException`, `SQLException`

#### Unchecked Exceptions
- Don't need to be declared or caught
- Extend `RuntimeException` or `Error`
- Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `ArithmeticException`

### 3. Try-Catch-Finally Structure

```java
try {
    // Code that might throw an exception
} catch (SpecificException e) {
    // Handle specific exception
} catch (Exception e) {
    // Handle general exception
} finally {
    // Always executed (cleanup code)
}
```

### 4. Exception Handling Patterns

#### Basic Pattern
```java
try {
    // Risky operation
} catch (Exception e) {
    // Handle exception
}
```

#### Multiple Catch Blocks
```java
try {
    // Multiple operations
} catch (IOException e) {
    // Handle IO exception
} catch (NumberFormatException e) {
    // Handle number format exception
} catch (Exception e) {
    // Handle any other exception
}
```

#### Try-With-Resources (Java 7+)
```java
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    // Use the resource
} catch (IOException e) {
    // Handle exception
}
// Resource automatically closed
```

### 5. Custom Exceptions

#### Creating Custom Exceptions
```java
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
    
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

#### Throwing Custom Exceptions
```java
public void validateAge(int age) throws InvalidAgeException {
    if (age < 0) {
        throw new InvalidAgeException("Age cannot be negative: " + age);
    }
}
```

### 6. Exception Chaining
Java supports exception chaining to preserve the original exception:
```java
try {
    // Low-level operation
} catch (IOException e) {
    throw new CustomException("High-level error", e);
}
```

### 7. Best Practices

1. **Catch Specific Exceptions First**: More specific exceptions should be caught before general ones
2. **Use Finally for Cleanup**: Always use finally blocks for resource cleanup
3. **Don't Swallow Exceptions**: Avoid empty catch blocks
4. **Use Try-With-Resources**: For resource management
5. **Provide Meaningful Messages**: Include context in exception messages
6. **Log Exceptions**: Use proper logging for debugging

### 8. Common Exception Types

- `ArithmeticException`: Division by zero
- `ArrayIndexOutOfBoundsException`: Invalid array index
- `NullPointerException`: Accessing null reference
- `NumberFormatException`: Invalid number format
- `FileNotFoundException`: File not found
- `IOException`: General IO errors
- `ClassNotFoundException`: Class not found
- `InterruptedException`: Thread interruption

### 9. Exception Handling in Collections

```java
List<String> data = Arrays.asList("123", "abc", "456");
List<Integer> results = new ArrayList<>();

for (String item : data) {
    try {
        int number = Integer.parseInt(item);
        results.add(number);
    } catch (NumberFormatException e) {
        // Handle invalid number format
    }
}
```

### 10. Resource Management

#### Traditional Approach
```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("file.txt"));
    // Use reader
} catch (IOException e) {
    // Handle exception
} finally {
    if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            // Handle close exception
        }
    }
}
```

#### Try-With-Resources Approach
```java
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    // Use reader
} catch (IOException e) {
    // Handle exception
}
// Reader automatically closed
```

## Python vs Java Comparison

| Feature | Python | Java |
|---------|--------|------|
| Basic Structure | `try/except/finally` | `try/catch/finally` |
| Exception Types | All exceptions are unchecked | Checked vs unchecked |
| Declaration | No declaration needed | Must declare checked exceptions |
| Context Managers | `with` statement | `try-with-resources` |
| Exception Chaining | `raise NewException() from old` | `throw new Exception(msg, cause)` |
| Multiple Exceptions | `except (Type1, Type2):` | Multiple catch blocks |
| Custom Exceptions | `class CustomException(Exception):` | `class CustomException extends Exception` |

## Learning Objectives

1. Understand the difference between checked and unchecked exceptions
2. Master try-catch-finally syntax and usage
3. Learn to create and use custom exceptions
4. Understand exception chaining and propagation
5. Master resource management with try-with-resources
6. Learn best practices for exception handling
7. Understand exception hierarchy and inheritance
8. Practice exception handling in real-world scenarios
