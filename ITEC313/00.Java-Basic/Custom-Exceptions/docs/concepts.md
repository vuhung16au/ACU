# Custom Exceptions - Concepts

## Overview

Custom exceptions in Java allow you to create specific error types for your application. They provide better error handling, debugging, and code organization compared to using generic exceptions.

## Key Concepts

### 1. Exception Hierarchy

Java exceptions are organized in a hierarchy:
- `Throwable` (root class)
  - `Error` (system errors, typically not handled)
  - `Exception` (application errors)
    - `RuntimeException` (unchecked exceptions)
    - Other checked exceptions

### 2. Checked vs Unchecked Exceptions

**Checked Exceptions (extends Exception):**
- Must be declared with `throws` or caught
- Represent recoverable conditions
- Examples: `IOException`, `SQLException`

**Unchecked Exceptions (extends RuntimeException):**
- Don't need to be declared
- Represent programming errors
- Examples: `NullPointerException`, `IllegalArgumentException`

### 3. Creating Custom Exceptions

**Basic Custom Exception:**
```java
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
```

**Custom Exception with Additional Data:**
```java
class BusinessException extends Exception {
    private String errorCode;
    private int severity;
    
    public BusinessException(String message, String errorCode, int severity) {
        super(message);
        this.errorCode = errorCode;
        this.severity = severity;
    }
}
```

### 4. Exception Chaining

Preserve the original cause when wrapping exceptions:
```java
try {
    // Some operation
} catch (OriginalException e) {
    throw new CustomException("Custom message", e);
}
```

### 5. Exception Handling Patterns

**Multiple Catch Blocks:**
```java
try {
    // Code that might throw different exceptions
} catch (SpecificException e) {
    // Handle specific exception
} catch (GeneralException e) {
    // Handle general exception
} finally {
    // Always executed
}
```

**Try-with-Resources (Java 7+):**
```java
try (Resource resource = new Resource()) {
    // Use resource
} catch (Exception e) {
    // Handle exception
}
```

## Best Practices

### 1. Exception Design
- Extend appropriate base class (Exception vs RuntimeException)
- Provide meaningful error messages
- Include relevant context information
- Override `toString()` for better debugging

### 2. Exception Handling
- Catch specific exceptions before general ones
- Don't catch exceptions you can't handle
- Use finally blocks for cleanup
- Log exceptions appropriately

### 3. Exception Creation
- Create exception hierarchies for complex applications
- Use exception factories for consistent creation
- Consider exception builders for complex scenarios
- Include recovery information when possible

### 4. Performance Considerations
- Exception creation is expensive
- Avoid using exceptions for control flow
- Use exceptions for exceptional conditions only
- Consider exception pooling for high-performance scenarios

## Common Pitfalls

### 1. Swallowing Exceptions
```java
// BAD: Exception is lost
try {
    riskyOperation();
} catch (Exception e) {
    // Empty catch block
}
```

### 2. Catching Generic Exceptions
```java
// BAD: Too broad
try {
    // Code
} catch (Exception e) {
    // Handles everything
}
```

### 3. Ignoring Checked Exceptions
```java
// BAD: Compilation error
public void method() {
    throw new Exception("Error"); // Must declare throws
}
```

## Advanced Patterns

### 1. Exception Factory
```java
class ExceptionFactory {
    public static ValidationException createValidationException(String field, String value) {
        return new ValidationException("Validation failed for " + field + ": " + value);
    }
}
```

### 2. Exception Builder
```java
class ExceptionBuilder {
    private String message;
    private String errorCode;
    
    public ExceptionBuilder message(String message) {
        this.message = message;
        return this;
    }
    
    public CustomException build() {
        return new CustomException(message);
    }
}
```

### 3. Exception Recovery
```java
class RecoverableException extends Exception {
    public enum RecoveryAction { RETRY, SKIP, ABORT }
    private RecoveryAction recoveryAction;
    
    public RecoverableException(String message, RecoveryAction action) {
        super(message);
        this.recoveryAction = action;
    }
}
```

## Python vs Java Comparison

### Exception Creation
**Python:**
```python
class CustomException(Exception):
    def __init__(self, message):
        super().__init__(message)
```

**Java:**
```java
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
```

### Exception Throwing
**Python:**
```python
raise CustomException("Error message")
```

**Java:**
```java
throw new CustomException("Error message");
```

### Exception Handling
**Python:**
```python
try:
    risky_operation()
except SpecificException as e:
    handle_specific(e)
except Exception as e:
    handle_general(e)
finally:
    cleanup()
```

**Java:**
```java
try {
    riskyOperation();
} catch (SpecificException e) {
    handleSpecific(e);
} catch (Exception e) {
    handleGeneral(e);
} finally {
    cleanup();
}
```

## Further Reading

- [Oracle Java Exception Documentation](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Java Language Specification - Exceptions](https://docs.oracle.com/javase/specs/jls/se11/html/jls-11.html)
- [Effective Java - Item 70: Use checked exceptions for recoverable conditions](https://www.amazon.com/Effective-Java-3rd-Joshua-Bloch/dp/0134685997)
- [Java Exception Handling Best Practices](https://www.baeldung.com/java-exceptions)
