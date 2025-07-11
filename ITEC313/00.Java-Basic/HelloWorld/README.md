# HelloWorld Java Program

A simple "Hello, World!" program in Java that demonstrates fundamental programming concepts for the ITEC313 Object-Oriented Programming course.

## 📋 Overview

This program serves as an introduction to Java programming, showcasing:
- Basic Java class structure
- Main method implementation
- Console output operations
- String variables and concatenation
- Professional commenting practices

## 📁 Files in this Directory

```
HelloWorld/
├── HelloWorld.java    # Java source code with detailed comments
├── Makefile          # GNU Makefile for building and running
├── HelloWorld.class  # Compiled bytecode (generated after compilation)
└── README.md         # This documentation file
```

## 🔍 Code Explanation

### Class Structure
```java
public class HelloWorld {
    public static void main(String[] args) {
        // Program logic here
    }
}
```

The program demonstrates:

1. **Class Declaration**: `public class HelloWorld` - Defines a public class named HelloWorld
2. **Main Method**: Entry point for Java applications with the signature `public static void main(String[] args)`
3. **Console Output**: Uses `System.out.println()` to display text
4. **Variables**: Demonstrates string and integer variable usage
5. **String Concatenation**: Shows how to combine strings using the `+` operator

### Key Learning Points

- **Public Access Modifier**: Makes the class accessible from anywhere
- **Static Method**: Allows the method to be called without creating an object instance
- **Void Return Type**: Indicates the method doesn't return any value
- **String Array Parameter**: `args` parameter receives command-line arguments
- **System.out.println()**: Standard method for console output in Java

## 🛠 Building and Running

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **GNU Make** (optional, for using the Makefile)

### Method 1: Using Makefile (Recommended)

```bash
# Compile the program
make compile

# Run the program
make run

# Clean compiled files
make clean

# View all available commands
make help
```

### Method 2: Manual Compilation

```bash
# Compile the Java source file
javac HelloWorld.java

# Run the compiled program
java HelloWorld

# Clean up (remove class files)
rm *.class
```

### Method 3: IDE Integration

Most Java IDEs (IntelliJ IDEA, Eclipse, VS Code) can:
1. Automatically compile the source file
2. Run the program with a single click
3. Provide debugging capabilities

## 📤 Expected Output

When you run the program, you should see:

```
Hello, World!
Welcome to Java programming!
Course: ITEC313 - Object-Oriented Programming
Year: 2025
=====================================
This program demonstrates:
1. Basic Java class structure
2. Main method implementation
3. Console output using System.out.println()
4. String variables and concatenation
5. Basic commenting practices
```

## 🔧 Makefile Targets

The included Makefile provides several useful targets:

| Target | Description |
|--------|-------------|
| `make` or `make all` | Compile the program (default) |
| `make compile` | Explicitly compile the Java source |
| `make run` | Compile and execute the program |
| `make clean` | Remove all compiled `.class` files |
| `make help` | Display usage information |
| `make check-java` | Verify Java installation |
| `make show-source` | Display the source code |
| `make compile-verbose` | Compile with verbose output |

## 🐛 Troubleshooting

### Common Issues

1. **"javac not found" or "java not found"**
   ```bash
   # Check if Java is installed
   java -version
   javac -version
   
   # If not installed, download JDK from:
   # https://www.oracle.com/java/technologies/downloads/
   ```

2. **"Could not find or load main class HelloWorld"**
   ```bash
   # Ensure you're in the correct directory
   ls -la *.class
   
   # Recompile the program
   javac HelloWorld.java
   ```

3. **Permission denied errors**
   ```bash
   # Check file permissions
   ls -la HelloWorld.java
   
   # Make file readable if needed
   chmod 644 HelloWorld.java
   ```

### Compilation Warnings

The program compiles cleanly without warnings. If you see warnings:
- Check for typos in the source code
- Ensure proper Java syntax
- Verify the class name matches the filename

## 📚 Educational Notes

### Why "Hello, World!"?

The "Hello, World!" program is a programming tradition that:
- Demonstrates basic syntax
- Verifies the development environment
- Provides a simple first program for beginners
- Shows the minimal structure needed for a working program

### Java Fundamentals Demonstrated

1. **Class-based Structure**: Everything in Java is part of a class
2. **Public Access**: Classes and methods can be made accessible
3. **Static Methods**: Can be called without object instantiation
4. **String Handling**: Basic string operations and output
5. **Method Signature**: Understanding parameter types and return values

## 🚀 Next Steps

After understanding this program, you can:

1. **Modify the output** - Change the messages printed
2. **Add user input** - Use `Scanner` to read user input
3. **Add methods** - Create additional methods in the class
4. **Add variables** - Experiment with different data types
5. **Add control structures** - Use if statements, loops, etc.

## 📖 Related Documentation

- [Main Repository README](../../README.md) - Overall project documentation
- [Java Setup Guide](../../00.Overview-Preparation/00.Setup-Java.md) - Environment setup
- [Java IDE Guide](../../00.Overview-Preparation/01.Java-IDE.md) - IDE recommendations

## 📝 Assignment Notes

This program serves as:
- A template for basic Java programs
- An introduction to the development workflow
- A foundation for more complex programming concepts
- Practice with compilation and execution

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
