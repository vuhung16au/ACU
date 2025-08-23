# Java Generics CLI Demo

A comprehensive CLI application demonstrating Java generics concepts.

## Overview

This application showcases the fundamental concepts of Java generics as outlined in Chapter 19 objectives, including:

- **Generic Stack**: Type-safe stack implementation with various data types
- **Wildcard Demonstrations**: Understanding the need for and usage of wildcards
- **ArrayList with Generics**: Type-safe collection operations
- **Generic Matrix Operations**: Mathematical operations with different numeric types
- **Automatic Execution**: Runs all demonstrations automatically without user interaction

## Objectives

Understand Java generics and their application in creating type-safe, reusable code. This chapter covers the following objectives.

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
├── pom.xml                           # Maven configuration
└── README.md                         # This file
```

## Building and Running

### Prerequisites

- Java 24 or later (matches pom.xml). If you have an older JDK, either install JDK 24 or update pom.xml to your JDK version.
- Maven 3.9.x or later

### Quick start

```bash
# From the project root
mvn clean package

# Run (option A – via Maven Exec Plugin)
mvn exec:java

# Run (option B – via the shaded JAR produced in target/)
java -jar target/generics-demo-cli-1.0.0.jar
```

Both options run all demos automatically.

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Run the application (runs all demos automatically)
mvn exec:java


```

### Alternative: Run from compiled classes (module-path)

If you prefer to run directly from compiled classes without packaging:

```bash
java --module-path target/classes \
	-m com.acu.genericcli.generics/com.acu.genericcli.generics.Launcher
```

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

