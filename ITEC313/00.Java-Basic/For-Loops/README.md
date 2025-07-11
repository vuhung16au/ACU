# For-Loops

Traditional for, enhanced for, nested loops

## 📋 Overview

This project demonstrates the different types of for loops available in Java and their practical applications. For loops are fundamental control structures that allow you to execute code repeatedly, making them essential for processing arrays, collections, and performing iterative operations.

The project covers traditional for loops (similar to Python's `for i in range()`), enhanced for loops (similar to Python's `for item in collection`), and nested for loops for multi-dimensional data processing.

## 📁 Files in this Directory

```
For-Loops/
├── ForLoops.java           # Main source code with comprehensive examples
├── Makefile                # Build automation
├── README.md               # This documentation
├── examples/               # Additional example files
│   ├── Example1.java       # Basic for loop examples
│   ├── Example2.java       # Enhanced for loop examples
│   └── Advanced.java       # Advanced patterns and techniques
├── data/                   # Sample data files
│   ├── input.txt           # Sample input data for processing
│   └── sample.csv          # CSV data for demonstration
├── docs/                   # Additional documentation
│   └── concepts.md         # Detailed concepts and best practices
└── tests/                  # Unit tests (removed per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the program
make run

# Clean compiled files
make clean

# Show available commands
make help
```

### Running Individual Examples

```bash
# Compile and run specific examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced
```

## 📚 Learning Objectives

This project teaches:

- **Traditional For Loops**: Understanding the three-part syntax (initialization, condition, increment)
- **Enhanced For Loops**: Using for-each loops for array and collection iteration
- **Nested For Loops**: Working with multi-dimensional data and complex patterns
- **Loop Control**: Using break and continue statements effectively
- **Array Processing**: Different ways to iterate over arrays and process data
- **Performance Optimization**: Best practices for efficient loop implementation
- **Error Prevention**: Avoiding common pitfalls like infinite loops and off-by-one errors

## 🎯 Key Takeaways

- **Loop Selection**: Choose the right loop type based on your needs
- **Index vs Value**: Traditional loops for index access, enhanced loops for value iteration
- **Nested Patterns**: Understanding when and how to use nested loops
- **Control Flow**: Using break and continue for better loop control
- **Python Comparison**: Understanding differences between Java and Python loop syntax

## 🔍 Important Concepts

### Traditional For Loop
```java
// Syntax: for (initialization; condition; increment)
for (int i = 0; i < 5; i++) {
    System.out.println("Count: " + i);
}
```

### Enhanced For Loop
```java
// Syntax: for (Type item : collection)
String[] fruits = {"apple", "banana", "orange"};
for (String fruit : fruits) {
    System.out.println("Fruit: " + fruit);
}
```

### Nested For Loops
```java
// Useful for 2D arrays and complex patterns
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        System.out.println(i + " x " + j + " = " + (i * j));
    }
}
```

## 🔍 Code Examples

### Basic Counting Loop
```java
// Count from 0 to 4
for (int i = 0; i < 5; i++) {
    System.out.println("i = " + i);
}
```

### Array Processing
```java
// Process array elements
int[] numbers = {1, 2, 3, 4, 5};
int sum = 0;
for (int number : numbers) {
    sum += number;
}
```

### Pattern Printing
```java
// Print triangle pattern
for (int i = 1; i <= 4; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print("*");
    }
    System.out.println();
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Syntax Structure**:
   - Python: `for i in range(5):`
   - Java: `for (int i = 0; i < 5; i++)`

2. **Type Declaration**:
   - Python: No explicit type needed
   - Java: Must declare variable type (`int i`)

3. **Array vs List**:
   - Python: Lists are dynamic
   - Java: Arrays are fixed-size

4. **Enhanced For Loop**:
   - Python: `for item in collection:`
   - Java: `for (Type item : collection)`

5. **Index Access**:
   - Python: `for i, item in enumerate(list):`
   - Java: `for (int i = 0; i < array.length; i++)`

### Common Patterns

| Python Pattern | Java Equivalent |
|----------------|-----------------|
| `for i in range(10):` | `for (int i = 0; i < 10; i++)` |
| `for item in list:` | `for (Type item : array)` |
| `for i in range(10, 0, -1):` | `for (int i = 10; i >= 1; i--)` |
| `for i, item in enumerate(list):` | `for (int i = 0; i < array.length; i++)` |

### Best Practices for Python Developers

1. **Remember Curly Braces**: Java uses `{}` instead of indentation
2. **Declare Types**: Always specify variable types in for loops
3. **Array Length**: Use `array.length` instead of `len(array)`
4. **Index Tracking**: Manual index tracking needed for enhanced for loops
5. **Loop Variables**: Variables declared in for loops have limited scope

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
