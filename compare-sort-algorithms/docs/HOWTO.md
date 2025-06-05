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
python3 scripts/generate_data.py
```

This will:

1. Read configuration from `config/number-of-data-points.txt`
2. Generate random datasets of specified sizes
3. Save them to the `datasets/` directory

You can also generate a specific size dataset by passing the size as a command-line argument:

```bash
python3 scripts/generate_data.py 1000000
```

## Running Individual Implementations

You can run each implementation individually for any of the sorting algorithms. Below are examples using different algorithms for each language:

### Python

```bash
python3 src/quick_sort.py     # Quick Sort example
python3 src/bubble_sort.py    # Bubble Sort example
python3 src/merge_sort.py     # Merge Sort example
# Other algorithms: selection_sort.py, insertion_sort.py, counting_sort.py, radix_sort.py
```

### C++

```bash
g++ -O2 -std=c++17 -o quick_sort_cpp src/quick_sort.cpp
./quick_sort_cpp          # Quick Sort example

# Other algorithms:
# g++ -O2 -std=c++17 -o bubble_sort_cpp src/bubble_sort.cpp
# g++ -O2 -std=c++17 -o selection_sort_cpp src/selection_sort.cpp
# etc.
```

### Java

```bash
javac src/QuickSort.java
java -cp src QuickSort      # Quick Sort example

# Other algorithms:
# javac src/BubbleSort.java
# java -cp src BubbleSort
# etc.
```

### JavaScript

```bash
node src/quick_sort.js        # Quick Sort example
node src/bubble_sort.js       # Bubble Sort example
# Other algorithms: selection_sort.js, insertion_sort.js, etc.
```

### Go

```bash
go run src/quick_sort.go      # Quick Sort example
go run src/merge_sort.go      # Merge Sort example
# Other algorithms: bubble_sort.go, selection_sort.go, etc.
```

### C

```bash
gcc -O2 -o quick_sort_c src/quick_sort.c
./quick_sort_c            # Quick Sort example

