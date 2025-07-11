# If-Else Conditions

## 📋 Overview

This project demonstrates fundamental conditional logic in Java programming. It covers basic if-else statements, nested conditions, ternary operators, and complex boolean expressions. The examples show how to make decisions in code based on various conditions, from simple comparisons to complex business logic scenarios.

Perfect for students transitioning from Python to Java, this project provides hands-on experience with Java's conditional control structures while highlighting key differences from Python's approach.

## 📁 Files in this Directory

```
If-Else-Conditions/
├── IfElseConditions.java    # Main demonstration program
├── Makefile                 # Build automation
├── README.md               # This documentation
├── examples/               # Additional example files
│   ├── Example1.java      # Basic if-else examples
│   ├── Example2.java      # Advanced conditional logic
│   └── Advanced.java      # Real-world applications
├── data/                  # Sample data files
│   ├── input.txt          # Test data for examples
│   └── sample.csv         # CSV data for testing
└── docs/                  # Documentation
    └── concepts.md        # Detailed concept explanations
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the main program
make run

# Run individual examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced

# Clean compiled files
make clean
```

## 📚 Learning Objectives

This project teaches:

- **Basic If-Else Statements**: Simple conditional execution
- **Nested Conditions**: Complex decision trees with multiple levels
- **Ternary Operators**: Concise conditional assignments
- **Logical Operators**: AND (&&), OR (||), NOT (!) operations
- **Boolean Expressions**: Complex condition combinations
- **Real-World Applications**: Practical business logic examples
- **Python to Java Differences**: Key syntax and conceptual differences
- **Best Practices**: Writing clean, readable conditional code

## 🎯 Key Takeaways

- **Conditional Logic**: How to make decisions in Java programs
- **Code Structure**: Proper organization of if-else chains
- **Performance**: Understanding short-circuit evaluation
- **Readability**: Writing clear and maintainable conditional code
- **Debugging**: Common pitfalls and how to avoid them
- **Real-World Usage**: Applying conditions to practical scenarios

## 🔍 Important Concepts

### 1. Basic If-Else Structure
```java
if (condition) {
    // Code executed when condition is true
} else {
    // Code executed when condition is false
}
```

### 2. Multiple Conditions
```java
if (condition1) {
    // Handle condition1
} else if (condition2) {
    // Handle condition2
} else {
    // Default case
}
```

### 3. Nested Conditions
```java
if (outerCondition) {
    if (innerCondition) {
        // Both conditions true
    } else {
        // Outer true, inner false
    }
} else {
    // Outer condition false
}
```

### 4. Ternary Operator
```java
// Compact conditional assignment
String result = (score >= 60) ? "Pass" : "Fail";
```

### 5. Logical Operators
```java
// AND - both must be true
if (age >= 18 && hasLicense) { }

// OR - at least one must be true
if (isWeekend || isHoliday) { }

// NOT - inverts the condition
if (!isRaining) { }
```

## 🔍 Code Examples

### Temperature Classification
```java
if (temperature > 30) {
    System.out.println("It's hot!");
} else if (temperature > 20) {
    System.out.println("Nice weather!");
} else if (temperature > 10) {
    System.out.println("It's cool.");
} else {
    System.out.println("It's cold!");
}
```

### Grade Calculation
```java
char grade;
if (score >= 90) {
    grade = 'A';
} else if (score >= 80) {
    grade = 'B';
} else if (score >= 70) {
    grade = 'C';
} else if (score >= 60) {
    grade = 'D';
} else {
    grade = 'F';
}
```

### Complex Business Logic
```java
double discount = 0.0;
if (purchaseAmount >= 200) {
    discount = 0.20;
} else if (purchaseAmount >= 100) {
    discount = 0.10;
} else if (purchaseAmount >= 50) {
    discount = 0.05;
}
if (isVIP) {
    discount += 0.05;
}
```

## 📝 Notes for Python Developers

### Syntax Differences
```python
# Python
if x > 10:
    print("Large")
elif x > 5:
    print("Medium")
else:
    print("Small")
```

```java
// Java
if (x > 10) {
    System.out.println("Large");
} else if (x > 5) {
    System.out.println("Medium");
} else {
    System.out.println("Small");
}
```

### Key Differences
1. **Braces Required**: Java requires curly braces `{}` around code blocks
2. **Parentheses Required**: Conditions must be in parentheses `()`
3. **Semicolons**: Statements end with semicolons `;`
4. **Explicit Boolean**: Java requires explicit boolean expressions
5. **String Comparison**: Use `.equals()` instead of `==` for strings

### Boolean Expressions
```python
# Python - truthy/falsy
if name:  # True if name is not empty
    print("Name provided")
```

```java
// Java - explicit boolean
if (!name.isEmpty()) {  // Must be explicit
    System.out.println("Name provided");
}
```

### Ternary Operator
```python
# Python
result = "Pass" if score >= 60 else "Fail"
```

```java
// Java
String result = (score >= 60) ? "Pass" : "Fail";
```

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
