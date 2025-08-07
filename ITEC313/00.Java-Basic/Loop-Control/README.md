# Loop-Control

Break, continue, labeled statements

## 📋 Overview

This project demonstrates Java loop control statements including `break`, `continue`, and labeled statements. It provides comprehensive examples showing how to control loop execution flow in Java, with comparisons to Python equivalents where applicable.

The project serves as a learning resource for students transitioning from Python to Java, highlighting the similarities and differences in loop control mechanisms between the two languages.

## 📁 Files in this Directory

```
Loop-Control/
├── LoopControl.java      # Main source code with comprehensive demonstrations
├── Makefile             # Build automation for compilation and execution
├── README.md            # This documentation file
├── examples/            # Additional example files
│   ├── Example1.java    # Basic break and continue examples
│   ├── Example2.java    # Labeled break and continue examples
│   └── Advanced.java    # Complex scenarios and practical applications
├── data/                # Sample data files
│   ├── input.txt        # Sample input data
│   └── sample.csv       # Sample CSV data
├── docs/                # Additional documentation
│   └── concepts.md      # Detailed concept explanations
└── tests/               # Test files (empty - no testing framework)
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

- **Basic Loop Control**: Understanding `break` and `continue` statements
- **Labeled Statements**: Using labels with `break` and `continue` in nested loops
- **Python to Java Transition**: Understanding differences in loop control between languages
- **Practical Applications**: Real-world scenarios using loop control
- **Code Organization**: Best practices for structuring loop control logic
- **Algorithm Optimization**: Using loop control for performance improvements

## 🎯 Key Takeaways

- **Break Statement**: Exits the current loop immediately
- **Continue Statement**: Skips the current iteration and continues with the next
- **Labeled Break**: Exits a specific labeled loop from nested loops
- **Labeled Continue**: Continues a specific labeled loop from nested loops
- **Python Comparison**: Basic `break`/`continue` work the same, but labeled statements are Java-specific
- **Performance**: Proper use of loop control can significantly improve algorithm efficiency

## 🔍 Important Concepts

### Break Statement
- Exits the innermost loop containing the `break` statement
- Useful for early termination when a condition is met
- Same functionality as Python's `break`

### Continue Statement
- Skips the current iteration and continues with the next
- Useful for filtering out unwanted iterations
- Same functionality as Python's `continue`

### Labeled Statements
- **Java-specific feature** not available in Python
- Allows breaking from or continuing to a specific labeled loop
- Useful in complex nested loop scenarios
- Syntax: `labelName: for(...)` then `break labelName;` or `continue labelName;`

### Nested Loop Control
- Break and continue affect the innermost loop by default
- Labels allow control of outer loops from inner loops
- Essential for complex algorithms and data processing

## 🔍 Code Examples

### Basic Break
```java
for (int i = 1; i <= 20; i++) {
    if (i % 7 == 0) {
        System.out.println("Found: " + i);
        break; // Exit loop immediately
    }
}
```

### Basic Continue
```java
for (int i = 1; i <= 10; i++) {
    if (i % 2 != 0) {
        continue; // Skip odd numbers
    }
    System.out.println("Even: " + i);
}
```

### Labeled Break
```java
outerLoop: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 5; j++) {
        if (i == 2 && j == 3) {
            break outerLoop; // Break from outer loop
        }
    }
}
```

### Labeled Continue
```java
outerLoop: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            continue outerLoop; // Continue outer loop
        }
    }
}
```

## 📝 Notes for Python Developers

### Similarities with Python
- **Basic `break`**: Works exactly the same as Python
- **Basic `continue`**: Works exactly the same as Python
- **Loop structure**: `for` and `while` loops work similarly

### Key Differences from Python
- **Labeled Statements**: Java supports labeled `break` and `continue` - Python does not
- **Loop Labels**: Java requires explicit labels for labeled statements
- **Nested Loop Control**: Java's labeled statements provide more control than Python's flag-based approach

### Python Equivalent for Labeled Break
```python
# Python equivalent using flag variables
found = False
for i in range(1, 4):
    for j in range(1, 6):
        if i == 2 and j == 3:
            found = True
            break
    if found:
        break
```

### When to Use Each
- **Basic break/continue**: Use for simple loop control (same as Python)
- **Labeled statements**: Use for complex nested loops where you need to control outer loops
- **Performance**: Use `break` for early termination, `continue` for filtering

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
