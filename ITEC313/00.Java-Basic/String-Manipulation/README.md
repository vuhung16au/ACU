# String-Manipulation

## 📋 Overview
This project demonstrates essential string manipulation techniques in Java, focusing on immutability, common string methods, and best practices. It is designed for graduate students transitioning from Python to Java, providing clear comparisons and educational commentary.

## 📁 Files in this Directory
```
String-Manipulation/
├── StringManipulation.java    # Main source code: Demonstrates string manipulation concepts
├── Makefile                  # Build automation for compiling and running Java code
├── README.md                 # This documentation
├── examples/                 # Additional example files (see below)
│   ├── Example1.java         # Simple string reversal
│   ├── Example2.java         # Substring replacement
│   └── Advanced.java         # Advanced string manipulation (regex, joining, splitting)
├── data/                     # Sample data files
│   ├── input.txt             # Example strings for manipulation
│   └── sample.csv            # CSV data for splitting examples
└── docs/
    └── concepts.md           # Concepts and best practices
```

## 🛠 Building and Running
```bash
# Compile the program
make compile

# Run the main demonstration
make run

# Clean compiled files
make clean
```

## 📚 Learning Objectives
- Understand Java string immutability and manipulation
- Use common string methods (substring, split, replace, etc.)
- Compare Java and Python string handling
- Apply best practices for string operations
- Handle errors and null values in Java

## 🗝️ Key Takeaways
- Java strings are immutable (like Python)
- StringBuilder is used for efficient concatenation (unlike Python's mutable str)
- Java string methods are similar but not identical to Python
- Error handling for null strings is crucial in Java

## 📖 Important Concepts
- String creation, concatenation, and immutability
- Substring extraction and character access
- String comparison (case-sensitive and insensitive)
- Splitting and joining strings
- Regular expressions in Java
- Efficient string building with StringBuilder
- Error handling: NullPointerException

## 🔍 Code Examples
See `StringManipulation.java` and files in `examples/` for:
- Basic string operations
- Reversing strings
- Reversing strings
- Regex extraction and splitting
- Joining arrays into strings

## 📝 Notes for Python Developers
- Java strings are immutable, like Python, but lack slicing syntax (use substring)
- No built-in string reversal: use loops or StringBuilder
- StringBuilder is recommended for many concatenations (Python's str is mutable)
- Java's `split` and `replaceAll` use regex by default
- Null handling: Java throws NullPointerException, Python raises AttributeError

---
**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
