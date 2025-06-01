# Sorting Algorithms Comparison Study (Updated June 2025)

This document presents a comprehensive comparison of various sorting algorithms implemented in multiple programming languages.

> **Latest Update:** For the most recent performance findings, refer to our [Performance Summary 2025](PERFORMANCE-SUMMARY-2025.md) and [Multi-Size Performance Study](MULTI_SIZE_PERFORMANCE_STUDY.md).

## Sorting Algorithms Included

The following sorting algorithms have been implemented and compared:

1. **Quick Sort** - A divide-and-conquer algorithm that selects a 'pivot' and partitions the array around it.
   - Average time complexity: O(n log n)
   - Worst-case time complexity: O(n²)
   - Space complexity: O(log n)

2. **Bubble Sort** - A simple algorithm that repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
   - Average time complexity: O(n²)
   - Worst-case time complexity: O(n²)
   - Space complexity: O(1)

3. **Selection Sort** - A simple algorithm that divides the input list into a sorted and an unsorted region, and repeatedly selects the smallest element from the unsorted region and moves it to the sorted region.
   - Average time complexity: O(n²)
   - Worst-case time complexity: O(n²)
   - Space complexity: O(1)

4. **Insertion Sort** - Builds the final sorted array one item at a time by taking elements from the unsorted part and inserting them at the correct position in the sorted part.
   - Average time complexity: O(n²)
   - Worst-case time complexity: O(n²)
   - Space complexity: O(1)
   - Efficient for small or nearly sorted arrays

5. **Merge Sort** - A divide-and-conquer algorithm that divides the input array into two halves, recursively sorts them, and then merges them.
   - Average time complexity: O(n log n)
   - Worst-case time complexity: O(n log n)
   - Space complexity: O(n)
   - Stable sorting algorithm

6. **Counting Sort** - A non-comparative sorting algorithm that counts the number of objects that have distinct key values, and using arithmetic to determine their positions.
   - Average time complexity: O(n+k) where k is the range of input
   - Worst-case time complexity: O(n+k)
   - Space complexity: O(n+k)
   - Efficient for integers with a small range

7. **Radix Sort** - A non-comparative sorting algorithm that sorts integers by processing individual digits.
   - Average time complexity: O(d(n+k)) where d is the number of digits
   - Worst-case time complexity: O(d(n+k))
   - Space complexity: O(n+k)
   - Works well for integers with a fixed number of digits

## Programming Languages

The algorithms are implemented in the following programming languages:

- **Python** - An interpreted language with dynamic typing
- **C++** - A compiled language with manual memory management
- **Java** - A compiled language running on the JVM with automatic memory management
- **JavaScript** - An interpreted language with JIT compilation
- **Go** - A compiled language with garbage collection
- **C** - A low-level compiled language with manual memory management

## Implementation Details

Each sorting algorithm is implemented in a way that:

1. Reads data from a file of random integers
2. Measures the execution time of the sort
3. Verifies that the array is sorted correctly
4. Outputs the results including execution time and elements sorted per second

## How to Run

To run the performance comparison:

```bash
./scripts/run_comparison.sh
```

This script will:

1. Generate random data if it doesn't exist
2. Compile and run each implementation
3. Collect and analyze the results
4. Generate a comprehensive report

## Complexity Comparison

The following table provides a comprehensive comparison of the time and space complexity characteristics for each sorting algorithm:

| Algorithm      | Best Case Time | Average Case Time | Worst Case Time | Space Complexity | Stable? |
|---------------|----------------|------------------|-----------------|-----------------|---------|
| Bubble Sort   | O(n)           | O(n²)            | O(n²)           | O(1)            | Yes     |
| Selection Sort| O(n²)          | O(n²)            | O(n²)           | O(1)            | No      |
| Insertion Sort| O(n)           | O(n²)            | O(n²)           | O(1)            | Yes     |
| Quick Sort    | O(n log n)     | O(n log n)       | O(n²)           | O(log n) (avg)  | No      |
| Counting Sort | O(n + k)       | O(n + k)         | O(n + k)        | O(k)            | Yes     |
| Radix Sort    | O(d(n + k))    | O(d(n + k))      | O(d(n + k))     | O(n + k)        | Yes     |
| Merge Sort    | O(n log n)     | O(n log n)       | O(n log n)      | O(n)            | Yes     |

**Notes:**

- **n**: Number of elements to be sorted
- **k**: Range of input values (Counting Sort) or base (Radix Sort)
- **d**: Number of digits (Radix Sort)
- **Stability**: Indicates whether the algorithm preserves the relative order of equal elements, important in data mining for multi-attribute sorting

## Performance Analysis

For a detailed performance analysis of all sorting algorithms across languages, see the `analysis/performance_analysis.txt` file after running the comparison script.

## Conclusions

Different sorting algorithms excel in different scenarios:

- **Quick Sort** typically offers the best average-case performance for general-purpose sorting
- **Merge Sort** provides consistent performance with the benefit of stability
- **Insertion Sort** performs well on small or partially sorted datasets
- **Counting Sort** and **Radix Sort** can be extremely fast for specific integer distributions
- **Bubble Sort** and **Selection Sort** are simple but inefficient for large datasets

In terms of language performance:

- **C** and **C++** typically offer the best raw performance due to direct compilation
- **Go** provides good performance with simplified memory management
- **Java** offers competitive performance with good ecosystem support
- **JavaScript** can be surprisingly fast due to modern JIT compilers
- **Python** prioritizes readability and maintainability over raw performance

## Future Work

Potential areas for future investigation include:

- Testing with different data distributions (sorted, reverse-sorted, partially sorted)
- Comparing with language-specific built-in sorting implementations
- Implementing parallel versions of the sorting algorithms
- Profiling memory usage alongside execution time
- Exploring hybrid sorting algorithms for better real-world performance
