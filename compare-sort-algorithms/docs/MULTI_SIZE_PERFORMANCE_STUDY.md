# Multi-Size Sorting Algorithms Performance Study

**Date:** $(date "+%B %d, %Y")  
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
| 1 | **Go - Quick Sort** | 0.000001 | 10,427,529 | 1.00x |
| 2 | **Go - Merge Sort** | 0.000001 | 8,000,000 | 1.00x |
| 3 | **Java - Insertion Sort** | 0.000002 | 6,666,666 | 1.50x |
| 4 | **C - Bubble Sort** | 0.000002 | 5,000,000 | 2.00x |
| 5 | **C - Insertion Sort** | 0.000002 | 5,000,000 | 2.00x |
| 6 | **C - Quick Sort** | 0.000002 | 5,000,000 | 2.00x |
| 7 | **Java - Selection Sort** | 0.000002 | 4,444,444 | 2.25x |
| 8 | **Java - Bubble Sort** | 0.000002 | 4,286,326 | 2.33x |
| 9 | **C - Selection Sort** | 0.000003 | 3,333,333 | 3.00x |
| 10 | **C - Radix Sort** | 0.000003 | 3,333,333 | 3.00x |
| 11 | **Python - Insertion Sort** | 0.000004 | 2,823,222 | 4.00x |
| 12 | **Java - Quick Sort** | 0.000004 | 2,637,131 | 4.00x |
| 13 | **C - Merge Sort** | 0.000004 | 2,500,000 | 4.00x |
| 14 | **Python - Selection Sort** | 0.000005 | 2,086,810 | 5.00x |
| 15 | **Java - Merge Sort** | 0.000007 | 1,463,486 | 6.83x |
| 16 | **Python - Bubble Sort** | 0.000007 | 1,528,812 | 7.00x |
| 17 | **Python - Quick Sort** | 0.000008 | 1,176,478 | 8.00x |
| 18 | **Java - Radix Sort** | 0.000008 | 1,176,470 | 8.50x |
| 19 | **Python - Merge Sort** | 0.000010 | 1,034,558 | 10.00x |
| 20 | **Go - Bubble Sort** | 0.000011 | 888,889 | 11.00x |
| 21 | **Go - Selection Sort** | 0.000011 | 882,379 | 11.00x |
| 22 | **Go - Insertion Sort** | 0.000011 | 948,587 | 11.00x |
| 23 | **Go - Radix Sort** | 0.000011 | 902,283 | 11.00x |
| 24 | **JavaScript - Insertion Sort** | 0.000023 | 433,219 | 23.00x |
| 25 | **Python - Radix Sort** | 0.000024 | 424,790 | 24.00x |
| 26 | **JavaScript - Selection Sort** | 0.000037 | 267,852 | 37.00x |
| 27 | **JavaScript - Bubble Sort** | 0.000043 | 230,329 | 43.00x |
| 28 | **JavaScript - Merge Sort** | 0.000047 | 214,284 | 47.00x |
| 29 | **JavaScript - Quick Sort** | 0.000101 | 98,684 | 101.00x |
| 30 | **JavaScript - Radix Sort** | 0.000106 | 94,228 | 106.00x |
| 31 | **C++ - Counting Sort** | 0.000433 | 23,095 | 433.00x |
| 32 | **C - Counting Sort** | 0.000566 | 17,668 | 566.00x |
| 33 | **Go - Counting Sort** | 0.001068 | 9,366 | 1068.00x |
| 34 | **Java - Counting Sort** | 0.002184 | 4,577 | 2184.42x |
| 35 | **JavaScript - Counting Sort** | 0.003743 | 2,671 | 3743.00x |
| 36 | **Python - Counting Sort** | 0.031499 | 317 | 31499.00x |

#### Algorithm-Specific Comparisons

**Bubble Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.000002 | 5,000,000 | 1.00x |
| 2 | **Java** | 0.000002 | 4,286,326 | 1.17x |
| 3 | **Python** | 0.000007 | 1,528,812 | 3.50x |
| 4 | **Go** | 0.000011 | 888,889 | 5.50x |
| 5 | **JavaScript** | 0.000043 | 230,329 | 21.50x |

**Selection Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Java** | 0.000002 | 4,444,444 | 1.00x |
| 2 | **C** | 0.000003 | 3,333,333 | 1.33x |
| 3 | **Python** | 0.000005 | 2,086,810 | 2.22x |
| 4 | **Go** | 0.000011 | 882,379 | 4.89x |
| 5 | **JavaScript** | 0.000037 | 267,852 | 16.44x |

