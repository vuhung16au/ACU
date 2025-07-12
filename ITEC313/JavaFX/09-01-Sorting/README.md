Lecture 9, titled "**Sorting**," is dedicated to the study and analysis of various sorting algorithms. The lecture aims to explain the principles behind these algorithms, how to implement them, and critically, their time complexity.

The **objectives** for this lecture include:
*   Studying and analyzing the time complexity of various sorting algorithms.
*   Designing, implementing, and analyzing **insertion sort**.
*   Designing, implementing, and analyzing **bubble sort**.
*   Designing, implementing, and analyzing **merge sort**.
*   Designing, implementing, and analyzing **quick sort**.
*   Designing and implementing a **binary heap**.
*   Designing, implementing, and analyzing **heap sort**.
*   Designing, implementing, and analyzing **bucket sort** and **radix sort**.

**Why Study Sorting?**
Sorting is a classic computer science subject studied for three main reasons:
1.  Sorting algorithms illustrate **many creative approaches to problem-solving** that can be applied to other problems.
2.  They are excellent for **practicing fundamental programming techniques** using selection statements, loops, methods, and arrays.
3.  They serve as **excellent examples to demonstrate algorithm performance**.

For simplicity, the lecture assumes the data to be sorted are integers, sorted in ascending order, and stored in an array, although the programs can be modified for other data types or sorting orders.

**Sorting Algorithms Discussed:**

*   **Insertion Sort**:
    *   This algorithm sorts a list by **repeatedly inserting an unsorted element into an already sorted sublist** until the entire list is sorted. It maintains a sorted sublist at the beginning of the array and iteratively takes the next unsorted element and inserts it into its correct position within the sorted sublist by shifting elements.
    *   Its time complexity is **O(n^2)**.

*   **Bubble Sort**:
    *   Bubble sort repeatedly **compares adjacent elements and swaps them if they are in the wrong order**, effectively "bubbling" the largest elements to the end of the list.
    *   Its time complexity is **O(n^2)**.

*   **Merge Sort**:
    *   This algorithm employs a **divide-and-conquer** approach. It works by recursively splitting an array into two halves, sorting each half independently, and then merging the sorted halves back together.
    *   The time complexity for merge sort is **O(nlogn)**.

*   **Quick Sort**:
    *   Developed by C. A. R. Hoare, quick sort also uses a divide-and-conquer strategy. It selects an element called a **pivot**, then partitions the array such that all elements less than or equal to the pivot are on one side, and all greater elements are on the other. It then recursively applies the sort to the two partitioned parts.
    *   **Worst-case time**: **O(n^2)**, occurring when the pivot consistently divides the array into one large subarray and one empty one.
    *   **Best-case and Average-case time**: **O(nlogn)**.

*   **Heap Sort**:
    *   Heap sort utilizes a **heap**, which is a **complete binary tree** where each node is greater than or equal to any of its children.
    *   In a heap, for a node at position `i`, its left child is at `2i+1` and its right child is at `2i+2`, and its parent is at `(i-1)/2`.
    *   Elements are added to the heap and then the largest element (the root) is repeatedly removed and the heap is rebuilt.
    *   The height of a heap of `n` elements is **O(logn)**. (While not explicitly stated as O(nlogn) in this lecture, it is a well-known complexity for heap sort, as seen in the previous lecture's comparison of common growth functions, which places it in the `O(nlogn)` category).

*   **Bucket Sort and Radix Sort**:
    *   These are specialized sorting algorithms that can perform better than comparison-based sorts (which have a **lower bound of O(nlogn)**) when keys are small integers.
    *   **Bucket Sort**: Assumes keys are in a specific range (e.g., 0 to N-1) and places elements directly into corresponding "buckets" based on their key value.
    *   **Radix Sort**: Sorts numbers by processing individual digits (or "radixes"). These can achieve **O(n)** time complexity under ideal conditions, making them very efficient.

Objectives
- • To study and analyze time complexity of various sorting algorithms (§§23.2–23.7).
- • To design, implement, and analyze insertion sort (§23.2).
- • To design, implement, and analyze bubble sort (§23.3).
- • To design, implement, and analyze merge sort (§23.4).
- • To design, implement, and analyze quick sort (§23.5).
- • To design and implement a binary heap (§23.6).
- • To design, implement, and analyze heap sort (§23.7).
- • To design, implement, and analyze bucket sort and radix sort (§23.8). (§23.8).

Sample code 

- https://liveexample.pearsoncmg.com/dsanimation/InsertionSortNeweBook.html
- https://liveexample.pearsoncmg.com/html/InsertionSort.html
- https://liveexample.pearsoncmg.com/html/BubbleSort.html
- https://liveexample.pearsoncmg.com/dsanimation/BubbleSortNeweBook.html
- https://liveexample.pearsoncmg.com/html/MergeSort.html
- https://liveexample.pearsoncmg.com/dsanimation/MergeSortNew.html
- https://liveexample.pearsoncmg.com/dsanimation/QuickSortNeweBook.html
- https://liveexample.pearsoncmg.com/html/QuickSort.html
- https://liveexample.pearsoncmg.com/dsanimation/HeapeBook.html
- https://liveexample.pearsoncmg.com/html/Heap.html
- https://liveexample.pearsoncmg.com/html/HeapSort.html
- https://liveexample.pearsoncmg.com/dsanimation/RadixSorteBook.html