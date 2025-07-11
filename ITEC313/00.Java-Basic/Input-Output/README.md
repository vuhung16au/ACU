# Input-Output

Scanner, BufferedReader, command-line arguments

## 📋 Overview

This project demonstrates various input and output techniques in Java, including console input using Scanner and BufferedReader, command-line argument processing, and formatted output. It serves as a comprehensive guide for Python developers learning Java I/O operations.

## 📁 Files in this Directory

```
Input-Output/
├── InputOutput.java      # Main source code with comprehensive I/O examples
├── Makefile             # Build automation for compilation and execution
├── README.md            # This documentation
├── examples/            # Additional example files
│   ├── Example1.java    # Basic Scanner input examples
│   ├── Example2.java    # BufferedReader and file I/O examples
│   └── Advanced.java    # Advanced I/O techniques and error handling
├── data/                # Sample data files
│   ├── input.txt        # Sample text input data
│   └── sample.csv       # Sample CSV data for file processing
└── docs/                # Additional documentation
    └── concepts.md      # Detailed concept explanations
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the program
make run

# Run with command-line arguments
make run ARGS="hello world 42"

# Clean compiled files
make clean

# Show available make targets
make help
```

## 📚 Learning Objectives

This project teaches:
- **Scanner Class**: Reading different data types from console input
- **BufferedReader**: Efficient text input processing
- **Command-line Arguments**: Processing program arguments
- **Formatted Output**: Using printf for formatted display
- **Input Validation**: Error handling and data validation
- **Error Handling**: Try-catch blocks for robust I/O operations
- **Data Type Conversion**: Converting between different data types
- **String Processing**: Text manipulation and analysis

## 🎯 Key Takeaways

- **Scanner vs BufferedReader**: When to use each for different input scenarios
- **Input Validation**: How to validate user input and handle errors gracefully
- **Formatted Output**: Using printf for professional-looking output
- **Command-line Processing**: How to handle program arguments effectively
- **Error Handling**: Best practices for I/O error management
- **Data Type Safety**: Ensuring correct data types in Java vs Python

## 🔍 Important Concepts

### Scanner Class
The Scanner class provides easy methods to parse primitive types and strings using regular expressions. It's the most convenient way to read input in Java.

```java
Scanner scanner = new Scanner(System.in);
String name = scanner.nextLine();
int age = scanner.nextInt();
```

### BufferedReader
BufferedReader provides efficient reading of characters, arrays, and lines. It's more efficient for reading large amounts of text.

```java
BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
String line = reader.readLine();
```

### Command-line Arguments
Java programs can receive arguments from the command line, accessible through the `args` parameter in the main method.

```java
public static void main(String[] args) {
    for (String arg : args) {
        System.out.println(arg);
    }
}
```

### Formatted Output
Java's printf method provides C-style formatted output with various format specifiers.

```java
System.out.printf("Name: %s, Age: %d, Salary: $%.2f%n", name, age, salary);
```

## 🔍 Code Examples

### Basic Scanner Input
```java
Scanner scanner = new Scanner(System.in);
System.out.print("Enter your name: ");
String name = scanner.nextLine();
System.out.printf("Hello, %s!%n", name);
```

### Input Validation
```java
public static int getValidInt(Scanner scanner) {
    while (!scanner.hasNextInt()) {
        System.out.print("Please enter a valid integer: ");
        scanner.next(); // Consume invalid input
    }
    return scanner.nextInt();
}
```

### Command-line Argument Processing
```java
public static void processCommandLineArguments(String[] args) {
    if (args.length == 0) {
        System.out.println("No arguments provided");
    } else {
        for (int i = 0; i < args.length; i++) {
            System.out.printf("args[%d] = \"%s\"%n", i, args[i]);
        }
    }
}
```

### Formatted Output Examples
```java
// Basic formatting
System.out.printf("Name: %s%n", name);
System.out.printf("Age: %d%n", age);
System.out.printf("Salary: $%.2f%n", salary);

// Advanced formatting
System.out.printf("%-15s: %s%n", "Name", name);
System.out.printf("%-15s: %05d%n", "Age (padded)", age);
System.out.printf("%-15s: $%,15.2f%n", "Salary", salary);
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Input Methods**: 
   - Python: `input()` function
   - Java: Scanner class or BufferedReader

2. **Type Conversion**:
   - Python: Automatic type inference
   - Java: Explicit type conversion required

3. **String Formatting**:
   - Python: f-strings or `.format()`
   - Java: `printf()` with format specifiers

4. **Error Handling**:
   - Python: try/except
   - Java: try/catch with specific exception types

5. **Command-line Arguments**:
   - Python: `sys.argv`
   - Java: `args` parameter in main method

### Java vs Python Syntax Comparison

| Feature | Python | Java |
|---------|--------|------|
| Console Input | `name = input("Name: ")` | `String name = scanner.nextLine();` |
| Integer Input | `age = int(input("Age: "))` | `int age = scanner.nextInt();` |
| String Formatting | `f"Hello {name}"` | `String.format("Hello %s", name)` |
| Print with Format | `print(f"Age: {age}")` | `System.out.printf("Age: %d%n", age)` |
| Command-line Args | `sys.argv[1]` | `args[0]` |

### Best Practices for Python Developers

1. **Always close resources**: Use try-with-resources or explicitly close Scanner/BufferedReader
2. **Handle exceptions**: Java requires explicit exception handling for I/O operations
3. **Use appropriate data types**: Java is strongly typed, so choose the right data type
4. **Validate input**: Always validate user input before processing
5. **Use StringBuilder**: For string concatenation in loops (similar to Python's efficiency)

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
