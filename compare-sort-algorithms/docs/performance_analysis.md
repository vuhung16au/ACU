# Sorting Algorithms Performance Analysis

---


## Performance Metrics

This section lists execution times and throughput (elements/second) for each language/algorithm combination.


### Data Size: 1,000 Elements

#### Execution Time Comparison

| Language/Algorithm          |   Execution Time (sec) |   Elements/Second | Sorted Correctly   |
|-----------------------------|------------------------|-------------------|--------------------|
| Python (Bubble Sort)        |               0.056271 |                 0 | False              |
| Python (Selection Sort)     |               0.042754 |                 0 | False              |
| Python (Insertion Sort)     |               0.045194 |                 0 | False              |
| Python (Quick Sort)         |               0.030893 |                 0 | False              |
| Python (Merge Sort)         |               0.033777 |                 0 | False              |
| Python (Counting Sort)      |               0.324482 |                 0 | False              |
| Python (Radix Sort)         |               0.029198 |                 0 | False              |
| C++ (Bubble Sort)           |               0.154395 |                 0 | False              |
| C++ (Selection Sort)        |               0.131955 |                 0 | False              |
| C++ (Insertion Sort)        |               0.126414 |                 0 | False              |
| C++ (Quick Sort)            |               0.145584 |                 0 | False              |
| C++ (Merge Sort)            |               0.223483 |                 0 | False              |
| C++ (Counting Sort)         |               0.236422 |                 0 | False              |
| C++ (Radix Sort)            |               0.213157 |                 0 | False              |
| Java (Bubble Sort)          |               0.06248  |                 0 | False              |
| Java (Selection Sort)       |               0.060629 |                 0 | False              |
| Java (Insertion Sort)       |               0.062471 |                 0 | False              |
| Java (Quick Sort)           |               0.06144  |                 0 | False              |
| Java (Merge Sort)           |               0.059605 |                 0 | False              |
| Java (Counting Sort)        |               0.064759 |                 0 | False              |
| Java (Radix Sort)           |               0.057966 |                 0 | False              |
| JavaScript (Bubble Sort)    |               0.031873 |                 0 | False              |
| JavaScript (Selection Sort) |               0.030402 |                 0 | False              |
| JavaScript (Insertion Sort) |               0.029501 |                 0 | False              |
| JavaScript (Quick Sort)     |               0.029913 |                 0 | False              |
| JavaScript (Merge Sort)     |               0.028744 |                 0 | False              |
| JavaScript (Counting Sort)  |               0.04842  |                 0 | False              |
| JavaScript (Radix Sort)     |               0.029074 |                 0 | False              |
| Go (Bubble Sort)            |               0.062406 |                 0 | False              |
| Go (Selection Sort)         |               0.059327 |                 0 | False              |
| Go (Insertion Sort)         |               0.061112 |                 0 | False              |
| Go (Quick Sort)             |               0.060959 |                 0 | False              |
| Go (Merge Sort)             |               0.059083 |                 0 | False              |
| Go (Counting Sort)          |               0.065353 |                 0 | False              |
| Go (Radix Sort)             |               0.059807 |                 0 | False              |
| C (Bubble Sort)             |               0.139469 |                 0 | False              |
| C (Selection Sort)          |               0.148034 |                 0 | False              |
| C (Insertion Sort)          |               0.134066 |                 0 | False              |
| C (Quick Sort)              |               0.13751  |                 0 | False              |
| C (Merge Sort)              |               0.232774 |                 0 | False              |
| C (Counting Sort)           |               0.220171 |                 0 | False              |
| C (Radix Sort)              |               0.225299 |                 0 | False              |

#### Performance Ranking

Ranks all implementations from fastest to slowest.

|   Rank | Language/Algorithm          |   Time (sec) | vs Fastest   |
|--------|-----------------------------|--------------|--------------|
|      1 | JavaScript (Merge Sort)     |     0.028744 | 1.00x        |
|      2 | JavaScript (Radix Sort)     |     0.029074 | 1.01x        |
|      3 | Python (Radix Sort)         |     0.029198 | 1.02x        |
|      4 | JavaScript (Insertion Sort) |     0.029501 | 1.03x        |
|      5 | JavaScript (Quick Sort)     |     0.029913 | 1.04x        |
|      6 | JavaScript (Selection Sort) |     0.030402 | 1.06x        |
|      7 | Python (Quick Sort)         |     0.030893 | 1.07x        |
|      8 | JavaScript (Bubble Sort)    |     0.031873 | 1.11x        |
|      9 | Python (Merge Sort)         |     0.033777 | 1.18x        |
|     10 | Python (Selection Sort)     |     0.042754 | 1.49x        |
|     11 | Python (Insertion Sort)     |     0.045194 | 1.57x        |
|     12 | JavaScript (Counting Sort)  |     0.04842  | 1.68x        |
|     13 | Python (Bubble Sort)        |     0.056271 | 1.96x        |
|     14 | Java (Radix Sort)           |     0.057966 | 2.02x        |
|     15 | Go (Merge Sort)             |     0.059083 | 2.06x        |
|     16 | Go (Selection Sort)         |     0.059327 | 2.06x        |
|     17 | Java (Merge Sort)           |     0.059605 | 2.07x        |
|     18 | Go (Radix Sort)             |     0.059807 | 2.08x        |
|     19 | Java (Selection Sort)       |     0.060629 | 2.11x        |
|     20 | Go (Quick Sort)             |     0.060959 | 2.12x        |
|     21 | Go (Insertion Sort)         |     0.061112 | 2.13x        |
|     22 | Java (Quick Sort)           |     0.06144  | 2.14x        |
|     23 | Go (Bubble Sort)            |     0.062406 | 2.17x        |
|     24 | Java (Insertion Sort)       |     0.062471 | 2.17x        |
|     25 | Java (Bubble Sort)          |     0.06248  | 2.17x        |
|     26 | Java (Counting Sort)        |     0.064759 | 2.25x        |
|     27 | Go (Counting Sort)          |     0.065353 | 2.27x        |
|     28 | C++ (Insertion Sort)        |     0.126414 | 4.40x        |
|     29 | C++ (Selection Sort)        |     0.131955 | 4.59x        |
|     30 | C (Insertion Sort)          |     0.134066 | 4.66x        |
|     31 | C (Quick Sort)              |     0.13751  | 4.78x        |
|     32 | C (Bubble Sort)             |     0.139469 | 4.85x        |
|     33 | C++ (Quick Sort)            |     0.145584 | 5.06x        |
|     34 | C (Selection Sort)          |     0.148034 | 5.15x        |
|     35 | C++ (Bubble Sort)           |     0.154395 | 5.37x        |
|     36 | C++ (Radix Sort)            |     0.213157 | 7.42x        |
|     37 | C (Counting Sort)           |     0.220171 | 7.66x        |
|     38 | C++ (Merge Sort)            |     0.223483 | 7.77x        |
|     39 | C (Radix Sort)              |     0.225299 | 7.84x        |
|     40 | C (Merge Sort)              |     0.232774 | 8.10x        |
|     41 | C++ (Counting Sort)         |     0.236422 | 8.23x        |
|     42 | Python (Counting Sort)      |     0.324482 | 11.29x       |

