# How to Run Quick Sort Implementations and Performance Tests

This guide explains how to run the Quick Sort implementations and performance tests in this project.

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

You can run each implementation individually:

### Python:
```bash
cd src
python3 quick_sort.py
```

### C++:
```bash
cd src
g++ -O2 -std=c++17 -o quick_sort_cpp quick_sort.cpp
./quick_sort_cpp
```

### Java:
```bash
cd src
javac QuickSort.java
java -cp . QuickSort
```

### JavaScript:
```bash
cd src
node quick_sort.js
```

### Go:
```bash
cd src
go run quick_sort.go
```

### C:
```bash
cd src
gcc -O2 -o quick_sort_c quick_sort.c
./quick_sort_c
```

## Running Performance Comparison

To run a performance comparison across all implementations:

```bash
cd scripts
./run_comparison.sh
```

This will:
1. Generate test data (if not already available)
2. Build and run all implementations
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

For a comprehensive benchmark study:

```bash
cd scripts
./run_complete_study.sh
```

This will:
1. Run all implementations on all dataset sizes
2. Generate detailed analysis reports
3. Create visualization data
4. Save comprehensive study results to `docs/QUICK-SORTS-COMPLETE-STUDY.md`

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
- Complete study report: `cat docs/QUICK-SORTS-COMPLETE-STUDY.md`

## Customizing Tests

You can customize the dataset sizes by editing the `config/number-of-data-points.txt` file.
Each line should contain a single integer representing a dataset size to test.
