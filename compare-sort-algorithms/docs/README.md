# Sorting Algorithms Documentation

This directory contains comprehensive documentation for the sorting algorithms performance comparison project.

## Quick Start Documents

- [**PERFORMANCE-SUMMARY-2025.md**](PERFORMANCE-SUMMARY-2025.md) - Latest performance testing results (June 2025)
- [**ALGORITHM-PERFORMANCE-GUIDE.md**](ALGORITHM-PERFORMANCE-GUIDE.md) - Practical guide on which algorithm to use when

## In-Depth Studies

- [**MULTI_SIZE_PERFORMANCE_STUDY.md**](MULTI_SIZE_PERFORMANCE_STUDY.md) - Analysis of algorithm performance across multiple dataset sizes
- [**SORTING-ALGORITHMS-COMPARISON.md**](SORTING-ALGORITHMS-COMPARISON.md) - Comprehensive comparison of all sorting algorithms
- [**QUICK-SORTS-COMPLETE-STUDY.md**](QUICK-SORTS-COMPLETE-STUDY.md) - Specialized analysis of Quick Sort implementations

## Contributing

- [**CONTRIBUTING.md**](CONTRIBUTING.md) - Guidelines for contributors
- [**HOWTO.md**](HOWTO.md) - Instructions for running tests and adding new algorithms
- [**PROMPTS.md**](PROMPTS.md) - Templates for documentation

## Latest Performance Highlights (June 2025)

- **Fastest Overall Algorithm:** Counting Sort (C implementation) - 206M elements/second at N=500K
- **Best General-Purpose Algorithm:** Quick Sort (Go implementation) - 19.7M elements/second at N=100K
- **Best Scaling:** Radix Sort maintained consistent performance from N=100K to N=500K
- **Language Efficiency Ranking:** C > C++ > Go > Java > JavaScript > Python

## Key Performance Metrics

For datasets of N = 500,000 integers:

| Algorithm | C | C++ | Go | Java | JavaScript | Python |
|-----------|---|-----|----|----- |-----------|--------|
| Quick Sort | 16.5M/s | 18.1M/s | 17.7M/s | 15.6M/s | 10.6M/s | 0.7M/s |
| Merge Sort | 7.8M/s | 19.9M/s | 10.4M/s | 9.7M/s | 4.6M/s | 0.5M/s |
| Counting Sort | 206.8M/s | 100.4M/s | 87.2M/s | 57.3M/s | 38.1M/s | 1.7M/s |
| Radix Sort | 75.4M/s | 74.0M/s | 40.2M/s | 19.6M/s | 13.4M/s | 0.7M/s |

*Elements per second (higher is better)*

---

*Documentation last updated: June 1, 2025*