**Insertion Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Java** | 0.000002 | 6,666,666 | 1.00x |
| 2 | **C** | 0.000002 | 5,000,000 | 1.33x |
| 3 | **Python** | 0.000004 | 2,823,222 | 2.67x |
| 4 | **Go** | 0.000011 | 948,587 | 7.33x |
| 5 | **JavaScript** | 0.000023 | 433,219 | 15.33x |

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.000001 | 10,427,529 | 1.00x |
| 2 | **C** | 0.000002 | 5,000,000 | 2.00x |
| 3 | **Java** | 0.000004 | 2,637,131 | 4.00x |
| 4 | **Python** | 0.000008 | 1,176,478 | 8.00x |
| 5 | **JavaScript** | 0.000101 | 98,684 | 101.00x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.000001 | 8,000,000 | 1.00x |
| 2 | **C** | 0.000004 | 2,500,000 | 4.00x |
| 3 | **Java** | 0.000007 | 1,463,486 | 6.83x |
| 4 | **Python** | 0.000010 | 1,034,558 | 10.00x |
| 5 | **JavaScript** | 0.000047 | 214,284 | 47.00x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.000433 | 23,095 | 1.00x |
| 2 | **C** | 0.000566 | 17,668 | 1.31x |
| 3 | **Go** | 0.001068 | 9,366 | 2.47x |
| 4 | **Java** | 0.002184 | 4,577 | 5.04x |
| 5 | **JavaScript** | 0.003743 | 2,671 | 8.64x |
| 6 | **Python** | 0.031499 | 317 | 72.75x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.000003 | 3,333,333 | 1.00x |
| 2 | **Java** | 0.000008 | 1,176,470 | 2.83x |
| 3 | **Go** | 0.000011 | 902,283 | 3.67x |
| 4 | **Python** | 0.000024 | 424,790 | 8.00x |
| 5 | **JavaScript** | 0.000106 | 94,228 | 35.33x |


### Dataset Size: 100000 (medium)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **C - Radix Sort** | 0.001185 | 84,388,186 | 1.00x |
| 2 | **C++ - Radix Sort** | 0.001256 | 79,617,834 | 1.06x |
| 3 | **C - Counting Sort** | 0.001415 | 70,671,378 | 1.19x |
| 4 | **C++ - Counting Sort** | 0.002173 | 46,019,328 | 1.83x |
| 5 | **Go - Counting Sort** | 0.002239 | 44,666,945 | 1.89x |
| 6 | **Go - Radix Sort** | 0.002475 | 40,401,314 | 2.09x |
| 7 | **Java - Counting Sort** | 0.004798 | 20,842,195 | 4.05x |
| 8 | **Go - Quick Sort** | 0.005084 | 19,669,227 | 4.29x |
| 9 | **C - Quick Sort** | 0.005399 | 18,521,949 | 4.56x |
| 10 | **C++ - Merge Sort** | 0.005824 | 17,170,330 | 4.91x |
| 11 | **JavaScript - Counting Sort** | 0.007501 | 13,331,406 | 6.33x |
| 12 | **Java - Radix Sort** | 0.007706 | 12,977,111 | 6.50x |
| 13 | **Go - Merge Sort** | 0.009093 | 10,997,319 | 7.67x |
| 14 | **Java - Quick Sort** | 0.009894 | 10,107,349 | 8.35x |
| 15 | **JavaScript - Quick Sort** | 0.012238 | 8,171,270 | 10.33x |
| 16 | **Java - Merge Sort** | 0.012500 | 8,000,266 | 10.55x |
| 17 | **JavaScript - Radix Sort** | 0.014520 | 6,886,933 | 12.25x |
| 18 | **C - Merge Sort** | 0.015096 | 6,624,271 | 12.74x |
| 19 | **JavaScript - Merge Sort** | 0.021900 | 4,566,183 | 18.48x |
| 20 | **Python - Counting Sort** | 0.103518 | 966,020 | 87.36x |
| 21 | **Python - Quick Sort** | 0.110967 | 901,167 | 93.64x |
| 22 | **Python - Radix Sort** | 0.130161 | 768,277 | 109.84x |
| 23 | **Python - Merge Sort** | 0.191603 | 521,912 | 161.69x |
| 24 | **C++ - Quick Sort** | 0.620043 | 161,279 | 523.24x |

