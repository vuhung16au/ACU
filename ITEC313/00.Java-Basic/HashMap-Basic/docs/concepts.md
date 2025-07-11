# HashMap-Basic - Concepts

## Overview

HashMap is a collection class in Java that implements the Map interface. It stores key-value pairs and provides fast access to values based on keys. HashMap is similar to Python's dictionary but with some key differences.

## Key Concepts

### 1. HashMap Fundamentals

**What is HashMap?**
- A data structure that stores key-value pairs
- Keys must be unique, values can be duplicate
- Provides O(1) average time complexity for get/put operations
- Not ordered (use LinkedHashMap for insertion order)

**Python Comparison:**
```python
# Python dictionary
my_dict = {}
my_dict['key'] = 'value'
value = my_dict['key']
```

```java
// Java HashMap
HashMap<String, String> myMap = new HashMap<>();
myMap.put("key", "value");
String value = myMap.get("key");
```

### 2. HashMap Operations

**Basic Operations:**
- `put(key, value)` - Add or update key-value pair
- `get(key)` - Retrieve value by key
- `remove(key)` - Remove key-value pair
- `containsKey(key)` - Check if key exists
- `containsValue(value)` - Check if value exists
- `size()` - Get number of key-value pairs
- `isEmpty()` - Check if HashMap is empty
- `clear()` - Remove all key-value pairs

**Python Equivalents:**
- `put()` → `my_dict[key] = value`
- `get()` → `my_dict[key]`
- `remove()` → `del my_dict[key]`
- `containsKey()` → `key in my_dict`
- `containsValue()` → `value in my_dict.values()`
- `size()` → `len(my_dict)`
- `isEmpty()` → `len(my_dict) == 0`
- `clear()` → `my_dict.clear()`

### 3. HashMap Iteration

**Java Methods:**
```java
// Iterate over keys
for (String key : map.keySet()) { }

// Iterate over values
for (String value : map.values()) { }

// Iterate over entries
for (Map.Entry<String, String> entry : map.entrySet()) {
    String key = entry.getKey();
    String value = entry.getValue();
}
```

**Python Equivalents:**
```python
# Iterate over keys
for key in my_dict: pass

# Iterate over values
for value in my_dict.values(): pass

# Iterate over items
for key, value in my_dict.items(): pass
```

### 4. HashMap Utility Methods

**Advanced Methods:**
- `getOrDefault(key, defaultValue)` - Get value or default if key doesn't exist
- `putIfAbsent(key, value)` - Add only if key doesn't exist
- `replace(key, value)` - Update only if key exists
- `replace(key, oldValue, newValue)` - Update only if key exists with specific old value

**Python Equivalents:**
- `getOrDefault()` → `my_dict.get(key, default_value)`
- `putIfAbsent()` → `my_dict.setdefault(key, value)`
- `replace()` → Manual check and update

### 5. HashMap with Different Data Types

**Type Declarations:**
```java
HashMap<String, Integer> stringToInt = new HashMap<>();
HashMap<Integer, String> intToString = new HashMap<>();
HashMap<String, Double> stringToDouble = new HashMap<>();
HashMap<String, Boolean> stringToBoolean = new HashMap<>();
```

**Python Comparison:**
```python
# Python dictionaries are dynamically typed
my_dict = {}  # Can store any types
```

### 6. Null Values in HashMap

**Java Null Handling:**
- HashMap can store null keys and null values
- Must be careful when checking for null values
- Use `==` for null comparison

**Python Comparison:**
- Python uses `None` instead of `null`
- Python dictionaries can store `None` values
- Use `is None` for None comparison

### 7. Nested HashMaps

**Java Nested Structure:**
```java
HashMap<String, HashMap<String, String>> nested = new HashMap<>();
```

**Python Equivalent:**
```python
nested = {}  # Dictionary of dictionaries
```

### 8. Performance Characteristics

**Time Complexity:**
- Average case: O(1) for get, put, remove
- Worst case: O(n) when many collisions occur
- Space complexity: O(n)

**Performance Tips:**
- Use `containsKey()` instead of `containsValue()` for better performance
- Initialize with expected size when possible
- Be aware of memory usage vs arrays

## Best Practices

### 1. Type Safety
- Always specify generic types for type safety
- Use appropriate data types for keys and values
- Consider using wrapper classes for primitive types

### 2. Null Handling
- Always check for null values when retrieving
- Use `getOrDefault()` to avoid null pointer exceptions
- Be consistent with null handling throughout your code

### 3. Iteration
- Use `entrySet()` for most efficient iteration
- Use `keySet()` when you only need keys
- Use `values()` when you only need values

### 4. Memory Management
- Clear HashMaps when no longer needed
- Be aware of memory usage for large HashMaps
- Consider using `WeakHashMap` for caching scenarios

## Common Pitfalls

### 1. Type Erasure
- Java's type system is erased at runtime
- Be careful with type casting
- Use appropriate generic bounds

### 2. Concurrent Modification
- Don't modify HashMap while iterating
- Use `Iterator.remove()` for safe removal during iteration
- Consider `ConcurrentHashMap` for multi-threaded applications

### 3. Hash Code and Equals
- Custom objects as keys must implement `hashCode()` and `equals()`
- Inconsistent hash codes can cause performance issues
- Follow the contract: equal objects must have equal hash codes

### 4. Memory Leaks
- Large HashMaps can consume significant memory
- Clear references when HashMaps are no longer needed
- Be careful with object references in HashMap values

## Further Reading

- Oracle Java Documentation: HashMap Class
- Java Collections Framework
- Hash Table Data Structure
- Java Generics and Type Erasure
- Java Memory Management
- Concurrent Programming with Collections
