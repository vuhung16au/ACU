# Methods-Basic

Method creation, parameters, return types

## 📋 Overview
This project demonstrates the basics of defining and using methods in Java, including method creation, parameters, return types, and best practices. It is designed for students transitioning from Python to Java.

## 📁 Files in this Directory
- `MethodsBasic.java`: Main source code demonstrating method concepts
- `Makefile`: Build automation for compiling and running the project
- `README.md`: This documentation file
- `examples/`: Additional example files (`Example1.java`, `Example2.java`, `Advanced.java`)
- `data/`: Sample data files (`input.txt`, `sample.csv`)
- `docs/`: Concepts documentation (`concepts.md`)

## 🛠 Building and Running
```bash
# Compile the program
make compile

# Run the program
make run

# Clean compiled files
make clean
```

## 📚 Learning Objectives
- Understand how to define and call methods in Java
- Learn about method parameters and return types
- Practice code organization using methods
- Compare Java methods to Python functions
- Apply best practices for method design

## 🗝️ Key Takeaways
- Java methods must be defined within a class
- Method signatures include return type, name, and parameters
- Method overloading allows multiple methods with the same name but different parameters
- Java is statically typed: parameter and return types must be declared
- No global functions: all methods belong to a class

## 📖 Important Concepts
- Method declaration and definition
- Parameter passing (by value)
- Return values and void methods
- Method overloading
- Access modifiers (public, private, etc.)
- Static vs. instance methods
- Differences from Python functions

## 🔍 Code Examples
```java
// Simple method definition
public int add(int a, int b) {
    return a + b; // Returns the sum of a and b
}

// Calling a method
int result = add(5, 3); // result is 8

// Method with no return value
public void printHello() {
    System.out.println("Hello, world!");
}
```

## 📝 Notes for Python Developers
- In Java, all methods must be inside a class (unlike Python, where functions can be standalone)
- Java requires explicit type declarations for parameters and return values
- No default arguments in Java methods (use method overloading instead)
- Indentation is not significant in Java; use braces `{}` to define code blocks
- Static methods in Java are similar to Python's `@staticmethod`
- Java uses `void` for methods that do not return a value (Python uses `None`)

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