#### Algorithm-Specific Comparisons

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **Go** | 0.005084 | 19,669,227 | 1.00x |
| 2 | **C** | 0.005399 | 18,521,949 | 1.06x |
| 3 | **Java** | 0.009894 | 10,107,349 | 1.95x |
| 4 | **JavaScript** | 0.012238 | 8,171,270 | 2.41x |
| 5 | **Python** | 0.110967 | 901,167 | 21.83x |
| 6 | **C++** | 0.620043 | 161,279 | 121.96x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.005824 | 17,170,330 | 1.00x |
| 2 | **Go** | 0.009093 | 10,997,319 | 1.56x |
| 3 | **Java** | 0.012500 | 8,000,266 | 2.15x |
| 4 | **C** | 0.015096 | 6,624,271 | 2.59x |
| 5 | **JavaScript** | 0.021900 | 4,566,183 | 3.76x |
| 6 | **Python** | 0.191603 | 521,912 | 32.90x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.001415 | 70,671,378 | 1.00x |
| 2 | **C++** | 0.002173 | 46,019,328 | 1.54x |
| 3 | **Go** | 0.002239 | 44,666,945 | 1.58x |
| 4 | **Java** | 0.004798 | 20,842,195 | 3.39x |
| 5 | **JavaScript** | 0.007501 | 13,331,406 | 5.30x |
| 6 | **Python** | 0.103518 | 966,020 | 73.16x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.001185 | 84,388,186 | 1.00x |
| 2 | **C++** | 0.001256 | 79,617,834 | 1.06x |
| 3 | **Go** | 0.002475 | 40,401,314 | 2.09x |
| 4 | **Java** | 0.007706 | 12,977,111 | 6.50x |
| 5 | **JavaScript** | 0.014520 | 6,886,933 | 12.25x |
| 6 | **Python** | 0.130161 | 768,277 | 109.84x |


### Dataset Size: 250000 (large)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **C - Counting Sort** | 0.001568 | 159,438,776 | 1.00x |
| 2 | **C++ - Counting Sort** | 0.003371 | 74,161,970 | 2.15x |
| 3 | **Go - Counting Sort** | 0.003380 | 73,972,704 | 2.16x |
| 4 | **C - Radix Sort** | 0.004223 | 59,199,621 | 2.69x |
| 5 | **C++ - Radix Sort** | 0.004567 | 54,740,530 | 2.91x |
| 6 | **Java - Counting Sort** | 0.006017 | 41,548,081 | 3.84x |
| 7 | **Go - Radix Sort** | 0.007706 | 32,442,602 | 4.91x |
| 8 | **JavaScript - Counting Sort** | 0.009834 | 25,423,189 | 6.27x |
| 9 | **C - Quick Sort** | 0.013293 | 18,806,891 | 8.48x |
| 10 | **C++ - Merge Sort** | 0.013347 | 18,730,801 | 8.51x |
| 11 | **Go - Quick Sort** | 0.013725 | 18,214,936 | 8.75x |
| 12 | **C++ - Quick Sort** | 0.013893 | 17,994,674 | 8.86x |
| 13 | **Java - Quick Sort** | 0.016186 | 15,445,447 | 10.32x |
| 14 | **Java - Radix Sort** | 0.017449 | 14,327,810 | 11.13x |
| 15 | **Go - Merge Sort** | 0.023718 | 10,540,462 | 15.13x |
| 16 | **JavaScript - Radix Sort** | 0.026039 | 9,600,906 | 16.61x |
| 17 | **JavaScript - Quick Sort** | 0.028173 | 8,873,613 | 17.97x |
| 18 | **Java - Merge Sort** | 0.028442 | 8,789,804 | 18.14x |
| 19 | **C - Merge Sort** | 0.031699 | 7,886,684 | 20.22x |
| 20 | **JavaScript - Merge Sort** | 0.051393 | 4,864,463 | 32.78x |
| 21 | **Python - Counting Sort** | 0.193661 | 1,290,917 | 123.51x |
| 22 | **Python - Quick Sort** | 0.300964 | 830,663 | 191.94x |
| 23 | **Python - Radix Sort** | 0.384141 | 650,803 | 244.99x |
| 24 | **Python - Merge Sort** | 0.525687 | 475,568 | 335.26x |

