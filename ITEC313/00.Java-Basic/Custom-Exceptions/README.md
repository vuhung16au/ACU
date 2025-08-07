# Custom-Exceptions

Creating and using custom exception classes in Java

## 📋 Overview

This project demonstrates how to create and use custom exceptions in Java. Custom exceptions allow you to create specific error types for your application, providing better error handling, debugging, and code organization compared to using generic exceptions.

The project covers fundamental concepts like checked vs unchecked exceptions, exception chaining, and advanced patterns like exception factories and builders. It's designed for Python developers transitioning to Java who need to understand Java's exception handling paradigm.

## 📁 Files in this Directory

```
Custom-Exceptions/
├── CustomExceptions.java    # Main source code with comprehensive examples
├── Makefile               # Build automation
├── README.md              # This documentation
├── examples/              # Additional example files
│   ├── Example1.java     # Basic custom exception examples
│   ├── Example2.java     # Advanced exception hierarchy examples
│   └── Advanced.java     # Complex exception patterns and factories
├── data/                 # Sample data files
│   ├── input.txt         # Test data for exception scenarios
│   └── sample.csv        # Structured test data
├── docs/                 # Additional documentation
│   └── concepts.md       # Detailed concept explanations
└── tests/                # (Removed - no test files per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the main program
make run

# Clean compiled files
make clean

# Show available targets
make help
```

**Expected Output:**
```
=== Custom Exceptions in Java ===
This program demonstrates custom exception creation and usage.
Key differences from Python:
- Custom exceptions must extend Exception or RuntimeException
- Checked exceptions must be declared with 'throws'
- Unchecked exceptions (RuntimeException) don't need declaration
- Exception chaining preserves original cause

=== Valid Cases ===
Age 25 is valid
Email user@example.com is valid
Order ORD-001 processed successfully: $100.5

=== Exception Handling Examples ===
Caught InvalidAgeException: Age cannot be negative: -5
Caught InvalidEmailException: Invalid email format: invalid-email
Business Logic Error: Order ID is required
Error Code: ORDER_001
Severity: 1
Chained Exception: Invalid age format: abc
Original Cause: For input string: "abc"
...
```

## 📚 Learning Objectives

This project teaches:

1. **Custom Exception Creation**
   - Creating custom exception classes extending Exception and RuntimeException
   - Understanding checked vs unchecked exceptions
   - Adding custom fields and methods to exceptions

2. **Exception Handling Patterns**
   - Try-catch-finally blocks
   - Multiple exception handling
   - Exception chaining and cause preservation
   - Resource management with try-with-resources

3. **Advanced Exception Concepts**
   - Exception hierarchies and inheritance
   - Exception factories for consistent creation
   - Exception builders for complex scenarios
   - Recovery strategies and error codes

4. **Best Practices**
   - When to use checked vs unchecked exceptions
   - Proper exception design and naming
   - Performance considerations
   - Debugging and logging strategies

## 🎯 Key Takeaways

### Main Concepts and Skills Gained

1. **Exception Design Principles**
   - Choose appropriate base class (Exception vs RuntimeException)
   - Include meaningful error messages and context
   - Design exception hierarchies for complex applications

2. **Exception Handling Strategies**
   - Catch specific exceptions before general ones
   - Use exception chaining to preserve original causes
   - Implement proper cleanup in finally blocks

3. **Advanced Patterns**
   - Exception factories for consistent error creation
   - Exception builders for complex error construction
   - Recovery strategies for different error types

4. **Java-Specific Concepts**
   - Checked exceptions must be declared with 'throws'
   - Unchecked exceptions (RuntimeException) don't need declaration
   - Exception chaining uses constructor with cause parameter

## 🔍 Important Concepts

### Checked vs Unchecked Exceptions

**Checked Exceptions (extends Exception):**
- Must be declared with `throws` or caught
- Represent recoverable conditions
- Examples: `IOException`, `SQLException`

**Unchecked Exceptions (extends RuntimeException):**
- Don't need to be declared
- Represent programming errors
- Examples: `NullPointerException`, `IllegalArgumentException`

### Exception Chaining

Java provides exception chaining to preserve the original cause:
```java
try {
    // Some operation
} catch (OriginalException e) {
    throw new CustomException("Custom message", e);
}
```

### Exception Hierarchies

Create organized exception hierarchies for complex applications:
```java
abstract class ApplicationException extends Exception {
    private String errorCode;
    // Common functionality
}

class UserException extends ApplicationException {
    private String userId;
    // User-specific functionality
}
```

## 🔍 Code Examples

### Basic Custom Exception
```java
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
    
    public InvalidAgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Custom Exception with Additional Data
```java
class BusinessLogicException extends Exception {
    private String errorCode;
    private int severity;
    
    public BusinessLogicException(String message, String errorCode, int severity) {
        super(message);
        this.errorCode = errorCode;
        this.severity = severity;
    }
}
```

### Exception Factory Pattern
```java
class ExceptionFactory {
    public static ValidationException createValidationException(String field, String value, String reason) {
        return new ValidationException(String.format("Validation failed for %s='%s': %s", field, value, reason));
    }
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Exception Declaration**
   - **Python**: Can raise any object as an exception
   - **Java**: Must extend Exception or RuntimeException

2. **Exception Handling**
   - **Python**: `raise` and `except`
   - **Java**: `throw` and `catch`

3. **Checked Exceptions**
   - **Python**: No concept of checked exceptions
   - **Java**: Checked exceptions must be declared with `throws`

4. **Exception Chaining**
   - **Python**: `raise NewException() from original_exception`
   - **Java**: `throw new NewException("message", originalException)`

5. **Resource Management**
   - **Python**: `with` statement
   - **Java**: `try-with-resources` (Java 7+)

### Common Translation Patterns

**Python:**
```python
class CustomException(Exception):
    def __init__(self, message):
        super().__init__(message)

try:
    risky_operation()
except CustomException as e:
    handle_error(e)
```

**Java:**
```java
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}

try {
    riskyOperation();
} catch (CustomException e) {
    handleError(e);
}
```

### Best Practices for Python Developers

1. **Start with RuntimeException** for most custom exceptions (similar to Python's approach)
2. **Use checked exceptions** only for recoverable conditions
3. **Always chain exceptions** when wrapping to preserve debugging information
4. **Override toString()** for better debugging output
5. **Consider exception factories** for consistent error creation

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
