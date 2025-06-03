# Python Sorting Algorithms

## Introduction
This file demonstrates several classic sorting algorithms. Sorting is the process of arranging data in a specific order, such as ascending or descending. Understanding sorting algorithms is essential for efficient data processing and searching.

## Algorithms Implemented

### 1. Bubble Sort
Repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
```python
def bubble_sort(arr):
    n = len(arr)
    for i in range(n):
        for j in range(0, n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
    return arr
```

### 2. Selection Sort
Finds the minimum element and moves it to the sorted part of the list.
```python
def selection_sort(arr):
    n = len(arr)
    for i in range(n):
        min_idx = i
        for j in range(i + 1, n):
            if arr[j] < arr[min_idx]:
                min_idx = j
        arr[i], arr[min_idx] = arr[min_idx], arr[i]
    return arr
```

### 3. Insertion Sort
Builds the sorted list one item at a time.
```python
def insertion_sort(arr):
    for i in range(1, len(arr)):
        key = arr[i]
        j = i - 1
        while j >= 0 and arr[j] > key:
            arr[j + 1] = arr[j]
            j -= 1
        arr[j + 1] = key
    return arr
```

### 4. Quick Sort
A divide-and-conquer algorithm that partitions the list and sorts the partitions.
```python
def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    pivot = arr[len(arr) // 2]
    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]
    return quick_sort(left) + middle + quick_sort(right)
```

### 5. Counting Sort
Works for non-negative integers and counts the occurrences of each value.
```python
def counting_sort(arr):
    if not arr:
        return arr
    max_val = max(arr)
    count = [0] * (max_val + 1)
    for num in arr:
        count[num] += 1
    sorted_arr = []
    for num, freq in enumerate(count):
        sorted_arr.extend([num] * freq)
    return sorted_arr
```

### 6. Radix Sort
Sorts numbers digit by digit, starting from the least significant digit.
```python
def radix_sort(arr):
    if not arr:
        return arr
    max_num = max(arr)
    exp = 1
    while max_num // exp > 0:
        arr = counting_sort_by_digit(arr, exp)
        exp *= 10
    return arr

def counting_sort_by_digit(arr, exp):
    n = len(arr)
    output = [0] * n
    count = [0] * 10
    for num in arr:
        index = (num // exp) % 10
        count[index] += 1
    for i in range(1, 10):
        count[i] += count[i - 1]
    for i in range(n - 1, -1, -1):
        index = (arr[i] // exp) % 10
        output[count[index] - 1] = arr[i]
        count[index] -= 1
    return output
```

### 7. Merge Sort
A divide-and-conquer algorithm that splits the list and merges sorted halves.
```python
def merge_sort(arr):
    if len(arr) <= 1:
        return arr
    mid = len(arr) // 2
    left = merge_sort(arr[:mid])
    right = merge_sort(arr[mid:])
    return merge(left, right)

def merge(left, right):
    result = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Table
| Algorithm       | Best Time | Average Time | Worst Time | Space    |
|----------------|-----------|--------------|------------|----------|
| Bubble Sort    | $O(n^2)$  | $O(n^2)$     | $O(n^2)$   | $O(1)$   |
| Selection Sort | $O(n^2)$  | $O(n^2)$     | $O(n^2)$   | $O(1)$   |
| Insertion Sort | $O(n)$    | $O(n^2)$     | $O(n^2)$   | $O(1)$   |
| Quick Sort     | $O(n \log n)$ | $O(n \log n)$ | $O(n^2)$ | $O(\log n)$ |
| Counting Sort  | $O(n + k)$| $O(n + k)$   | $O(n + k)$ | $O(n + k)$ |
| Radix Sort     | $O(nk)$   | $O(nk)$      | $O(nk)$    | $O(n + k)$ |
| Merge Sort     | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | $O(n)$ |

- $n$ = number of items to sort
- $k$ = range of input (for counting/radix sort)

#### Proof & Cases
- **Bubble/Selection/Insertion:**
  - Best: $O(n)$ (insertion sort, already sorted)
  - Average/Worst: $O(n^2)$
- **Quick/Merge Sort:**
  - Best/Average: $O(n \log n)$
  - Quick Sort Worst: $O(n^2)$ (rare, bad pivots)
- **Counting/Radix:**
  - Best/Average/Worst: $O(n + k)$ or $O(nk)$

## Important Notes
- Simple sorts are easy to understand but slow for large lists.
- Quick sort and merge sort are much faster for big lists.
- Counting and radix sort are very fast for numbers with a small range or fixed number of digits.
- Always consider the size and type of your data when choosing a sorting algorithm.

## Real-World Applications
- Sorting grades, names, or files.
- Organizing search results.
- Used in databases, spreadsheets, and many software systems.

## Ideas for Self-Practicing
- Implement a sort that orders numbers in descending order.
- Try sorting a list of strings alphabetically.
- Compare the speed of different sorts on large lists.

## Further Readings & Connections
- [Sorting Algorithms (Khan Academy)](https://www.khanacademy.org/computing/computer-science/algorithms)
- [GeeksforGeeks: Sorting Algorithms](https://www.geeksforgeeks.org/sorting-algorithms/)
- Learn about searching algorithms (see Chapter 4).
- Explore data structures like arrays and linked lists (see Chapter 3 and 10).

---
**Key Terms:**
- **Sort:** Arranging data in order.
- **Stable Sort:** Maintains the order of equal elements.
- **Divide and Conquer:** Breaking a problem into smaller parts, solving, and combining results. 