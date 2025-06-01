# How to Run Sorting Algorithm Implementations and Performance Tests

This guide explains how to run the various sorting algorithm implementations and performance tests in this project. The following algorithms are included:

- Bubble Sort
- Selection Sort
- Insertion Sort
- Quick Sort
- Counting Sort
- Radix Sort
- Merge Sort

## Prerequisites

Make sure you have the following installed:

- Python 3.x
- C/C++ compiler (GCC or equivalent)
- Java Development Kit (JDK)
- Node.js
- Go compiler

## Generating Test Data

Before running any performance tests, you need to generate test datasets:

```bash
cd scripts
python3 generate_data.py
```

This will:
1. Read configuration from `config/number-of-data-points.txt`
2. Generate random datasets of specified sizes
3. Save them to the `datasets/` directory

You can also generate a specific size dataset by passing the size as a command-line argument:

```bash
cd scripts
python3 generate_data.py 1000000
```

## Running Individual Implementations

You can run each implementation individually for any of the sorting algorithms. Below are examples using different algorithms for each language:

### Python:
```bash
cd src
python3 quick_sort.py     # Quick Sort example
python3 bubble_sort.py    # Bubble Sort example
python3 merge_sort.py     # Merge Sort example
# Other algorithms: selection_sort.py, insertion_sort.py, counting_sort.py, radix_sort.py
```

### C++:
```bash
cd src
g++ -O2 -std=c++17 -o quick_sort_cpp quick_sort.cpp
./quick_sort_cpp          # Quick Sort example

# Other algorithms:
# g++ -O2 -std=c++17 -o bubble_sort_cpp bubble_sort.cpp
# g++ -O2 -std=c++17 -o selection_sort_cpp selection_sort.cpp
# etc.
```

### Java:
```bash
cd src
javac QuickSort.java
java -cp . QuickSort      # Quick Sort example

# Other algorithms:
# javac BubbleSort.java
# java -cp . BubbleSort
# etc.
```

### JavaScript:
```bash
cd src
node quick_sort.js        # Quick Sort example
node bubble_sort.js       # Bubble Sort example
# Other algorithms: selection_sort.js, insertion_sort.js, etc.
```

### Go:
```bash
cd src
go run quick_sort.go      # Quick Sort example
go run merge_sort.go      # Merge Sort example
# Other algorithms: bubble_sort.go, selection_sort.go, etc.
```

### C:
```bash
cd src
gcc -O2 -o quick_sort_c quick_sort.c
./quick_sort_c            # Quick Sort example

# Other algorithms:
# gcc -O2 -o bubble_sort_c bubble_sort.c
# gcc -O2 -o selection_sort_c selection_sort.c
# etc.
```

## Running Performance Comparison

To run a performance comparison across all implementations of all sorting algorithms:

```bash
cd scripts
./run_comparison.sh
```

This will:
1. Generate test data (if not already available)
2. Build and run all implementations of all sorting algorithms
3. Measure execution time for each
4. Compile and display results
5. Save consolidated results to `analysis/consolidated_results.txt`

## Running Multi-Size Comparison

To test performance across multiple dataset sizes:

```bash
cd scripts
./run_multi_size_comparison.sh
```

This will run each implementation against datasets of different sizes and save size-specific results.

## Running Complete Study

For a comprehensive benchmark study of all sorting algorithms:

```bash
cd scripts
./run_complete_study.sh
```

This will:
1. Run all sorting algorithm implementations on all dataset sizes
2. Generate detailed analysis reports for each algorithm
3. Create visualization data comparing algorithm performance
4. Save comprehensive study results to documentation files:
   - Quick Sort specific study: `docs/QUICK-SORTS-COMPLETE-STUDY.md`
   - All algorithms comparison: `docs/SORTING-ALGORITHMS-COMPARISON.md`
   - Multi-size performance study: `docs/MULTI_SIZE_PERFORMANCE_STUDY.md`

## Analyzing Results

After running tests, you can analyze the results:

```bash
cd scripts
python3 analyze_results.py
```

This will:
1. Parse all result files
2. Generate detailed performance analysis
3. Save analysis to `analysis/performance_analysis.txt`

## Viewing Results

- Quick results summary: `cat analysis/consolidated_results.txt`
- Detailed analysis: `cat analysis/performance_analysis.txt`
- Complete Quick Sort study: `cat docs/QUICK-SORTS-COMPLETE-STUDY.md`
- Sorting algorithms comparison: `cat docs/SORTING-ALGORITHMS-COMPARISON.md`
- Multi-size performance study: `cat docs/MULTI_SIZE_PERFORMANCE_STUDY.md`
- Algorithm selection guide: `cat docs/ALGORITHM-PERFORMANCE-GUIDE.md`

## Customizing Tests

You can customize the dataset sizes by editing the `config/number-of-data-points.txt` file.
Each line should contain a single integer representing a dataset size to test.
