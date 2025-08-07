# Array-Algorithms

Comprehensive implementation of array algorithms including sorting, searching, and manipulation techniques.

## 📋 Overview

This project demonstrates fundamental and advanced array algorithms in Java, providing a comprehensive learning resource for students transitioning from Python to Java. The implementation includes multiple sorting algorithms, searching techniques, and array manipulation methods with detailed explanations and Python comparisons.

**Key Features:**
- Multiple sorting algorithms (Bubble, Selection, Insertion, Quick, Merge, Heap, Counting)
- Advanced searching techniques (Linear, Binary, First/Last occurrence)
- Array manipulation and transformation methods
- Performance comparison between algorithms
- Interactive demonstration with user input
- Memory-efficient implementations

## 📁 Files in this Directory

```
Array-Algorithms/
├── ArrayAlgorithms.java    # Main implementation with comprehensive algorithms
├── Makefile               # Build automation
├── README.md              # This documentation
├── examples/              # Additional example files
│   ├── Example1.java      # Basic array operations and bounds checking
│   ├── Example2.java      # Intermediate algorithms and transformations
│   └── Advanced.java      # Advanced algorithms (Merge, Heap, Counting sort)
├── data/                  # Sample data files
│   ├── input.txt          # Sample input data
│   └── sample.csv         # Sample CSV data
├── docs/                  # Additional documentation
│   └── concepts.md        # Detailed concept explanations
└── tests/                 # Unit tests (removed per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the program
make run

# Clean compiled files
make clean

# Show help
make help
```

**Example Output:**
```
=== Array Algorithms Demonstration ===

Original array: [64, 34, 25, 12, 22, 11, 90]
Sorted array: [11, 12, 22, 25, 34, 64, 90]

=== Sorting Algorithms ===
Bubble Sort:
Before: [64, 34, 25, 12, 22, 11, 90]
After:  [11, 12, 22, 25, 34, 64, 90]

=== Searching Algorithms ===
Linear Search for 25:
Found at index: 3

Binary Search for 25:
Found at index: 3
```

## 📚 Learning Objectives

This project teaches:

### Core Algorithm Concepts
- **Sorting Algorithms**: Understanding different approaches to ordering data
  - Bubble Sort (O(n²)) - Simple but inefficient
  - Selection Sort (O(n²)) - Simple, good for small datasets
  - Insertion Sort (O(n²)) - Efficient for nearly sorted data
  - Quick Sort (O(n log n)) - Efficient general-purpose sorting
  - Merge Sort (O(n log n)) - Stable, predictable performance
  - Heap Sort (O(n log n)) - In-place sorting with heap data structure
  - Counting Sort (O(n + k)) - Linear time for small integer ranges

- **Searching Algorithms**: Techniques for finding data efficiently
  - Linear Search (O(n)) - Simple, works on any array
  - Binary Search (O(log n)) - Fast but requires sorted array
  - First/Last Occurrence - Modified binary search for duplicates
  - K-th Smallest Element - Using QuickSelect algorithm

- **Array Manipulation**: Common operations and transformations
  - Reversing arrays in-place
  - Rotating arrays efficiently
  - Finding maximum/minimum values
  - Calculating sums and averages
  - Removing duplicates
  - Filtering and transforming data

### Java-Specific Learning
- **Array Declaration and Initialization**: Understanding Java's fixed-size arrays
- **Memory Management**: Working with array bounds and null safety
- **Performance Optimization**: Choosing appropriate algorithms for different scenarios
- **Error Handling**: Proper exception handling for array operations
- **Built-in vs Custom**: When to use Java's Arrays.sort() vs custom implementations

### Programming Best Practices
- **Algorithm Analysis**: Understanding time and space complexity
- **Code Organization**: Structuring algorithms into reusable methods
- **Documentation**: Writing clear, educational code with detailed comments
- **Testing**: Verifying algorithm correctness with various inputs
- **Performance Measurement**: Comparing algorithm efficiency

## �� Key Takeaways

### Algorithmic Thinking
- **Problem Decomposition**: Breaking complex problems into smaller, manageable parts
- **Pattern Recognition**: Identifying common algorithmic patterns and techniques
- **Optimization Strategies**: Understanding trade-offs between time, space, and code complexity
- **Correctness Verification**: Ensuring algorithms work correctly for all input cases

### Java Programming Skills
- **Array Operations**: Mastering Java's array syntax and limitations
- **Method Design**: Creating reusable, well-documented methods
- **Exception Handling**: Proper error management for edge cases
- **Performance Awareness**: Understanding when to use built-in vs custom implementations

