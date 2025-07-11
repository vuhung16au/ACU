# Polymorphism - Concepts

## Overview

Polymorphism is one of the four fundamental principles of Object-Oriented Programming (OOP). It allows objects of different types to be treated as objects of a common superclass or interface. The word "polymorphism" comes from Greek words meaning "many forms."

## Key Concepts

### 1. Types of Polymorphism

#### Runtime Polymorphism (Dynamic Polymorphism)
- **Method Overriding**: Subclasses provide specific implementations of methods defined in their superclass
- **Late Binding**: The method to be called is determined at runtime based on the actual object type
- **Example**: `Animal animal = new Dog(); animal.makeSound();` calls Dog's makeSound method

#### Compile-time Polymorphism (Static Polymorphism)
- **Method Overloading**: Multiple methods with the same name but different parameters
- **Early Binding**: The method to be called is determined at compile time
- **Example**: `displayInfo(String)` and `displayInfo(String, int)` are different methods

### 2. Method Overriding vs Method Overloading

#### Method Overriding
```java
// Superclass
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

// Subclass
class Dog extends Animal {
    @Override  // Annotation is good practice
    public void makeSound() {
        System.out.println("Woof!");
    }
}
```

#### Method Overloading
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

### 3. Casting in Polymorphism

#### Upcasting (Implicit)
```java
Animal animal = new Dog();  // Upcasting - always safe
```

#### Downcasting (Explicit)
```java
Animal animal = new Dog();
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;  // Downcasting - requires type check
    dog.fetch();  // Can now call Dog-specific methods
}
```

### 4. Interface Polymorphism

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

class Airplane implements Flyable {
    @Override
    public void fly() {
        System.out.println("Airplane is flying");
    }
}

// Polymorphic usage
Flyable[] flyables = {new Bird(), new Airplane()};
for (Flyable flyable : flyables) {
    flyable.fly();  // Calls appropriate implementation
}
```

### 5. Abstract Class Polymorphism

```java
abstract class Vehicle {
    protected String brand;
    
    public Vehicle(String brand) {
        this.brand = brand;
    }
    
    public abstract void startEngine();  // Abstract method
    
    public void displayInfo() {  // Concrete method
        System.out.println("Brand: " + brand);
    }
}

class Car extends Vehicle {
    @Override
    public void startEngine() {
        System.out.println("Car engine starting...");
    }
}
```

### 6. Generic Polymorphism

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

class StringProcessor implements DataProcessor<String> {
    @Override
    public String process(String data) {
        return data.toUpperCase();
    }
}
```

## Best Practices

### 1. Method Overriding
- Use `@Override` annotation to catch errors
- Maintain the same method signature (name, parameters, return type)
- Follow the Liskov Substitution Principle
- Don't change the access modifier to be more restrictive

### 2. Casting
- Always check the type before downcasting using `instanceof`
- Prefer polymorphism over casting when possible
- Use casting only when you need access to subclass-specific methods

### 3. Interface Design
- Keep interfaces focused and cohesive
- Use interfaces to define contracts
- Consider default methods for backward compatibility

### 4. Abstract Classes
- Use abstract classes when you want to share code between related classes
- Use interfaces when you want to define a contract for unrelated classes
- Abstract classes can have both abstract and concrete methods

## Common Pitfalls

### 1. Forgetting @Override Annotation
```java
// Without @Override - no compile-time check
public void makeSound() {
    // This might not actually override the parent method
}

// With @Override - compile-time safety
@Override
public void makeSound() {
    // Compiler will check if this actually overrides
}
```

### 2. Incorrect Casting
```java
Animal animal = new Dog();
Cat cat = (Cat) animal;  // ClassCastException at runtime!
```

### 3. Ignoring Polymorphic Behavior
```java
// Bad - not using polymorphism
if (animal instanceof Dog) {
    ((Dog) animal).makeSound();
} else if (animal instanceof Cat) {
    ((Cat) animal).makeSound();
}

// Good - using polymorphism
animal.makeSound();  // Calls appropriate method automatically
```

## Python vs Java Comparison

### Method Overriding
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
    @Override  // Explicit annotation
    public void makeSound() {
        System.out.println("Woof!");
    }
}
```

### Duck Typing vs Static Typing
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

### Multiple Inheritance
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

## Further Reading

- Oracle Java Documentation on Polymorphism
- Java Language Specification - Method Overriding
- Design Patterns and Polymorphism
- Generic Types and Polymorphism
- Best Practices for Object-Oriented Design
