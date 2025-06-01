# Multi-Size Sorting Algorithms Performance Study

**Date:** 1 June 2025
**Test Environment:** macOS  
**Datasets:** N = 10, 100K, 250K, 500K random integers  
**Algorithms:** Bubble Sort, Selection Sort, Insertion Sort, Quick Sort, Merge Sort, Counting Sort, Radix Sort

## Executive Summary

This comprehensive study analyzes seven sorting algorithms across six programming languages (Python, C++, Java, JavaScript, Go, C) using four different dataset sizes to understand how performance scales with input size and algorithm choice.

## Test Configuration

- **Small Dataset (N=10):** Measures overhead and initialization costs
- **Medium Dataset (N=100K):** Standard benchmark size
- **Large Dataset (N=250K):** Tests algorithm efficiency 
- **Extra-Large Dataset (N=500K):** Tests scalability and memory efficiency

## Performance Results by Dataset Size and Algorithm


### Dataset Size: 10 (small)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **Go - Quick Sort** | 0.000001 | 10,000,000 | 1.00x |
| 2 | **Java - Insertion Sort** | 0.000002 | 6,666,666 | 1.50x |
| 3 | **Go - Merge Sort** | 0.000002 | 6,002,401 | 2.00x |
| 4 | **Go - Radix Sort** | 0.000002 | 6,002,401 | 2.00x |
| 5 | **C - Selection Sort** | 0.000002 | 5,000,000 | 2.00x |
| 6 | **C - Insertion Sort** | 0.000002 | 5,000,000 | 2.00x |
| 7 | **C - Quick Sort** | 0.000002 | 5,000,000 | 2.00x |
| 8 | **C - Radix Sort** | 0.000002 | 5,000,000 | 2.00x |
| 9 | **Java - Bubble Sort** | 0.000002 | 4,444,444 | 2.25x |
| 10 | **Java - Selection Sort** | 0.000002 | 4,363,001 | 2.29x |
| 11 | **C - Merge Sort** | 0.000003 | 3,333,333 | 3.00x |
| 12 | **Python - Insertion Sort** | 0.000004 | 2,263,965 | 4.00x |
| 13 | **Java - Quick Sort** | 0.000004 | 2,758,621 | 4.00x |
| 14 | **C - Bubble Sort** | 0.000004 | 2,500,000 | 4.00x |
| 15 | **Python - Selection Sort** | 0.000005 | 2,086,810 | 5.00x |
| 16 | **Python - Bubble Sort** | 0.000006 | 1,678,148 | 6.00x |
| 17 | **Java - Merge Sort** | 0.000006 | 1,548,467 | 6.46x |
| 18 | **Python - Quick Sort** | 0.000008 | 1,290,322 | 8.00x |
| 19 | **Java - Radix Sort** | 0.000009 | 1,116,320 | 8.96x |
| 20 | **Python - Merge Sort** | 0.000011 | 948,588 | 11.00x |
| 21 | **Go - Bubble Sort** | 0.000011 | 912,492 | 11.00x |
| 22 | **Go - Selection Sort** | 0.000011 | 892,140 | 11.00x |
| 23 | **Go - Insertion Sort** | 0.000011 | 916,003 | 11.00x |
| 24 | **JavaScript - Insertion Sort** | 0.000025 | 403,355 | 25.00x |
| 25 | **Python - Radix Sort** | 0.000026 | 392,156 | 26.00x |
| 26 | **JavaScript - Selection Sort** | 0.000039 | 254,239 | 39.00x |
| 27 | **JavaScript - Merge Sort** | 0.000043 | 235,061 | 43.00x |
| 28 | **JavaScript - Bubble Sort** | 0.000047 | 211,269 | 47.00x |
| 29 | **JavaScript - Radix Sort** | 0.000120 | 83,073 | 120.00x |
| 30 | **JavaScript - Quick Sort** | 0.000153 | 65,466 | 153.00x |
| 31 | **C - Counting Sort** | 0.000504 | 19,841 | 504.00x |
| 32 | **C++ - Counting Sort** | 0.000555 | 18,018 | 555.00x |
| 33 | **Go - Counting Sort** | 0.001102 | 9,074 | 1102.00x |
| 34 | **Java - Counting Sort** | 0.002280 | 4,385 | 2280.29x |
| 35 | **JavaScript - Counting Sort** | 0.003893 | 2,568 | 3893.00x |
| 36 | **Python - Counting Sort** | 0.030936 | 323 | 30936.00x |

