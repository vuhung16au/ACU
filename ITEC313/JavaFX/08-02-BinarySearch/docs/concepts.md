# Binary Search Algorithm Concepts

## Overview

This project demonstrates the binary search algorithm through a JavaFX graphical interface. The implementation is based on the iterative binary search algorithm from GeeksforGeeks.

## Binary Search Algorithm

### What is Binary Search?

Binary search is an efficient algorithm for finding a target element in a sorted array. It works by repeatedly dividing the search interval in half.

### How it Works

1. **Prerequisites**: The array must be sorted in ascending order
2. **Process**:
   - Compare the target element with the middle element of the array
   - If they match, return the index
   - If the target is less than the middle element, search the left half
   - If the target is greater than the middle element, search the right half
   - Repeat until the element is found or the search space is exhausted

### Time Complexity

- **Best Case**: O(1) - Element found at the middle
- **Average Case**: O(log n) - Element found after log₂(n) comparisons
- **Worst Case**: O(log n) - Element not found

### Space Complexity

- **Iterative Implementation**: O(1) - Constant space
- **Recursive Implementation**: O(log n) - Stack space for recursion

## Implementation Details

### Geeks Class

The `Geeks` class contains the core binary search implementation:

```java
static int binarySearch(int a[], int l, int r, int x)
```

**Parameters**:
- `a[]`: The sorted array to search in
- `l`: Left boundary index
- `r`: Right boundary index  
- `x`: The element to search for

**Return Value**:
- Index of the element if found
- -1 if the element is not present

### Algorithm Steps

1. **Initialize**: Set left pointer to 0, right pointer to array length - 1
2. **Loop**: While left ≤ right:
   - Calculate middle index: `mid = (left + right) / 2`
   - Compare target with middle element
   - If equal: return middle index
   - If target < middle: set right = middle - 1
   - If target > middle: set left = middle + 1
3. **Not Found**: Return -1 if loop exits without finding element

## JavaFX Application Features

### User Interface Components

1. **Input Fields**:
   - Array input: Comma-separated integers
   - Search element: Single integer to find

2. **Control Buttons**:
   - Search: Perform binary search with current input
   - Run Original Demo: Execute the original example
   - Clear: Reset output area

3. **Output Display**:
   - Text area showing search results
   - Visual indicators for success/failure
   - Detailed information about the search process

### Input Validation

- Ensures array elements are valid integers
- Verifies array is sorted in ascending order
- Provides user-friendly error messages

### Example Usage

**Input Array**: `2, 3, 4, 10, 40`
**Search Element**: `10`
**Expected Result**: Element found at index 3

## Key Learning Points

1. **Algorithm Efficiency**: Binary search is much faster than linear search for large datasets
2. **Sorted Requirement**: The algorithm only works on sorted arrays
3. **Divide and Conquer**: The algorithm repeatedly divides the problem space
4. **Implementation**: Both iterative and recursive approaches are possible

## Educational Value

This application helps students understand:
- How binary search works visually
- The importance of sorted data
- Algorithm complexity analysis
- Practical implementation in Java
- User interface design for algorithm demonstration 