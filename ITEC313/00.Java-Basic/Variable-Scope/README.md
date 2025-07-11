# Variable-Scope

## 📋 Overview
This project demonstrates the concept of variable scope in Java, including local, instance, and class (static) variables. It is designed for students transitioning from Python to Java, providing clear examples and detailed explanations.

## 📁 Files in this Directory
- `VariableScope.java`: Main source file demonstrating variable scope concepts.
- `Makefile`: Build automation for compiling and running the project.
- `README.md`: Project documentation and instructions.
- `examples/Example1.java`: Example of local and instance variable scope.
- `examples/Example2.java`: Example of class (static) variable scope.
- `examples/Advanced.java`: Advanced example combining all scopes.
- `data/input.txt`: Sample input data (not used for user input, just for demonstration).
- `data/sample.csv`: Sample CSV data file.
- `docs/concepts.md`: Detailed explanation of variable scope concepts.

## 🛠 Building and Running

```bash
make compile   # Compile the program
make run       # Run the program
make clean     # Remove compiled files
```

## 📚 Learning Objectives
- Understand the difference between local, instance, and class (static) variables in Java
- Learn how variable scope affects program behavior
- Compare Java variable scope to Python
- Practice best practices in code organization and commenting

## 🗝️ Key Takeaways
- Java has three main variable scopes: local, instance, and class (static)
- Scope determines the lifetime and accessibility of a variable
- Proper use of scope improves code readability and maintainability

## 📖 Important Concepts
- **Local Variables**: Declared inside methods; only accessible within that method
- **Instance Variables**: Declared in a class but outside methods; each object has its own copy
- **Class (Static) Variables**: Declared with `static`; shared among all instances of the class
- **Scope Lifetime**: Local variables exist only during method execution; instance variables exist as long as the object exists; static variables exist as long as the class is loaded

## 🔍 Code Examples
See `examples/Example1.java`, `examples/Example2.java`, and `examples/Advanced.java` for practical demonstrations.

## 📝 Notes for Python Developers
- In Python, variables declared inside a function are local, similar to Java
- Python class variables are similar to Java static variables, but the syntax differs
- Java requires explicit type declarations; Python is dynamically typed
- Java enforces access control and scope more strictly than Python

## 🚫 Restrictions
- **NO TEST CODE**: No JUnit or test files included
- **NO USER INPUT**: Programs do not prompt for user input
- **EDUCATIONAL FOCUS**: Code is designed for learning, not production
