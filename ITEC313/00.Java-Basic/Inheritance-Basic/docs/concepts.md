# Inheritance-Basic - Concepts

## Overview

Inheritance is a fundamental concept in object-oriented programming that allows a class to inherit properties and methods from another class. In Java, inheritance is implemented using the `extends` keyword and supports single inheritance (unlike Python's multiple inheritance).

## Key Concepts

### 1. Extends Keyword
- **Java**: `class Child extends Parent`
- **Python**: `class Child(Parent):`
- **Purpose**: Establishes an "is-a" relationship between classes
- **Example**: A Dog is an Animal, so Dog extends Animal

### 2. Super Keyword
- **Java**: `super()` to call parent constructor, `super.methodName()` to call parent methods
- **Python**: `super().__init__()` and `super().method_name()`
- **Purpose**: Access parent class members and methods
- **Usage**: Must be first statement in constructor when calling parent constructor

### 3. Method Overriding
- **Java**: Use `@Override` annotation for clarity and safety
- **Python**: Simply define method with same name
- **Purpose**: Provide different implementation of parent method in child class
- **Rules**: Same method signature (name, parameters, return type)

### 4. Constructor Chaining
- **Java**: Explicit `super()` call required in child constructors
- **Python**: `super().__init__()` call in child constructors
- **Purpose**: Initialize parent class state before child class
- **Rule**: Must be first statement in constructor

### 5. Access Modifiers
- **public**: Accessible everywhere
- **protected**: Accessible in same package and subclasses
- **private**: Accessible only in same class
- **default**: Accessible in same package only

### 6. Abstract Classes
- **Java**: `abstract class` with `abstract` methods
- **Python**: No direct equivalent, use `NotImplementedError`
- **Purpose**: Define common interface for related classes
- **Rule**: Cannot instantiate abstract classes

### 7. Interfaces
- **Java**: `interface` with method signatures
- **Python**: No direct equivalent, use abstract base classes
- **Purpose**: Define contract for classes to implement
- **Benefit**: Enables multiple inheritance through interfaces

## Best Practices

### 1. Design Principles
- **Favor Composition over Inheritance**: Use composition when possible
- **Single Responsibility**: Each class should have one reason to change
- **Open/Closed Principle**: Open for extension, closed for modification
- **Liskov Substitution**: Child classes should be substitutable for parent classes

### 2. Naming Conventions
- Use descriptive class names that indicate inheritance relationships
- Follow Java naming conventions (PascalCase for classes)
- Use meaningful method names that clearly indicate purpose

### 3. Documentation
- Document inheritance relationships clearly
- Use `@Override` annotation for overridden methods
- Include comments explaining design decisions

### 4. Error Handling
- Handle exceptions appropriately in inherited methods
- Maintain consistent error handling patterns across inheritance hierarchy
- Consider using checked exceptions for public APIs

## Common Pitfalls

### 1. Constructor Issues
- **Problem**: Forgetting to call `super()` in child constructor
- **Solution**: Always call parent constructor first
- **Example**: `super(name, age);` must be first statement

### 2. Method Signature Mismatch
- **Problem**: Different method signatures in parent and child
- **Solution**: Ensure exact same signature for overridden methods
- **Check**: Use `@Override` annotation to catch errors

### 3. Access Modifier Problems
- **Problem**: Reducing visibility in child class
- **Solution**: Maintain or increase visibility in child classes
- **Rule**: Child methods cannot be less accessible than parent methods

### 4. Inheritance vs Composition
- **Problem**: Using inheritance when composition is better
- **Solution**: Ask "is-a" vs "has-a" relationship
- **Example**: Car has-an Engine (composition), not Car is-an Engine

## Python to Java Differences

### 1. Multiple Inheritance
- **Python**: Supports multiple inheritance with complex MRO
- **Java**: Single inheritance, multiple interfaces
- **Impact**: Simpler but less flexible inheritance model

### 2. Method Resolution
- **Python**: Dynamic method resolution with MRO
- **Java**: Static method resolution at compile time
- **Benefit**: Better performance and compile-time error checking

### 3. Constructor Calls
- **Python**: Optional `super().__init__()` calls
- **Java**: Required `super()` calls in child constructors
- **Benefit**: Ensures proper initialization

### 4. Abstract Classes
- **Python**: Use `NotImplementedError` or ABC
- **Java**: Explicit `abstract` keyword and methods
- **Benefit**: Clearer intent and compile-time checking

## Further Reading

### Official Documentation
- [Oracle Java Inheritance Tutorial](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- [Java Language Specification - Inheritance](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.1.4)

### Design Patterns
- Template Method Pattern
- Strategy Pattern
- Factory Pattern
- Decorator Pattern

### Best Practices Guides
- Effective Java by Joshua Bloch
- Clean Code by Robert C. Martin
- Design Patterns by Gang of Four

### Related Concepts
- Polymorphism
- Encapsulation
- Abstraction
- Interfaces
- Abstract Classes
