# Python vs Cython Performance Comparison: Radix Sort

This project compares the performance of Python and Cython implementations of the radix sort algorithm. Radix sort is a non-comparative sorting algorithm that sorts integers by processing individual digits.

## Project Structure

```
cython-vs-python/
├── generate_random_list.py    # Script to generate random test data
├── radix_sort.py              # Python implementation of radix sort
├── radix_sort_cy.pyx          # Cython implementation of radix sort
├── radix_sort_test.py         # Performance testing script
├── setup.py                   # Setup script for Cython compilation
├── requirements.txt           # Python dependencies
├── Makefile                   # Build automation
└── README.md                  # This file
```

## Prerequisites

### For macOS:

1. **Install Homebrew** (if not already installed):
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. **Install Cython and setuptools**:
   ```bash
   brew install cython
   brew install setuptools
   ```

3. **Install Python 3.14** (if not already installed):
   ```bash
   brew install python@3.14
   ```

## Setup

### 1. Create Virtual Environment

The project uses Python 3.14 and creates a virtual environment automatically:

```bash
make setup
```

This will:
- Create a virtual environment using `/opt/homebrew/bin/python3.14`
- Install all required dependencies from `requirements.txt`

### 2. Manual Setup (Alternative)

If you prefer to set up manually:

```bash
# Create virtual environment
/opt/homebrew/bin/python3.14 -m venv .venv

# Activate virtual environment
source .venv/bin/activate

# Install dependencies
pip install -r requirements.txt
```

## Dependencies

The project requires the following Python packages:

- **cython** (>=3.0.0): For compiling Cython code
- **setuptools** (>=65.0.0): For building extensions
- **numpy** (>=1.24.0): For numerical operations in Cython

## Usage

### Quick Start

1. **Setup the environment**:
   ```bash
   make setup
   ```

2. **Generate test data**:
   ```bash
   make random      # Generate 1,000,000 numbers
   make random10m   # Generate 10,000,000 numbers
   ```

3. **Run performance tests**:
   ```bash
   make test
   ```

### Quick Performance Tests

You can also run quick performance tests with different data sizes:

```bash
make test1k    # Test with 1,000 elements
make test10k   # Test with 10,000 elements  
make test100k  # Test with 100,000 elements
make test1m    # Test with 1,000,000 elements
```

These targets will automatically generate the test data and run the performance comparison.

### Performance Visualization

Generate comprehensive performance comparison graphs:

```bash
make graph
```

This will:
- Test performance with 1K, 10K, 100K, 250K, 500K, and 1M elements
- Generate two graphs:
  - `python_vs_cython.png`: Performance comparison over time
  - `cython_speedup.png`: Speedup visualization
- Display detailed performance summary

### Manual Usage

#### Generate Random Test Data

```bash
# Generate 1,000,000 random integers
python generate_random_list.py -n 1000000 -o random_list_1000000.txt

# Generate 10,000,000 random integers
python generate_random_list.py -n 10000000 -o random_list_10000000.txt

# Custom parameters
python generate_random_list.py -n 5000000 -min 0 -max 1000000 -o custom_list.txt
```

#### Test Individual Implementations

```bash
# Test Python implementation
python radix_sort.py random_list_1000000.txt

# Test Cython implementation (after building)
python setup.py build_ext --inplace
python radix_sort_cy.pyx random_list_1000000.txt
```

#### Run Performance Comparison

```bash
# Build Cython extension and run tests
python setup.py build_ext --inplace
python radix_sort_test.py
```

## Makefile Targets

| Target | Description |
|--------|-------------|
| `make setup` | Create virtual environment and install dependencies |
| `make random` | Generate random list with 1,000,000 numbers |
| `make random10m` | Generate random list with 10,000,000 numbers |
| `make build` | Build Cython extension |
| `make test` | Run performance tests with existing data files |
| `make test1k` | Test performance with 1,000 elements |
| `make test10k` | Test performance with 10,000 elements |
| `make test100k` | Test performance with 100,000 elements |
| `make test1m` | Test performance with 1,000,000 elements |
| `make graph` | Generate performance comparison graphs |
| `make clean` | Remove all generated files |
| `make help` | Show available targets |

## Performance Results

The performance comparison between Python and Cython implementations of radix sort shows dramatic improvements with Cython optimization. The results demonstrate the power of compiled extensions for numerical algorithms.

### Performance Comparison Graph

![Python vs Cython Performance](images/python_vs_cython.png)

