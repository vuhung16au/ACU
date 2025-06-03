# Chapter 4: Simple Sorting and Searching Algorithms

## Introduction
This chapter introduces basic sorting and searching algorithms. You will learn how to search for values in a list and how to sort lists using different methods. These are foundational skills for working with data in computer science.

## Algorithms Implemented

### 1. Linear Search
Checks each element in the list until it finds the target value.
```python
def linear_search(numbers, target):
    for index, value in enumerate(numbers):
        if value == target:
            return index
    return -1
```

### 2. Binary Search
Works on sorted lists. Repeatedly divides the list in half to find the target.
```python
def binary_search(sorted_numbers, target):
    left = 0
    right = len(sorted_numbers) - 1
    while left <= right:
        mid = (left + right) // 2
        if sorted_numbers[mid] == target:
            return mid
        elif sorted_numbers[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1
```

### 3. Bubble Sort
Repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
```python
def bubble_sort(numbers):
    n = len(numbers)
    for i in range(n):
        for j in range(0, n - i - 1):
            if numbers[j] > numbers[j + 1]:
                numbers[j], numbers[j + 1] = numbers[j + 1], numbers[j]
    return numbers
```

### 4. Selection Sort
Finds the minimum element and moves it to the beginning.
```python
def selection_sort(numbers):
    n = len(numbers)
    for i in range(n):
        min_index = i
        for j in range(i + 1, n):
            if numbers[j] < numbers[min_index]:
                min_index = j
        numbers[i], numbers[min_index] = numbers[min_index], numbers[i]
    return numbers
```

### 5. Insertion Sort
Builds the sorted list one item at a time.
```python
def insertion_sort(numbers):
    for i in range(1, len(numbers)):
        key = numbers[i]
        j = i - 1
        while j >= 0 and numbers[j] > key:
            numbers[j + 1] = numbers[j]
            j -= 1
        numbers[j + 1] = key
    return numbers
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Linear Search:** $O(n)$ time, $O(1)$ space
- **Binary Search:** $O(\log n)$ time, $O(1)$ space (only works on sorted lists)
- **Bubble Sort:** $O(n^2)$ time, $O(1)$ space
- **Selection Sort:** $O(n^2)$ time, $O(1)$ space
- **Insertion Sort:** $O(n^2)$ time, $O(1)$ space

#### Proof & Cases
- **Linear Search:**
  - Best: $O(1)$ (target is first)
  - Worst: $O(n)$ (target is last or not present)
- **Binary Search:**
  - Always $O(\log n)$ (halves the list each time)
- **Bubble/Selection/Insertion Sort:**
  - Best: $O(n)$ (insertion sort, already sorted)
  - Average/Worst: $O(n^2)$

## Important Notes
- Binary search is much faster than linear search, but only works on sorted lists.
- Simple sorts are easy to understand but slow for large lists.
- For large data, more advanced sorts (like quick sort or merge sort) are better (see below).

## Real-World Applications
- **Searching:** Looking up a name in a contact list, finding a word in a document.
- **Sorting:** Organizing grades, arranging files by date, sorting search results.

## Ideas for Self-Practicing
- Modify bubble sort to sort in descending order.
- Write a function to count how many times a value appears in a list.
- Try binary search on a list of words (strings).

## Further Readings & Connections
- [Khan Academy: Searching and Sorting](https://www.khanacademy.org/computing/computer-science/algorithms)
- [GeeksforGeeks: Sorting Algorithms](https://www.geeksforgeeks.org/sorting-algorithms/)
- Learn about more efficient sorts like quick sort and merge sort (see Chapter 6).
- Explore data structures like arrays and linked lists (see Chapter 3 and 10).

---
**Key Terms:**
- **Search:** Finding a value in a list.
- **Sort:** Arranging values in order.
- **Algorithm:** A step-by-step procedure to solve a problem. 