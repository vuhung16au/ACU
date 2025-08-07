# Polymorphism

Runtime polymorphism, casting, and advanced polymorphic concepts in Java.

## 📋 Overview

This project demonstrates polymorphism in Java, one of the four fundamental principles of Object-Oriented Programming. Polymorphism allows objects of different types to be treated as objects of a common superclass or interface, enabling flexible and extensible code design.

The project covers:
- **Runtime Polymorphism**: Method overriding and dynamic method dispatch
- **Compile-time Polymorphism**: Method overloading
- **Interface Polymorphism**: Using interfaces for polymorphic behavior
- **Abstract Class Polymorphism**: Abstract classes and inheritance
- **Generic Polymorphism**: Generics and type-safe polymorphic operations
- **Casting**: Upcasting and downcasting with type safety

## 📁 Files in this Directory

```
Polymorphism/
├── Polymorphism.java      # Main source code demonstrating all concepts
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Basic polymorphism with shapes
│   ├── Example2.java     # Interface and abstract class polymorphism
│   └── Advanced.java     # Advanced concepts with generics
├── data/                 # Sample data files
│   ├── input.txt         # Sample input data
│   └── sample.csv        # Structured data for demonstrations
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

# Run specific examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced

# Clean compiled files
make clean
```

## 📚 Learning Objectives

This project teaches:

### Core Concepts
- **Runtime Polymorphism**: Understanding how method overriding enables dynamic method dispatch
- **Method Overriding**: Implementing polymorphic behavior through inheritance
- **Method Overloading**: Creating multiple methods with the same name but different signatures
- **Casting**: Safe upcasting and downcasting between related types
- **Interface Polymorphism**: Using interfaces to define polymorphic contracts
- **Abstract Class Polymorphism**: Combining abstract and concrete methods

### Advanced Concepts
- **Generic Polymorphism**: Type-safe polymorphic operations with generics
- **Wildcard Polymorphism**: Using bounded and unbounded wildcards
- **Design Patterns**: Factory and Strategy patterns with polymorphism
- **Complex Inheritance**: Managing multiple inheritance through interfaces

### Practical Skills
- **Type Safety**: Ensuring compile-time and runtime type safety
- **Code Reusability**: Writing flexible, extensible code
- **Best Practices**: Following Java conventions and design principles
- **Error Handling**: Proper casting and type checking

## 🎯 Key Takeaways

### 1. Polymorphism Types
- **Runtime Polymorphism**: Method calls resolved at runtime based on actual object type
- **Compile-time Polymorphism**: Method calls resolved at compile time based on method signature
- **Interface Polymorphism**: Objects treated through interface contracts
- **Generic Polymorphism**: Type-safe operations with generic types

### 2. Method Overriding vs Overloading
- **Overriding**: Subclass provides specific implementation of superclass method
- **Overloading**: Multiple methods with same name but different parameters
- **@Override Annotation**: Compile-time safety for method overriding

### 3. Casting Safety
- **Upcasting**: Always safe, implicit conversion from subclass to superclass
- **Downcasting**: Requires explicit cast and instanceof check
- **Type Safety**: Always verify type before casting to avoid runtime errors

### 4. Design Benefits
- **Flexibility**: Code can work with any object that implements required interface
- **Extensibility**: Easy to add new types without modifying existing code
- **Maintainability**: Changes in one class don't affect others
- **Reusability**: Same code can work with different object types

## 🔍 Important Concepts

### Runtime Polymorphism (Dynamic Binding)
```java
Animal animal = new Dog();  // Upcasting
animal.makeSound();         // Calls Dog's makeSound method
```

### Interface Polymorphism
```java
interface Flyable {
    void fly();
}

class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Bird is flying");
    }
}

// Polymorphic usage
Flyable flyable = new Bird();
flyable.fly();  // Calls Bird's fly method
```

### Abstract Class Polymorphism
```java
abstract class Vehicle {
    public abstract void startEngine();
    public void displayInfo() {
        // Concrete method
    }
}

class Car extends Vehicle {
    @Override
    public void startEngine() {
        System.out.println("Car engine starting...");
    }
}
```

### Generic Polymorphism
```java
interface DataProcessor<T> {
    T process(T data);
}

class NumberProcessor implements DataProcessor<Integer> {
    @Override
    public Integer process(Integer data) {
        return data * 2;
    }
}
```

## 🔍 Code Examples

### Basic Polymorphism
```java
// Base class
class Animal {
    public void makeSound() {
        System.out.println("Some animal sound");
    }
}

// Subclass with method overriding
class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

// Polymorphic usage
Animal animal = new Dog();
animal.makeSound();  // Outputs: "Woof! Woof!"
```

### Method Overloading
```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

### Safe Casting
```java
Animal animal = new Dog();

// Safe downcasting
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
    dog.fetch();  // Can call Dog-specific methods
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

#### 1. Method Overriding
**Python:**
```python
class Animal:
    def make_sound(self):
        print("Some sound")

class Dog(Animal):
    def make_sound(self):  # Just define the same method
        print("Woof!")
```

**Java:**
```java
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override  // Explicit annotation for safety
    public void makeSound() {
        System.out.println("Woof!");
    }
}
```

#### 2. Duck Typing vs Static Typing
**Python (Duck Typing):**
```python
def process_animal(animal):
    animal.make_sound()  # Works if animal has make_sound method

# Can pass any object with make_sound method
process_animal(Dog())
process_animal(Cat())
```

**Java (Static Typing):**
```java
public static void processAnimal(Animal animal) {
    animal.makeSound();  // Must be Animal or subclass
}

// Must pass Animal or subclass
processAnimal(new Dog());
processAnimal(new Cat());
```

#### 3. Multiple Inheritance
**Python:**
```python
class FlyingCar(Car, Airplane):  # Multiple inheritance
    pass
```

**Java:**
```java
class FlyingCar extends Car implements Flyable {  // Single inheritance + interfaces
    @Override
    public void fly() {
        // Implementation
    }
}
```

#### 4. Type Checking
**Python:**
```python
if isinstance(animal, Dog):
    animal.fetch()
```

**Java:**
```java
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
    dog.fetch();
}
```

### Learning Path for Python Developers
1. **Start with Method Overriding**: Understand how Java's explicit typing affects polymorphism
2. **Learn Casting**: Java requires explicit casting, unlike Python's duck typing
3. **Explore Interfaces**: Java uses interfaces for multiple inheritance
4. **Study Generics**: Java's type-safe approach to generic programming
5. **Practice Type Safety**: Understand compile-time vs runtime type checking

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
