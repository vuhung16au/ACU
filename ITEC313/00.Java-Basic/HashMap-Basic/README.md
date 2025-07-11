# HashMap-Basic

## 📋 Overview

This project demonstrates the fundamental concepts of HashMap in Java, a collection class that stores key-value pairs. HashMap is similar to Python's dictionary but with important differences in syntax, type safety, and behavior. This project serves as a comprehensive guide for Python developers learning Java, showing how to work with key-value data structures effectively.

## 📁 Files in this Directory

```
HashMap-Basic/
├── HashMapBasic.java      # Main source code with comprehensive examples
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Basic HashMap operations
│   ├── Example2.java     # HashMap iteration and utilities
│   └── Advanced.java     # Advanced HashMap patterns
├── data/                 # Sample data files
│   ├── input.txt         # Sample input data
│   └── sample.csv        # Structured CSV data
└── docs/                 # Additional documentation
    └── concepts.md       # Detailed HashMap concepts
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

1. **HashMap Fundamentals**
   - Understanding key-value pair storage
   - Creating and initializing HashMaps
   - Basic operations (put, get, remove)

2. **Type Safety in Java**
   - Generic type declarations
   - Working with different data types
   - Type safety vs Python's dynamic typing

3. **HashMap Operations**
   - Adding and retrieving key-value pairs
   - Checking for key and value existence
   - Updating and removing elements
   - Size and emptiness checks

4. **Iteration Techniques**
   - Iterating over keys, values, and entries
   - Using different iteration methods
   - Comparing with Python dictionary iteration

5. **Advanced Features**
   - Utility methods (getOrDefault, putIfAbsent, replace)
   - Null value handling
   - Nested HashMaps
   - Custom objects as values

6. **Performance Considerations**
   - Time complexity understanding
   - Memory usage considerations
   - Best practices for HashMap usage

## 🎯 Key Takeaways

### Main Concepts and Skills Gained

1. **Java vs Python Differences**
   - Type declarations required in Java
   - Different method names and syntax
   - Null vs None handling
   - Static typing benefits and constraints

2. **HashMap Operations Mastery**
   - Confident use of all basic operations
   - Understanding of utility methods
   - Proper iteration techniques
   - Null-safe programming practices

3. **Data Structure Understanding**
   - Hash table implementation concepts
   - Key-value pair relationships
   - Performance characteristics
   - Memory considerations

4. **Java Collections Framework**
   - HashMap as part of the Map interface
   - Relationship to other collection types
   - When to use HashMap vs other collections

## 🔍 Important Concepts

### HashMap Fundamentals

HashMap is a hash table-based implementation of the Map interface. It provides constant-time performance for basic operations (get, put) under normal circumstances.

**Key Characteristics:**
- Stores key-value pairs
- Keys must be unique
- Values can be duplicate
- Not ordered (use LinkedHashMap for insertion order)
- Allows null keys and values
- Thread-unsafe (use ConcurrentHashMap for multi-threading)

### Type Safety

Java requires explicit type declarations for HashMap keys and values:

```java
HashMap<String, Integer> scores = new HashMap<>();
HashMap<Integer, String> reverse = new HashMap<>();
HashMap<String, Double> prices = new HashMap<>();
```

### Python Comparisons

| Java HashMap | Python Dictionary |
|--------------|-------------------|
| `put(key, value)` | `my_dict[key] = value` |
| `get(key)` | `my_dict[key]` |
| `containsKey(key)` | `key in my_dict` |
| `size()` | `len(my_dict)` |
| `clear()` | `my_dict.clear()` |

## 🔍 Code Examples

### Basic HashMap Operations

```java
// Creating a HashMap
HashMap<String, Integer> scores = new HashMap<>();

// Adding key-value pairs
scores.put("Alice", 95);
scores.put("Bob", 87);

// Retrieving values
int aliceScore = scores.get("Alice");

// Checking existence
boolean hasEve = scores.containsKey("Eve");

// Updating values
scores.put("Bob", 90);

// Removing elements
scores.remove("Charlie");
```

### HashMap Iteration

```java
// Iterate over keys
for (String key : scores.keySet()) {
    System.out.println(key);
}

// Iterate over values
for (Integer value : scores.values()) {
    System.out.println(value);
}

// Iterate over entries
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
    System.out.println(key + ": " + value);
}
```

### Utility Methods

```java
// Get with default value
int score = scores.getOrDefault("Eve", 0);

// Put only if key doesn't exist
scores.putIfAbsent("Alice", 100); // Won't change existing value

// Replace only if key exists
scores.replace("Bob", 92);
```

## 📝 Notes for Python Developers

### Key Differences from Python

1. **Type Declarations**
   - Java requires explicit type declarations
   - Python dictionaries are dynamically typed
   - Java provides compile-time type safety

2. **Method Names**
   - Java uses `put()` instead of assignment
   - Java uses `get()` instead of bracket notation
   - Java has different method names for similar operations

3. **Null vs None**
   - Java uses `null` for missing values
   - Python uses `None`
   - Java requires explicit null checking

4. **Iteration Syntax**
   - Java uses `entrySet()` for key-value iteration
   - Python uses `items()` method
   - Java iteration is more verbose but type-safe

5. **Performance Characteristics**
   - Both provide O(1) average case performance
   - Java HashMap has more predictable performance
   - Java requires more memory due to object overhead

### Migration Tips

1. **Start with Type Declarations**
   - Always specify key and value types
   - Use wrapper classes for primitive types
   - Consider using `var` for local variables (Java 10+)

2. **Learn Java-Specific Methods**
   - `getOrDefault()` for safe value retrieval
   - `putIfAbsent()` for conditional insertion
   - `replace()` for conditional updates

3. **Handle Null Values**
   - Always check for null when retrieving values
   - Use `getOrDefault()` to avoid null pointer exceptions
   - Be consistent with null handling

4. **Use Appropriate Iteration**
   - Use `entrySet()` for most efficient iteration
   - Use `keySet()` when you only need keys
   - Use `values()` when you only need values

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
