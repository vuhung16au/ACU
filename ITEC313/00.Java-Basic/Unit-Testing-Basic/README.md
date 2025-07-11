# Unit-Testing-Basic

## 📋 Overview
This project introduces the basics of unit testing logic in Java—without using JUnit or any testing framework. It demonstrates how to structure and verify code correctness using simple, educational examples. The focus is on learning Java syntax, best practices, and how to reason about correctness, especially for those familiar with Python.

## 📁 Files in this Directory

```
Unit-Testing-Basic/
├── UnitTestingBasic.java    # Main source code
├── Makefile                # Build automation
├── README.md               # This documentation
├── examples/               # Additional example files
│   ├── Example1.java
│   ├── Example2.java
│   └── Advanced.java
├── data/                   # Sample data files
│   ├── input.txt
│   └── sample.csv
└── docs/                   # Concepts documentation
    └── concepts.md
```

## 🛠 Building and Running

```bash
make compile   # Compile the program
make run       # Run the main program
make clean     # Remove compiled files
```

## 📚 Learning Objectives
- Understand how to structure and check code correctness in Java
- Learn Java syntax for assertions and error handling
- Compare Java and Python approaches to code verification
- Practice writing clear, maintainable, and well-commented Java code

## 🗝️ Key Takeaways
- Java's approach to code correctness and error handling
- How to write and check logic without a testing framework
- Differences between Java and Python in code structure and error reporting

## 🧠 Important Concepts
- Manual verification of code logic
- Use of assertions and error messages
- Java best practices for educational code

## 🔍 Code Examples
See `UnitTestingBasic.java` and files in `examples/` for sample code demonstrating these concepts.

## 📝 Notes for Python Developers
- Java does not have an interactive REPL by default; code is compiled and run
- Assertions in Java use the `assert` keyword, but are often disabled by default (unlike Python's always-on `assert`)
- Error handling uses `try-catch` blocks, similar to Python's `try-except`
- Java is statically typed; variable types must be declared
- No test frameworks are used here—focus is on learning Java syntax and logic
