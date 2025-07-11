# Encapsulation

Private fields, getters/setters, data hiding

## 📋 Overview

This project demonstrates encapsulation in Java - one of the four fundamental principles of Object-Oriented Programming (OOP). Encapsulation combines data (fields) and methods that operate on that data into a single unit (class) and hides the internal state from outside access.

The project provides comprehensive examples showing how to implement proper encapsulation using private fields, public getter and setter methods, validation logic, and controlled access to object state. It's designed as a learning resource for Python developers transitioning to Java.

## 📁 Files in this Directory

```
Encapsulation/
├── Encapsulation.java    # Main source code with comprehensive examples
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Simple Rectangle encapsulation
│   ├── Example2.java     # Advanced Car encapsulation
│   └── Advanced.java     # Complex Shopping Cart system
├── data/                 # Sample data files
│   ├── input.txt         # Sample input data
│   └── sample.csv        # Structured data examples
├── docs/                 # Additional documentation
│   └── concepts.md       # Detailed concept explanations
└── tests/                # (Removed - no test files per requirements)
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

1. **Data Hiding**: Understanding how to use private fields to hide internal state
2. **Controlled Access**: Implementing public getter and setter methods for controlled access
3. **Validation Logic**: Adding validation in setter methods to ensure data integrity
4. **Business Logic Encapsulation**: Encapsulating complex operations in public methods
5. **Computed Properties**: Creating getter methods that compute values from private data
6. **State Management**: Controlling how object state changes over time
7. **Access Modifiers**: Understanding private, public, and other access levels
8. **Best Practices**: Following Java encapsulation conventions and patterns

## 🎯 Key Takeaways

- **Encapsulation is fundamental**: It's one of the four pillars of OOP
- **Private fields protect data**: Use private access modifier to hide internal state
- **Public methods provide access**: Use getters and setters for controlled access
- **Validation ensures integrity**: Always validate input in setter methods
- **Business logic belongs in methods**: Encapsulate complex operations
- **Computed properties are powerful**: Use getters to provide calculated values
- **Consistency is key**: Always use getter/setter methods, never direct field access

## 🔍 Important Concepts

### 1. Data Hiding
Encapsulation hides the internal representation of an object and only exposes what is necessary. In Java, this is achieved through:
- **Private fields**: Cannot be accessed directly from outside the class
- **Public methods**: Provide controlled access to private data
- **Information protection**: Prevents direct manipulation of object state

### 2. Getter and Setter Methods
- **Getters**: Public methods that return private field values
- **Setters**: Public methods that modify private fields with validation
- **Validation logic**: Ensure data integrity through setter methods

### 3. Business Logic Encapsulation
- **Complex operations**: Encapsulate complex operations in public methods
- **State management**: Control how object state changes over time
- **Computed properties**: Provide calculated values based on private data

### 4. Access Modifiers
- **private**: Only accessible within the same class
- **public**: Accessible from anywhere
- **protected**: Accessible within same package and subclasses
- **default**: Accessible within same package only

## 🔍 Code Examples

### Basic Encapsulation
```java
class Student {
    private String name;    // Private field
    private int age;        // Private field
    
    // Public getter
    public String getName() {
        return name;
    }
    
    // Public setter with validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
}
```

### Computed Properties
```java
class Rectangle {
    private double width;
    private double height;
    
    // Computed property (like @property in Python)
    public double getArea() {
        return width * height;
    }
    
    public double getPerimeter() {
        return 2 * (width + height);
    }
}
```

### Business Logic Encapsulation
```java
class BankAccount {
    private double balance;
    
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Access Control**:
   - **Python**: Uses naming conventions (`_private`) but no enforcement
   - **Java**: Uses access modifiers (`private`, `public`) with compiler enforcement

2. **Property Access**:
   - **Python**: Uses `@property` decorator for computed attributes
   - **Java**: Uses getter methods that compute values

3. **Validation**:
   - **Python**: Uses `@property.setter` for validation
   - **Java**: Uses setter methods with validation logic

4. **Naming Conventions**:
   - **Python**: `get_name()`, `set_name()` or `@property`
   - **Java**: `getName()`, `setName()` (camelCase)

### Example Comparison

**Python:**
```python
class Student:
    def __init__(self):
        self._name = ""
        self._age = 0
    
    @property
    def name(self):
        return self._name
    
    @name.setter
    def name(self, value):
        if value and value.strip():
            self._name = value
```

**Java:**
```java
class Student {
    private String name;
    private int age;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
}
```

### Migration Tips

1. **Always use private fields**: Unlike Python's convention-based privacy
2. **Provide getters and setters**: Even for simple fields
3. **Add validation in setters**: Java doesn't have property decorators
4. **Use computed getters**: For derived values instead of Python's `@property`
5. **Follow Java naming conventions**: camelCase for methods, PascalCase for classes

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
