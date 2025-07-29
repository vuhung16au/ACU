# Binary I/O CLI Demonstration

This is a command-line interface (CLI) version of the Binary I/O demonstration that showcases various binary I/O operations in Java.

## Overview

The CLI version provides an interactive menu-driven interface to demonstrate the following binary I/O concepts:

1. **File Stream Operations** - Basic file input/output streams
2. **Data Stream Operations** - Reading/writing primitive data types
3. **File Copy Operations** - Buffered file copying
4. **Object Stream Operations** - Serialization and deserialization
5. **Array Object Streams** - Working with arrays in object streams
6. **Random Access File Operations** - Direct file access and modification

## Features

- **Interactive Menu System** - Easy-to-use command-line interface
- **Comprehensive Demonstrations** - All major binary I/O concepts covered
- **Error Handling** - Robust error handling with user-friendly messages
- **File Management** - Automatic creation and cleanup of demonstration files
- **Cross-Platform** - Works on Windows, macOS, and Linux

## Running the CLI Demo

### Prerequisites

- Java 24 or later
- Maven 3.9.x or later

### Quick Start

#### On macOS/Linux:
```bash
./run-cli.sh
```

#### On Windows:
```cmd
run-cli.bat
```

#### Using Maven directly:
```bash
mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo"
```

### Menu Options

When you run the CLI demo, you'll see the following menu:

```
=== Available Demonstrations ===
1. Test File Stream
2. Test Data Stream
3. Copy File
4. Test Object Output Stream
5. Test Object Input Stream
6. Test Object Stream for Array
7. Test Random Access File
8. Run All Demonstrations
9. Exit
```

## Demonstration Details

### 1. Test File Stream
- Writes integers 1-10 to a binary file
- Reads and displays the values from the file
- Demonstrates basic `FileInputStream` and `FileOutputStream`

### 2. Test Data Stream
- Writes student data (names and scores) using `DataOutputStream`
- Reads the data back using `DataInputStream`
- Shows how to work with primitive data types in binary format

### 3. Copy File
- Creates a test source file with text content
- Copies the file using buffered streams
- Reports the number of bytes copied
- Demonstrates efficient file copying with `BufferedInputStream` and `BufferedOutputStream`

### 4. Test Object Output Stream
- Writes a string, double, and Date object to a file
- Uses `ObjectOutputStream` for serialization
- Shows object serialization capabilities

### 5. Test Object Input Stream
- Reads the serialized objects back from the file
- Uses `ObjectInputStream` for deserialization
- Demonstrates object deserialization

### 6. Test Object Stream for Array
- Writes arrays of integers and strings to a file
- Reads the arrays back and displays their contents
- Shows how to serialize and deserialize arrays

### 7. Test Random Access File
- Creates a file with 200 integers
- Demonstrates random access operations:
  - Reading specific positions
  - Modifying data in place
  - Appending new data
  - Seeking to different positions

### 8. Run All Demonstrations
- Executes all demonstrations in sequence
- Provides a comprehensive overview of all binary I/O operations

## Generated Files

The demonstrations create several files in the project directory:

- `temp.dat` - Used by file stream and data stream demos
- `source.txt` and `target.txt` - Used by copy demo
- `object.dat` - Used by object stream demos
- `array.dat` - Used by array object stream demo
- `inout.dat` - Used by random access file demo

## Technical Implementation

### Key Classes Used

- `FileInputStream` / `FileOutputStream` - Basic file I/O
- `DataInputStream` / `DataOutputStream` - Primitive data type I/O
- `BufferedInputStream` / `BufferedOutputStream` - Buffered I/O
- `ObjectInputStream` / `ObjectOutputStream` - Object serialization
- `RandomAccessFile` - Random access file operations

### Error Handling

The CLI version includes comprehensive error handling:
- IOException handling for file operations
- ClassNotFoundException handling for object deserialization
- User-friendly error messages with checkmarks (✓) and crosses (✗)

### User Interface

- Clean, intuitive menu system
- Clear section headers for each demonstration
- Progress indicators and success/failure messages
- Pause between demonstrations for better readability

## Comparison with JavaFX Version

| Feature | CLI Version | JavaFX Version |
|---------|-------------|----------------|
| Interface | Command-line menu | Graphical user interface |
| User Interaction | Keyboard input | Mouse clicks |
| Output Display | Console text | Text area with scrolling |
| File Management | Automatic | Automatic |
| Error Handling | Console messages | GUI alerts |
| Cross-platform | Yes | Yes |

## Building from Source

```bash
# Compile the project
mvn compile

# Run the CLI demo
mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo"

# Package the application
mvn package
```

## Troubleshooting

### Common Issues

1. **Java not found**: Ensure Java 24+ is installed and in PATH
2. **Maven not found**: Ensure Maven 3.9+ is installed and in PATH
3. **Permission denied**: Make sure the run script is executable (`chmod +x run-cli.sh`)
4. **File access errors**: Ensure the application has write permissions in the project directory

### Getting Help

If you encounter issues:
1. Check that Java and Maven are properly installed
2. Verify you're running the script from the project directory
3. Check file permissions on the run scripts
4. Review the console output for specific error messages

## License

This project is part of the JavaFX learning materials and follows the same licensing terms as the main project. 