### Software Engineering Principles
- **Code Readability**: Writing self-documenting code with clear variable names
- **Modularity**: Breaking functionality into focused, single-purpose methods
- **Maintainability**: Creating code that's easy to understand and modify
- **Educational Value**: Prioritizing learning over production optimization

## 🔍 Important Concepts

### Array Fundamentals
- **Fixed Size**: Java arrays have a fixed length that cannot be changed after creation
- **Zero-Indexed**: Array indices start at 0, similar to Python lists
- **Type Safety**: Arrays can only contain elements of the declared type
- **Memory Layout**: Arrays are stored contiguously in memory for efficient access

### Algorithm Complexity
- **Time Complexity**: How algorithm performance scales with input size
- **Space Complexity**: How much additional memory an algorithm requires
- **Best/Worst/Average Cases**: Understanding performance in different scenarios
- **Big O Notation**: Standard way to express algorithm efficiency

### Sorting Algorithm Characteristics
- **Stability**: Whether equal elements maintain their relative order
- **In-Place**: Whether the algorithm modifies the original array
- **Adaptive**: Whether the algorithm performs better on partially sorted data
- **Comparison-Based**: Whether the algorithm compares elements to determine order

### Search Algorithm Trade-offs
- **Linear Search**: Simple, works on any array, but slow for large datasets
- **Binary Search**: Fast, but requires sorted array and more complex implementation
- **Hash-Based Search**: Very fast, but requires additional data structure overhead

## 🔍 Code Examples

### Basic Array Operations
```java
// Creating and initializing arrays
int[] numbers = {1, 2, 3, 4, 5};
int[] scores = new int[5]; // Initialize with zeros

// Accessing elements
int first = numbers[0];
int last = numbers[numbers.length - 1];

// Iterating through arrays
for (int i = 0; i < numbers.length; i++) {
    System.out.println(numbers[i]);
}

// Enhanced for loop (Python equivalent: for item in list:)
for (int num : numbers) {
    System.out.println(num);
}
```

### Sorting Algorithm Implementation
```java
// Bubble Sort - Simple but inefficient
public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // Swap elements
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
```

### Searching Algorithm Implementation
```java
// Binary Search - Fast but requires sorted array
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return -1; // Not found
}
```

### Array Manipulation
```java
// Reverse array in-place
public static void reverseArray(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        left++;
        right--;
    }
}

// Find maximum value
public static int findMaximum(int[] arr) {
    if (arr.length == 0) {
        throw new IllegalArgumentException("Array cannot be empty");
    }
    
    int max = arr[0];
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    return max;
}
```

## 📝 Notes for Python Developers

### Key Differences from Python Lists

**Array Declaration:**
- **Java**: `int[] arr = new int[5];` or `int[] arr = {1, 2, 3, 4, 5};`
- **Python**: `arr = [1, 2, 3, 4, 5]` or `arr = [0] * 5`

**Array Length:**
- **Java**: `arr.length` (property, not method)
- **Python**: `len(arr)` (function)

**Array Bounds:**
- **Java**: Throws `ArrayIndexOutOfBoundsException` for invalid indices
- **Python**: Throws `IndexError` for invalid indices

**Sorting:**
- **Java**: `Arrays.sort(arr)` or custom implementation
- **Python**: `arr.sort()` or `sorted(arr)`

**Searching:**
- **Java**: Manual implementation of linear/binary search
- **Python**: `arr.index(value)` or `value in arr`

**Array Operations:**
- **Java**: Fixed size, manual operations for resizing
- **Python**: Dynamic size, built-in methods for common operations

### Performance Considerations

**Memory Usage:**
- Java arrays are more memory-efficient than Python lists
- Java arrays have fixed size, Python lists can grow dynamically

**Algorithm Implementation:**
- Java requires manual implementation of many algorithms
- Python provides built-in methods for common operations

**Type Safety:**
- Java arrays are type-safe at compile time
- Python lists can contain mixed types

### Learning Strategy

1. **Start with Basic Operations**: Master array creation, access, and iteration
2. **Implement Simple Algorithms**: Begin with bubble sort and linear search
3. **Progress to Advanced Algorithms**: Move to quick sort and binary search
4. **Understand Trade-offs**: Learn when to use different algorithms
5. **Practice Performance Analysis**: Compare algorithm efficiency

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