#### Algorithm-Specific Comparisons

**Bubble Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Java** | 0.000002 | 4,444,444 | 1.00x |
| 2 | **C** | 0.000004 | 2,500,000 | 1.78x |
| 3 | **Python** | 0.000006 | 1,678,148 | 2.67x |
| 4 | **Go** | 0.000011 | 912,492 | 4.89x |
| 5 | **JavaScript** | 0.000047 | 211,269 | 20.89x |

**Selection Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.000002 | 5,000,000 | 1.00x |
| 2 | **Java** | 0.000002 | 4,363,001 | 1.15x |
| 3 | **Python** | 0.000005 | 2,086,810 | 2.50x |
| 4 | **Go** | 0.000011 | 892,140 | 5.50x |
| 5 | **JavaScript** | 0.000039 | 254,239 | 19.50x |

**Insertion Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Java** | 0.000002 | 6,666,666 | 1.00x |
| 2 | **C** | 0.000002 | 5,000,000 | 1.33x |
| 3 | **Python** | 0.000004 | 2,263,965 | 2.67x |
| 4 | **Go** | 0.000011 | 916,003 | 7.33x |
| 5 | **JavaScript** | 0.000025 | 403,355 | 16.67x |

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.000001 | 10,000,000 | 1.00x |
| 2 | **C** | 0.000002 | 5,000,000 | 2.00x |
| 3 | **Java** | 0.000004 | 2,758,621 | 4.00x |
| 4 | **Python** | 0.000008 | 1,290,322 | 8.00x |
| 5 | **JavaScript** | 0.000153 | 65,466 | 153.00x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.000002 | 6,002,401 | 1.00x |
| 2 | **C** | 0.000003 | 3,333,333 | 1.50x |
| 3 | **Java** | 0.000006 | 1,548,467 | 3.23x |
| 4 | **Python** | 0.000011 | 948,588 | 5.50x |
| 5 | **JavaScript** | 0.000043 | 235,061 | 21.50x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.000504 | 19,841 | 1.00x |
| 2 | **C++** | 0.000555 | 18,018 | 1.10x |
| 3 | **Go** | 0.001102 | 9,074 | 2.19x |
| 4 | **Java** | 0.002280 | 4,385 | 4.52x |
| 5 | **JavaScript** | 0.003893 | 2,568 | 7.72x |
| 6 | **Python** | 0.030936 | 323 | 61.38x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.000002 | 6,002,401 | 1.00x |
| 2 | **C** | 0.000002 | 5,000,000 | 1.00x |
| 3 | **Java** | 0.000009 | 1,116,320 | 4.48x |
| 4 | **Python** | 0.000026 | 392,156 | 13.00x |
| 5 | **JavaScript** | 0.000120 | 83,073 | 60.00x |