*Performance comparison showing execution time for different data sizes. Cython consistently outperforms Python with significant speedups.*

### Speedup Visualization

![Cython Speedup](images/cython_speedup.png)

*Speedup factors showing how many times faster Cython is compared to Python for different data sizes.*

### Detailed Performance Results

| Data Size | Python Time | Cython Time | Speedup | Improvement |
|-----------|-------------|-------------|---------|-------------|
| 1K        | 0.0014s     | 0.0000s     | 31.03x  | 96.8%       |
| 10K       | 0.0135s     | 0.0003s     | 40.08x  | 97.5%       |
| 100K      | 0.1468s     | 0.0034s     | 43.51x  | 97.7%       |
| 250K      | 0.4213s     | 0.0080s     | 52.48x  | 98.1%       |
| 500K      | 1.0114s     | 0.0173s     | 58.58x  | 98.3%       |
| 1M        | 2.5179s     | 0.0333s     | 75.69x  | 98.7%       |

### Key Performance Insights

- **Average Speedup**: 50.23x faster with Cython
- **Best Performance**: 75.69x speedup at 1M elements
- **Scaling**: Performance advantage increases with larger datasets
- **Consistency**: Cython maintains significant advantages across all tested sizes

### Why Cython is Faster

Cython is faster because it compiles Python code to C extensions, eliminating the Python interpreter overhead and allowing direct memory access with C-level data types. This enables compiler optimizations and removes Python object overhead in tight loops, resulting in performance that approaches native C code while maintaining Python's syntax and ease of use.

### Performance Testing

The performance test compares:

1. **Python Implementation**: Pure Python radix sort with Python lists and loops
2. **Cython Implementation**: Cython-optimized radix sort with C-level optimizations

### Sample Output

```
Python vs Cython Radix Sort Performance Test
============================================================

Testing with random_list_1000000.txt...
============================================================
Loading data...
Loaded 1000000 numbers
Range: 0 to 9999999

Testing Python radix sort...
Python radix sort completed in 2.3456 seconds
First 10 elements: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
Last 10 elements: [9999990, 9999991, 9999992, 9999993, 9999994, 9999995, 9999996, 9999997, 9999998, 9999999]

Testing Cython radix sort...
Cython radix sort completed in 0.4567 seconds
First 10 elements: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
Last 10 elements: [9999990, 9999991, 9999992, 9999993, 9999994, 9999995, 9999996, 9999997, 9999998, 9999999]

✓ Results match between Python and Cython implementations

Performance Comparison:
Python time:  2.3456 seconds
Cython time:  0.4567 seconds
Speedup:      5.13x
Cython is 5.13x faster than Python
============================================================
```

## Algorithm Details

### Radix Sort

Radix sort is a non-comparative integer sorting algorithm that:

1. Sorts integers by processing individual digits
2. Uses counting sort as a subroutine
3. Processes digits from least significant to most significant
4. Has time complexity O(d × (n + k)) where d is the number of digits, n is the number of elements, and k is the range of input

### Implementation Differences

- **Python**: Pure Python implementation with Python lists and loops
- **Cython**: C-optimized implementation with:
  - C-level data types (`cdef` declarations)
  - Direct memory management
  - Compiler optimizations (`-O3`, `-ffast-math`)
  - Bounds checking disabled for performance

## Cleaning Up

To remove all generated files:

```bash
make clean
```

This removes:
- Generated random list files
- Compiled Cython extensions (`.so`, `.c` files)
- Build directories
- Virtual environment
- Python cache files

## Troubleshooting

### Common Issues

1. **Python 3.14 not found**:
   - Ensure Python 3.14 is installed: `brew install python@3.14`
   - Check the path in the Makefile matches your installation

2. **Cython compilation errors**:
   - Ensure Cython is installed: `brew install cython`
   - Check that all dependencies are installed: `pip install -r requirements.txt`

3. **Import errors**:
   - Ensure the virtual environment is activated: `source .venv/bin/activate`
   - Rebuild the Cython extension: `python setup.py build_ext --inplace`

4. **Performance not as expected**:
   - Ensure compiler optimizations are enabled
   - Check that the Cython extension is properly compiled
   - Verify the data is large enough to see meaningful differences

### Getting Help

Run `make help` to see all available targets and their descriptions.

## License

This project is for educational purposes and demonstrates the performance differences between Python and Cython implementations of the radix sort algorithm.
