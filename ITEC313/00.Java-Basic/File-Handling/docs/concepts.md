# File-Handling - Concepts

## Overview

File handling in Java involves reading from and writing to files, managing file properties, and handling various file types. This is a fundamental skill for any Java developer, especially when transitioning from Python where file operations are more straightforward.

## Key Concepts

### Main Learning Points

1. **File I/O Streams**: Understanding the hierarchy of input/output streams in Java
   - **InputStream/OutputStream**: Base classes for byte-based operations
   - **Reader/Writer**: Base classes for character-based operations
   - **FileReader/FileWriter**: Specific classes for file operations
   - **BufferedReader/BufferedWriter**: Wrapper classes for efficient buffering

2. **Resource Management**: Proper handling of file resources
   - Always close file streams to prevent resource leaks
   - Use try-with-resources (Java 7+) for automatic resource management
   - Understand the difference between flush() and close()

3. **Exception Handling**: Managing file operation errors
   - **FileNotFoundException**: When a file doesn't exist
   - **IOException**: General input/output errors
   - **SecurityException**: When access is denied
   - Custom exception handling for specific scenarios

4. **File Operations**: Working with files and directories
   - **File class**: Represents file and directory paths
   - **Path interface**: Modern way to handle file paths (Java 7+)
   - **Files utility class**: Static methods for common file operations

### Best Practices

- **Use buffered classes** for better performance when reading/writing text files
- **Always handle exceptions** - file operations can fail for many reasons
- **Close resources properly** - use try-with-resources when possible
- **Check file existence** before attempting operations
- **Use appropriate file types** - text vs binary operations
- **Handle encoding properly** - specify character encoding when needed
- **Validate file paths** - ensure paths are valid and accessible

### Common Pitfalls

- **Forgetting to close files**: Leads to resource leaks
- **Not handling exceptions**: Can cause program crashes
- **Using wrong stream types**: Text vs binary operations
- **Ignoring file permissions**: Can cause SecurityException
- **Not checking file existence**: Can cause FileNotFoundException
- **Poor error messages**: Make debugging difficult

## File Reading Patterns

### Basic File Reading
```java
// Simple file reading
FileReader reader = new FileReader("file.txt");
int character;
while ((character = reader.read()) != -1) {
    System.out.print((char) character);
}
reader.close();
```

### Buffered File Reading
```java
// Efficient line-by-line reading
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
String line;
while ((line = reader.readLine()) != null) {
    System.out.println(line);
}
reader.close();
```

### Modern File Reading (Java 7+)
```java
// Read entire file at once
String content = new String(Files.readAllBytes(Paths.get("file.txt")));
```

## File Writing Patterns

### Basic File Writing
```java
// Simple file writing
FileWriter writer = new FileWriter("file.txt");
writer.write("Hello, World!");
writer.close();
```

### Buffered File Writing
```java
// Efficient writing with buffering
BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"));
writer.write("Line 1");
writer.newLine();
writer.write("Line 2");
writer.close();
```

### Appending to Files
```java
// Append mode (second parameter = true)
FileWriter writer = new FileWriter("file.txt", true);
writer.write("Appended content");
writer.close();
```

## Error Handling Patterns

### Basic Exception Handling
```java
try {
    FileReader reader = new FileReader("file.txt");
    // File operations
    reader.close();
} catch (FileNotFoundException e) {
    System.err.println("File not found: " + e.getMessage());
} catch (IOException e) {
    System.err.println("IO error: " + e.getMessage());
}
```

### Try-with-Resources (Java 7+)
```java
try (FileReader reader = new FileReader("file.txt");
     BufferedReader bufferedReader = new BufferedReader(reader)) {
    // File operations - resources automatically closed
} catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
}
```

## File Properties and Information

### Checking File Properties
```java
File file = new File("file.txt");
if (file.exists()) {
    System.out.println("Size: " + file.length() + " bytes");
    System.out.println("Readable: " + file.canRead());
    System.out.println("Writable: " + file.canWrite());
    System.out.println("Last modified: " + new Date(file.lastModified()));
}
```

### Directory Operations
```java
File directory = new File("path/to/directory");
if (directory.exists() && directory.isDirectory()) {
    File[] files = directory.listFiles();
    for (File file : files) {
        System.out.println(file.getName());
    }
}
```

## Advanced Concepts

### Binary File Operations
```java
// Reading binary files
FileInputStream input = new FileInputStream("file.bin");
byte[] buffer = new byte[1024];
int bytesRead = input.read(buffer);
input.close();

// Writing binary files
FileOutputStream output = new FileOutputStream("file.bin");
output.write(buffer, 0, bytesRead);
output.close();
```

### Properties Files
```java
// Reading properties
Properties props = new Properties();
FileInputStream input = new FileInputStream("config.properties");
props.load(input);
input.close();

// Writing properties
Properties props = new Properties();
props.setProperty("key", "value");
FileOutputStream output = new FileOutputStream("config.properties");
props.store(output, "Comments");
output.close();
```

### File Copying and Moving
```java
// Copy file (Java 7+)
Files.copy(Paths.get("source.txt"), Paths.get("dest.txt"));

// Move file (Java 7+)
Files.move(Paths.get("source.txt"), Paths.get("dest.txt"));
```

## Performance Considerations

- **Buffering**: Always use buffered classes for text files
- **Chunked reading**: Read large files in chunks to manage memory
- **Stream closing**: Always close streams to free resources
- **Exception handling**: Don't let exceptions break the program flow
- **File size checking**: Check file size before reading large files

## Further Reading

- [Oracle Java Documentation - File I/O](https://docs.oracle.com/javase/tutorial/essential/io/)
- [Java NIO.2 Tutorial](https://docs.oracle.com/javase/tutorial/essential/io/file.html)
- [Java File I/O Best Practices](https://docs.oracle.com/javase/8/docs/api/java/io/package-summary.html)
- [Java Exception Handling](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
