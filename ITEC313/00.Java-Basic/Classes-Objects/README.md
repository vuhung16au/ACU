# Classes-Objects

Class definition, object instantiation, and object-oriented programming fundamentals in Java.

## 📋 Overview

This project demonstrates fundamental object-oriented programming concepts in Java, focusing on class definition, object instantiation, and object relationships. The project includes comprehensive examples showing how to create classes, instantiate objects, and work with object-oriented principles.

**Key Features:**
- Basic class definition and object creation
- Constructor types (default and parameterized)
- Instance variables and methods
- Getter and setter methods
- Object arrays and collections
- Method overloading
- Object comparison and equality
- Static vs instance methods
- Advanced concepts: composition, factory pattern, builder pattern

## 📁 Files in this Directory

```
Classes-Objects/
├── ClassesObjects.java    # Main source code with comprehensive examples
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Basic class and object creation
│   ├── Example2.java     # Advanced concepts (arrays, overloading)
│   └── Advanced.java     # Complex OOP patterns (composition, factory, builder)
├── data/                 # Sample data files
│   ├── input.txt         # Sample input data
│   └── sample.csv        # Sample CSV data
└── docs/                 # Additional documentation
    └── concepts.md       # Detailed concept explanations
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

# Show help
make help
```

## 📚 Learning Objectives

This project teaches:

### Core Concepts
- **Class Definition**: How to define classes with fields, constructors, and methods
- **Object Instantiation**: Creating objects using the `new` keyword
- **Instance Variables**: Declaring and managing object state
- **Methods**: Creating instance methods and static methods
- **Constructors**: Default and parameterized constructors

### Advanced Concepts
- **Encapsulation**: Using private fields with public getters/setters
- **Method Overloading**: Multiple methods with the same name but different parameters
- **Object Arrays**: Working with collections of objects
- **Object Comparison**: Reference vs content equality
- **Static Members**: Class-level variables and methods

### Design Patterns
- **Factory Pattern**: Creating objects through a factory class
- **Builder Pattern**: Complex object construction with fluent interface
- **Composition**: Object relationships and hierarchies

## 🎯 Key Takeaways

### Main Concepts and Skills Gained

1. **Object-Oriented Thinking**: Understanding how to model real-world entities as objects
2. **Class Design**: Creating well-structured classes with appropriate encapsulation
3. **Object Lifecycle**: Managing object creation, usage, and cleanup
4. **Code Organization**: Structuring code using classes and objects
5. **Java Syntax**: Mastering Java-specific syntax for OOP

### Practical Skills
- Writing clean, maintainable object-oriented code
- Understanding the difference between classes and objects
- Implementing proper encapsulation and data hiding
- Using constructors effectively
- Working with object collections and arrays

## 🔍 Important Concepts

### Class vs Object
- **Class**: A blueprint or template that defines the structure and behavior
- **Object**: An instance of a class with specific data values

### Constructor Types
```java
// Default constructor (no parameters)
public Student() {
    this.name = "Unknown";
    this.age = 0;
}

// Parameterized constructor
public Student(String name, int age) {
    this.name = name;
    this.age = age;
}
```

### Instance vs Static Members
- **Instance Members**: Belong to individual objects (use `this`)
- **Static Members**: Belong to the class itself (use class name)

### Method Overloading
```java
public int add(int a, int b) { return a + b; }
public double add(double a, double b) { return a + b; }
public int add(int a, int b, int c) { return a + b + c; }
```

## 🔍 Code Examples

### Basic Class Definition
```java
class Student {
    // Instance variables (fields)
    private String name;
    private int age;
    
    // Constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getter method
    public String getName() {
        return name;
    }
    
    // Setter method
    public void setName(String name) {
        this.name = name;
    }
}
```

### Object Creation and Usage
```java
// Create objects
Student student1 = new Student("Alice", 20);
Student student2 = new Student("Bob", 22);

// Use object methods
student1.setName("Alice Johnson");
System.out.println(student1.getName());
```

### Object Arrays
```java
// Create array of objects
Student[] students = new Student[3];
students[0] = new Student("Alice", 20);
students[1] = new Student("Bob", 22);
students[2] = new Student("Carol", 21);

// Iterate through objects
for (Student student : students) {
    student.displayInfo();
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Object Creation**:
   - **Java**: `Student student = new Student("Alice", 20);`
   - **Python**: `student = Student("Alice", 20)`

2. **Constructor**:
   - **Java**: `public Student(String name, int age) { ... }`
   - **Python**: `def __init__(self, name, age): ...`

3. **Self Reference**:
   - **Java**: `this.name = name;`
   - **Python**: `self.name = name`

4. **Method Overloading**:
   - **Java**: Multiple methods with same name, different parameters
   - **Python**: Use default parameters or different method names

5. **Type Declarations**:
   - **Java**: `private String name;` (explicit types)
   - **Python**: `self.name = name` (dynamic typing)

6. **Access Modifiers**:
   - **Java**: `private`, `public`, `protected`
   - **Python**: Convention-based (single underscore for "private")

### Common Patterns

**Getter/Setter Pattern**:
```java
// Java
private String name;
public String getName() { return name; }
public void setName(String name) { this.name = name; }
```

```python
# Python equivalent
@property
def name(self):
    return self._name

@name.setter
def name(self, value):
    self._name = value
```

**Constructor Chaining**:
```java
// Java
public Student() {
    this("Unknown", 0);  // Call other constructor
}
```

```python
# Python equivalent
def __init__(self, name="Unknown", age=0):
    self.name = name
    self.age = age
```

## 🚀 Best Practices

1. **Encapsulation**: Use private fields with public getters/setters
2. **Constructor Design**: Provide meaningful default and parameterized constructors
3. **Method Naming**: Use clear, descriptive method names
4. **Object Equality**: Override `equals()` and `hashCode()` for custom classes
5. **Documentation**: Comment complex methods and important design decisions

## 🔧 Technical Requirements

- **Java Version**: Java 8 or higher
- **Build System**: Makefile for compilation
- **Documentation**: Comprehensive README with examples
- **Examples**: Multiple example files showing different aspects

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
