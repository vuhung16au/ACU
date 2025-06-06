## Quick Start

0. Quick start (clean, analyze, visualize):

Edit `config/number-of-data-points.txt` to set the number of data points to sort, then run:

   ```bash
   make clean ; make analyze; make visualize
   ```

1. Run the all-in-one script to generate data, run comparisons, and update documentation:

   ```bash
   python scripts/run_all.py
   ```

   To run each test multiple times and average the results (e.g., 7 times):

   ```bash
   python scripts/run_all.py --repeat 7
   ```

2. View consolidated results for a specific size (e.g., 100,000):

   ```bash
   cat analysis/consolidated_results_100000.txt
   ```

3. View the performance summary:

   ```bash
   cat docs/PERFORMANCE-SUMMARY-2025.md
   ```

4. View multi-size comparison study:

   ```bash
   cat docs/MULTI_SIZE_PERFORMANCE_STUDY.md
   ```

## Usage

This repository provides scripts to run and compare sorting algorithm implementations across languages.

### Quick Start

Run all tests with default settings:

```bash
./scripts/run-all.sh
```

### Additional Options

The main script (`run-all.sh`) supports several options:

- `--clean` - Clean all generated datasets and results
- `--clean-results` - Clean only results files
- `--clean-datasets` - Clean only dataset files
- `--generate-only` - Generate datasets but don't run comparisons
- `--sizes SIZE1,SIZE2,...` - Run tests only for specific data sizes (comma-separated)

#### Examples

```bash
# Clean all data and start fresh
./scripts/run-all.sh --clean

# Run only for small dataset (faster testing)
./scripts/run-all.sh --sizes 5000

# Generate data without running tests
./scripts/run-all.sh --generate-only
```

### Data Sizes

The following data sizes are currently configured for testing:
10, 100, 1,000, 10,000

Last updated: 06 June 2025

## Requirements