#### Algorithm-Specific Comparisons

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.013293 | 18,806,891 | 1.00x |
| 2 | **Go** | 0.013725 | 18,214,936 | 1.03x |
| 3 | **C++** | 0.013893 | 17,994,674 | 1.05x |
| 4 | **Java** | 0.016186 | 15,445,447 | 1.22x |
| 5 | **JavaScript** | 0.028173 | 8,873,613 | 2.12x |
| 6 | **Python** | 0.300964 | 830,663 | 22.64x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.013347 | 18,730,801 | 1.00x |
| 2 | **Go** | 0.023718 | 10,540,462 | 1.78x |
| 3 | **Java** | 0.028442 | 8,789,804 | 2.13x |
| 4 | **C** | 0.031699 | 7,886,684 | 2.37x |
| 5 | **JavaScript** | 0.051393 | 4,864,463 | 3.85x |
| 6 | **Python** | 0.525687 | 475,568 | 39.39x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.001568 | 159,438,776 | 1.00x |
| 2 | **C++** | 0.003371 | 74,161,970 | 2.15x |
| 3 | **Go** | 0.003380 | 73,972,704 | 2.16x |
| 4 | **Java** | 0.006017 | 41,548,081 | 3.84x |
| 5 | **JavaScript** | 0.009834 | 25,423,189 | 6.27x |
| 6 | **Python** | 0.193661 | 1,290,917 | 123.51x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.004223 | 59,199,621 | 1.00x |
| 2 | **C++** | 0.004567 | 54,740,530 | 1.08x |
| 3 | **Go** | 0.007706 | 32,442,602 | 1.82x |
| 4 | **Java** | 0.017449 | 14,327,810 | 4.13x |
| 5 | **JavaScript** | 0.026039 | 9,600,906 | 6.17x |
| 6 | **Python** | 0.384141 | 650,803 | 90.96x |


### Dataset Size: 500000 (extra-large)

| Rank | Implementation | Time (seconds) | Elements/Second | Relative Speed |
|------|---------------|----------------|-----------------|----------------|
| 1 | **C - Counting Sort** | 0.002318 | 215,703,192 | 1.00x |
| 2 | **C++ - Counting Sort** | 0.005094 | 98,154,692 | 2.20x |
| 3 | **Go - Counting Sort** | 0.005403 | 92,536,180 | 2.33x |
| 4 | **C - Radix Sort** | 0.006478 | 77,184,316 | 2.79x |
| 5 | **C++ - Radix Sort** | 0.006685 | 74,794,316 | 2.88x |
| 6 | **Java - Counting Sort** | 0.009812 | 50,958,223 | 4.23x |
| 7 | **Go - Radix Sort** | 0.012170 | 41,086,042 | 5.25x |
| 8 | **JavaScript - Counting Sort** | 0.013427 | 37,239,323 | 5.79x |
| 9 | **C++ - Merge Sort** | 0.024826 | 20,140,176 | 10.71x |
| 10 | **Java - Radix Sort** | 0.025703 | 19,452,634 | 11.09x |
| 11 | **C++ - Quick Sort** | 0.027703 | 18,048,587 | 11.95x |
| 12 | **C - Quick Sort** | 0.027815 | 17,975,912 | 12.00x |
| 13 | **Go - Quick Sort** | 0.029100 | 17,181,909 | 12.55x |
| 14 | **Java - Quick Sort** | 0.033437 | 14,953,513 | 14.42x |
| 15 | **JavaScript - Radix Sort** | 0.036817 | 13,580,590 | 15.88x |
| 16 | **Go - Merge Sort** | 0.047679 | 10,486,870 | 20.57x |
| 17 | **JavaScript - Quick Sort** | 0.049987 | 10,002,634 | 21.56x |
| 18 | **Java - Merge Sort** | 0.051238 | 9,758,414 | 22.10x |
| 19 | **C - Merge Sort** | 0.063601 | 7,861,512 | 27.44x |
| 20 | **JavaScript - Merge Sort** | 0.110749 | 4,514,694 | 47.78x |
| 21 | **Python - Counting Sort** | 0.298062 | 1,677,506 | 128.59x |
| 22 | **Python - Quick Sort** | 0.696510 | 717,864 | 300.48x |
| 23 | **Python - Radix Sort** | 0.743186 | 672,779 | 320.62x |
| 24 | **Python - Merge Sort** | 1.122972 | 445,247 | 484.46x |

#### Algorithm-Specific Comparisons

**Quick Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.027703 | 18,048,587 | 1.00x |
| 2 | **C** | 0.027815 | 17,975,912 | 1.00x |
| 3 | **Go** | 0.029100 | 17,181,909 | 1.05x |
| 4 | **Java** | 0.033437 | 14,953,513 | 1.21x |
| 5 | **JavaScript** | 0.049987 | 10,002,634 | 1.80x |
| 6 | **Python** | 0.696510 | 717,864 | 25.14x |

