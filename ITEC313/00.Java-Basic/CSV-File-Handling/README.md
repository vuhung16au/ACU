# CSV-File-Handling

Working with CSV files in Java - Reading, writing, parsing, and manipulating CSV data.

## 📋 Overview

This project demonstrates comprehensive CSV file handling in Java, covering fundamental concepts like reading and writing CSV files, as well as advanced operations including data validation, filtering, transformation, and error handling. The project is designed for Python developers transitioning to Java, with detailed comparisons and educational focus.

## 📁 Files in this Directory

```
CSV-File-Handling/
├── CSVFileHandling.java    # Main source code with comprehensive examples
├── Makefile               # Build automation
├── README.md              # This documentation
├── examples/              # Additional example files
│   ├── Example1.java      # Basic CSV reading operations
│   ├── Example2.java      # CSV writing operations
│   └── Advanced.java      # Advanced CSV operations (validation, filtering, transformation)
├── data/                  # Sample data files
│   ├── sample.csv         # Sample CSV data for examples
│   └── input.txt          # Sample text data
├── docs/                  # Additional documentation
│   └── concepts.md        # Detailed concepts and best practices
└── tests/                 # Test files (removed per requirements)
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

- **File I/O Operations**: Reading and writing CSV files using Java's I/O classes
- **String Processing**: Parsing and manipulating CSV data using String methods
- **Data Validation**: Implementing comprehensive data validation and error handling
- **Advanced CSV Operations**: Handling quoted fields, filtering, and data transformation
- **Exception Handling**: Proper handling of file operations and data parsing errors
- **Resource Management**: Using try-with-resources for automatic cleanup
- **Data Structures**: Working with arrays, lists, and maps for CSV data
- **Best Practices**: Following Java conventions and writing maintainable code

## 🎯 Key Takeaways

- **CSV Fundamentals**: Understanding CSV file structure and format
- **Java I/O**: Mastering BufferedReader, BufferedWriter, and file operations
- **Data Processing**: Techniques for parsing, validating, and transforming CSV data
- **Error Handling**: Robust error handling for file operations and data validation
- **Performance Considerations**: Efficient processing of CSV data
- **Python to Java Transition**: Understanding differences between Python and Java CSV handling

## 🔍 Important Concepts

### CSV File Structure
- **Header Row**: Contains column names (optional but common)
- **Data Rows**: Each row represents a record with values separated by commas
- **Field Delimiter**: Comma (,) is the standard delimiter
- **Text Qualifier**: Double quotes (") for fields containing commas or special characters

### File I/O Operations
- **Reading**: Using BufferedReader and FileReader with try-with-resources
- **Writing**: Using BufferedWriter and FileWriter for output
- **Exception Handling**: Proper handling of FileNotFoundException and IOException
- **Resource Management**: Automatic cleanup with try-with-resources

### String Processing
- **Splitting**: Using String.split() method for parsing fields
- **Joining**: Using String.join() method for creating CSV lines
- **Trimming**: Removing whitespace with trim()
- **Parsing**: Converting strings to appropriate data types

### Data Validation
- **Field Count**: Ensuring consistent number of fields per row
- **Data Types**: Validating numeric fields, dates, etc.
- **Required Fields**: Checking for non-empty required fields
- **Range Validation**: Ensuring values fall within expected ranges

### Advanced CSV Handling
- **Quoted Fields**: Handling fields that contain commas or quotes
- **Escaping**: Properly escaping special characters
- **Error Recovery**: Graceful handling of malformed data
- **Data Transformation**: Converting and processing data

## 🔍 Code Examples

### Reading CSV Files
```java
// Python equivalent: with open('file.csv', 'r') as file:
try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
    String line;
    while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        // Process fields
    }
}
```

### Writing CSV Files
```java
// Python equivalent: with open('file.csv', 'w') as file:
try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
    writer.write("Name,Age,City\n");
    writer.write("John,25,Sydney\n");
}
```

### Data Validation
```java
// Validate field count and data types
if (fields.length != expectedFields) {
    System.err.println("Invalid field count");
}
try {
    int age = Integer.parseInt(fields[1]);
    if (age < 0 || age > 150) {
        System.err.println("Invalid age: " + age);
    }
} catch (NumberFormatException e) {
    System.err.println("Age is not a number");
}
```

### Advanced Parsing (Quoted Fields)
```java
// Handle quoted fields with embedded commas
List<String> parseQuotedCSV(String line) {
    List<String> fields = new ArrayList<>();
    StringBuilder currentField = new StringBuilder();
    boolean inQuotes = false;
    
    for (char c : line.toCharArray()) {
        if (c == '"') {
            inQuotes = !inQuotes;
        } else if (c == ',' && !inQuotes) {
            fields.add(currentField.toString().trim());
            currentField = new StringBuilder();
        } else {
            currentField.append(c);
        }
    }
    fields.add(currentField.toString().trim());
    return fields;
}
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **File Operations**:
   - **Python**: `with open('file.csv', 'r') as file:`
   - **Java**: `try (BufferedReader reader = new BufferedReader(new FileReader(filename)))`

2. **CSV Module**:
   - **Python**: Built-in `csv` module with `csv.reader()` and `csv.writer()`
   - **Java**: No built-in CSV module - manual parsing required

3. **String Operations**:
   - **Python**: `line.strip().split(',')`
   - **Java**: `line.split(",")` (trimming done separately)

4. **Data Structures**:
   - **Python**: List of lists `[["John", "25"], ["Jane", "30"]]`
   - **Java**: 2D array `{{"John", "25"}, {"Jane", "30"}}` or `List<List<String>>`

5. **Exception Handling**:
   - **Python**: `try/except` with specific exception types
   - **Java**: `try/catch` with more explicit exception handling

6. **Resource Management**:
   - **Python**: Context managers (`with` statement)
   - **Java**: Try-with-resources (automatic cleanup)

### Common Python Patterns and Java Equivalents

```python
# Python: Reading CSV
import csv
with open('data.csv', 'r') as file:
    reader = csv.reader(file)
    for row in reader:
        print(row)
```

```java
// Java: Reading CSV (manual parsing)
try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        System.out.println(Arrays.toString(fields));
    }
}
```

```python
# Python: Writing CSV
import csv
with open('output.csv', 'w') as file:
    writer = csv.writer(file)
    writer.writerow(['Name', 'Age'])
    writer.writerow(['John', 25])
```

```java
// Java: Writing CSV
try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
    writer.write("Name,Age\n");
    writer.write("John,25\n");
}
```

### Performance Considerations

- **Python**: pandas for large datasets, built-in csv module for simple operations
- **Java**: Manual parsing for simple cases, libraries like Apache Commons CSV for complex scenarios
- **Memory**: Java requires more explicit memory management
- **Streaming**: Both languages support streaming for large files

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
