# Instance Variables in Java

## Overview

Instance variables (also called fields or member variables) are variables declared within a class but outside any method. They belong to individual instances of the class and maintain their values throughout the object's lifetime.

## Key Concepts

### 1. Declaration and Scope

Instance variables are declared at the class level:
```java
public class Employee {
    private String name;        // Instance variable
    private int age;           // Instance variable
    private double salary;     // Instance variable
    private boolean isActive;  // Instance variable
}
```

### 2. Default Values

Instance variables receive default values when declared:
- `int`, `long`, `short`, `byte`: `0`
- `float`, `double`: `0.0`
- `boolean`: `false`
- Object references: `null`
- `char`: `'\u0000'`

### 3. The 'this' Keyword

The `this` keyword refers to the current instance of the class:
```java
public void setName(String name) {
    this.name = name;  // 'this' distinguishes instance variable from parameter
}
```

### 4. Initialization

Instance variables can be initialized in several ways:
```java
public class Employee {
    private String name = "Unknown";           // Direct initialization
    private int age;                          // Default value (0)
    private double salary = 50000.0;          // Direct initialization
    private boolean isActive = true;           // Direct initialization
    
    public Employee(String name, int age) {
        this.name = name;                     // Constructor initialization
        this.age = age;
    }
}
```

## Comparison with Python

| Java | Python | Description |
|------|--------|-------------|
| `private String name;` | `self.name = None` | Declaration |
| `this.name = name;` | `self.name = name` | Assignment |
| `return name;` | `return self.name` | Access |
| `private static int count;` | `count = 0` (class level) | Static variable |

## Best Practices

### 1. Encapsulation
- Use `private` access modifier for instance variables
- Provide public getter and setter methods
- Validate data in setter methods

### 2. Naming Conventions
- Use descriptive names
- Follow camelCase convention
- Avoid single-letter names (except for loops)

### 3. Initialization
- Initialize in constructors when possible
- Use meaningful default values
- Consider using builder pattern for complex objects

## Common Patterns

### 1. Builder Pattern
```java
public class Employee {
    private String name;
    private int age;
    private double salary;
    
    public static class Builder {
        private String name;
        private int age;
        private double salary;
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }
        
        public Employee build() {
            return new Employee(this);
        }
    }
}
```

### 2. Immutable Objects
```java
public final class Employee {
    private final String name;
    private final int age;
    private final double salary;
    
    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    
    // Only getters, no setters
}
```

## Memory Management

### 1. Object Lifecycle
- Instance variables are created when object is instantiated
- They persist throughout the object's lifetime
- They are destroyed when the object is garbage collected

### 2. Memory Allocation
- Instance variables are stored in heap memory
- Each object instance has its own copy of instance variables
- Static variables are shared across all instances

## Common Mistakes

### 1. Shadowing
```java
public void setName(String name) {
    name = name;  // WRONG: parameter shadows instance variable
    this.name = name;  // CORRECT: use 'this' keyword
}
```

### 2. Forgetting 'this'
```java
public void setAge(int age) {
    age = age;  // WRONG: assigns parameter to itself
    this.age = age;  // CORRECT: assigns to instance variable
}
```

### 3. Not Initializing
```java
public class Employee {
    private String name;  // Will be null
    private int age;      // Will be 0
    
    public void displayInfo() {
        System.out.println(name.length());  // NullPointerException!
    }
}
```

## Advanced Topics

### 1. Static vs Instance Variables
```java
public class Employee {
    private String name;           // Instance variable (per object)
    private static int count = 0;  // Static variable (shared)
    
    public Employee(String name) {
        this.name = name;
        count++;  // Increment shared counter
    }
}
```

### 2. Final Instance Variables
```java
public class Employee {
    private final String id;       // Must be initialized in constructor
    private final String name;     // Cannot be changed after initialization
    
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

### 3. Instance Variable Inheritance
```java
public class Person {
    protected String name;  // Accessible to subclasses
    private int age;       // Not accessible to subclasses
}

public class Employee extends Person {
    private double salary;
    
    public Employee(String name, int age, double salary) {
        this.name = name;  // Can access protected variable
        // this.age = age;  // ERROR: cannot access private variable
        this.salary = salary;
    }
}
```
