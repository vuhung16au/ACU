Lecture 5 covers **Input/Output (I/O)** operations in Java, focusing on both **text I/O** and **binary I/O**.

The overall motivation for I/O is that a `File` object primarily encapsulates file properties and path names, but it does not contain methods for reading or writing data from or to a file. To perform I/O, you need to create objects using specific Java I/O classes.

**Lecture 5 Supplement: Text I/O**
This part focuses on reading and writing **human-readable text**.

*   **The `File` Class**:
    *   Provides an abstraction for machine-dependent complexities of files and path names in a machine-independent way.
    *   Acts as a wrapper for the file name and its directory path.
    *   You can use methods in the `File` class to obtain file properties.
*   **Writing Data (`PrintWriter`)**:
    *   The `java.io.PrintWriter` class is used to write strings and numeric values to a text file.
    *   It includes `print`, `println`, and `printf` methods for various data types.
    *   `println` adds a line separator, which is system-defined (e.g., `\r\n` on Windows, `\n` on Unix).
*   **Reading Data (`Scanner`)**:
    *   The `java.util.Scanner` class is used to read data from a file or a string.
    *   It provides methods like `hasNext()`, `next()`, `nextByte()`, `nextInt()`, `nextDouble()`, etc., for reading different data types.
    *   The `useDelimiter(pattern: String)` method allows setting a custom delimiting pattern.
*   **Try-with-resources**:
    *   Introduced in JDK 7, this syntax **automatically closes files**, preventing common programming errors where resources are forgotten to be closed.
*   **Command-line arguments**:
    *   The `main` method in Java programs can accept command-line arguments as a `String` array.
    *   A case study, `ReplaceText`, demonstrates how to replace a string in a text file with a new string using command-line arguments for source file, target file, old string, and new string.

Sample code: 

- https://liveexample.pearsoncmg.com/html/TestFileClass.html
- https://liveexample.pearsoncmg.com/html/WriteData.html
- https://liveexample.pearsoncmg.com/html/WriteDataWithAutoClose.html
- https://liveexample.pearsoncmg.com/html/ReadData.html
- https://liveexample.pearsoncmg.com/html/ReplaceText.html