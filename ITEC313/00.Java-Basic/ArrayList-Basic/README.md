# ArrayList-Basic

Dynamic arrays, common operations

## 📋 Overview

This project demonstrates ArrayList operations in Java, which is Java's dynamic array implementation. ArrayList is one of the most commonly used data structures in Java, providing a resizable array that can grow or shrink as needed. This project is designed for Python developers transitioning to Java, showing the key differences and similarities between Python lists and Java ArrayLists.

## 📁 Files in this Directory

```
ArrayList-Basic/
├── ArrayListBasic.java    # Main source code with comprehensive examples
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional example files
│   ├── Example1.java     # Basic ArrayList operations
│   ├── Example2.java     # Different data types and iteration
│   └── Advanced.java     # Advanced concepts (nested lists, objects)
├── data/                 # Sample data files
│   ├── input.txt         # Sample input data for testing
│   └── sample.csv        # CSV data for demonstration
└── docs/                 # Additional documentation
    └── concepts.md       # Detailed concept explanations
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
```

## 📚 Learning Objectives

This project teaches:
- **ArrayList Creation and Initialization**: How to create and populate ArrayLists
- **Basic Operations**: Adding, removing, accessing, and modifying elements
- **Type Safety**: Working with generic types and type parameters
- **Iteration Methods**: Different ways to iterate through ArrayLists
- **Collections Framework**: Using utility methods from Collections class
- **Performance Considerations**: When to use ArrayList vs other data structures
- **Real-world Applications**: Practical examples of ArrayList usage

## 🎯 Key Takeaways

After completing this project, students will understand:
- How ArrayList differs from Python lists
- When to use ArrayList vs regular arrays
- Common ArrayList methods and their Python equivalents
- Performance implications of different operations
- Best practices for ArrayList usage in Java

## 🔍 Important Concepts

### ArrayList vs Python Lists

| Python List | Java ArrayList | Description |
|-------------|----------------|-------------|
| `my_list = []` | `ArrayList<String> list = new ArrayList<>()` | Creation |
| `my_list.append(item)` | `list.add(item)` | Add element |
| `my_list.remove(item)` | `list.remove(item)` | Remove by value |
| `my_list.pop(index)` | `list.remove(index)` | Remove by index |
| `len(my_list)` | `list.size()` | Get size |
| `item in my_list` | `list.contains(item)` | Check existence |
| `my_list[0]` | `list.get(0)` | Access element |
| `my_list[0] = value` | `list.set(0, value)` | Set element |

### Key Differences from Python

1. **Type Safety**: Java ArrayLists require type specification
2. **Method Names**: Different method names (add vs append)
3. **Access Pattern**: get(index) vs direct indexing
4. **Primitive Types**: Must use wrapper classes for primitives
5. **Memory**: More memory overhead due to object boxing

### Common ArrayList Methods

- `add(element)`: Add element to end
- `add(index, element)`: Insert element at specific index
- `remove(element)`: Remove first occurrence of element
- `remove(index)`: Remove element at index
- `get(index)`: Get element at index
- `set(index, element)`: Replace element at index
- `size()`: Get number of elements
- `isEmpty()`: Check if list is empty
- `clear()`: Remove all elements
- `contains(element)`: Check if element exists
- `indexOf(element)`: Find first index of element
- `lastIndexOf(element)`: Find last index of element

## 🔍 Code Examples

### Basic ArrayList Operations

```java
// Create ArrayList
ArrayList<String> fruits = new ArrayList<>();

// Add elements
fruits.add("apple");
fruits.add("banana");

// Access element
String first = fruits.get(0);

// Remove element
fruits.remove("banana");

// Check size
int size = fruits.size();
```

### ArrayList with Different Types

```java
// Integer ArrayList
ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(1);
numbers.add(2);

// Double ArrayList
ArrayList<Double> prices = new ArrayList<>();
prices.add(19.99);

// Boolean ArrayList
ArrayList<Boolean> flags = new ArrayList<>();
flags.add(true);
```

### Iteration Methods

```java
ArrayList<String> items = new ArrayList<>();
// ... add items ...

// Method 1: Traditional for loop
for (int i = 0; i < items.size(); i++) {
    System.out.println(items.get(i));
}

// Method 2: Enhanced for loop
for (String item : items) {
    System.out.println(item);
}

// Method 3: Lambda forEach
items.forEach(item -> System.out.println(item));
```

## 📝 Notes for Python Developers

### Major Differences

1. **Type Declaration**: Java requires explicit type declaration
   ```java
   // Java
   ArrayList<String> names = new ArrayList<>();
   
   // Python equivalent
   names = []
   ```

2. **Method Names**: Different method names for similar operations
   ```java
   // Java
   list.add("item");        // Python: list.append("item")
   list.remove("item");     // Python: list.remove("item")
   list.get(0);            // Python: list[0]
   ```

3. **Primitive Types**: Must use wrapper classes
   ```java
   // Java - must use Integer, not int
   ArrayList<Integer> numbers = new ArrayList<>();
   numbers.add(1);  // Autoboxing happens automatically
   
   // Python - no such restriction
   numbers = [1, 2, 3]
   ```

4. **Access Pattern**: Use get() method instead of direct indexing
   ```java
   // Java
   String item = list.get(0);
   list.set(0, "new item");
   
   // Python
   item = list[0]
   list[0] = "new item"
   ```

### Similarities

1. **Dynamic Sizing**: Both grow and shrink automatically
2. **Ordered**: Both maintain insertion order
3. **Indexed Access**: Both support indexed access
4. **Common Operations**: Both support add, remove, search operations

### Performance Considerations

- **ArrayList**: Good for frequent access, poor for frequent insertions at beginning
- **Python List**: Generally good for all operations, but less type-safe
- **Memory**: ArrayList uses more memory due to object overhead
- **Speed**: ArrayList can be faster for large datasets due to JVM optimizations

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