# Other algorithms:
# gcc -O2 -o bubble_sort_c src/bubble_sort.c
# gcc -O2 -o selection_sort_c src/selection_sort.c
# etc.
```

## Running Performance Comparison

To run a performance comparison across all implementations of all sorting algorithms:

```bash
python3 scripts/run_comparison.py
```

### Using run_comparison.py

The project includes a Python script (`run_comparison.py`) that provides flexibility and options for running performance comparisons:

```bash
python3 scripts/run_comparison.py [OPTIONS]
```

#### Available Options

- `--size SIZE1,SIZE2`: Comma-separated list of data sizes to test (e.g., 100000,250000). If not specified, uses config/number-of-data-points.txt
- `--algorithm ALGOS`: Comma-separated list of algorithms to test (e.g., quick,merge). Default: all algorithms
- `--language LANGS`: Comma-separated list of languages to test (e.g., cpp,java). Default: all languages
- `--generate-data Y/N`: Whether to re-generate data before running (Y/Yes/True to enable, default: False)
- `--repeat N`: Number of times to repeat each test and average the results (default: 1)
- `--help`: Show help message and exit

#### Examples

Run all algorithms in all languages with default settings:
```bash
python3 scripts/run_comparison.py
```

Test specific algorithms in specific languages:
```bash
python3 scripts/run_comparison.py --algorithm=counting,quick --language=cpp,java
```

Test with specific data sizes:
```bash
python3 scripts/run_comparison.py --size=100000,250000,500000
```

Force data regeneration before running:
```bash
python3 scripts/run_comparison.py --generate-data=Y
```

Run each test multiple times and average results:
```bash
python3 scripts/run_comparison.py --repeat=5
```

## Running Multi-Size Comparison

To test performance across multiple dataset sizes:

```bash
python3 scripts/run_multi_size_comparison.py [OPTIONS]
```

### Available Options for Multi-Size Comparison

- `--sizes SIZE1,SIZE2,...`: Comma-separated list of data sizes to test (e.g., 10000,100000,1000000)
- `--algorithm ALGOS`: Comma-separated list of algorithms to test (e.g., quick,merge)
- `--language LANGS`: Comma-separated list of languages to test (e.g., cpp,java)
- `--generate-data Y/N`: Whether to re-generate data before running
- `--repeat N`: Number of times to repeat each test
- `--output FILE`: Output file for results (default: analysis/multi_size_results.txt)
- `--help`: Show help message

#### Examples

Run multi-size comparison with default settings:
```bash
python3 scripts/run_multi_size_comparison.py
```

Test specific sizes and algorithms:
```bash
python3 scripts/run_multi_size_comparison.py --sizes=10000,100000,1000000 --algorithm=quick,merge
```

## Running Complete Study

For a comprehensive benchmark study of all sorting algorithms:

```bash
python3 scripts/run_complete_study.py [OPTIONS]
```

### Available Options for Complete Study

- `--sizes SIZE1,SIZE2,...`: Comma-separated list of data sizes to test
- `--algorithm ALGOS`: Comma-separated list of algorithms to test
- `--language LANGS`: Comma-separated list of languages to test
- `--generate-data Y/N`: Whether to re-generate data before running
- `--repeat N`: Number of times to repeat each test
- `--output-dir DIR`: Directory for output files (default: analysis/)
- `--help`: Show help message

#### Examples

Run complete study with default settings:
```bash
python3 scripts/run_complete_study.py
```

Run complete study with specific parameters:
```bash
python3 scripts/run_complete_study.py --sizes=10000,100000 --algorithm=quick,merge --repeat=3
```

## Using the Makefile

The project includes a Makefile to automate building, running, analyzing, and visualizing sorting algorithm benchmarks. Below is an overview of the main Makefile rules and how to use them:

### Main Targets

- **all**: Runs the full workflow: build, run, analyze, visualize, and update docs.
  ```bash
  make all
  ```
- **build**: Compiles all C, C++, and Java sorting implementations.
  ```bash
  make build
  ```
- **run**: Runs the main Python script to execute all benchmarks.
  ```bash
  make run
  ```
- **analyze**: Analyzes benchmark results using a Python script.
  ```bash
  make analyze
  ```
- **visualize**: Generates plots/visualizations from results.
  ```bash
  make visualize
  ```
- **docs**: Updates documentation using a Python script.
  ```bash
  make docs
  ```
- **clean**: Removes build artifacts, result files, analysis, and generated images.
  ```bash
  make clean
  ```

### Additional Targets

- **generate-data**: Generates datasets for testing.
  ```bash
  make generate-data
  ```
- **all-scripts**: Runs all scripts in sequence: generate data, run, analyze, visualize, docs.
  ```bash
  make all-scripts
  ```
- **shell**: Runs the main shell script (`run-all.sh`).
  ```bash
  make shell
  ```
- **comparison**: Runs the performance comparison Python script.
  ```bash
  make comparison
  ```
- **multi-size-comparison**: Runs the multi-size comparison Python script.
  ```bash
  make multi-size-comparison
  ```
- **complete-study**: Runs the complete study Python script.
  ```bash
  make complete-study
  ```
- **venv**: Sets up a Python virtual environment and installs requirements for visualization.
  ```bash
  make venv
  ```
- **generate-json**: Generates JSON files containing performance data for visualization.
  ```bash
  make generate-json
  ```

You can use these Makefile rules to automate the workflow for building, running, analyzing, and visualizing sorting algorithm benchmarks. For example, to run the entire workflow from scratch, simply use `make all` or `make all-scripts`.

## Analyzing Results

After running tests, you can analyze the results:

```bash
python3 scripts/analyze_results.py
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

## Visualizing Results

To generate a log-log plot of execution time vs. data size for all algorithms and languages:

1. Set up the Python virtual environment using make:
   ```bash
   make venv
   ```
   This will create a virtual environment and install all required dependencies.

2. Run the visualization script:
   ```bash
   python3 scripts/visualize_results.py
   ```

   This will save the plot as `docs/sorting_performance_loglog.png`.

## Managing Virtual Environment

### Deactivating Virtual Environment

When you're done working with the virtual environment, you can deactivate it by running:
```bash
deactivate
```

### Deleting Virtual Environment

If you want to completely remove the virtual environment, you can:

1. First deactivate it if it's active:
   ```bash
   deactivate
   ```

2. Then delete the virtual environment directory:
   ```bash
   rm -rf venv
   ```

You can always recreate the virtual environment later using `make venv` when needed.
