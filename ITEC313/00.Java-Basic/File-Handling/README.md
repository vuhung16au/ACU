# File-Handling

File I/O, FileReader, FileWriter, and advanced file operations in Java.

## 📋 Overview

This project demonstrates comprehensive file handling capabilities in Java, including reading from and writing to files, working with different file types, and implementing proper error handling. The examples show both basic and advanced file operations, making it an excellent resource for Python developers transitioning to Java.

## 📁 Files in this Directory

```
File-Handling/
├── FileHandling.java      # Main source code with comprehensive examples
├── Makefile               # Build automation
├── README.md              # This documentation
├── examples/              # Additional example files
│   ├── Example1.java      # Basic file reading operations
│   ├── Example2.java      # File writing operations
│   └── Advanced.java      # Advanced file handling concepts
├── data/                  # Sample data files
│   ├── input.txt          # Sample text file for reading
│   └── sample.csv         # Sample CSV file for parsing
└── docs/                  # Additional documentation
    └── concepts.md        # Detailed concept explanations
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

1. **Basic File Operations**
   - Reading text files line by line
   - Writing content to files
   - Checking file existence and properties
   - Working with file paths and directories

2. **Advanced File Handling**
   - Binary file operations
   - CSV file parsing
   - Properties file handling
   - File copying and moving

3. **Error Handling**
   - Try-catch blocks for file operations
   - Custom exception handling
   - Graceful error recovery
   - Resource management

4. **Java-Specific Concepts**
   - FileReader and FileWriter classes
   - BufferedReader for efficient reading
   - BufferedWriter for efficient writing
   - NIO.2 API for modern file operations

## 🎯 Key Takeaways

- **Resource Management**: Java requires explicit resource management (close() calls)
- **Exception Handling**: File operations must be wrapped in try-catch blocks
- **Buffering**: Use BufferedReader/BufferedWriter for better performance
- **File Types**: Different classes for different file operations (text vs binary)
- **Error Recovery**: Always handle potential file operation failures

## 🔍 Important Concepts

### File Reading in Java
```java
// Basic file reading
FileReader fileReader = new FileReader("file.txt");
BufferedReader bufferedReader = new BufferedReader(fileReader);
String line;
while ((line = bufferedReader.readLine()) != null) {
    // Process line
}
bufferedReader.close();
```

### File Writing in Java
```java
// Basic file writing
FileWriter fileWriter = new FileWriter("file.txt");
BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
bufferedWriter.write("Content");
bufferedWriter.close();
```

### Error Handling
```java
try {
    // File operations
} catch (FileNotFoundException e) {
    // Handle missing file
} catch (IOException e) {
    // Handle other IO errors
}
```

## 🔍 Code Examples

### Reading a File Line by Line
```java
public static void readFileLineByLine(String filename) {
    try {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        
        bufferedReader.close();
        fileReader.close();
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
}
```

### Writing to a File
```java
public static void writeToFile(String filename, String content) {
    try {
        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();
    } catch (IOException e) {
        System.err.println("Error writing file: " + e.getMessage());
    }
}
```

### Checking File Properties
```java
public static void checkFileProperties(String filename) {
    File file = new File(filename);
    
    if (file.exists()) {
        System.out.println("File size: " + file.length() + " bytes");
        System.out.println("Can read: " + file.canRead());
        System.out.println("Can write: " + file.canWrite());
        System.out.println("Last modified: " + new Date(file.lastModified()));
    }
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Resource Management**
   - **Python**: `with open('file.txt', 'r') as f:` (automatic resource management)
   - **Java**: Explicit `close()` calls required

2. **Exception Handling**
   - **Python**: `try/except` blocks
   - **Java**: `try/catch` blocks with specific exception types

3. **File Operations**
   - **Python**: Single `open()` function with mode parameter
   - **Java**: Different classes for different operations (FileReader, FileWriter, etc.)

4. **Buffering**
   - **Python**: Automatic buffering
   - **Java**: Explicit BufferedReader/BufferedWriter for efficiency

5. **File Paths**
   - **Python**: `os.path` module
   - **Java**: `File` class and `Path` interface

### Equivalent Operations

| Python | Java |
|--------|------|
| `open('file.txt', 'r')` | `new FileReader("file.txt")` |
| `f.read()` | `Files.readAllBytes(Paths.get("file.txt"))` |
| `f.write(content)` | `fileWriter.write(content)` |
| `os.path.exists()` | `file.exists()` |
| `shutil.copy()` | `Files.copy()` |

### Best Practices for Python Developers

1. **Always close resources** - Unlike Python's context managers, Java requires explicit cleanup
2. **Use try-catch blocks** - File operations can throw exceptions
3. **Use buffered classes** - BufferedReader/BufferedWriter for better performance
4. **Handle multiple exceptions** - Different exception types for different errors
5. **Check file existence** - Always verify files exist before operations

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
