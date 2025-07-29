# JavaFX File Class Demo - Concepts and Design Decisions

## Overview

This JavaFX application demonstrates various File class operations from Java I/O, including file properties, reading, writing, and text replacement. The application provides an interactive GUI for exploring these concepts.

## Main Concepts

### 1. File Class Operations

The `File` class in Java provides an abstraction for file and directory pathnames. It allows you to:
- Check file existence and properties
- Get file size, permissions, and metadata
- Determine if a file is a directory or regular file
- Get absolute paths and last modified dates

### 2. Text I/O Operations

#### Writing Data (PrintWriter)
- `PrintWriter` is used for writing formatted text to files
- Provides methods like `print()`, `println()`, and `printf()`
- Automatically handles character encoding
- Must be explicitly closed to ensure data is written

#### Reading Data (Scanner)
- `Scanner` is used for reading formatted data from files
- Provides methods like `next()`, `nextInt()`, `hasNext()`
- Can use custom delimiters with `useDelimiter()`
- Must be explicitly closed to free resources

### 3. Try-with-Resources

Introduced in Java 7, try-with-resources automatically closes resources:
- Prevents resource leaks
- Ensures proper cleanup even if exceptions occur
- More concise than manual try-catch-finally blocks
- Works with any class that implements `AutoCloseable`

### 4. Text Replacement

Demonstrates reading from one file and writing to another:
- Uses `replaceAll()` for string replacement
- Handles file existence checks
- Shows proper error handling
- Demonstrates command-line argument processing

## Design Decisions

### 1. Modular Architecture

The application is structured with:
- **Demo Classes**: Each example is encapsulated in its own class
- **Controller**: Handles UI interactions and coordinates demos
- **FXML**: Separates UI layout from logic
- **CSS**: Provides consistent styling

### 2. User Interface Design

- **Tabbed Interface**: Each demo has its own tab for organization
- **Interactive Elements**: Buttons trigger specific operations
- **Output Area**: Shows results and error messages
- **Form Fields**: Allow user input for file paths and parameters

### 3. Error Handling

- Graceful handling of file operations
- User-friendly error messages
- Validation of user inputs
- Proper resource cleanup

### 4. Cross-Platform Compatibility

- Maven-based build system
- Platform-specific JavaFX dependencies
- Consistent behavior across operating systems
- Proper path handling

## Key Learning Objectives

1. **File Properties**: Understanding how to inspect file metadata
2. **Text I/O**: Learning to read and write text files
3. **Resource Management**: Using try-with-resources for automatic cleanup
4. **Error Handling**: Proper exception handling in I/O operations
5. **User Interface**: Creating interactive applications with JavaFX

## Technical Implementation

### Package Structure
```
com.acu.javafx.fileclass
├── Launcher.java                    # Application entry point
├── FileClassDemoController.java     # Main controller
└── demo/
    ├── TestFileClass.java          # File properties demo
    ├── WriteData.java              # Basic file writing
    ├── WriteDataWithAutoClose.java # Try-with-resources demo
    ├── ReadData.java               # File reading demo
    └── ReplaceText.java            # Text replacement demo
```

### Build System
- **Maven**: Dependency management and build automation
- **JavaFX Maven Plugin**: Handles JavaFX runtime
- **Cross-platform**: Automatic platform detection
- **Executable JAR**: Can be packaged as standalone application

This design provides a comprehensive learning environment for understanding Java File I/O operations through hands-on experimentation. 