- Python 3.x
- C/C++ compiler (GCC or equivalent)
- Java Development Kit (JDK)
- Node.js
- Go compiler
- Rust compiler (rustc)
- .NET 9.0 SDK (for C#)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Key Performance Findings (June 2025)

Our latest multi-size performance comparison study revealed:

- **Fastest Algorithm:** Counting Sort in C achieved 206.8M elements/second with 500K dataset
- **Best General-Purpose Algorithm:** Quick Sort demonstrated excellent performance across all languages
- **Quadratic Impact:** O(n²) algorithms became impractical beyond tiny datasets (N > 10)
- **Language Performance:** Low-level languages (C, C++) significantly outperformed interpreted languages
- **Scaling Characteristics:** Linear algorithms (Counting, Radix) showed the best scaling properties

### Performance Highlights (N = 500K elements)
TODO: Check the performance of Rust, C#.

| Algorithm    | C         | C++       | Go       | JavaScript | Java     | Python   | Rust     | C#      |
|--------------|-----------|-----------|----------|------------|----------|----------|----------|----------|
| Counting Sort| **206.8M/s** | 100.4M/s  | 87.2M/s  | 38.1M/s    | 57.3M/s  | `1.7M/s` | 180.2M/s | (see docs) |
| Radix Sort   | **75.4M/s**  | 74.0M/s   | 40.2M/s  | 13.4M/s    | 19.6M/s  | `0.7M/s` | 72.8M/s  | (see docs) |
| Quick Sort   | 16.5M/s   | **18.1M/s**   | 17.7M/s  | 10.6M/s    | 15.6M/s  | `0.7M/s` | 17.9M/s  | (see docs) |
| Merge Sort   | `7.8M/s`    | **19.9M/s**   | 10.4M/s  | 4.6M/s     | 9.7M/s   | 0.5M/s   | 18.2M/s  | (see docs) |

*Numbers represent elements sorted per second. **Bold = highest (best)**, `Monospace = lowest (worst)`. Higher is better/faster.*

For comprehensive results and analysis, see [docs/PERFORMANCE-SUMMARY-2025.md](docs/PERFORMANCE-SUMMARY-2025.md) and [docs/MULTI_SIZE_PERFORMANCE_STUDY.md](docs/MULTI_SIZE_PERFORMANCE_STUDY.md).

## Documentation

For more detailed information on how to run the implementations and tests, see [docs/HOWTO.md](docs/HOWTO.md).

For information on how to contribute to the project, see [docs/CONTRIBUTING.md](docs/CONTRIBUTING.md).

For a complete list of documentation, see [docs/README.md](docs/README.md).

For guidance on which sorting algorithm to use for different scenarios, see [docs/ALGORITHM-PERFORMANCE-GUIDE.md](docs/ALGORITHM-PERFORMANCE-GUIDE.md).

For a comprehensive comparison of all sorting algorithms, see [docs/SORTING-ALGORITHMS-COMPARISON.md](docs/SORTING-ALGORITHMS-COMPARISON.md).

## Visualization of Results

The following log-log plot visualizes the execution time vs. data size for each algorithm and language:

![Merge Sorting Algorithm Performance: Execution Time vs. Data Size (log-log)](docs/merge_sort_across_languages.png)

To generate this plot, use the provided Python script:

```bash
make visualize
```

This will generate a log-log plot of execution time vs. data size for each algorithm and language, saved as `images/*.png`.


### C#

All C# sorting algorithms are implemented in a single .NET project using .NET 9.0. To run a specific algorithm:

```bash
# Example: Run Bubble Sort in C#
dotnet run --project src/SortAlgorithmsCs -- bubble datasets/random_list_1000.txt results/results_cs_bubble_1000.txt

# Replace 'bubble' with any of: bubble, insertion, selection, merge, quick, counting, radix
# The first argument is the algorithm, followed by the input dataset and output result file.
```

### PHP

- Memory limit set to 1GB for handling large arrays
- Execution time limit of 5 minutes (300 seconds)

To run a specific algorithm:

```bash
# Example: Run Bubble Sort in PHP
php src/bubble_sort.php < datasets/random_list_1000.txt > results/results_php_bubble_1000.txt

# Replace 'bubble' with any of: bubble, insertion, selection, merge, quick, counting, radix
# The input is read from stdin and output is written to stdout
```

# System Information

| Language | Version | Full Info |
|----------|---------|-----------|
| .NET | 9.0.201 | .NET SDK version 9.0.201 |
| C (GCC) | 17.0.0 | Apple clang version 17.0.0 (clang-1700.0.13.5), Target: arm64-apple-darwin24.5.0 |
| C++ (G++) | 17.0.0 | Apple clang version 17.0.0 (clang-1700.0.13.5), Target: arm64-apple-darwin24.5.0 |
| Rust | 1.87.0 | rustc 1.87.0 (17067e9ac 2025-05-09) (Homebrew) |
| Java | 24.0.1 | openjdk version "24.0.1" 2025-04-15, OpenJDK Runtime Environment Temurin-24.0.1+9 |
| Python | 3.13.4 | Python 3.13.4 |
| Node.js | 22.15.0 | v22.15.0 |
| Go | 1.24.3 | go version go1.24.3 darwin/arm64 |
| PHP | 8.4.8 | PHP 8.4.8 (cli) (built: Jun  3 2025 16:29:26) (NTS) / Zend Engine v4.4.8 |

```bash
uname -a
Darwin 24.5.0 Darwin Kernel Version 24.5.0: Tue Apr 22 19:54:49 PDT 2025; root:xnu-11417.121.6~2/RELEASE_ARM64_T6000 arm64
```

### C#

All C# sorting algorithms are implemented in a single .NET project using .NET 9.0. To run a specific algorithm:

```bash
# Example: Run Bubble Sort in C#
dotnet run --project src/SortAlgorithmsCs -- bubble datasets/random_list_1000.txt results/results_cs_bubble_1000.txt

# Replace 'bubble' with any of: bubble, insertion, selection, merge, quick, counting, radix
# The first argument is the algorithm, followed by the input dataset and output result file.
```
