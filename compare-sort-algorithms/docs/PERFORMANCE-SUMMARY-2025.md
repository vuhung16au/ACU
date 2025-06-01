# Sorting Algorithms Performance Summary 2025

*Generated on: Sun  1 Jun 2025 16:45:39 AEST*

This document provides a summary of the performance benchmarks for various sorting algorithms implemented in different programming languages.

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

The following table shows the overall performance ranking of different implementations:

| Rank | Language/Algorithm | Time (seconds) | Relative Speed |
|------|-------------------|----------------|----------------|

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
