# Binary I/O Concepts

## Overview

This project demonstrates various Binary I/O operations in Java, showcasing how to read and write data in binary form for program consumption rather than human readability.

## Key Concepts

### 1. Binary I/O vs Text I/O

**Binary I/O**:
- Data is stored in binary form (bytes)
- Cannot be read directly by humans
- More efficient for program processing
- No encoding/decoding conversions required
- Exact byte values are preserved

**Text I/O**:
- Data is human-readable
- Requires character encoding/decoding
- Less efficient for program processing
- Subject to platform-specific encoding issues

### 2. Stream Classes

#### Base Stream Classes
- **InputStream**: Base class for reading bytes
- **OutputStream**: Base class for writing bytes

#### File Streams
- **FileInputStream**: Reads bytes from a file
- **FileOutputStream**: Writes bytes to a file

#### Filter Streams
- **DataInputStream**: Reads primitive types and strings
- **DataOutputStream**: Writes primitive types and strings
- **ObjectInputStream**: Reads objects (serialization)
- **ObjectOutputStream**: Writes objects (serialization)
- **BufferedInputStream**: Provides buffering for efficiency
- **BufferedOutputStream**: Provides buffering for efficiency

### 3. Object Serialization

**Serializable Interface**:
- Marker interface with no methods
- Required for objects to be serializable
- Automates the process of storing/restoring objects

**transient Keyword**:
- Marks fields to be ignored during serialization
- Used for non-serializable instance data
- Static variables are not serialized by default

### 4. Random Access Files

**RandomAccessFile**:
- Allows reading and writing at random locations
- Uses a file pointer to track current position
- Provides seek() method to move file pointer
- Supports both read and write operations

## Examples Demonstrated

### 1. TestFileStream
- Demonstrates basic FileInputStream and FileOutputStream
- Writes integers 1-10 to a file
- Reads and displays the values

### 2. TestDataStream
- Shows DataInputStream and DataOutputStream usage
- Writes strings and primitive types
- Reads data in the same order as written

### 3. Copy
- Demonstrates file copying with buffered streams
- Shows efficient byte-by-byte copying
- Uses BufferedInputStream and BufferedOutputStream

### 4. TestObjectOutputStream
- Demonstrates object serialization
- Writes strings, primitives, and objects
- Shows ObjectOutputStream usage

### 5. TestObjectInputStream
- Shows object deserialization
- Reads serialized data back into objects
- Demonstrates ObjectInputStream usage

### 6. TestObjectStreamForArray
- Shows array serialization
- Writes and reads entire arrays
- Demonstrates object stream with arrays

### 7. TestRandomAccessFile
- Demonstrates random access file operations
- Shows file pointer manipulation
- Illustrates reading/writing at specific positions

## Best Practices

1. **Always close streams**: Use try-with-resources to ensure proper cleanup
2. **Read in same order as written**: Maintain consistency in data reading order
3. **Handle exceptions**: Properly catch and handle IOException
4. **Use buffering**: Use buffered streams for better performance
5. **Check file existence**: Verify files exist before reading
6. **Use appropriate stream types**: Choose the right stream for your data type

## Common Patterns

### Writing Data
```java
try (OutputStream output = new FileOutputStream("file.dat")) {
    // Write data
    output.write(data);
}
```

### Reading Data
```java
try (InputStream input = new FileInputStream("file.dat")) {
    // Read data
    int value = input.read();
}
```

### Object Serialization
```java
try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("object.dat"))) {
    output.writeObject(myObject);
}
```

### Object Deserialization
```java
try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("object.dat"))) {
    MyObject obj = (MyObject) input.readObject();
}
``` 