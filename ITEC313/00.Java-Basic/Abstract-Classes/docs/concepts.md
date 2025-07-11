# Abstract Classes - Concepts

## Overview

Abstract classes and methods are fundamental concepts in object-oriented programming that allow you to create base classes that cannot be instantiated directly but provide a common interface and shared functionality for their subclasses.

## Key Concepts

### Main Learning Points

1. **Abstract Classes**: Classes that cannot be instantiated directly but serve as base classes for other classes. They can contain both abstract and concrete methods.

2. **Abstract Methods**: Methods declared in abstract classes that must be implemented by concrete subclasses. They define a contract that subclasses must fulfill.

3. **Template Method Pattern**: A design pattern where an abstract class defines the skeleton of an algorithm, letting subclasses override specific steps without changing the algorithm's structure.

4. **Polymorphism with Abstract Classes**: The ability to treat objects of different concrete classes uniformly through their common abstract base class.

### Abstract Class Characteristics

- **Cannot be instantiated**: You cannot create objects directly from an abstract class
- **Can have constructors**: Useful for initializing common fields
- **Can have abstract methods**: Methods without implementation that must be overridden
- **Can have concrete methods**: Methods with implementation that can be inherited
- **Can have fields**: Instance variables that are inherited by subclasses
- **Can have static methods**: Class-level methods that don't require instantiation

### Abstract Method Characteristics

- **No implementation**: Abstract methods have no body in the abstract class
- **Must be implemented**: All concrete subclasses must provide implementations
- **Can have access modifiers**: Can be public, protected, or package-private
- **Cannot be static**: Abstract methods cannot be static
- **Cannot be final**: Abstract methods cannot be final

### Best Practices

- **Use abstract classes for shared behavior**: When you have common functionality that should be shared among related classes
- **Keep abstract methods focused**: Each abstract method should have a single, clear purpose
- **Provide meaningful default implementations**: Use concrete methods for common functionality
- **Use the template method pattern**: Define algorithms with hooks for customization
- **Follow naming conventions**: Use clear, descriptive names for abstract classes and methods
- **Document abstract methods**: Clearly describe what subclasses should implement

### Common Pitfalls

- **Trying to instantiate abstract classes**: Remember that abstract classes cannot be instantiated directly
- **Forgetting to implement abstract methods**: All concrete subclasses must implement all abstract methods
- **Over-abstracting**: Don't create abstract classes for everything; use them when there's genuine shared behavior
- **Ignoring the template method pattern**: This pattern is powerful for defining algorithms with customizable steps
- **Not using polymorphism**: Abstract classes are most powerful when used with polymorphism

## Design Patterns with Abstract Classes

### Template Method Pattern
```java
abstract class AbstractClass {
    public final void templateMethod() {
        step1();
        step2();
        step3();
    }
    
    protected abstract void step2(); // Must be implemented
    protected void step1() { /* default implementation */ }
    protected void step3() { /* default implementation */ }
}
```

### Factory Pattern
```java
abstract class Creator {
    public abstract Product createProduct();
    
    public void useProduct() {
        Product product = createProduct();
        product.operation();
    }
}
```

## When to Use Abstract Classes vs Interfaces

### Use Abstract Classes When:
- You want to share code among several closely related classes
- You expect that classes that extend your abstract class have many common methods or fields
- You want to declare non-public members
- You want to define a template method pattern

### Use Interfaces When:
- You expect that unrelated classes would implement your interface
- You want to specify the behavior of a particular data type
- You want to take advantage of multiple inheritance of type
- You want to provide a common API for different implementations

## Further Reading

- Oracle Java Documentation: Abstract Classes and Methods
- Java Language Specification: Chapter 8 - Classes
- Design Patterns: Template Method Pattern
- Best Practices for Object-Oriented Design
- Effective Java by Joshua Bloch
