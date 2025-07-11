# Static-Members

## 📋 Overview

This project demonstrates the concept of static members in Java, including static variables, methods, and blocks. Static members belong to the class itself rather than to instances of the class, making them shared across all objects and accessible without creating an instance.

The project provides comprehensive examples showing how static members work, their use cases, and how they differ from instance members. It's designed as a learning resource for Python developers transitioning to Java.

## 📁 Files in this Directory

```
Static-Members/
├── StaticMembers.java      # Main demonstration program
├── Makefile               # Build automation
├── README.md              # This documentation
├── examples/              # Additional example files
│   ├── Example1.java      # Basic static variables and methods
│   ├── Example2.java      # Static blocks and advanced concepts
│   └── Advanced.java      # Complex static patterns and nested classes
├── data/                  # Sample data files
│   ├── input.txt          # Configuration data
│   └── sample.csv         # Sample CSV data
├── docs/                  # Additional documentation
│   └── concepts.md        # Detailed concept explanations
└── tests/                 # Test files (removed per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the main demonstration
make run

# Run individual examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced

# Clean compiled files
make clean

# Show help
make help
```

## 📚 Learning Objectives

This project teaches:

- **Static Variables**: Understanding how static variables are shared across all instances
- **Static Methods**: Learning to create and use static methods that don't require object creation
- **Static Blocks**: Understanding initialization code that runs when a class is loaded
- **Static Constants**: Working with final static variables for shared constants
- **Static vs Instance**: Understanding the differences between static and instance members
- **Static Nested Classes**: Organizing related static functionality
- **Factory Patterns**: Using static methods to create instances
- **Utility Classes**: Creating classes with only static methods
- **Memory Management**: Understanding the implications of static members on memory
- **Thread Safety**: Considerations when using static members in multi-threaded applications

## 🎯 Key Takeaways

- **Shared State**: Static members are shared across all instances of a class
- **Class-Level Access**: Static methods can be called without creating objects
- **Initialization**: Static blocks run when the class is first loaded
- **Memory Implications**: Static variables are never garbage collected
- **Design Patterns**: Static members enable factory patterns and utility classes
- **Python Comparison**: Understanding differences from Python's class variables and static methods

## 🔍 Important Concepts

### Static Variables
Static variables belong to the class itself, not to instances. They are shared across all objects and maintain a single copy in memory.

```java
public static int instanceCount = 0;  // Shared across all instances
```

### Static Methods
Static methods can be called without creating an instance of the class. They cannot access instance variables directly.

```java
public static void displayInfo() {
    System.out.println("Static method called");
}
```

### Static Blocks
Static blocks run when the class is loaded, before any instances are created. They're used for initialization.

```java
static {
    System.out.println("Class loaded - initializing static members");
}
```

### Static Constants
Final static variables that cannot be changed, typically used for shared constants.

```java
public static final int MAX_INSTANCES = 10;
```

## 🔍 Code Examples

### Basic Static Variable
```java
public class Counter {
    public static int totalCount = 0;  // Shared across all instances
    
    public Counter() {
        totalCount++;  // Increment shared counter
    }
}
```

### Static Method
```java
public static void displayTotalCount() {
    System.out.println("Total count: " + totalCount);
}
```

### Static Block
```java
static {
    System.out.println("Initializing static members...");
    // Complex initialization code
}
```

### Static Factory Method
```java
public static MyClass createDefault() {
    return new MyClass("default");
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Static Keyword**: Java uses the `static` keyword explicitly, while Python uses class variables and decorators
2. **Access Pattern**: Java static members are accessed via class name, Python class variables via class or instance
3. **Method Decorators**: Python uses `@staticmethod` and `@classmethod`, Java uses `static` keyword
4. **Initialization**: Java static blocks vs Python module-level code or `__init_subclass__`
5. **Memory Model**: Java static variables have different memory implications than Python class variables

### Python Equivalents

```python
# Python class variable (equivalent to Java static variable)
class Counter:
    total_count = 0

# Python static method (equivalent to Java static method)
@staticmethod
def display_total_count():
    print(f"Total count: {Counter.total_count}")

# Python class method (equivalent to Java static factory)
@classmethod
def create_default(cls):
    return cls("default")
```

### Learning Path
1. Start with basic static variables and methods
2. Understand static blocks and initialization
3. Learn static nested classes and utility patterns
4. Explore advanced patterns like factory methods
5. Consider memory and thread safety implications

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
