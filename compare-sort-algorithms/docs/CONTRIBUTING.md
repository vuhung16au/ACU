# Contributing to Quick Sort Performance Comparison Project

Thank you for your interest in contributing to this project! We welcome contributions from everyone who is interested in sorting algorithm performance.

## Ways to Contribute

There are several ways to contribute to this project:

1. **Adding new language implementations** - Implement Quick Sort in additional programming languages
2. **Optimizing existing implementations** - Improve the performance of current implementations
3. **Enhancing analysis tools** - Improve the analysis scripts to provide more insights
4. **Improving documentation** - Enhance explanations, add examples, or fix typos
5. **Adding new sorting algorithms** - Implement and compare other sorting algorithms
6. **Bug fixes** - Fix issues in existing code or documentation

## Getting Started

1. Fork the repository
2. Clone your forked repository to your local machine
3. Create a new branch for your feature or fix
4. Make your changes
5. Test your changes thoroughly
6. Push your changes to your fork
7. Submit a pull request

## Guidelines for Quick Sort Implementations

When adding a new implementation or optimizing an existing one, please follow these guidelines:

1. **Standard Implementation** - Use a standard partition scheme (Lomuto or Hoare)
2. **Consistent Interface** - Follow the same input/output format as existing implementations
3. **Benchmarking** - Include timing code to measure execution time
4. **Validation** - Include a check that the array is properly sorted
5. **Documentation** - Add comments explaining your implementation choices

Example structure for a new implementation:

```
1. Parse command-line arguments or use default input file
2. Load data from the specified file
3. Record start time
4. Run the Quick Sort algorithm
5. Record end time
6. Verify the array is sorted correctly
7. Calculate and display execution time and elements per second
8. Save results to a file
```

## Code Style

Please follow the established code style for each language:

- **Python**: PEP 8
- **C/C++**: LLVM style with 4-space indentation
- **Java**: Google Java Style
- **JavaScript**: Standard JS
- **Go**: Go standard formatting (gofmt)

## Commit Messages

Write clear and descriptive commit messages that explain the purpose of your changes.

## Testing

Before submitting a pull request, please test your changes:

1. Generate test datasets using `scripts/generate_data.py`
2. Run your implementation against these datasets
3. Verify that results are correct and performance is as expected
4. Run the comparison script to see how your changes stack up against other implementations

## Questions?

If you have any questions about contributing, feel free to open an issue in the repository.

Thank you for helping improve this project!
