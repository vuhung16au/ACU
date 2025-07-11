# Switch-Statement

## 📋 Overview

This project demonstrates the use of switch statements in Java, including traditional switch statements and enhanced switch expressions. Switch statements provide a structured way to handle multiple conditional branches, making code more readable and maintainable than long if-else chains.

For Python developers, switch statements are similar to if-elif-else chains but with more structured syntax and additional features like fall-through behavior and expression capabilities.

## 📁 Files in this Directory

```
Switch-Statement/
├── SwitchStatement.java    # Main source code with comprehensive examples
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Basic switch patterns
│   ├── Example2.java     # Advanced switch patterns
│   └── Advanced.java     # Complex switch scenarios
├── data/                 # Sample data files
│   ├── sample.txt        # Test data for examples
│   └── input.txt         # Structured input data
└── docs/                 # Additional documentation
    └── concepts.md       # Detailed concept explanations
```

## 🛠 Building and Running

```bash
# Compile the main program
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
- **Traditional Switch Statements**: Basic syntax and structure
- **Break Statements**: Understanding fall-through behavior and control flow
- **Default Cases**: Handling unmatched conditions
- **Enhanced Switch Expressions**: Modern Java 14+ syntax
- **Multiple Values**: Concise case handling
- **Enum Integration**: Type-safe switch with enums
- **Nested Switches**: Complex decision logic
- **Switch with Yield**: Advanced expression patterns
- **Real-world Applications**: Practical use cases

## 🎯 Key Takeaways

- **Structured Control Flow**: Switch statements provide organized multi-way branching
- **Fall-through Behavior**: Understanding when and how to use break statements
- **Expression Capabilities**: Modern switch can return values like conditional expressions
- **Type Safety**: Enums provide compile-time safety for switch cases
- **Performance**: Switch statements can be more efficient than if-else chains
- **Readability**: Clean, structured syntax for multiple conditions

## 🔍 Important Concepts

### Traditional Switch Statement
```java
switch (expression) {
    case value1:
        // code block
        break;
    case value2:
        // code block
        break;
    default:
        // default code block
        break;
}
```

### Enhanced Switch Expression (Java 14+)
```java
String result = switch (value) {
    case 1 -> "One";
    case 2 -> "Two";
    default -> "Unknown";
};
```

### Switch with Yield
```java
String grade = switch (score / 10) {
    case 10, 9 -> {
        if (score == 100) {
            yield "Perfect A+";
        } else {
            yield "A";
        }
    }
    case 8 -> "B";
    default -> "F";
};
```

## 🔍 Code Examples

### Basic Switch with Break
```java
int day = 3;
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    default:
        System.out.println("Invalid day");
        break;
}
```

### Switch with Fall-through
```java
switch (month) {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
        System.out.println("31 days");
        break;
    case 2:
        System.out.println("28 or 29 days");
        break;
    default:
        System.out.println("30 days");
        break;
}
```

### Enhanced Switch Expression
```java
String category = switch (num) {
    case 1, 2, 3 -> "Small";
    case 4, 5, 6 -> "Medium";
    case 7, 8, 9 -> "Large";
    default -> "Huge";
};
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Syntax Structure**: Java switch uses `case` and `break` instead of `elif`
2. **Fall-through Behavior**: Java cases fall through without `break` (unlike Python's `elif`)
3. **Expression Capability**: Modern Java switch can return values like Python's conditional expressions
4. **Type Safety**: Java switch with enums provides compile-time safety
5. **Multiple Values**: Java allows multiple values in a single case: `case 1, 2, 3:`

### Python Equivalent Patterns

**Python if-elif-else:**
```python
if day == 1:
    print("Monday")
elif day == 2:
    print("Tuesday")
elif day == 3:
    print("Wednesday")
else:
    print("Invalid day")
```

**Java switch:**
```java
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    default:
        System.out.println("Invalid day");
        break;
}
```

**Python conditional expression:**
```python
result = "Even" if num % 2 == 0 else "Odd"
```

**Java enhanced switch:**
```java
String result = switch (num % 2) {
    case 0 -> "Even";
    case 1 -> "Odd";
    default -> "Unknown";
};
```

### Best Practices

1. **Always use break** unless fall-through is intentional
2. **Include default case** for unmatched conditions
3. **Use enhanced switch** for simple value assignments
4. **Consider enums** for type-safe switch statements
5. **Keep cases simple** and readable

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
