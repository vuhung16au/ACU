# String-Algorithms

Common string algorithms

## 📋 Overview
Brief description of the project and its purpose

## 📁 Files in this Directory
Detailed list of all files with descriptions

```
String-Algorithms/
├── StringAlgorithms.java      # Main source code
├── Makefile                  # Build automation
├── README.md                 # Documentation
├── examples/                 # Additional example files
│   ├── Example1.java
│   ├── Example2.java
│   └── Advanced.java
├── data/                     # Sample data files
│   ├── input.txt
│   └── sample.csv
└── docs/                     # Concepts documentation
    └── concepts.md
```

## 🛠 Building and Running
Step-by-step instructions for compilation and execution

```bash
# Compile the program
make compile

# Run the program
make run

# Clean compiled files
make clean
```

## 📚 Learning Objectives
- Understand and implement common string algorithms in Java
- Learn Java string manipulation techniques
- Compare Java string handling with Python
- Practice best practices in Java code organization and documentation

## 🗝️ Key Takeaways
- Java String is immutable; StringBuilder/StringBuffer for mutability
- Common algorithms: reversal, palindrome check, substring search, etc.
- Differences in string handling between Java and Python
- Importance of error handling and input validation

## 📖 Important Concepts
- String immutability and memory management
- StringBuilder vs StringBuffer
- Common string algorithms (reverse, search, replace, split, join)
- Java string methods vs Python string methods

## 🔍 Code Examples
Sample code snippets demonstrating key concepts (see main file and examples/)

```java
// Reverse a string in Java
String original = "hello";
String reversed = new StringBuilder(original).reverse().toString();
System.out.println(reversed); // Output: olleh

// Compare with Python:
// reversed = original[::-1]
```

## 📝 Notes for Python Developers
- Java strings are immutable (like Python), but string concatenation is less efficient; use StringBuilder for many modifications
- Java does not have built-in slicing like Python; use substring and related methods
- String comparison uses .equals(), not ==
- No f-strings; use String.format or concatenation

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
