# Java Generics CLI Demo

A comprehensive CLI application demonstrating Java generics concepts through interactive examples and real-time demonstrations.

## Overview

This application showcases the fundamental concepts of Java generics as outlined in Chapter 19 objectives, including:

- **Generic Stack**: Type-safe stack implementation with various data types
- **Wildcard Demonstrations**: Understanding the need for and usage of wildcards
- **ArrayList with Generics**: Type-safe collection operations
- **Generic Matrix Operations**: Mathematical operations with different numeric types
- **Automatic Execution**: Runs all demonstrations automatically without user interaction

## Original Objectives

Understand Java generics and their application in creating type-safe, reusable code. This chapter covers the following objectives:

• To understand the benefits of generics for type safety and code reusability (§19.1).
• To define and use generic classes and methods (§19.2).
• To understand generic type erasure and its implications (§19.3).
• To use wildcards in generic programming (§19.4).
• To understand bounded wildcards and their applications (§19.5).
• To implement generic matrices and understand inheritance with generics (§19.6).
• To develop practical applications using generics for robust software design (§19.7).

Examples
- https://liveexample.pearsoncmg.com/html/GenericStack.html
- https://liveexample.pearsoncmg.com/html/WildCardNeedDemo.html
- https://liveexample.pearsoncmg.com/html/AnyWildCardDemo.html
- https://liveexample.pearsoncmg.com/html/SuperWildCardDemo.html
- https://liveexample.pearsoncmg.com/html/TestArrayListNew.html
- https://liveexample.pearsoncmg.com/html/GenericMatrix.html
- https://liveexample.pearsoncmg.com/html/IntegerMatrix.html
- https://liveexample.pearsoncmg.com/html/TestIntegerMatrix.html
- https://liveexample.pearsoncmg.com/html/RationalMatrix.html
- https://liveexample.pearsoncmg.com/html/TestRationalMatrix.html

## Features

### 📚 Generic Stack Demo
- **Type-Safe Stack Operations**: Demonstrates generic stack with String, Integer, and Double types
- **No Casting Required**: Shows the benefits of generics in eliminating explicit casting
- **Performance Benefits**: Illustrates type safety without runtime overhead
- **Interactive Demo**: Live execution with real-time output display

### ❓ Wildcard Need Demo
- **Type Invariance Issues**: Demonstrates why wildcards are necessary
- **Problem Illustration**: Shows limitations of strict type parameters
- **Solution Presentation**: How wildcards solve generic programming challenges
- **Practical Examples**: Real-world scenarios requiring wildcard usage

### 🔍 Any Wildcard Demo
- **Unbounded Wildcards**: Usage of `?` wildcard in generic methods
- **Read-Only Operations**: Demonstrates safe operations with unknown types
- **Limitations and Benefits**: Shows what you can and cannot do with unbounded wildcards
- **Collection Processing**: Generic methods for processing any collection type

### ⬆️ Super Wildcard Demo
- **Lower Bounded Wildcards**: Usage of `? super T` in generic programming
- **Write Operations**: Demonstrates safe writing to collections with super wildcards
- **Producer-Consumer Pattern**: Illustrates PECS (Producer Extends, Consumer Super) principle
- **Type Safety Maintenance**: How super wildcards maintain type safety while allowing flexibility

### 📋 ArrayList Demo
- **Generic Collections**: Type-safe ArrayList operations
- **Compile-Time Safety**: Elimination of ClassCastException at runtime
- **Enhanced For-Each**: Improved iteration with generics
- **Collection Framework**: Integration with Java Collections Framework

### 🧮 Generic Matrix Demo
- **Abstract Generic Matrix**: Base class for mathematical matrix operations
- **Integer Matrix**: Concrete implementation for integer calculations
- **Rational Matrix**: Arithmetic operations with rational numbers
- **Polymorphic Operations**: Demonstrates inheritance and polymorphism with generics

## Technical Specifications

### Development Environment

- **Java Version**: OpenJDK 24
- **Maven Version**: 3.9.x or later
- **Build System**: Maven

### Cross-Platform Support

The application is designed to run on:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64  
- **Linux**: x86_64 and ARM64

## Project Structure

```
04-01-Generics-CLI/
├── src/main/java/com/acu/genericcli/generics/
│   ├── Launcher.java                 # CLI application launcher
│   ├── GenericStack.java             # Generic stack implementation
│   ├── GenericStackDemo.java         # Stack demonstration
│   ├── WildCardNeedDemo.java         # Wildcard necessity demo
│   ├── AnyWildCardDemo.java          # Unbounded wildcard demo
│   ├── SuperWildCardDemo.java        # Lower bounded wildcard demo
│   ├── TestArrayListNew.java         # ArrayList with generics demo
│   ├── GenericMatrix.java            # Abstract generic matrix
│   ├── IntegerMatrix.java            # Integer matrix implementation
│   ├── TestIntegerMatrix.java        # Integer matrix testing
│   ├── RationalMatrix.java           # Rational number matrix
│   ├── TestRationalMatrix.java       # Rational matrix testing
│   └── Rational.java                 # Rational number class
├── docs/                             # Documentation and guides
├── images/                           # Screenshots and diagrams
├── pom.xml                           # Maven configuration
└── README.md                         # This file
```

## Building and Running

### Prerequisites

- Java 24 or later
- Maven 3.9.x or later

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Run the application (runs all demos automatically)
mvn exec:java -Dexec.mainClass="com.acu.genericcli.generics.Launcher"

# Alternative run methods
./run.sh          # Unix/Linux/macOS
./run.bat         # Windows
```

### Running the Application

When you run the application, it will automatically execute all demonstrations in sequence:

```
=== Java Generics CLI Demo ===

Running all demonstrations automatically...

1. Generic Stack Demo:
==================================================
[Demo output...]

2. Wildcard Need Demo:
==================================================
[Demo output...]

3. Any Wildcard Demo:
==================================================
[Demo output...]

4. Super Wildcard Demo:
==================================================
[Demo output...]

5. ArrayList with Generics Demo:
==================================================
[Demo output...]

6. Integer Matrix Demo:
==================================================
[Demo output...]

7. Rational Matrix Demo:
==================================================
[Demo output...]

=== All demonstrations completed ===
```

The application runs completely automatically without requiring any user input.

## Key Learning Concepts

### 1. Type Safety
- Compile-time type checking
- Elimination of ClassCastException
- No need for explicit casting

### 2. Code Reusability
- Single implementation for multiple types
- Parameterized classes and methods
- Generic algorithms

### 3. Wildcard Usage
- Unbounded wildcards (`?`)
- Upper bounded wildcards (`? extends T`)
- Lower bounded wildcards (`? super T`)

### 4. Type Erasure
- Runtime type information removal
- Backward compatibility with pre-generic code
- Bridge methods and type inference

### 5. Generic Inheritance
- Inheritance relationships with generic types
- Covariance and contravariance
- Type parameter bounds

## Educational Value

This demonstration provides hands-on experience with:

- **Generic Class Design**: Creating reusable, type-safe classes
- **Wildcard Patterns**: Understanding when and how to use wildcards
- **Collection Framework**: Leveraging generics in Java Collections
- **Mathematical Applications**: Applying generics to computational problems
- **Best Practices**: Following established patterns for generic programming

## Screenshots

![04-01-Generics](images/04-01-Generics.png)