# Interfaces-Basic

## 📋 Overview

This project demonstrates Java interfaces, which are fundamental to Java's object-oriented programming paradigm. Interfaces provide a way to define contracts that classes must implement, enabling polymorphism and code reusability. This is similar to Python's abstract base classes but with key differences in syntax and behavior.

## 📁 Files in this Directory

```
Interfaces-Basic/
├── InterfacesBasic.java    # Main source code demonstrating interface concepts
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Basic interface implementation (payment system)
│   ├── Example2.java     # Default methods and functional interfaces
│   └── Advanced.java     # Advanced patterns (nested interfaces, inheritance)
├── data/                 # Sample data files
│   ├── input.txt         # Sample input data
│   └── sample.csv        # Sample CSV data
├── docs/                 # Additional documentation
│   └── concepts.md       # Detailed concept explanations
└── tests/                # Unit tests (removed per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the main program
make run

# Run individual examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced

# Clean compiled files
make clean

# Show help
make help
```

## 📚 Learning Objectives

This project teaches:
- **Interface Definition**: How to define interfaces with abstract methods and constants
- **Interface Implementation**: How classes implement interfaces and provide concrete implementations
- **Default Methods**: Java 8+ feature for providing default implementations in interfaces
- **Multiple Interface Implementation**: How classes can implement multiple interfaces
- **Interface Inheritance**: How interfaces can extend other interfaces
- **Nested Interfaces**: Advanced concept of interfaces within interfaces
- **Static Methods in Interfaces**: Java 8+ feature for utility methods in interfaces
- **Functional Interfaces**: Single-method interfaces for lambda expressions
- **Polymorphism**: Using interfaces as types for polymorphic behavior
- **Interface Composition**: Combining multiple interfaces for complex behavior

## 🎯 Key Takeaways

- **Contract Definition**: Interfaces define contracts that implementing classes must follow
- **Loose Coupling**: Interfaces enable loose coupling between components
- **Multiple Inheritance**: Java interfaces support multiple inheritance (unlike classes)
- **Default Methods**: Provide backward compatibility and shared behavior
- **Type Safety**: Interfaces provide compile-time type checking
- **Design Patterns**: Interfaces are fundamental to many design patterns
- **API Design**: Interfaces are crucial for designing clean APIs

## 🔍 Important Concepts

### Interface Basics
- **Abstract Methods**: Methods without implementation that must be implemented by classes
- **Constants**: Public static final fields that are shared across implementations
- **Implementation**: Classes use `implements` keyword to implement interfaces
- **Polymorphism**: Objects can be treated as their interface type

### Default Methods (Java 8+)
- **Shared Behavior**: Provide default implementations that can be inherited
- **Backward Compatibility**: Allow adding methods to interfaces without breaking existing code
- **Override Capability**: Classes can override default methods if needed
- **Multiple Inheritance**: Default methods enable multiple inheritance of behavior

### Functional Interfaces
- **Single Abstract Method**: Interfaces with exactly one abstract method
- **Lambda Expressions**: Can be implemented using lambda expressions
- **Method References**: Can use method references as implementations
- **Built-in Examples**: `Runnable`, `Comparator`, `Predicate`

### Advanced Patterns
- **Nested Interfaces**: Interfaces defined within other interfaces
- **Interface Inheritance**: Interfaces can extend other interfaces
- **Static Methods**: Utility methods that belong to the interface
- **Interface Composition**: Combining multiple interfaces for complex behavior

## 🔍 Code Examples

### Basic Interface Implementation
```java
interface Drawable {
    void draw();
    double getArea();
}

class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}
```

### Default Methods
```java
interface Loggable {
    default void log(String message) {
        System.out.println("[LOG] " + message);
    }
    
    void info(String message);
}
```

### Multiple Interface Implementation
```java
class SmartPhone implements Powerable, Connectable, Processable {
    // Implements methods from all three interfaces
}
```

### Functional Interface with Lambda
```java
@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
}

MathOperation add = (a, b) -> a + b;
double result = add.operate(5, 3); // 8.0
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Syntax**: Java uses `interface` keyword vs Python's `ABC` (Abstract Base Class)
2. **Implementation**: Java uses `implements` vs Python's inheritance
3. **Multiple Inheritance**: Java interfaces support multiple inheritance, Python classes don't
4. **Default Methods**: Java 8+ default methods vs Python mixins
5. **Type Safety**: Java interfaces provide compile-time checking vs Python's duck typing

### Python Equivalents

```python
# Python equivalent of Java interface
from abc import ABC, abstractmethod

class Drawable(ABC):
    @abstractmethod
    def draw(self):
        pass
    
    @abstractmethod
    def get_area(self):
        pass

class Circle(Drawable):
    def draw(self):
        print("Drawing circle")
    
    def get_area(self):
        return math.pi * self.radius ** 2
```

### Java vs Python Comparison

| Feature | Java | Python |
|---------|------|--------|
| Interface Definition | `interface` keyword | `ABC` with `@abstractmethod` |
| Implementation | `implements` keyword | Inheritance |
| Multiple Inheritance | ✅ Interfaces only | ❌ Classes only |
| Default Methods | ✅ Java 8+ | ✅ Mixins |
| Type Checking | Compile-time | Runtime (duck typing) |
| Lambda Support | ✅ Functional interfaces | ✅ Callable objects |

### Migration Tips

1. **Start Simple**: Begin with basic interfaces before advanced patterns
2. **Use Default Methods**: Leverage default methods for shared behavior
3. **Embrace Type Safety**: Use interfaces for compile-time checking
4. **Design Contracts**: Think of interfaces as contracts between components
5. **Practice Polymorphism**: Use interfaces as types for flexible code

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
