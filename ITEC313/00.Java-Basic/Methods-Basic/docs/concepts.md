# Methods-Basic - Concepts

## Overview

This document covers the fundamentals of methods in Java, including how to define, call, and use methods effectively. It is designed for students familiar with Python who are learning Java.

## Key Concepts

### Main Learning Points

1. **Method Declaration and Definition**: In Java, methods must be declared inside a class. The method signature includes the return type, method name, and parameters.
2. **Parameter Passing**: Java passes arguments by value. For primitive types, the value is copied; for objects, the reference is copied (not the object itself).
3. **Return Types**: Every method must declare a return type. Use `void` if no value is returned.
4. **Method Overloading**: Java allows multiple methods with the same name but different parameter lists.
5. **Access Modifiers**: Control method visibility using `public`, `private`, `protected`, or package-private (default).
6. **Static vs. Instance Methods**: Static methods belong to the class; instance methods belong to objects.

### Best Practices

- Use descriptive method names following Java naming conventions (camelCase).
- Keep methods focused on a single task (single responsibility principle).
- Use appropriate parameter and return types.
- Document methods with comments (Javadoc style recommended).
- Handle errors gracefully using exceptions where appropriate.
- Avoid side effects unless necessary.

### Common Pitfalls

- Forgetting to specify a return type (unlike Python, where `def` is used).
- Trying to define methods outside of a class (not allowed in Java).
- Confusing static and instance methods.
- Not matching parameter types or counts when calling methods.
- Assuming Java passes objects by reference (it passes references by value).

## Python vs. Java Methods

- **Location**: Java methods must be inside a class; Python functions can be standalone.
- **Type Declarations**: Java requires explicit types for parameters and return values; Python is dynamically typed.
- **Default Arguments**: Java does not support default arguments; use method overloading instead.
- **Static Methods**: Java uses the `static` keyword; Python uses `@staticmethod`.
- **Return Type**: Java uses `void` for no return; Python uses `None`.

## Further Reading

- [Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)
- [Java Language Specification](https://docs.oracle.com/javase/specs/)
- [Best Practices Guides](https://www.oracle.com/java/technologies/javase/codeconventions-150003.pdf)
