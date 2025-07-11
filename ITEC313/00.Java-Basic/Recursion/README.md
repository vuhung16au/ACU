# Recursion

## 📋 Overview
This project demonstrates recursion in Java, covering base cases, the call stack, and practical recursive algorithms. It is designed for students transitioning from Python to Java.

## 📁 Files in this Directory
```
Recursion/
├── Recursion.java      # Main source code
├── Makefile            # Build automation
├── README.md           # Documentation
├── examples/           # Additional example files
│   ├── Example1.java   # Simple recursion example
│   ├── Example2.java   # Recursion with arrays
│   └── Advanced.java   # Advanced recursion (e.g., backtracking)
├── data/               # Sample data files
│   ├── input.txt       # Input data for examples
│   └── sample.csv      # Sample CSV data
└── docs/
    └── concepts.md     # Concepts and explanations
```

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
- Understand recursion and base cases in Java
- Trace the call stack and recursive calls
- Apply recursion to solve practical problems
- Compare recursion in Java and Python
- Recognize common pitfalls and best practices

## 🗝️ Key Takeaways
- Recursion involves a method calling itself
- Every recursion must have a base case
- The call stack tracks recursive calls
- Recursion can be less efficient than iteration in Java
- Java requires explicit type declarations (unlike Python)

## 📖 Important Concepts
- Base case and recursive case
- Stack overflow and recursion depth
- Tail recursion (and Java's lack of optimization)
- When to use recursion vs. iteration

## 🔍 Code Examples
```java
// Factorial (simple recursion)
public static int factorial(int n) {
    if (n == 0) return 1; // base case
    return n * factorial(n - 1); // recursive case
}

// Fibonacci (inefficient recursion)
public static int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

## 📝 Notes for Python Developers
- Java requires explicit types: `int`, `double`, etc.
- No default support for big integers (use `BigInteger` for large values)
- No list slicing or dynamic typing
- Recursion depth is limited by the JVM stack size
- Use comments to clarify base and recursive cases