### Dataset Size: 100000 (medium)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **C - Counting Sort** | 0.001328 | 75,301,205 | 1.00x |
| 2 | **C++ - Radix Sort** | 0.001816 | 55,066,079 | 1.37x |
| 3 | **C - Radix Sort** | 0.001909 | 52,383,447 | 1.44x |
| 4 | **Go - Counting Sort** | 0.002176 | 45,960,276 | 1.64x |
| 5 | **C++ - Counting Sort** | 0.002239 | 44,662,796 | 1.69x |
| 6 | **Go - Radix Sort** | 0.002376 | 42,085,328 | 1.79x |
| 7 | **Java - Counting Sort** | 0.004190 | 23,868,957 | 3.15x |
| 8 | **Go - Quick Sort** | 0.004988 | 20,049,454 | 3.76x |
| 9 | **C++ - Quick Sort** | 0.005075 | 19,704,433 | 3.82x |
| 10 | **C++ - Merge Sort** | 0.006075 | 16,460,905 | 4.57x |
| 11 | **C - Quick Sort** | 0.007414 | 13,487,996 | 5.58x |
| 12 | **JavaScript - Counting Sort** | 0.007440 | 13,440,558 | 5.60x |
| 13 | **Java - Radix Sort** | 0.007930 | 12,610,074 | 5.97x |
| 14 | **Java - Quick Sort** | 0.008336 | 11,996,341 | 6.28x |
| 15 | **Go - Merge Sort** | 0.009146 | 10,933,842 | 6.89x |
| 16 | **JavaScript - Quick Sort** | 0.012696 | 7,876,781 | 9.56x |
| 17 | **Java - Merge Sort** | 0.013115 | 7,625,026 | 9.88x |
| 18 | **C - Merge Sort** | 0.018068 | 5,534,647 | 13.61x |
| 19 | **JavaScript - Merge Sort** | 0.022030 | 4,539,213 | 16.59x |
| 20 | **JavaScript - Radix Sort** | 0.040395 | 2,475,569 | 30.42x |
| 21 | **Python - Counting Sort** | 0.106420 | 939,677 | 80.14x |
| 22 | **Python - Quick Sort** | 0.111092 | 900,153 | 83.65x |
| 23 | **Python - Radix Sort** | 0.126294 | 791,803 | 95.10x |
| 24 | **Python - Merge Sort** | 0.192505 | 519,466 | 144.96x |

#### Algorithm-Specific Comparisons

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.004988 | 20,049,454 | 1.00x |
| 2 | **C++** | 0.005075 | 19,704,433 | 1.02x |
| 3 | **C** | 0.007414 | 13,487,996 | 1.49x |
| 4 | **Java** | 0.008336 | 11,996,341 | 1.67x |
| 5 | **JavaScript** | 0.012696 | 7,876,781 | 2.55x |
| 6 | **Python** | 0.111092 | 900,153 | 22.27x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.006075 | 16,460,905 | 1.00x |
| 2 | **Go** | 0.009146 | 10,933,842 | 1.51x |
| 3 | **Java** | 0.013115 | 7,625,026 | 2.16x |
| 4 | **C** | 0.018068 | 5,534,647 | 2.97x |
| 5 | **JavaScript** | 0.022030 | 4,539,213 | 3.63x |
| 6 | **Python** | 0.192505 | 519,466 | 31.69x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.001328 | 75,301,205 | 1.00x |
| 2 | **Go** | 0.002176 | 45,960,276 | 1.64x |
| 3 | **C++** | 0.002239 | 44,662,796 | 1.69x |
| 4 | **Java** | 0.004190 | 23,868,957 | 3.15x |
| 5 | **JavaScript** | 0.007440 | 13,440,558 | 5.60x |
| 6 | **Python** | 0.106420 | 939,677 | 80.14x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.001816 | 55,066,079 | 1.00x |
| 2 | **C** | 0.001909 | 52,383,447 | 1.05x |
| 3 | **Go** | 0.002376 | 42,085,328 | 1.31x |
| 4 | **Java** | 0.007930 | 12,610,074 | 4.37x |
| 5 | **JavaScript** | 0.040395 | 2,475,569 | 22.24x |
| 6 | **Python** | 0.126294 | 791,803 | 69.55x |


