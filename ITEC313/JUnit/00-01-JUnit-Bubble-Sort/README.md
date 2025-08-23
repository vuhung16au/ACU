# JUnit 5 Bubble Sort Example

A simple Java project demonstrating the Bubble Sort algorithm with focused JUnit 5 tests. This project showcases best practices in unit testing and algorithm implementation.

## Project Overview

This project was migrated from a simple calculator example to demonstrate the bubble sort algorithm with JUnit 5 testing. It includes:

- **Bubble Sort Implementation**: Both ascending and descending sorting
- **Focused JUnit 5 Tests**: 5 test cases covering essential scenarios
- **Maven Build System**: Clean, build, test, and run capabilities
- **Educational Focus**: Demonstrates sorting algorithms and unit testing

## Project Structure

```
src/
├── main/java/com/acu/bubblesort/
│   └── BubbleSort.java          # Main bubble sort implementation
└── test/java/com/acu/bubblesort/
    └── BubbleSortTests.java     # JUnit 5 test cases (5 tests)
```

## Prerequisites

- **Java 11** or higher
- **Maven 3.6** or higher

## How to Run the Project

### 1. Clone and Navigate to Project
```bash
git clone <repository-url>
cd 00-01-JUnit-Bubble-Sort
```

### 2. Build and Test Commands

#### Clean and Compile
```bash
mvn clean compile
```
- Removes previous build artifacts (`target/` directory)
- Compiles source code to `target/classes/`
- Compiles test code to `target/test-classes/`

#### Run Tests Only
```bash
mvn test
```
- Compiles and runs all JUnit 5 tests
- Shows test results and coverage
- **Expected Output**: 5 tests passing

#### Clean, Compile, and Test (All-in-One)
```bash
mvn clean compile test
```
- **Recommended command** for development workflow
- Ensures clean build and runs all tests
- **Expected Output**: BUILD SUCCESS with 5 tests passing

#### Run the Main Application
```bash
mvn exec:java
```
- Compiles and runs the `BubbleSort` main class
- Demonstrates bubble sort with various test cases
- Shows before/after arrays for different scenarios

### 3. Test Results

When you run `mvn test`, you should see output like:
```
[INFO] Running com.acu.bubblesort.BubbleSortTests
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### 4. Demo Output

When you run `mvn exec:java`, you'll see:
```
=== JUnit 5 Bubble Sort Demo ===

Original array:
[64, 34, 25, 12, 22, 11, 90]
Sorted array:
[11, 12, 22, 25, 34, 64, 90]

Already sorted array:
[1, 2, 3, 4, 5]
After sorting:
[1, 2, 3, 4, 5]

Array with duplicates:
[3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
Sorted array:
[1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]

Array for descending sort:
[5, 2, 8, 1, 9]
Descending sorted array:
[9, 8, 5, 2, 1]

Empty array:
[]
After sorting:
[]

Run 'mvn test' to execute the JUnit 5 tests!
```

## Features

### Bubble Sort Algorithm
- **Ascending Sort**: `sort(int[] arr)` - Sorts array in ascending order
- **Descending Sort**: `sortDescending(int[] arr)` - Sorts array in descending order
- **Optimized**: Early termination when no swaps are needed
- **Edge Case Handling**: Null, empty, and single-element arrays

### JUnit 5 Test Coverage (5 Tests)
- **Sort Tests**: Essential sorting functionality (5 tests)
- **Core Functionality**: Basic array sorting operations
- **Edge Cases**: Empty arrays, single elements
- **Focused Testing**: Streamlined test suite for learning purposes

### Test Categories
1. **Basic Operations**: Standard array sorting
2. **Edge Cases**: Empty arrays, single elements
3. **Core Functionality**: Essential bubble sort operations

## Algorithm Details

### Bubble Sort Implementation
```java
public int[] sort(int[] arr) {
    if (arr == null || arr.length <= 1) {
        return arr;
    }
    
    int n = arr.length;
    boolean swapped;
    
    for (int i = 0; i < n - 1; i++) {
        swapped = false;
        
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // Swap elements
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                swapped = true;
            }
        }
        
        // Early termination if no swaps occurred
        if (!swapped) {
            break;
        }
    }
    
    return arr;
}
```

**Time Complexity**: O(n²) worst case, O(n) best case (already sorted)
**Space Complexity**: O(1) - in-place sorting

## JUnit 5 Features Demonstrated

- `@Test` - Basic test annotations
- `@DisplayName` - Readable test names
- `@Nested` - Organizing related tests
- `@BeforeEach` - Test setup
- Assertions: `assertArrayEquals`, `assertTrue`