#### Performance Insights

- **Fastest:** JavaScript (Merge Sort) (0.028744 seconds)
- **Slowest:** Python (Counting Sort) (0.324482 seconds)
- **Speed difference:** 11.29x
- **Average time:** 0.098015 seconds
- **Standard deviation:** 0.075456 seconds

#### Language and Algorithm Analysis

Provides insights for each programming language and algorithm.

**Python (Bubble Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Python (Selection Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Python (Insertion Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Python (Quick Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Python (Merge Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Python (Counting Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Python (Radix Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C++ (Bubble Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C++ (Selection Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C++ (Insertion Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C++ (Quick Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C++ (Merge Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C++ (Counting Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C++ (Radix Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Java (Bubble Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Java (Selection Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Java (Insertion Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Java (Quick Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Java (Merge Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Java (Counting Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Java (Radix Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**JavaScript (Bubble Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**JavaScript (Selection Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**JavaScript (Insertion Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**JavaScript (Quick Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**JavaScript (Merge Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**JavaScript (Counting Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**JavaScript (Radix Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Go (Bubble Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Go (Selection Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Go (Insertion Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Go (Quick Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Go (Merge Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Go (Counting Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Go (Radix Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C (Bubble Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C (Selection Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C (Insertion Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C (Quick Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C (Merge Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C (Counting Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C (Radix Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second


---


### Data Size: 10,000 Elements

#### Execution Time Comparison

| Language/Algorithm          |   Execution Time (sec) |   Elements/Second | Sorted Correctly   |
|-----------------------------|------------------------|-------------------|--------------------|
| Python (Bubble Sort)        |               3.27356  |                 0 | False              |
| Python (Selection Sort)     |               1.40646  |                 0 | False              |
| Python (Insertion Sort)     |               1.78206  |                 0 | False              |
| Python (Quick Sort)         |               0.040905 |                 0 | False              |
| Python (Merge Sort)         |               0.049023 |                 0 | False              |
| Python (Counting Sort)      |               0.332075 |                 0 | False              |
| Python (Radix Sort)         |               0.043078 |                 0 | False              |
| C++ (Bubble Sort)           |               0.174839 |                 0 | False              |
| C++ (Selection Sort)        |               0.25335  |                 0 | False              |
| C++ (Insertion Sort)        |               0.137172 |                 0 | False              |
| C++ (Quick Sort)            |               0.162755 |                 0 | False              |
| C++ (Merge Sort)            |               0.153542 |                 0 | False              |
| C++ (Counting Sort)         |               0.150989 |                 0 | False              |
| C++ (Radix Sort)            |               0.139729 |                 0 | False              |
| Java (Bubble Sort)          |               0.119515 |                 0 | False              |
| Java (Selection Sort)       |               0.103375 |                 0 | False              |
| Java (Insertion Sort)       |               0.080542 |                 0 | False              |
| Java (Quick Sort)           |               0.06703  |                 0 | False              |
| Java (Merge Sort)           |               0.076269 |                 0 | False              |
| Java (Counting Sort)        |               0.071917 |                 0 | False              |
| Java (Radix Sort)           |               0.065991 |                 0 | False              |
| JavaScript (Bubble Sort)    |               0.351321 |                 0 | False              |
| JavaScript (Selection Sort) |               0.08119  |                 0 | False              |
| JavaScript (Insertion Sort) |               0.052892 |                 0 | False              |
| JavaScript (Quick Sort)     |               0.033498 |                 0 | False              |
| JavaScript (Merge Sort)     |               0.031978 |                 0 | False              |
| JavaScript (Counting Sort)  |               0.047164 |                 0 | False              |
| JavaScript (Radix Sort)     |               0.033235 |                 0 | False              |
| Go (Bubble Sort)            |               0.160163 |                 0 | False              |
| Go (Selection Sort)         |               0.100805 |                 0 | False              |
| Go (Insertion Sort)         |               0.072824 |                 0 | False              |
| Go (Quick Sort)             |               0.058199 |                 0 | False              |
| Go (Merge Sort)             |               0.057388 |                 0 | False              |
| Go (Counting Sort)          |               0.064284 |                 0 | False              |
| Go (Radix Sort)             |               0.057468 |                 0 | False              |
| C (Bubble Sort)             |               0.187853 |                 0 | False              |
| C (Selection Sort)          |               0.263696 |                 0 | False              |
| C (Insertion Sort)          |               0.149184 |                 0 | False              |
| C (Quick Sort)              |               0.149378 |                 0 | False              |
| C (Merge Sort)              |               0.145166 |                 0 | False              |
| C (Counting Sort)           |               0.142334 |                 0 | False              |
| C (Radix Sort)              |               0.145394 |                 0 | False              |

#### Performance Ranking

Ranks all implementations from fastest to slowest.

|   Rank | Language/Algorithm          |   Time (sec) | vs Fastest   |
|--------|-----------------------------|--------------|--------------|
|      1 | JavaScript (Merge Sort)     |     0.031978 | 1.00x        |
|      2 | JavaScript (Radix Sort)     |     0.033235 | 1.04x        |
|      3 | JavaScript (Quick Sort)     |     0.033498 | 1.05x        |
|      4 | Python (Quick Sort)         |     0.040905 | 1.28x        |
|      5 | Python (Radix Sort)         |     0.043078 | 1.35x        |
|      6 | JavaScript (Counting Sort)  |     0.047164 | 1.47x        |
|      7 | Python (Merge Sort)         |     0.049023 | 1.53x        |
|      8 | JavaScript (Insertion Sort) |     0.052892 | 1.65x        |
|      9 | Go (Merge Sort)             |     0.057388 | 1.79x        |
|     10 | Go (Radix Sort)             |     0.057468 | 1.80x        |
|     11 | Go (Quick Sort)             |     0.058199 | 1.82x        |
|     12 | Go (Counting Sort)          |     0.064284 | 2.01x        |
|     13 | Java (Radix Sort)           |     0.065991 | 2.06x        |
|     14 | Java (Quick Sort)           |     0.06703  | 2.10x        |
|     15 | Java (Counting Sort)        |     0.071917 | 2.25x        |
|     16 | Go (Insertion Sort)         |     0.072824 | 2.28x        |
|     17 | Java (Merge Sort)           |     0.076269 | 2.39x        |
|     18 | Java (Insertion Sort)       |     0.080542 | 2.52x        |
|     19 | JavaScript (Selection Sort) |     0.08119  | 2.54x        |
|     20 | Go (Selection Sort)         |     0.100805 | 3.15x        |
|     21 | Java (Selection Sort)       |     0.103375 | 3.23x        |
|     22 | Java (Bubble Sort)          |     0.119515 | 3.74x        |
|     23 | C++ (Insertion Sort)        |     0.137172 | 4.29x        |
|     24 | C++ (Radix Sort)            |     0.139729 | 4.37x        |
|     25 | C (Counting Sort)           |     0.142334 | 4.45x        |
|     26 | C (Merge Sort)              |     0.145166 | 4.54x        |
|     27 | C (Radix Sort)              |     0.145394 | 4.55x        |
|     28 | C (Insertion Sort)          |     0.149184 | 4.67x        |
|     29 | C (Quick Sort)              |     0.149378 | 4.67x        |
|     30 | C++ (Counting Sort)         |     0.150989 | 4.72x        |
|     31 | C++ (Merge Sort)            |     0.153542 | 4.80x        |
|     32 | Go (Bubble Sort)            |     0.160163 | 5.01x        |
|     33 | C++ (Quick Sort)            |     0.162755 | 5.09x        |
|     34 | C++ (Bubble Sort)           |     0.174839 | 5.47x        |
|     35 | C (Bubble Sort)             |     0.187853 | 5.87x        |
|     36 | C++ (Selection Sort)        |     0.25335  | 7.92x        |
|     37 | C (Selection Sort)          |     0.263696 | 8.25x        |
|     38 | Python (Counting Sort)      |     0.332075 | 10.38x       |
|     39 | JavaScript (Bubble Sort)    |     0.351321 | 10.99x       |
|     40 | Python (Selection Sort)     |     1.40646  | 43.98x       |
|     41 | Python (Insertion Sort)     |     1.78206  | 55.73x       |
|     42 | Python (Bubble Sort)        |     3.27356  | 102.37x      |

#### Performance Insights

- **Fastest:** JavaScript (Merge Sort) (0.031978 seconds)
- **Slowest:** Python (Bubble Sort) (3.273564 seconds)
- **Speed difference:** 102.37x
- **Average time:** 0.263562 seconds
- **Standard deviation:** 0.578716 seconds

#### Language and Algorithm Analysis

Provides insights for each programming language and algorithm.

**Python (Bubble Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Python (Selection Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Python (Insertion Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Python (Quick Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Python (Merge Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Python (Counting Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Python (Radix Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C++ (Bubble Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C++ (Selection Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C++ (Insertion Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C++ (Quick Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C++ (Merge Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C++ (Counting Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C++ (Radix Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Java (Bubble Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Java (Selection Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Java (Insertion Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Java (Quick Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Java (Merge Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Java (Counting Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Java (Radix Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**JavaScript (Bubble Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**JavaScript (Selection Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**JavaScript (Insertion Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**JavaScript (Quick Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**JavaScript (Merge Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**JavaScript (Counting Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**JavaScript (Radix Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Go (Bubble Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Go (Selection Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Go (Insertion Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Go (Quick Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Go (Merge Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Go (Counting Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Go (Radix Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C (Bubble Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C (Selection Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C (Insertion Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C (Quick Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C (Merge Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C (Counting Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C (Radix Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second


---


### Data Size: 50,000 Elements

#### Execution Time Comparison

| Language/Algorithm          | Execution Time (sec)   | Elements/Second   | Sorted Correctly   |
|-----------------------------|------------------------|-------------------|--------------------|
| Python (Bubble Sort)        | N/A                    | N/A               | False              |
| Python (Selection Sort)     | N/A                    | N/A               | False              |
| Python (Insertion Sort)     | N/A                    | N/A               | False              |
| Python (Quick Sort)         | 0.096903               | 0                 | False              |
| Python (Merge Sort)         | 0.123037               | 0                 | False              |
| Python (Counting Sort)      | 0.372508               | 0                 | False              |
| Python (Radix Sort)         | 0.120091               | 0                 | False              |
| C++ (Quick Sort)            | 0.167036               | 0                 | False              |
| C++ (Merge Sort)            | 0.148744               | 0                 | False              |
| C++ (Counting Sort)         | 0.148691               | 0                 | False              |
| C++ (Radix Sort)            | 0.160476               | 0                 | False              |
| Java (Bubble Sort)          | N/A                    | N/A               | False              |
| Java (Selection Sort)       | N/A                    | N/A               | False              |
| Java (Insertion Sort)       | N/A                    | N/A               | False              |
| Java (Quick Sort)           | 0.080998               | 0                 | False              |
| Java (Merge Sort)           | 0.097417               | 0                 | False              |
| Java (Counting Sort)        | 0.076753               | 0                 | False              |
| Java (Radix Sort)           | 0.073523               | 0                 | False              |
| JavaScript (Bubble Sort)    | N/A                    | N/A               | False              |
| JavaScript (Selection Sort) | N/A                    | N/A               | False              |
| JavaScript (Insertion Sort) | N/A                    | N/A               | False              |
| JavaScript (Quick Sort)     | 0.043863               | 0                 | False              |
| JavaScript (Merge Sort)     | 0.048772               | 0                 | False              |
| JavaScript (Counting Sort)  | 0.053140               | 0                 | False              |
| JavaScript (Radix Sort)     | 0.048513               | 0                 | False              |
| Go (Bubble Sort)            | N/A                    | N/A               | False              |
| Go (Selection Sort)         | N/A                    | N/A               | False              |
| Go (Insertion Sort)         | N/A                    | N/A               | False              |
| Go (Quick Sort)             | 0.062098               | 0                 | False              |
| Go (Merge Sort)             | 0.060440               | 0                 | False              |
| Go (Counting Sort)          | 0.066998               | 0                 | False              |
| Go (Radix Sort)             | 0.060279               | 0                 | False              |
| C (Bubble Sort)             | N/A                    | N/A               | False              |
| C (Selection Sort)          | N/A                    | N/A               | False              |
| C (Insertion Sort)          | N/A                    | N/A               | False              |
| C (Quick Sort)              | 0.155772               | 0                 | False              |
| C (Merge Sort)              | 0.153535               | 0                 | False              |
| C (Counting Sort)           | 0.154731               | 0                 | False              |
| C (Radix Sort)              | 0.143549               | 0                 | False              |

#### Performance Ranking

Ranks all implementations from fastest to slowest.

|   Rank | Language/Algorithm          | Time (sec)   | vs Fastest   |
|--------|-----------------------------|--------------|--------------|
|      1 | JavaScript (Quick Sort)     | 0.043863     | 1.00x        |
|      2 | JavaScript (Radix Sort)     | 0.048513     | 1.11x        |
|      3 | JavaScript (Merge Sort)     | 0.048772     | 1.11x        |
|      4 | JavaScript (Counting Sort)  | 0.053140     | 1.21x        |
|      5 | Go (Radix Sort)             | 0.060279     | 1.37x        |
|      6 | Go (Merge Sort)             | 0.060440     | 1.38x        |
|      7 | Go (Quick Sort)             | 0.062098     | 1.42x        |
|      8 | Go (Counting Sort)          | 0.066998     | 1.53x        |
|      9 | Java (Radix Sort)           | 0.073523     | 1.68x        |
|     10 | Java (Counting Sort)        | 0.076753     | 1.75x        |
|     11 | Java (Quick Sort)           | 0.080998     | 1.85x        |
|     12 | Python (Quick Sort)         | 0.096903     | 2.21x        |
|     13 | Java (Merge Sort)           | 0.097417     | 2.22x        |
|     14 | Python (Radix Sort)         | 0.120091     | 2.74x        |
|     15 | Python (Merge Sort)         | 0.123037     | 2.81x        |
|     16 | C (Radix Sort)              | 0.143549     | 3.27x        |
|     17 | C++ (Counting Sort)         | 0.148691     | 3.39x        |
|     18 | C++ (Merge Sort)            | 0.148744     | 3.39x        |
|     19 | C (Merge Sort)              | 0.153535     | 3.50x        |
|     20 | C (Counting Sort)           | 0.154731     | 3.53x        |
|     21 | C (Quick Sort)              | 0.155772     | 3.55x        |
|     22 | C++ (Radix Sort)            | 0.160476     | 3.66x        |
|     23 | C++ (Quick Sort)            | 0.167036     | 3.81x        |
|     24 | Python (Counting Sort)      | 0.372508     | 8.49x        |
|     25 | Python (Bubble Sort)        | N/A          | N/A          |
|     26 | Python (Selection Sort)     | N/A          | N/A          |
|     27 | Python (Insertion Sort)     | N/A          | N/A          |
|     28 | Java (Bubble Sort)          | N/A          | N/A          |
|     29 | Java (Selection Sort)       | N/A          | N/A          |
|     30 | Java (Insertion Sort)       | N/A          | N/A          |
|     31 | JavaScript (Bubble Sort)    | N/A          | N/A          |
|     32 | JavaScript (Selection Sort) | N/A          | N/A          |
|     33 | JavaScript (Insertion Sort) | N/A          | N/A          |
|     34 | Go (Bubble Sort)            | N/A          | N/A          |
|     35 | Go (Selection Sort)         | N/A          | N/A          |
|     36 | Go (Insertion Sort)         | N/A          | N/A          |
|     37 | C (Bubble Sort)             | N/A          | N/A          |
|     38 | C (Selection Sort)          | N/A          | N/A          |
|     39 | C (Insertion Sort)          | N/A          | N/A          |

#### Performance Insights

- **Fastest:** JavaScript (Quick Sort) (0.043863 seconds)
- **Slowest:** Python (Counting Sort) (0.372508 seconds)
- **Speed difference:** 8.49x
- **Average time:** 0.113244 seconds
- **Standard deviation:** 0.069988 seconds

#### Language and Algorithm Analysis

Provides insights for each programming language and algorithm.

**Python (Bubble Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Python (Selection Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Python (Insertion Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Python (Quick Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Python (Merge Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Python (Counting Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Python (Radix Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C++ (Quick Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C++ (Merge Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C++ (Counting Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C++ (Radix Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Java (Bubble Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Java (Selection Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Java (Insertion Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Java (Quick Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Java (Merge Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Java (Counting Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Java (Radix Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**JavaScript (Bubble Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**JavaScript (Selection Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**JavaScript (Insertion Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**JavaScript (Quick Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**JavaScript (Merge Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**JavaScript (Counting Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**JavaScript (Radix Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Go (Bubble Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Go (Selection Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Go (Insertion Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Go (Quick Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Go (Merge Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Go (Counting Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Go (Radix Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C (Bubble Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C (Selection Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C (Insertion Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C (Quick Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C (Merge Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C (Counting Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C (Radix Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second


---


### Data Size: 100,000 Elements

#### Execution Time Comparison

| Language/Algorithm          | Execution Time (sec)   | Elements/Second   | Sorted Correctly   |
|-----------------------------|------------------------|-------------------|--------------------|
| Python (Bubble Sort)        | N/A                    | N/A               | False              |
| Python (Selection Sort)     | N/A                    | N/A               | False              |
| Python (Insertion Sort)     | N/A                    | N/A               | False              |
| Python (Quick Sort)         | 0.157977               | 0                 | False              |
| Python (Merge Sort)         | 0.239818               | 0                 | False              |
| Python (Counting Sort)      | 0.401182               | 0                 | False              |
| Python (Radix Sort)         | 0.193924               | 0                 | False              |
| C++ (Quick Sort)            | 1.161536               | 0                 | False              |
| C++ (Merge Sort)            | 0.158871               | 0                 | False              |
| C++ (Counting Sort)         | 0.166549               | 0                 | False              |
| C++ (Radix Sort)            | 0.167109               | 0                 | False              |
| Java (Bubble Sort)          | N/A                    | N/A               | False              |
| Java (Selection Sort)       | N/A                    | N/A               | False              |
| Java (Insertion Sort)       | N/A                    | N/A               | False              |
| Java (Quick Sort)           | 0.099843               | 0                 | False              |
| Java (Merge Sort)           | 0.088452               | 0                 | False              |
| Java (Counting Sort)        | 0.088130               | 0                 | False              |
| Java (Radix Sort)           | 0.085704               | 0                 | False              |
| JavaScript (Bubble Sort)    | N/A                    | N/A               | False              |
| JavaScript (Selection Sort) | N/A                    | N/A               | False              |
| JavaScript (Insertion Sort) | N/A                    | N/A               | False              |
| JavaScript (Quick Sort)     | 0.054750               | 0                 | False              |
| JavaScript (Merge Sort)     | 0.061851               | 0                 | False              |
| JavaScript (Counting Sort)  | 0.063397               | 0                 | False              |
| JavaScript (Radix Sort)     | 0.055175               | 0                 | False              |
| Go (Bubble Sort)            | N/A                    | N/A               | False              |
| Go (Selection Sort)         | N/A                    | N/A               | False              |
| Go (Insertion Sort)         | N/A                    | N/A               | False              |
| Go (Quick Sort)             | 0.063918               | 0                 | False              |
| Go (Merge Sort)             | 0.068086               | 0                 | False              |
| Go (Counting Sort)          | 0.071379               | 0                 | False              |
| Go (Radix Sort)             | 0.060599               | 0                 | False              |
| C (Bubble Sort)             | N/A                    | N/A               | False              |
| C (Selection Sort)          | N/A                    | N/A               | False              |
| C (Insertion Sort)          | N/A                    | N/A               | False              |
| C (Quick Sort)              | 0.160292               | 0                 | False              |
| C (Merge Sort)              | 0.173957               | 0                 | False              |
| C (Counting Sort)           | 0.156491               | 0                 | False              |
| C (Radix Sort)              | 0.151663               | 0                 | False              |

#### Performance Ranking

Ranks all implementations from fastest to slowest.

|   Rank | Language/Algorithm          | Time (sec)   | vs Fastest   |
|--------|-----------------------------|--------------|--------------|
|      1 | JavaScript (Quick Sort)     | 0.054750     | 1.00x        |
|      2 | JavaScript (Radix Sort)     | 0.055175     | 1.01x        |
|      3 | Go (Radix Sort)             | 0.060599     | 1.11x        |
|      4 | JavaScript (Merge Sort)     | 0.061851     | 1.13x        |
|      5 | JavaScript (Counting Sort)  | 0.063397     | 1.16x        |
|      6 | Go (Quick Sort)             | 0.063918     | 1.17x        |
|      7 | Go (Merge Sort)             | 0.068086     | 1.24x        |
|      8 | Go (Counting Sort)          | 0.071379     | 1.30x        |
|      9 | Java (Radix Sort)           | 0.085704     | 1.57x        |
|     10 | Java (Counting Sort)        | 0.088130     | 1.61x        |
|     11 | Java (Merge Sort)           | 0.088452     | 1.62x        |
|     12 | Java (Quick Sort)           | 0.099843     | 1.82x        |
|     13 | C (Radix Sort)              | 0.151663     | 2.77x        |
|     14 | C (Counting Sort)           | 0.156491     | 2.86x        |
|     15 | Python (Quick Sort)         | 0.157977     | 2.89x        |
|     16 | C++ (Merge Sort)            | 0.158871     | 2.90x        |
|     17 | C (Quick Sort)              | 0.160292     | 2.93x        |
|     18 | C++ (Counting Sort)         | 0.166549     | 3.04x        |
|     19 | C++ (Radix Sort)            | 0.167109     | 3.05x        |
|     20 | C (Merge Sort)              | 0.173957     | 3.18x        |
|     21 | Python (Radix Sort)         | 0.193924     | 3.54x        |
|     22 | Python (Merge Sort)         | 0.239818     | 4.38x        |
|     23 | Python (Counting Sort)      | 0.401182     | 7.33x        |
|     24 | C++ (Quick Sort)            | 1.161536     | 21.22x       |
|     25 | Python (Bubble Sort)        | N/A          | N/A          |
|     26 | Python (Selection Sort)     | N/A          | N/A          |
|     27 | Python (Insertion Sort)     | N/A          | N/A          |
|     28 | Java (Bubble Sort)          | N/A          | N/A          |
|     29 | Java (Selection Sort)       | N/A          | N/A          |
|     30 | Java (Insertion Sort)       | N/A          | N/A          |
|     31 | JavaScript (Bubble Sort)    | N/A          | N/A          |
|     32 | JavaScript (Selection Sort) | N/A          | N/A          |
|     33 | JavaScript (Insertion Sort) | N/A          | N/A          |
|     34 | Go (Bubble Sort)            | N/A          | N/A          |
|     35 | Go (Selection Sort)         | N/A          | N/A          |
|     36 | Go (Insertion Sort)         | N/A          | N/A          |
|     37 | C (Bubble Sort)             | N/A          | N/A          |
|     38 | C (Selection Sort)          | N/A          | N/A          |
|     39 | C (Insertion Sort)          | N/A          | N/A          |

#### Performance Insights

- **Fastest:** JavaScript (Quick Sort) (0.054750 seconds)
- **Slowest:** C++ (Quick Sort) (1.161536 seconds)
- **Speed difference:** 21.22x
- **Average time:** 0.172944 seconds
- **Standard deviation:** 0.224673 seconds

#### Language and Algorithm Analysis

Provides insights for each programming language and algorithm.

**Python (Bubble Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Python (Selection Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Python (Insertion Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Python (Quick Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Python (Merge Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Python (Counting Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Python (Radix Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C++ (Quick Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C++ (Merge Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C++ (Counting Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C++ (Radix Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Java (Bubble Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Java (Selection Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Java (Insertion Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Java (Quick Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Java (Merge Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Java (Counting Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Java (Radix Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**JavaScript (Bubble Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**JavaScript (Selection Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**JavaScript (Insertion Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**JavaScript (Quick Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**JavaScript (Merge Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**JavaScript (Counting Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**JavaScript (Radix Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Go (Bubble Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Go (Selection Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Go (Insertion Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Go (Quick Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Go (Merge Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Go (Counting Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Go (Radix Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C (Bubble Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C (Selection Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C (Insertion Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C (Quick Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C (Merge Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C (Counting Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C (Radix Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second


---


### Data Size: 250,000 Elements

#### Execution Time Comparison

| Language/Algorithm          | Execution Time (sec)   | Elements/Second   | Sorted Correctly   |
|-----------------------------|------------------------|-------------------|--------------------|
| Python (Bubble Sort)        | N/A                    | N/A               | False              |
| Python (Selection Sort)     | N/A                    | N/A               | False              |
| Python (Insertion Sort)     | N/A                    | N/A               | False              |
| Python (Quick Sort)         | 0.369432               | 0                 | False              |
| Python (Merge Sort)         | 0.570557               | 0                 | False              |
| Python (Counting Sort)      | 0.545373               | 0                 | False              |
| Python (Radix Sort)         | 0.445239               | 0                 | False              |
| C++ (Quick Sort)            | 0.201928               | 0                 | False              |
| C++ (Merge Sort)            | 0.201981               | 0                 | False              |
| C++ (Counting Sort)         | 0.169103               | 0                 | False              |
| C++ (Radix Sort)            | 0.197219               | 0                 | False              |
| Java (Bubble Sort)          | N/A                    | N/A               | False              |
| Java (Selection Sort)       | N/A                    | N/A               | False              |
| Java (Insertion Sort)       | N/A                    | N/A               | False              |
| Java (Quick Sort)           | 0.106502               | 0                 | False              |
| Java (Merge Sort)           | 0.111380               | 0                 | False              |
| Java (Counting Sort)        | 0.095835               | 0                 | False              |
| Java (Radix Sort)           | 0.100734               | 0                 | False              |
| JavaScript (Bubble Sort)    | N/A                    | N/A               | False              |
| JavaScript (Selection Sort) | N/A                    | N/A               | False              |
| JavaScript (Insertion Sort) | N/A                    | N/A               | False              |
| JavaScript (Quick Sort)     | 0.088762               | 0                 | False              |
| JavaScript (Merge Sort)     | 0.116536               | 0                 | False              |
| JavaScript (Counting Sort)  | 0.080940               | 0                 | False              |
| JavaScript (Radix Sort)     | 0.085188               | 0                 | False              |
| Go (Bubble Sort)            | N/A                    | N/A               | False              |
| Go (Selection Sort)         | N/A                    | N/A               | False              |
| Go (Insertion Sort)         | N/A                    | N/A               | False              |
| Go (Quick Sort)             | 0.081810               | 0                 | False              |
| Go (Merge Sort)             | 0.100493               | 0                 | False              |
| Go (Counting Sort)          | 0.071606               | 0                 | False              |
| Go (Radix Sort)             | 0.071536               | 0                 | False              |
| C (Bubble Sort)             | N/A                    | N/A               | False              |
| C (Selection Sort)          | N/A                    | N/A               | False              |
| C (Insertion Sort)          | N/A                    | N/A               | False              |
| C (Quick Sort)              | 0.153608               | 0                 | False              |
| C (Merge Sort)              | 0.180514               | 0                 | False              |
| C (Counting Sort)           | 0.161767               | 0                 | False              |
| C (Radix Sort)              | 0.150737               | 0                 | False              |

#### Performance Ranking

Ranks all implementations from fastest to slowest.

|   Rank | Language/Algorithm          | Time (sec)   | vs Fastest   |
|--------|-----------------------------|--------------|--------------|
|      1 | Go (Radix Sort)             | 0.071536     | 1.00x        |
|      2 | Go (Counting Sort)          | 0.071606     | 1.00x        |
|      3 | JavaScript (Counting Sort)  | 0.080940     | 1.13x        |
|      4 | Go (Quick Sort)             | 0.081810     | 1.14x        |
|      5 | JavaScript (Radix Sort)     | 0.085188     | 1.19x        |
|      6 | JavaScript (Quick Sort)     | 0.088762     | 1.24x        |
|      7 | Java (Counting Sort)        | 0.095835     | 1.34x        |
|      8 | Go (Merge Sort)             | 0.100493     | 1.40x        |
|      9 | Java (Radix Sort)           | 0.100734     | 1.41x        |
|     10 | Java (Quick Sort)           | 0.106502     | 1.49x        |
|     11 | Java (Merge Sort)           | 0.111380     | 1.56x        |
|     12 | JavaScript (Merge Sort)     | 0.116536     | 1.63x        |
|     13 | C (Radix Sort)              | 0.150737     | 2.11x        |
|     14 | C (Quick Sort)              | 0.153608     | 2.15x        |
|     15 | C (Counting Sort)           | 0.161767     | 2.26x        |
|     16 | C++ (Counting Sort)         | 0.169103     | 2.36x        |
|     17 | C (Merge Sort)              | 0.180514     | 2.52x        |
|     18 | C++ (Radix Sort)            | 0.197219     | 2.76x        |
|     19 | C++ (Quick Sort)            | 0.201928     | 2.82x        |
|     20 | C++ (Merge Sort)            | 0.201981     | 2.82x        |
|     21 | Python (Quick Sort)         | 0.369432     | 5.16x        |
|     22 | Python (Radix Sort)         | 0.445239     | 6.22x        |
|     23 | Python (Counting Sort)      | 0.545373     | 7.62x        |
|     24 | Python (Merge Sort)         | 0.570557     | 7.98x        |
|     25 | Python (Bubble Sort)        | N/A          | N/A          |
|     26 | Python (Selection Sort)     | N/A          | N/A          |
|     27 | Python (Insertion Sort)     | N/A          | N/A          |
|     28 | Java (Bubble Sort)          | N/A          | N/A          |
|     29 | Java (Selection Sort)       | N/A          | N/A          |
|     30 | Java (Insertion Sort)       | N/A          | N/A          |
|     31 | JavaScript (Bubble Sort)    | N/A          | N/A          |
|     32 | JavaScript (Selection Sort) | N/A          | N/A          |
|     33 | JavaScript (Insertion Sort) | N/A          | N/A          |
|     34 | Go (Bubble Sort)            | N/A          | N/A          |
|     35 | Go (Selection Sort)         | N/A          | N/A          |
|     36 | Go (Insertion Sort)         | N/A          | N/A          |
|     37 | C (Bubble Sort)             | N/A          | N/A          |
|     38 | C (Selection Sort)          | N/A          | N/A          |
|     39 | C (Insertion Sort)          | N/A          | N/A          |

#### Performance Insights

- **Fastest:** Go (Radix Sort) (0.071536 seconds)
- **Slowest:** Python (Merge Sort) (0.570557 seconds)
- **Speed difference:** 7.98x
- **Average time:** 0.185782 seconds
- **Standard deviation:** 0.145787 seconds

#### Language and Algorithm Analysis

Provides insights for each programming language and algorithm.

**Python (Bubble Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Python (Selection Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Python (Insertion Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Python (Quick Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Python (Merge Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Python (Counting Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Python (Radix Sort):**
  - Interpreted language with dynamic typing. Generally slower but highly readable and maintainable.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C++ (Quick Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C++ (Merge Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C++ (Counting Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C++ (Radix Sort):**
  - Compiled language with manual memory management. Typically fastest due to low-level optimizations.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Java (Bubble Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Java (Selection Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Java (Insertion Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Java (Quick Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Java (Merge Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Java (Counting Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Java (Radix Sort):**
  - Compiled to bytecode, runs on JVM. Good performance with automatic memory management.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**JavaScript (Bubble Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**JavaScript (Selection Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**JavaScript (Insertion Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**JavaScript (Quick Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**JavaScript (Merge Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**JavaScript (Counting Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**JavaScript (Radix Sort):**
  - Interpreted/JIT compiled. Performance varies by engine (V8 is highly optimized).
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**Go (Bubble Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**Go (Selection Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**Go (Insertion Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**Go (Quick Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**Go (Merge Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**Go (Counting Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**Go (Radix Sort):**
  - Compiled language with garbage collection, designed for concurrency and performance.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second

**C (Bubble Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Inefficient for large datasets.
  - Throughput: 0 elements/second

**C (Selection Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Performs poorly on large datasets.
  - Throughput: 0 elements/second

**C (Insertion Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Simple comparison-based algorithm with O(n²) complexity. Efficient for small or nearly sorted data.
  - Throughput: 0 elements/second

**C (Quick Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with average O(n log n) complexity. Good for general-purpose sorting.
  - Throughput: 0 elements/second

**C (Merge Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Divide-and-conquer algorithm with O(n log n) complexity. Stable sort with predictable performance.
  - Throughput: 0 elements/second

**C (Counting Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(n+k) complexity where k is the range of input.
  - Throughput: 0 elements/second

**C (Radix Sort):**
  - Low-level compiled language with manual memory management. Typically very fast.
  - Non-comparative integer sorting algorithm with O(d*(n+k)) complexity where d is the number of digits.
  - Throughput: 0 elements/second


---


## Cross-Size Performance Analysis

Analyzes how performance scales with different data sizes.

### Performance Scaling

**Python (Bubble Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 58.2x
    - Scaling factor: 5.82
**Python (Selection Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 32.9x
    - Scaling factor: 3.29
**Python (Insertion Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 39.4x
    - Scaling factor: 3.94
**Python (Quick Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.3x
    - Scaling factor: 0.13
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 2.4x
    - Scaling factor: 0.47
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.6x
    - Scaling factor: 0.82
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 2.3x
    - Scaling factor: 0.94
**Python (Merge Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.5x
    - Scaling factor: 0.15
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 2.5x
    - Scaling factor: 0.50
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.9x
    - Scaling factor: 0.97
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 2.4x
    - Scaling factor: 0.95
**Python (Counting Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.0x
    - Scaling factor: 0.10
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.22
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.54
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.4x
    - Scaling factor: 0.54
**Python (Radix Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.5x
    - Scaling factor: 0.15
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 2.8x
    - Scaling factor: 0.56
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.6x
    - Scaling factor: 0.81
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 2.3x
    - Scaling factor: 0.92
**C++ (Bubble Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
**C++ (Selection Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.9x
    - Scaling factor: 0.19
**C++ (Insertion Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
**C++ (Quick Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 7.0x
    - Scaling factor: 3.48
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 0.2x
    - Scaling factor: 0.07
**C++ (Merge Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 0.7x
    - Scaling factor: 0.07
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.19
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.53
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.3x
    - Scaling factor: 0.51
**C++ (Counting Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 0.6x
    - Scaling factor: 0.06
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.20
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.56
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.0x
    - Scaling factor: 0.41
**C++ (Radix Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 0.7x
    - Scaling factor: 0.07
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.23
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.0x
    - Scaling factor: 0.52
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.2x
    - Scaling factor: 0.47
**Java (Bubble Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.9x
    - Scaling factor: 0.19
**Java (Selection Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.7x
    - Scaling factor: 0.17
**Java (Insertion Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.3x
    - Scaling factor: 0.13
**Java (Quick Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.2x
    - Scaling factor: 0.24
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.2x
    - Scaling factor: 0.62
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.1x
    - Scaling factor: 0.43
**Java (Merge Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.3x
    - Scaling factor: 0.13
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.3x
    - Scaling factor: 0.26
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 0.9x
    - Scaling factor: 0.45
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.3x
    - Scaling factor: 0.50
**Java (Counting Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.57
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.1x
    - Scaling factor: 0.43
**Java (Radix Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.22
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.2x
    - Scaling factor: 0.58
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.2x
    - Scaling factor: 0.47
**JavaScript (Bubble Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 11.0x
    - Scaling factor: 1.10
**JavaScript (Selection Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 2.7x
    - Scaling factor: 0.27
**JavaScript (Insertion Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.8x
    - Scaling factor: 0.18
**JavaScript (Quick Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.3x
    - Scaling factor: 0.26
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.2x
    - Scaling factor: 0.62
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.6x
    - Scaling factor: 0.65
**JavaScript (Merge Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.5x
    - Scaling factor: 0.31
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.3x
    - Scaling factor: 0.63
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.9x
    - Scaling factor: 0.75
**JavaScript (Counting Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.0x
    - Scaling factor: 0.10
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.23
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.2x
    - Scaling factor: 0.60
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.3x
    - Scaling factor: 0.51
**JavaScript (Radix Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.5x
    - Scaling factor: 0.29
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.57
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.5x
    - Scaling factor: 0.62
**Go (Bubble Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 2.6x
    - Scaling factor: 0.26
**Go (Selection Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.7x
    - Scaling factor: 0.17
**Go (Insertion Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.2x
    - Scaling factor: 0.12
**Go (Quick Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.0x
    - Scaling factor: 0.10
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.0x
    - Scaling factor: 0.51
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.3x
    - Scaling factor: 0.51
**Go (Merge Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.0x
    - Scaling factor: 0.10
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.56
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.5x
    - Scaling factor: 0.59
**Go (Counting Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.0x
    - Scaling factor: 0.10
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.53
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.0x
    - Scaling factor: 0.40
**Go (Radix Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.0x
    - Scaling factor: 0.10
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.0x
    - Scaling factor: 0.50
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.2x
    - Scaling factor: 0.47
**C (Bubble Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.3x
    - Scaling factor: 0.13
**C (Selection Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.8x
    - Scaling factor: 0.18
**C (Insertion Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
**C (Quick Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 1.1x
    - Scaling factor: 0.11
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.0x
    - Scaling factor: 0.51
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.0x
    - Scaling factor: 0.38
**C (Merge Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 0.6x
    - Scaling factor: 0.06
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.21
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.57
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.0x
    - Scaling factor: 0.42
**C (Counting Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 0.6x
    - Scaling factor: 0.06
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.1x
    - Scaling factor: 0.22
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.0x
    - Scaling factor: 0.51
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.0x
    - Scaling factor: 0.41
**C (Radix Sort):**
  - 1,000 → 10,000 elements:
    - Size increase: 10.0x
    - Time increase: 0.6x
    - Scaling factor: 0.06
  - 10,000 → 50,000 elements:
    - Size increase: 5.0x
    - Time increase: 1.0x
    - Scaling factor: 0.20
  - 50,000 → 100,000 elements:
    - Size increase: 2.0x
    - Time increase: 1.1x
    - Scaling factor: 0.53
  - 100,000 → 250,000 elements:
    - Size increase: 2.5x
    - Time increase: 1.0x
    - Scaling factor: 0.40

---

## Practical Recommendations for Algorithm Selection

- Small datasets (< 10,000): Any algorithm is suitable
- Medium datasets (10,000-100,000): Quick Sort or Merge Sort recommended
- Large datasets (> 100,000): Quick Sort, Merge Sort, or specialized algorithms
- Very large datasets (> 500,000): Consider parallel implementations