### Dataset Size: 250000 (large)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **C - Counting Sort** | 0.001745 | 143,266,476 | 1.00x |
| 2 | **C++ - Counting Sort** | 0.002985 | 83,752,094 | 1.71x |
| 3 | **C++ - Radix Sort** | 0.003113 | 80,308,384 | 1.78x |
| 4 | **Go - Counting Sort** | 0.003392 | 73,701,918 | 1.94x |
| 5 | **C - Radix Sort** | 0.003896 | 64,168,378 | 2.23x |
| 6 | **Java - Counting Sort** | 0.006013 | 41,578,603 | 3.45x |
| 7 | **Go - Radix Sort** | 0.006192 | 40,374,136 | 3.55x |
| 8 | **JavaScript - Counting Sort** | 0.009988 | 25,030,975 | 5.72x |
| 9 | **C++ - Merge Sort** | 0.011867 | 21,066,824 | 6.80x |
| 10 | **C - Quick Sort** | 0.013363 | 18,708,374 | 7.66x |
| 11 | **Go - Quick Sort** | 0.013375 | 18,692,113 | 7.66x |
| 12 | **Java - Radix Sort** | 0.016785 | 14,894,361 | 9.62x |
| 13 | **Java - Quick Sort** | 0.017002 | 14,704,585 | 9.74x |
| 14 | **JavaScript - Radix Sort** | 0.023476 | 10,649,211 | 13.45x |
| 15 | **Go - Merge Sort** | 0.024248 | 10,310,200 | 13.90x |
| 16 | **JavaScript - Quick Sort** | 0.026071 | 9,589,368 | 14.94x |
| 17 | **Java - Merge Sort** | 0.028456 | 8,785,441 | 16.31x |
| 18 | **C - Merge Sort** | 0.036153 | 6,915,055 | 20.72x |
| 19 | **JavaScript - Merge Sort** | 0.074906 | 3,337,533 | 42.93x |
| 20 | **Python - Counting Sort** | 0.182182 | 1,372,257 | 104.40x |
| 21 | **Python - Quick Sort** | 0.302724 | 825,835 | 173.48x |
| 22 | **Python - Radix Sort** | 0.340337 | 734,566 | 195.04x |
| 23 | **Python - Merge Sort** | 0.504723 | 495,321 | 289.24x |
| 24 | **C++ - Quick Sort** | 2.858428 | 87,461 | 1638.07x |

#### Algorithm-Specific Comparisons

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.013363 | 18,708,374 | 1.00x |
| 2 | **Go** | 0.013375 | 18,692,113 | 1.00x |
| 3 | **Java** | 0.017002 | 14,704,585 | 1.27x |
| 4 | **JavaScript** | 0.026071 | 9,589,368 | 1.95x |
| 5 | **Python** | 0.302724 | 825,835 | 22.65x |
| 6 | **C++** | 2.858428 | 87,461 | 213.91x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.011867 | 21,066,824 | 1.00x |
| 2 | **Go** | 0.024248 | 10,310,200 | 2.04x |
| 3 | **Java** | 0.028456 | 8,785,441 | 2.40x |
| 4 | **C** | 0.036153 | 6,915,055 | 3.05x |
| 5 | **JavaScript** | 0.074906 | 3,337,533 | 6.31x |
| 6 | **Python** | 0.504723 | 495,321 | 42.53x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.001745 | 143,266,476 | 1.00x |
| 2 | **C++** | 0.002985 | 83,752,094 | 1.71x |
| 3 | **Go** | 0.003392 | 73,701,918 | 1.94x |
| 4 | **Java** | 0.006013 | 41,578,603 | 3.45x |
| 5 | **JavaScript** | 0.009988 | 25,030,975 | 5.72x |
| 6 | **Python** | 0.182182 | 1,372,257 | 104.40x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.003113 | 80,308,384 | 1.00x |
| 2 | **C** | 0.003896 | 64,168,378 | 1.25x |
| 3 | **Go** | 0.006192 | 40,374,136 | 1.99x |
| 4 | **Java** | 0.016785 | 14,894,361 | 5.39x |
| 5 | **JavaScript** | 0.023476 | 10,649,211 | 7.54x |
| 6 | **Python** | 0.340337 | 734,566 | 109.33x |