**Merge Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C++** | 0.024826 | 20,140,176 | 1.00x |
| 2 | **Go** | 0.047679 | 10,486,870 | 1.92x |
| 3 | **Java** | 0.051238 | 9,758,414 | 2.06x |
| 4 | **C** | 0.063601 | 7,861,512 | 2.56x |
| 5 | **JavaScript** | 0.110749 | 4,514,694 | 4.46x |
| 6 | **Python** | 1.122972 | 445,247 | 45.23x |

**Counting Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.002318 | 215,703,192 | 1.00x |
| 2 | **C++** | 0.005094 | 98,154,692 | 2.20x |
| 3 | **Go** | 0.005403 | 92,536,180 | 2.33x |
| 4 | **Java** | 0.009812 | 50,958,223 | 4.23x |
| 5 | **JavaScript** | 0.013427 | 37,239,323 | 5.79x |
| 6 | **Python** | 0.298062 | 1,677,506 | 128.59x |

**Radix Sort:**

| Rank | Language | Time (seconds) | Elements/Second | Relative Speed |
|------|----------|----------------|-----------------|----------------|
| 1 | **C** | 0.006478 | 77,184,316 | 1.00x |
| 2 | **C++** | 0.006685 | 74,794,316 | 1.03x |
| 3 | **Go** | 0.012170 | 41,086,042 | 1.88x |
| 4 | **Java** | 0.025703 | 19,452,634 | 3.97x |
| 5 | **JavaScript** | 0.036817 | 13,580,590 | 5.68x |
| 6 | **Python** | 0.743186 | 672,779 | 114.72x |


## Scaling Analysis

### Algorithm Performance Across Dataset Sizes

The following analysis shows how each algorithm's performance scales with dataset size:

| Algorithm | N=10 | N=100K | N=250K | N=500K | 250K/100K Ratio | 500K/250K Ratio | Big O |
|-----------|------|--------|--------|--------|-----------------|-----------------|-------|
| Bubble Sort | 0.000002 | N/A | N/A | N/A | N/A | N/A | O(n²) |
| Selection Sort | 0.000003 | N/A | N/A | N/A | N/A | N/A | O(n²) |
| Insertion Sort | 0.000002 | N/A | N/A | N/A | N/A | N/A | O(n²) |
| Quick Sort | 0.000002 | 0.005399 | 0.013293 | 0.027815 | 2.5x | 2.1x | O(n log n) |
| Merge Sort | 0.000004 | 0.015096 | 0.031699 | 0.063601 | 2.1x | 2.0x | O(n log n) |
| Counting Sort | 0.000566 | 0.001415 | 0.001568 | 0.002318 | 1.1x | 1.5x | O(n) |
| Radix Sort | 0.000003 | 0.001185 | 0.004223 | 0.006478 | 3.6x | 1.5x | O(n) |

### Language Performance Across Dataset Sizes

The following analysis shows how each language's performance scales with dataset size using Quick Sort as reference:

| Language | N=10 | N=100K | N=250K | N=500K | 100K/10 Ratio | 250K/100K Ratio | 500K/250K Ratio |
|----------|------|--------|--------|--------|---------------|-----------------|-----------------|
| Python | 0.000008 | 0.110967 | 0.300964 | 0.696510 | 13870.9x | 2.7x | 2.3x |
| C++ | 0.000000 | 0.620043 | 0.013893 | 0.027703 | N/A | 0.0x | 2.0x |
| Java | 0.000004 | 0.009894 | 0.016186 | 0.033437 | 2473.5x | 1.6x | 2.1x |
| JavaScript | 0.000101 | 0.012238 | 0.028173 | 0.049987 | 121.2x | 2.3x | 1.8x |
| Go | 0.000001 | 0.005084 | 0.013725 | 0.029100 | 5084.0x | 2.7x | 2.1x |
| C | 0.000002 | 0.005399 | 0.013293 | 0.027815 | 2699.5x | 2.5x | 2.1x |

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

---

**Study Generated:** $(date)  
**Total Test Executions:** $(( ${#ALGORITHMS[@]} * ${#SIZES[@]} * 6 )) (7 algorithms × 4 dataset sizes × 6 languages)  
**Files Generated:** $(( ${#ALGORITHMS[@]} * ${#SIZES[@]} * 6 )) result files + ${#SIZES[@]} consolidated reports + this analysis

