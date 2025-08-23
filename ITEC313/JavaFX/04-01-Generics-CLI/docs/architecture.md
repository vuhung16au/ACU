# Java Generics CLI - Architecture Documentation

This document describes the architecture of the Java Generics CLI application.

## Application Overview

The Java Generics CLI is a command-line application that demonstrates various aspects of Java generics through interactive examples. Each concept is demonstrated through separate demo classes that output results to the console.

## Architecture Patterns

### 1. Command Pattern
Each demo class implements a specific generics concept demonstration:
- `GenericStackDemo` - Generic stack operations
- `WildCardNeedDemo` - Wildcard necessity examples
- `AnyWildCardDemo` - Unbounded wildcard usage
- `SuperWildCardDemo` - Lower bounded wildcard usage
- `TestArrayListNew` - ArrayList with generics
- `TestIntegerMatrix` - Integer matrix operations
- `TestRationalMatrix` - Rational matrix operations

### 2. Template Method Pattern
The `GenericMatrix<E>` class uses the Template Method pattern:
```java
public abstract class GenericMatrix<E extends Number> {
    public E[][] addMatrix(E[][] matrix1, E[][] matrix2) { ... }
    public E[][] multiplyMatrix(E[][] matrix1, E[][] matrix2) { ... }
    
    protected abstract E add(E o1, E o2);
    protected abstract E multiply(E o1, E o2);
    protected abstract E zero();
}
```

### 3. Strategy Pattern
Different matrix implementations provide different arithmetic strategies:
- `IntegerMatrix` - Integer arithmetic
- `RationalMatrix` - Rational arithmetic

## Package Structure

```
com.acu.genericcli.generics/
├── Launcher.java              # Application entry point
├── GenericStack.java          # Generic stack implementation
├── GenericStackDemo.java      # Stack demonstration
├── WildCardNeedDemo.java      # Wildcard need demonstration
├── AnyWildCardDemo.java       # Unbounded wildcard demo
├── SuperWildCardDemo.java     # Lower bounded wildcard demo
├── TestArrayListNew.java      # ArrayList with generics demo
├── GenericMatrix.java         # Abstract generic matrix
├── IntegerMatrix.java         # Integer matrix implementation
├── TestIntegerMatrix.java     # Integer matrix demo
├── Rational.java              # Rational number class
├── RationalMatrix.java        # Rational matrix implementation
└── TestRationalMatrix.java    # Rational matrix demo
```

## Key Design Decisions

### 1. CLI Interface
- Simple console output for demonstrations
- No complex UI dependencies
- Easy to run and understand

### 2. Static Demo Methods
- No instance state needed
- Simple to invoke from main method
- Clear separation of demo logic

### 3. StringBuilder for Output
- Efficient string concatenation
- Easy to format output
- Memory efficient

### 4. Generic Matrix Design
- Abstract class with bounded type parameter
- Ensures only numeric types
- Template method pattern

## Class Responsibilities

### Launcher
- Application entry point
- Orchestrates demo execution

### GenericStack<E>
- Type-safe stack implementation
- Generic type parameter

### GenericMatrix<E>
- Abstract matrix operations
- Template method pattern

### Demo Classes
- Demonstrate specific generics concepts
- Static utility methods
- Formatted console output

## Error Handling
- Compile-time type safety through generics
- Runtime exceptions for matrix operations
- Graceful error display in console output

## Performance
- Type erasure eliminates runtime overhead
- No casting operations needed
- Efficient memory usage

## Extensibility
- Easy to add new demo classes
- Simple to extend matrix types
- Modular design for new concepts 