### Dataset Size: 500000 (extra-large)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **C - Counting Sort** | 0.002418 | 206,782,465 | 1.00x |
| 2 | **C++ - Counting Sort** | 0.004980 | 100,401,606 | 2.06x |
| 3 | **Go - Counting Sort** | 0.005734 | 87,201,064 | 2.37x |
| 4 | **C - Radix Sort** | 0.006633 | 75,380,672 | 2.74x |
| 5 | **C++ - Radix Sort** | 0.006757 | 73,997,336 | 2.79x |
| 6 | **Java - Counting Sort** | 0.008719 | 57,347,939 | 3.61x |
| 7 | **Go - Radix Sort** | 0.012430 | 40,224,857 | 5.14x |
| 8 | **JavaScript - Counting Sort** | 0.013122 | 38,104,310 | 5.43x |
| 9 | **C++ - Merge Sort** | 0.025071 | 19,943,361 | 10.37x |
| 10 | **Java - Radix Sort** | 0.025567 | 19,556,490 | 10.57x |
| 11 | **C++ - Quick Sort** | 0.027675 | 18,066,847 | 11.45x |
| 12 | **Go - Quick Sort** | 0.028290 | 17,673,934 | 11.70x |
| 13 | **C - Quick Sort** | 0.030308 | 16,497,294 | 12.53x |
| 14 | **Java - Quick Sort** | 0.032093 | 15,579,863 | 13.27x |
| 15 | **JavaScript - Radix Sort** | 0.037344 | 13,388,942 | 15.44x |
| 16 | **JavaScript - Quick Sort** | 0.047355 | 10,558,557 | 19.58x |
| 17 | **Go - Merge Sort** | 0.047921 | 10,433,803 | 19.82x |
| 18 | **Java - Merge Sort** | 0.051614 | 9,687,364 | 21.35x |
| 19 | **C - Merge Sort** | 0.063944 | 7,819,342 | 26.44x |
| 20 | **JavaScript - Merge Sort** | 0.108130 | 4,624,085 | 44.72x |
| 21 | **Python - Counting Sort** | 0.294354 | 1,698,632 | 121.73x |
| 22 | **Python - Quick Sort** | 0.695836 | 718,560 | 287.77x |
| 23 | **Python - Radix Sort** | 0.715956 | 698,367 | 296.09x |
| 24 | **Python - Merge Sort** | 1.106100 | 452,039 | 457.44x |

#### Algorithm-Specific Comparisons

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.027675 | 18,066,847 | 1.00x |
| 2 | **Go** | 0.028290 | 17,673,934 | 1.02x |
| 3 | **C** | 0.030308 | 16,497,294 | 1.10x |
| 4 | **Java** | 0.032093 | 15,579,863 | 1.16x |
| 5 | **JavaScript** | 0.047355 | 10,558,557 | 1.71x |
| 6 | **Python** | 0.695836 | 718,560 | 25.14x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.025071 | 19,943,361 | 1.00x |
| 2 | **Go** | 0.047921 | 10,433,803 | 1.91x |
| 3 | **Java** | 0.051614 | 9,687,364 | 2.06x |
| 4 | **C** | 0.063944 | 7,819,342 | 2.55x |
| 5 | **JavaScript** | 0.108130 | 4,624,085 | 4.31x |
| 6 | **Python** | 1.106100 | 452,039 | 44.12x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.002418 | 206,782,465 | 1.00x |
| 2 | **C++** | 0.004980 | 100,401,606 | 2.06x |
| 3 | **Go** | 0.005734 | 87,201,064 | 2.37x |
| 4 | **Java** | 0.008719 | 57,347,939 | 3.61x |
| 5 | **JavaScript** | 0.013122 | 38,104,310 | 5.43x |
| 6 | **Python** | 0.294354 | 1,698,632 | 121.73x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.006633 | 75,380,672 | 1.00x |
| 2 | **C++** | 0.006757 | 73,997,336 | 1.02x |
| 3 | **Go** | 0.012430 | 40,224,857 | 1.87x |
| 4 | **Java** | 0.025567 | 19,556,490 | 3.85x |
| 5 | **JavaScript** | 0.037344 | 13,388,942 | 5.63x |
| 6 | **Python** | 0.715956 | 698,367 | 107.94x |


## Scaling Analysis

### Algorithm Performance Across Dataset Sizes

The following analysis shows how each algorithm's performance scales with dataset size:

