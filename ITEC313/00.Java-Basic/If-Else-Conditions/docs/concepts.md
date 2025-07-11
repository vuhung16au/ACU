# If-Else Conditions - Concepts

## Overview

If-else conditions are fundamental control structures in Java that allow programs to make decisions based on boolean expressions. They enable conditional execution of code blocks, making programs dynamic and responsive to different situations.

## Key Concepts

### 1. Basic If Statement
The simplest form of conditional execution:
```java
if (condition) {
    // Code to execute if condition is true
}
```

### 2. If-Else Statement
Provides an alternative path when the condition is false:
```java
if (condition) {
    // Code to execute if condition is true
} else {
    // Code to execute if condition is false
}
```

### 3. If-Else-If Chain
Handles multiple mutually exclusive conditions:
```java
if (condition1) {
    // Code for condition1
} else if (condition2) {
    // Code for condition2
} else if (condition3) {
    // Code for condition3
} else {
    // Default code
}
```

### 4. Nested If Statements
If statements can be nested inside other if statements:
```java
if (outerCondition) {
    if (innerCondition) {
        // Code for both conditions true
    } else {
        // Code for outer true, inner false
    }
} else {
    // Code for outer condition false
}
```

### 5. Ternary Operator
A concise way to assign values based on conditions:
```java
// Syntax: condition ? valueIfTrue : valueIfFalse
String result = (score >= 60) ? "Pass" : "Fail";
```

## Logical Operators

### AND Operator (&&)
Both conditions must be true:
```java
if (age >= 18 && hasLicense) {
    // Can drive
}
```

### OR Operator (||)
At least one condition must be true:
```java
if (isWeekend || isHoliday) {
    // No work today
}
```

### NOT Operator (!)
Inverts the boolean value:
```java
if (!isRaining) {
    // Good weather for outdoor activities
}
```

## Best Practices

### 1. Clear and Readable Conditions
- Use descriptive variable names
- Break complex conditions into multiple lines
- Use parentheses for clarity

### 2. Proper Indentation
- Maintain consistent indentation
- Use braces even for single statements
- Follow Java coding conventions

### 3. Avoid Deep Nesting
- Limit nesting to 3-4 levels maximum
- Consider using early returns
- Extract complex logic into methods

### 4. Use Appropriate Comparison
- Use `equals()` for String comparison
- Use `==` for primitive types
- Be careful with floating-point comparisons

## Common Pitfalls

### 1. Assignment vs Comparison
```java
// WRONG - assigns value instead of comparing
if (x = 5) { }

// CORRECT - compares values
if (x == 5) { }
```

### 2. String Comparison
```java
// WRONG - compares object references
if (str == "hello") { }

// CORRECT - compares string content
if (str.equals("hello")) { }
```

### 3. Floating-Point Comparison
```java
// WRONG - may fail due to precision
if (x == 0.1) { }

// CORRECT - use tolerance
if (Math.abs(x - 0.1) < 0.0001) { }
```

## Python to Java Differences

### 1. Syntax Differences
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

### 2. Boolean Expressions
```python
# Python - truthy/falsy values
if name:  # True if name is not empty
    print("Name provided")
```

```java
// Java - explicit boolean required
if (!name.isEmpty()) {  // Must be explicit
    System.out.println("Name provided");
}
```

### 3. Ternary Operator
```python
# Python
result = "Pass" if score >= 60 else "Fail"
```

```java
// Java
String result = (score >= 60) ? "Pass" : "Fail";
```

## Real-World Applications

### 1. Input Validation
```java
if (age < 0 || age > 150) {
    System.out.println("Invalid age");
} else if (age < 18) {
    System.out.println("Minor");
} else {
    System.out.println("Adult");
}
```

### 2. Business Logic
```java
if (purchaseAmount >= 200) {
    discount = 0.20;
} else if (purchaseAmount >= 100) {
    discount = 0.10;
} else {
    discount = 0.05;
}
```

### 3. Error Handling
```java
if (file != null && file.exists()) {
    // Process file
} else {
    // Handle error
}
```

## Performance Considerations

### 1. Short-Circuit Evaluation
- `&&` and `||` use short-circuit evaluation
- Right side is only evaluated if necessary
- Order conditions from most likely to least likely

### 2. Switch vs If-Else
- Use switch for multiple equality checks
- Use if-else for complex conditions
- Switch is generally faster for simple cases

## Further Reading

- Oracle Java Documentation: Control Flow Statements
- Java Language Specification: Chapter 14
- Effective Java by Joshua Bloch
- Clean Code by Robert C. Martin
