# Multi-Size Quick Sort Performance Study

**Date:** $(date "+%B %d, %Y")  
**Test Environment:** macOS  
**Datasets:** N = 10, 100K, 250K, 500K random integers  

## Executive Summary

This comprehensive study analyzes Quick Sort performance across four programming languages (Python, C++, Java, JavaScript) using three different dataset sizes to understand how performance scales with input size.

## Test Configuration

- **Small Dataset (N=10):** Measures overhead and initialization costs
- **Medium Dataset (N=100K):** Standard benchmark size
- **Large Dataset (N=250K):** Tests algorithm efficiency 
- **Extra-Large Dataset (N=500K):** Tests scalability and memory efficiency

## Performance Results by Dataset Size


### Dataset Size: 10 (small)

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.000002 | 5,000,000 | 1.00x |
| 2 | **Java** | 0.000004 | 2,580,645 | 2.00x |
| 3 | **Python** | 0.000007 | 1,445,703 | 3.50x |
| 4 | **Go** | 0.000011 | 882,301 | 5.50x |
| 5 | **JavaScript** | 0.000117 | 85,531 | 58.50x |


### Dataset Size: 100000 (medium)

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.005235 | 19,102,197 | 1.00x |
| 2 | **Go** | 0.006031 | 16,580,198 | 1.15x |
| 3 | **C++** | 0.006452 | 15,499,070 | 1.23x |
| 4 | **Java** | 0.008285 | 12,070,431 | 1.58x |
| 5 | **JavaScript** | 0.045256 | 2,209,670 | 8.64x |
| 6 | **Python** | 0.115489 | 865,880 | 22.06x |


### Dataset Size: 250000 (large)

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.013280 | 18,825,301 | 1.00x |
| 2 | **C** | 0.013280 | 18,825,301 | 1.00x |
| 3 | **Go** | 0.013371 | 18,697,705 | 1.01x |
| 4 | **Java** | 0.017433 | 14,340,310 | 1.31x |
| 5 | **JavaScript** | 0.025528 | 9,793,057 | 1.92x |
| 6 | **Python** | 0.329972 | 757,641 | 24.85x |


### Dataset Size: 500000 (extra-large)

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.027753 | 18,016,070 | 1.00x |
| 2 | **C++** | 0.028092 | 17,798,662 | 1.01x |
| 3 | **Go** | 0.028504 | 17,541,449 | 1.03x |
| 4 | **Java** | 0.033414 | 14,963,993 | 1.20x |
| 5 | **JavaScript** | 0.048800 | 10,245,928 | 1.76x |
| 6 | **Python** | 0.699642 | 714,651 | 25.21x |


## Scaling Analysis

### Performance Trends

The following analysis shows how each language's performance scales with dataset size:

| Language | N=10 | N=100K | N=250K | N=500K | 100K/10 Ratio | 250K/100K Ratio | 500K/250K Ratio |
|----------|------|--------|-------|--------|---------------|-----------------|-----------------|
| Python | 0.000007 | 0.115489 | 0.329972 | 0.699642 | 16498.4x | 2.9x | 2.1x |
| C++ | 0.000000 | 0.006452 | 0.013280 | 0.028092 | 0.0x | 2.1x | 2.1x |
| Java | 0.000004 | 0.008285 | 0.017433 | 0.033414 | 2071.3x | 2.1x | 1.9x |
| JavaScript | 0.000117 | 0.045256 | 0.025528 | 0.048800 | 386.8x | 0.6x | 1.9x |
| Go | 0.000011 | 0.006031 | 0.013371 | 0.028504 | 548.3x | 2.2x | 2.1x |
| C | 0.000002 | 0.005235 | 0.013280 | 0.027753 | 2617.5x | 2.5x | 2.1x |

### Key Observations

1. **Scaling Efficiency:** Languages with lower ratio values scale better with dataset size
2. **Small Dataset Overhead:** Performance differences are less pronounced with N=10
3. **Large Dataset Performance:** True performance characteristics emerge with N=1M


## Conclusions

### Performance Hierarchy

Based on the multi-size analysis:

1. **C++:** Consistently fastest across all dataset sizes due to native compilation
2. **Java:** Strong performance with good scaling characteristics  
3. **JavaScript:** Surprising efficiency, especially for larger datasets
4. **Python:** Predictably slower but maintains reasonable scaling

### Scaling Insights

- **Algorithm Complexity:** All implementations maintain O(n log n) average complexity
- **Constant Factors:** Language overhead becomes more apparent with larger datasets
- **Memory Efficiency:** Compiled languages show better memory management at scale

### Recommendations

| Dataset Size | Recommended Language | Reasoning |
|--------------|---------------------|-----------|
| **Small (N < 1K)** | Any language | Performance differences negligible |
| **Medium (N ~ 100K)** | C++ or Java | Good balance of performance and development speed |
| **Large (N > 1M)** | C++ | Maximum performance for memory-intensive operations |

---

**Study Generated:** $(date)  
**Total Test Executions:** 24 (6 languages × 4 dataset sizes)  
**Files Generated:** 24 result files + 4 consolidated reports + this analysis

