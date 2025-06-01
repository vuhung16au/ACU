# Sorting Algorithms Performance Comparison

This project compares the performance of various sorting algorithm implementations across multiple programming languages including Python, C++, Java, JavaScript, Go, and C. The following sorting algorithms are implemented:

- Bubble Sort
- Selection Sort
- Insertion Sort
- Quick Sort
- Counting Sort
- Radix Sort
- Merge Sort

## Project Structure

- `src/`: Contains all sorting algorithm implementations
  - Bubble Sort: `bubble_sort.py`, `bubble_sort.cpp`, `BubbleSort.java`, `bubble_sort.js`, `bubble_sort.go`, `bubble_sort.c`
  - Selection Sort: `selection_sort.py`, `selection_sort.cpp`, `SelectionSort.java`, `selection_sort.js`, `selection_sort.go`, `selection_sort.c`
  - Insertion Sort: `insertion_sort.py`, `insertion_sort.cpp`, `InsertionSort.java`, `insertion_sort.js`, `insertion_sort.go`, `insertion_sort.c`
  - Quick Sort: `quick_sort.py`, `quick_sort.cpp`, `QuickSort.java`, `quick_sort.js`, `quick_sort.go`, `quick_sort.c`
  - Counting Sort: `counting_sort.py`, `counting_sort.cpp`, `CountingSort.java`, `counting_sort.js`, `counting_sort.go`, `counting_sort.c`
  - Radix Sort: `radix_sort.py`, `radix_sort.cpp`, `RadixSort.java`, `radix_sort.js`, `radix_sort.go`, `radix_sort.c`
  - Merge Sort: `merge_sort.py`, `merge_sort.cpp`, `MergeSort.java`, `merge_sort.js`, `merge_sort.go`, `merge_sort.c`

- `scripts/`: Contains all performance test and utility scripts
  - `generate_data.py`: Generates random datasets for testing
  - `run_comparison.sh`: Runs performance tests across all implementations
  - `run_multi_size_comparison.sh`: Runs tests with multiple data sizes
  - `run_complete_study.sh`: Runs a comprehensive benchmarking study
  - `analyze_results.py`: Analyzes and summarizes test results

- `config/`: Contains configuration files
  - `number-of-data-points.txt`: Defines dataset sizes for testing

- `datasets/`: Contains generated random datasets for testing
  - `random_list_{size}.txt`: Random integer datasets of different sizes
  - `random_list.txt`: Default dataset (copy of the 100,000 elements dataset)

- `results/`: Contains raw performance results
  - `results_{language}.txt`: Results for each programming language implementation
  - `results_{language}_{algorithm}.txt`: Algorithm-specific results for certain language implementations

- `analysis/`: Contains analysis reports and consolidated results
  - `consolidated_results_{size}.txt`: Combined results for specific dataset sizes
  - `performance_analysis.txt`: Detailed performance analysis

- `docs/`: Contains documentation and findings
  - `HOWTO.md`: Instructions for running tests and implementations
  - `CONTRIBUTING.md`: Guidelines for contributing to the project
  - `QUICK-SORTS-COMPLETE-STUDY.md`: Comprehensive study of Quick Sort performance
  - `SORTING-ALGORITHMS-COMPARISON.md`: Comparison of all sorting algorithms
  - `MULTI_SIZE_PERFORMANCE_STUDY.md`: Performance analysis across different dataset sizes

## Quick Start

1. Generate random datasets:

   ```bash
   python3 scripts/generate_data.py
   ```

2. Run performance comparison for all sorting algorithms:

   ```bash
   ./scripts/run_comparison.sh
   ```

3. View consolidated results:

   ```bash
   cat analysis/consolidated_results.txt
   ```

4. View detailed analysis:

   ```bash
   cat analysis/performance_analysis.txt
   ```

5. View sorting algorithms comparison:

   ```bash
   cat docs/SORTING-ALGORITHMS-COMPARISON.md
   ```

## Requirements

- Python 3.x
- C/C++ compiler (GCC or equivalent)
- Java Development Kit (JDK)
- Node.js
- Go compiler

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Documentation

For more detailed information on how to run the implementations and tests, see [docs/HOWTO.md](docs/HOWTO.md).

For information on how to contribute to the project, see [docs/CONTRIBUTING.md](docs/CONTRIBUTING.md).

For guidance on which sorting algorithm to use for different scenarios, see [docs/ALGORITHM-PERFORMANCE-GUIDE.md](docs/ALGORITHM-PERFORMANCE-GUIDE.md).

For a comprehensive comparison of all sorting algorithms, see [docs/SORTING-ALGORITHMS-COMPARISON.md](docs/SORTING-ALGORITHMS-COMPARISON.md).
