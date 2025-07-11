# Text-File-Processing

## 📋 Overview

This project demonstrates comprehensive text file processing techniques in Java, covering everything from basic file I/O operations to advanced stream-based processing. It serves as a practical guide for Python developers learning Java file handling concepts.

The project includes multiple examples showing different approaches to reading, writing, and processing text files, with detailed comparisons to Python equivalents.

## 📁 Files in this Directory

```
Text-File-Processing/
├── TextFileProcessing.java    # Main source code with comprehensive examples
├── Makefile                  # Build automation
├── README.md                 # This documentation
├── examples/                 # Additional example files
│   ├── Example1.java        # Basic file reading and writing
│   ├── Example2.java        # Advanced buffered I/O processing
│   └── Advanced.java        # Complex NIO and stream processing
├── data/                    # Sample data files
│   ├── sample.txt           # Basic text file for demonstrations
│   ├── input.txt            # Structured data for processing
│   ├── sample.csv           # CSV data for parsing examples
│   ├── employees.csv        # Employee data (created by main program)
│   ├── config.txt           # Configuration file (created by main program)
│   └── output.txt           # Output file (created by main program)
└── docs/                    # Additional documentation
    └── concepts.md          # Detailed concept explanations
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

- **Basic File I/O**: Reading and writing text files using FileReader/FileWriter
- **Buffered I/O**: Efficient file processing with BufferedReader/BufferedWriter
- **Modern NIO**: Using java.nio.file.Files for modern file operations
- **Stream Processing**: Processing large files using Java Streams API
- **Error Handling**: Proper exception handling for file operations
- **Resource Management**: Using try-with-resources for automatic cleanup
- **File Formats**: Working with different text file formats (CSV, logs, config)
- **Encoding Handling**: Managing different character encodings
- **Batch Processing**: Processing multiple files efficiently

## 🎯 Key Takeaways

- **Java vs Python**: Understanding differences in file handling approaches
- **Performance**: When to use buffered vs unbuffered I/O
- **Best Practices**: Proper resource management and error handling
- **Modern Java**: Leveraging NIO and Streams for efficient processing
- **Real-world Applications**: Practical file processing scenarios

## 🔍 Important Concepts

### File I/O Fundamentals

**Basic File Operations**
- `FileReader`/`FileWriter`: Basic character-based I/O
- `BufferedReader`/`BufferedWriter`: Efficient buffered I/O
- `Files` utility class: Modern NIO file operations

**Resource Management**
```java
// Java: try-with-resources (automatic cleanup)
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    // File operations
} catch (IOException e) {
    // Error handling
}

// Python equivalent: with open('file.txt', 'r') as f:
```

**Exception Handling**
- Java requires explicit exception handling for I/O operations
- Use try-catch blocks to handle IOException
- Always close resources properly

### Advanced Concepts

**Stream Processing**
```java
// Java: Stream-based file processing
Files.lines(Paths.get("file.txt"))
    .filter(line -> !line.isEmpty())
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Python equivalent: [line.upper() for line in open('file.txt') if line.strip()]
```

**NIO Operations**
- `Files.readAllLines()`: Read entire file into List
- `Files.write()`: Write content to file
- `Files.copy()`: Copy files efficiently
- `Files.lines()`: Stream-based line reading

## 🔍 Code Examples

### Basic File Reading
```java
// Read file line by line
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

### File Writing
```java
// Write content to file
try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
    writer.write("Hello, World!\n");
    writer.write("Second line\n");
}
```

### Modern NIO Approach
```java
// Read all lines at once
List<String> lines = Files.readAllLines(Paths.get("file.txt"));

// Write lines to file
Files.write(Paths.get("output.txt"), lines);
```

### Stream Processing
```java
// Process file with streams
Files.lines(Paths.get("data.csv"))
    .skip(1) // Skip header
    .map(line -> line.split(","))
    .filter(parts -> parts.length >= 3)
    .forEach(parts -> System.out.println(parts[0]));
```

## 📝 Notes for Python Developers

### Key Differences from Python

**File Opening**
- **Java**: Explicit resource management with try-with-resources
- **Python**: Context managers with `with` statement

**Exception Handling**
- **Java**: Compulsory exception handling for I/O operations
- **Python**: Optional exception handling (but recommended)

**Buffering**
- **Java**: Explicit buffering with BufferedReader/BufferedWriter
- **Python**: Automatic buffering in file objects

**Stream Processing**
- **Java**: Stream API with functional operations
- **Python**: List comprehensions and generators

**Encoding**
- **Java**: Explicit encoding specification
- **Python**: Default UTF-8, explicit encoding optional

### Python Equivalents

| Python | Java |
|--------|------|
| `with open('file.txt', 'r') as f:` | `try (BufferedReader reader = new BufferedReader(new FileReader("file.txt")))` |
| `f.read()` | `Files.readAllBytes(Paths.get("file.txt"))` |
| `f.readlines()` | `Files.readAllLines(Paths.get("file.txt"))` |
| `for line in f:` | `while ((line = reader.readLine()) != null)` |
| `f.write(content)` | `writer.write(content)` |
| `[line.strip() for line in f]` | `Files.lines(path).map(String::trim).collect(Collectors.toList())` |

### Best Practices

1. **Always use try-with-resources** for automatic resource cleanup
2. **Handle exceptions properly** - don't ignore IOException
3. **Use buffered I/O** for better performance with large files
4. **Consider encoding** when working with international text
5. **Use NIO Files utility** for simple operations
6. **Leverage streams** for functional processing of large datasets

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
