# Quick Sort Performance Comparison

This project compares the performance of Quick Sort algorithm implementations across multiple programming languages including Python, C++, Java, JavaScript, Go, and C.

## Project Structure

- `src/`: Contains all Quick Sort implementations
  - `quick_sort.py`: Python implementation
  - `quick_sort.cpp`: C++ implementation
  - `QuickSort.java`: Java implementation
  - `quick_sort.js`: JavaScript implementation
  - `quick_sort.go`: Go implementation
  - `quick_sort.c`: C implementation

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
  - `results_{language}_{size}.txt`: Individual test results for each language and dataset size

- `analysis/`: Contains analysis reports and consolidated results
  - `consolidated_results_{size}.txt`: Combined results for specific dataset sizes
  - `performance_analysis.txt`: Detailed performance analysis

- `docs/`: Contains documentation and findings
  - `HOWTO.md`: Instructions for running tests and implementations
  - `CONTRIBUTING.md`: Guidelines for contributing to the project
  - `QUICK-SORTS-COMPLETE-STUDY.md`: Comprehensive study findings

## Quick Start

1. Generate random datasets:
   ```bash
   python3 scripts/generate_data.py
   ```

2. Run performance comparison:
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
