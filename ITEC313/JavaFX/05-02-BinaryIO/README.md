Objectives
To discover how I/O is processed in Java (§17.2).
To distinguish between text I/O and binary I/O (§17.3).
To read and write bytes using FileInputStream and FileOutputStream (§17.4.1).
To read and write primitive values and strings using DataInputStream/DataOutputStream (§17.4.3).
To store and restore objects using ObjectOutputStream and ObjectInputStream, and to understand how objects are serialized and what kind of objects can be serialized (§17.6). 
To implement the Serializable interface to make objects serializable (§17.6.1).
To serialize arrays (§17.6.2).
To read and write the same file using the RandomAccessFile class (§17.7).


**Lecture 5: Binary I/O**
This part delves into reading and writing data in **binary form**, which is designed to be read by programs rather than humans.

*   **Motivation and Distinction from Text I/O**:
    *   Data in text files is human-readable, while data in binary files is in binary form and **cannot be read directly by humans**.
    *   The **advantage of binary files is efficiency**; they are more efficient to process than text files.
    *   **Binary I/O does not require conversions** (encoding/decoding) that text I/O needs for character sets (e.g., Unicode to file-specific encoding and vice-versa). When you write or read a byte to/from a binary file, the exact byte is copied or returned.
*   **I/O Handling in Java (Streams)**:
    *   Java uses an **input stream** to read data and an **output stream** to write data.
    *   Base classes for byte-oriented I/O are `InputStream` (for reading) and `OutputStream` (for writing).
    *   `InputStream` methods include `read()` (reads a byte as an `int`), `read(byte[])`, `close()`, and `skip()`.
    *   `OutputStream` methods include `write(int b)` (writes an `int` as a byte), `write(byte[])`, `close()`, and `flush()`.
*   **`FileInputStream`/`FileOutputStream`**:
    *   These classes **associate a binary input/output stream directly with an external file**.
    *   `FileInputStream` requires the file to exist, otherwise a `FileNotFoundException` occurs.
    *   `FileOutputStream` will create a new file if it doesn't exist; if it exists, it will delete current contents by default, unless `true` is passed to the `append` parameter in the constructor to retain and append data.
*   **Filter Streams (`FilterInputStream`/`FilterOutputStream`)**:
    *   Filter streams are used to **filter bytes for a specific purpose**. They wrap basic byte streams to provide additional functionality (e.g., reading integers or strings instead of just bytes).
*   **`DataInputStream`/`DataOutputStream`**:
    *   These filter streams read/write primitive type values and strings.
    *   `DataInputStream` converts bytes from the stream into appropriate primitive type values or strings.
    *   `DataOutputStream` converts primitive type values or strings into bytes for the stream.
    *   They are created by wrapping them around existing input/output streams. This concept is often referred to as a **pipeline**.
    *   It's crucial to **read data in the same order and format in which they were stored** (e.g., if a string was written with `writeUTF`, it must be read with `readUTF`).
    *   An `EOFException` occurs if you read past the end of a stream; `input.available() == 0` can be used to check for the end of a file.
*   **`BufferedInputStream`/`BufferedOutputStream`**:
    *   These classes use **buffers to speed up I/O** operations. They inherit all their methods from their superclasses and don't introduce new ones.
*   **Object I/O (`ObjectInputStream`/`ObjectOutputStream`)**:
    *   These streams extend the functionality to perform I/O for **objects**, in addition to primitive types and strings.
    *   **`Serializable` Interface**: Not all objects can be written to an object stream. An object must be an instance of `java.io.Serializable` to be serializable. This is a **marker interface** with no methods, used to automate the process of storing objects and arrays.
    *   **`transient` Keyword**: If a serializable object contains non-serializable instance data fields, those fields must be marked with `transient` to be ignored during serialization, otherwise a `java.io.NotSerializableException` will occur. Static variables are also not serialized.
    *   **Serializing Arrays**: An entire array can be saved and restored if all its elements are serializable.
*   **Random Access Files (`RandomAccessFile`)**:
    *   Unlike sequential read-only or write-only streams, `RandomAccessFile` allows a file to be **read from and written to at random locations**.
    *   It uses a **file pointer** that indicates the current position for read/write operations. When data is read or written, the file pointer moves forward.
    *   It provides methods like `seek(long pos)` to set the file pointer, `getFilePointer()` to get its current position, and `length()` to get the file length. Many methods are similar to `DataInputStream` and `DataOutputStream`.
    

Sample code: 

- https://liveexample.pearsoncmg.com/html/TestFileStream.html
- https://liveexample.pearsoncmg.com/html/TestDataStream.html
- https://liveexample.pearsoncmg.com/html/Copy.html
- https://liveexample.pearsoncmg.com/html/TestObjectOutputStream.html
- http://www.cs.armstrong.edu/liang/intro11e/html/TestObjectInputStream.html
- https://liveexample.pearsoncmg.com/html/TestObjectStreamForArray.html
- https://liveexample.pearsoncmg.com/html/TestObjectStreamForArray.html
- https://liveexample.pearsoncmg.com/html/TestRandomAccessFile.html