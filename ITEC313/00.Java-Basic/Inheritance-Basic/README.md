# Inheritance-Basic

## 📋 Overview

This project demonstrates key inheritance concepts in Java programming, designed specifically for Python developers learning Java. Inheritance is a fundamental object-oriented programming concept that allows classes to inherit properties and methods from other classes, creating a hierarchical relationship between classes.

**Key Features:**
- Extends keyword for class inheritance
- Super keyword for accessing parent class members
- Method overriding with @Override annotation
- Constructor chaining with super()
- Abstract classes and interfaces
- Polymorphism through inheritance

## 📁 Files in this Directory

```
Inheritance-Basic/
├── InheritanceBasic.java    # Main source code with comprehensive examples
├── Makefile                 # Build automation
├── README.md                # This documentation
├── examples/                # Additional example files
│   ├── Example1.java       # Bank account inheritance hierarchy
│   ├── Example2.java       # Media library with interfaces
│   └── Advanced.java       # Complex scenarios with generics
├── data/                   # Sample data files
│   ├── input.txt           # Sample input data
│   └── sample.csv          # CSV data for examples
├── docs/                   # Additional documentation
│   └── concepts.md         # Detailed concept explanations
└── tests/                  # Unit tests (removed per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the main program
make run

# Clean compiled files
make clean

# Run individual examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced
```

## 📚 Learning Objectives

This project teaches:

### Core Inheritance Concepts
- **Extends Keyword**: How to create inheritance relationships using `extends`
- **Super Keyword**: Accessing parent class constructors and methods
- **Method Overriding**: Providing different implementations in child classes
- **Constructor Chaining**: Proper initialization of inheritance hierarchies

### Advanced Concepts
- **Abstract Classes**: Creating base classes that cannot be instantiated
- **Interfaces**: Defining contracts for classes to implement
- **Polymorphism**: Using parent class references with child class objects
- **Access Modifiers**: Understanding public, protected, private, and default access

### Design Patterns
- **Factory Pattern**: Creating objects through inheritance hierarchies
- **Template Method**: Using inheritance for code reuse
- **Strategy Pattern**: Using inheritance for different behaviors

## 🎯 Key Takeaways

### 1. Inheritance Fundamentals
- Java uses single inheritance (unlike Python's multiple inheritance)
- The `extends` keyword establishes "is-a" relationships
- Child classes inherit all non-private members from parent classes
- Constructor chaining ensures proper initialization

### 2. Method Overriding
- Use `@Override` annotation for clarity and safety
- Method signatures must match exactly between parent and child
- Child methods cannot be less accessible than parent methods
- Polymorphism allows parent references to call child implementations

### 3. Best Practices
- Favor composition over inheritance when possible
- Follow the Liskov Substitution Principle
- Use abstract classes for common behavior
- Use interfaces for defining contracts

## 🔍 Important Concepts

### Extends Keyword
```java
// Java syntax
class Dog extends Animal {
    // Dog inherits from Animal
}

// Python equivalent
class Dog(Animal):
    # Dog inherits from Animal
```

### Super Keyword
```java
// Java constructor chaining
public Dog(String name, int age, String breed) {
    super(name, age);  // Must be first statement
    this.breed = breed;
}

// Python equivalent
def __init__(self, name, age, breed):
    super().__init__(name, age)
    self.breed = breed
```

### Method Overriding
```java
// Java with @Override annotation
@Override
public void makeSound() {
    System.out.println("Woof!");
}

// Python equivalent
def make_sound(self):
    print("Woof!")
```

### Abstract Classes
```java
// Java abstract class
abstract class Shape {
    public abstract double calculateArea();
}

// Python equivalent (using ABC)
from abc import ABC, abstractmethod
class Shape(ABC):
    @abstractmethod
    def calculate_area(self):
        pass
```

## 🔍 Code Examples

### Basic Inheritance
```java
class Animal {
    protected String name;
    protected int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);  // Call parent constructor
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println("Dog barks: Woof!");
    }
}
```

### Constructor Chaining
```java
class Vehicle {
    protected String model;
    protected int year;
    
    public Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }
}

class Car extends Vehicle {
    private String type;
    
    public Car(String model, int year, String type) {
        super(model, year);  // Chain to parent constructor
        this.type = type;
    }
}
```

### Polymorphism
```java
// Parent class reference with child objects
Animal[] animals = {
    new Dog("Buddy", 3, "Golden Retriever"),
    new Cat("Whiskers", 2, "Persian")
};

for (Animal animal : animals) {
    animal.makeSound();  // Calls appropriate child implementation
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Multiple Inheritance**
   - **Python**: Supports multiple inheritance with complex MRO
   - **Java**: Single inheritance, multiple interfaces
   - **Impact**: Simpler but less flexible inheritance model

2. **Constructor Calls**
   - **Python**: Optional `super().__init__()` calls
   - **Java**: Required `super()` calls in child constructors
   - **Benefit**: Ensures proper initialization

3. **Method Resolution**
   - **Python**: Dynamic method resolution with MRO
   - **Java**: Static method resolution at compile time
   - **Benefit**: Better performance and compile-time error checking

4. **Abstract Classes**
   - **Python**: Use `NotImplementedError` or ABC
   - **Java**: Explicit `abstract` keyword and methods
   - **Benefit**: Clearer intent and compile-time checking

### Common Translation Patterns

| Python | Java |
|--------|------|
| `class Child(Parent):` | `class Child extends Parent` |
| `super().__init__(args)` | `super(args)` |
| `def method(self):` | `public void method()` |
| `@abstractmethod` | `abstract` keyword |
| Multiple inheritance | Single inheritance + interfaces |

### Best Practices for Python Developers

1. **Start Simple**: Begin with basic inheritance before complex patterns
2. **Use @Override**: Always use the annotation for overridden methods
3. **Plan Interfaces**: Use interfaces for multiple inheritance needs
4. **Test Polymorphism**: Verify that parent references work with child objects
5. **Document Relationships**: Clearly document inheritance hierarchies

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
