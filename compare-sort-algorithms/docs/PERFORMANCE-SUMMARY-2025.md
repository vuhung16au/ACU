# Sorting Algorithms Performance Summary 2025

*Generated on: Sun  1 Jun 2025 16:45:39 AEST*

This document provides a summary of the performance benchmarks for various sorting algorithms implemented in different programming languages.

## Performance Visualization

The following log-log plot visualizes the execution time vs. data size for each algorithm and language:

![Sorting Algorithm Performance: Execution Time vs. Data Size (log-log)](sorting_performance_loglog.png)

## Overview

The benchmarks compare the following sorting algorithms:
- Bubble Sort
- Selection Sort
- Insertion Sort
- Quick Sort
- Merge Sort
- Counting Sort
- Radix Sort

Across implementations in:
- Python
- C++
- Java
- JavaScript
- Go
- C

## Key Findings

## Overall Performance Ranking

The following table shows the overall performance ranking of different implementations (N = 500,000, fastest for each language/algorithm):

| Rank | Language/Algorithm      | Time (seconds) | Relative Speed |
|------|------------------------|----------------|----------------|
| 1    | C / Counting Sort      | 0.00242        | 1.0×           |
| 2    | C++ / Counting Sort    | 0.00498        | 2.1×           |
| 3    | Go / Counting Sort     | 0.00573        | 2.4×           |
| 4    | Java / Counting Sort   | 0.00873        | 3.6×           |
| 5    | JavaScript / Counting Sort | 0.01312    | 5.4×           |
| 6    | Python / Counting Sort | 0.29412        | 121.6×         |
| 7    | C++ / Quick Sort       | 0.02768        | 11.4×          |
| 8    | Go / Quick Sort        | 0.02829        | 11.7×          |
| 9    | C / Quick Sort         | 0.03031        | 12.5×          |
| 10   | Java / Quick Sort      | 0.03209        | 13.3×          |
| 11   | JavaScript / Quick Sort| 0.04736        | 19.6×          |
| 12   | Python / Quick Sort    | 0.69584        | 287.6×         |

*Note: O(n²) algorithms (Bubble, Selection, Insertion) were not run on large datasets due to impractical runtimes.*

## Algorithm-specific Performance

### Fast Algorithms

Across all implementations, the following algorithms consistently perform best:

- Quick Sort
- Merge Sort
- Counting Sort (for appropriate datasets)
- Radix Sort

### Slow Algorithms

The following algorithms generally performed more slowly, especially with large datasets:

- Bubble Sort
- Selection Sort
- Insertion Sort

## Language Comparison

### Best Performing Languages

Based on average performance across all algorithms:

1. C
2. C++
3. Go
4. Java
5. JavaScript
6. Python

## Conclusion

The benchmarks confirm the theoretical time complexity expectations:

- O(n²) algorithms (Bubble, Selection, Insertion) perform poorly on large datasets
- O(n log n) algorithms (Quick, Merge) perform well across all dataset sizes
- O(n) algorithms (Counting, Radix) can outperform others in specific circumstances

For most practical applications, Quick Sort or Merge Sort implementations should be preferred, with the specific language choice depending on the project requirements.