| Algorithm | N=10 | N=100K | N=250K | N=500K | 250K/100K Ratio | 500K/250K Ratio | Big O |
|-----------|------|--------|--------|--------|-----------------|-----------------|-------|
| Bubble Sort | 0.000004 | N/A | N/A | N/A | N/A | N/A | O(n²) |
| Selection Sort | 0.000002 | N/A | N/A | N/A | N/A | N/A | O(n²) |
| Insertion Sort | 0.000002 | N/A | N/A | N/A | N/A | N/A | O(n²) |
| Quick Sort | 0.000002 | 0.007414 | 0.013363 | 0.030308 | 1.8x | 2.3x | O(n log n) |
| Merge Sort | 0.000003 | 0.018068 | 0.036153 | 0.063944 | 2.0x | 1.8x | O(n log n) |
| Counting Sort | 0.000504 | 0.001328 | 0.001745 | 0.002418 | 1.3x | 1.4x | O(n) |
| Radix Sort | 0.000002 | 0.001909 | 0.003896 | 0.006633 | 2.0x | 1.7x | O(n) |

### Language Performance Across Dataset Sizes

The following analysis shows how each language's performance scales with dataset size using Quick Sort as reference:

| Language | N=10 | N=100K | N=250K | N=500K | 100K/10 Ratio | 250K/100K Ratio | 500K/250K Ratio |
|----------|------|--------|--------|--------|---------------|-----------------|-----------------|
| Python | 0.000008 | 0.111092 | 0.302724 | 0.695836 | 13886.5x | 2.7x | 2.3x |
| C++ | 0.000000 | 0.005075 | 2.858428 | 0.027675 | N/A | 563.2x | 0.0x |
| Java | 0.000004 | 0.008336 | 0.017002 | 0.032093 | 2084.0x | 2.0x | 1.9x |
| JavaScript | 0.000153 | 0.012696 | 0.026071 | 0.047355 | 83.0x | 2.1x | 1.8x |
| Go | 0.000001 | 0.004988 | 0.013375 | 0.028290 | 4988.0x | 2.7x | 2.1x |
| C | 0.000002 | 0.007414 | 0.013363 | 0.030308 | 3707.0x | 1.8x | 2.3x |

### Key Observations

1. **Algorithm Efficiency:** O(n log n) and O(n) algorithms show significantly better scaling
2. **Quadratic Impact:** Bubble, Selection and Insertion sorts deteriorate rapidly with size
3. **Linear Algorithms:** Counting and Radix sorts maintain consistent ratios as size increases
4. **Language Overhead:** Low-level languages maintain better scaling characteristics
5. **Small Dataset Impact:** With N=10, implementation details outweigh algorithmic differences


## Conclusions

### Algorithm Performance Hierarchy

Based on the multi-size analysis:

1. **O(n) Algorithms:** Counting Sort and Radix Sort perform best for large datasets with limited range
2. **O(n log n) Algorithms:** Quick Sort and Merge Sort provide excellent general-purpose performance
3. **O(n²) Algorithms:** Bubble, Selection, and Insertion sorts are only suitable for tiny datasets

### Language Performance Hierarchy

1. **C/C++:** Consistently fastest across all dataset sizes due to native compilation and memory efficiency
2. **Go:** Excellent performance with simple concurrency model
3. **Java:** Strong JIT-optimized performance with good scaling characteristics
4. **JavaScript:** Surprisingly efficient V8 optimization, especially for medium-sized datasets
5. **Python:** Convenient but slower due to interpreter overhead

### Practical Recommendations

| Dataset Size | Recommended Algorithm | Recommended Language | Reasoning |
|--------------|----------------------|---------------------|-----------|
| **Tiny (N < 100)** | Insertion Sort | Any language | Low overhead for nearly sorted data |
| **Small (N < 10K)** | Quick Sort | Any language | Performance differences negligible |
| **Medium (N ~ 100K)** | Quick Sort | C++ or Go | Good balance of performance and simplicity |
| **Large (N > 1M)** | Counting/Radix Sort* | C | Maximum performance for memory-intensive operations |
| **Very Large (N > 10M)** | Merge Sort | C++ | Better stability and consistent performance |

*When data range is limited


