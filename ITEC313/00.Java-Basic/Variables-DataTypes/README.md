# Variables-DataTypes

## 📋 Overview
This project demonstrates the use of variables and data types in Java, including primitive types, wrapper classes, and type conversion. It is designed as a refresher for graduate students transitioning from Python to Java.

## 📁 Files in this Directory
- `VariablesDataTypes.java`: Main source code demonstrating variables and data types
- `Makefile`: Build automation for compiling and running the project
- `README.md`: Project documentation and instructions
- `examples/Example1.java`: Simple example of primitive data types
- `examples/Example2.java`: Example of wrapper classes and autoboxing/unboxing
- `examples/Advanced.java`: Example of type conversion and error handling
- `data/input.txt`: Sample data file with variable values
- `data/sample.csv`: Sample CSV file with variable names and types
- `docs/concepts.md`: Documentation of key concepts

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
- Understand Java primitive data types and their usage
- Learn about wrapper classes and their purpose
- Practice type conversion and error handling in Java
- Compare Java variable handling to Python
- Apply best practices in variable declaration and usage

## 🗝️ Key Takeaways
- Java requires explicit type declaration; Python is dynamically typed
- Wrapper classes provide object representations for primitives
- Type conversion in Java is stricter than in Python
- Error handling for invalid conversions is essential

## 🧠 Important Concepts
- Primitive types: int, double, boolean, char
- Wrapper classes: Integer, Double, Boolean, Character
- Autoboxing and unboxing
- Type conversion and parsing
- Differences between Java and Python variable handling

## 🔍 Code Examples
```java
// Primitive type declaration
int age = 25;
double salary = 55000.5;
boolean isStudent = false;
char grade = 'A';

// Wrapper class usage
Integer num = 10;
double pi = Double.valueOf("3.14");

// Type conversion
int n = Integer.parseInt("123");
```

## 📝 Notes for Python Developers
- In Python, variables are dynamically typed (e.g., `age = 25`)
- Java requires explicit type declaration (e.g., `int age = 25;`)
- No distinction between primitive and wrapper types in Python
- Type conversion in Python is more flexible (e.g., `int("123")`), but Java requires error handling for invalid conversions
