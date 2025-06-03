# Chapter 6: Divide and Conquer

## Introduction
This chapter introduces the divide and conquer strategy, a method of solving problems by breaking them into smaller subproblems, solving each one, and combining the results. You will learn how this approach works through classic sorting examples.

## Algorithms Implemented

### 1. Merge Sort
A sorting algorithm that splits the list into halves, sorts each half, and merges them.
```python
def merge_sort(numbers):
    if len(numbers) <= 1:
        return numbers
    mid = len(numbers) // 2
    left_half = merge_sort(numbers[:mid])
    right_half = merge_sort(numbers[mid:])
    return merge(left_half, right_half)

def merge(left, right):
    merged = []
    left_index = 0
    right_index = 0
    while left_index < len(left) and right_index < len(right):
        if left[left_index] < right[right_index]:
            merged.append(left[left_index])
            left_index += 1
        else:
            merged.append(right[right_index])
            right_index += 1
    merged.extend(left[left_index:])
    merged.extend(right[right_index:])
    return merged
```
**Step-by-step:**
1. If the list has 0 or 1 element, it is already sorted.
2. Divide the list into two halves.
3. Recursively sort each half.
4. Merge the sorted halves.

### 2. Quick Sort
A sorting algorithm that picks a pivot, splits the list, and sorts the parts.
```python
def quick_sort(numbers):
    if len(numbers) <= 1:
        return numbers
    pivot = numbers[0]
    less_than_pivot = [x for x in numbers[1:] if x <= pivot]
    greater_than_pivot = [x for x in numbers[1:] if x > pivot]
    return quick_sort(less_than_pivot) + [pivot] + quick_sort(greater_than_pivot)
```
**Step-by-step:**
1. If the list has 0 or 1 element, it is already sorted.
2. Choose a pivot (first element).
3. Split the rest into less than or greater than the pivot.
4. Recursively sort the sublists.
5. Combine the results.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Merge Sort:** $O(n \log n)$ time, $O(n)$ space
- **Quick Sort:**
  - Best/Average: $O(n \log n)$ time, $O(\log n)$ space
  - Worst: $O(n^2)$ time (rare, bad pivots)

#### Proof & Cases
- **Merge Sort:** Always splits in half, always $O(n \log n)$
- **Quick Sort:** Usually splits well, but can be slow if splits are uneven

## Important Notes
- Divide and conquer is powerful for breaking big problems into smaller ones.
- Merge sort is always fast, but uses extra memory.
- Quick sort is usually fast and uses less memory, but can be slow in rare cases.

## Real-World Applications
- Sorting large datasets (databases, spreadsheets)
- Algorithms for searching, multiplying large numbers, and more

## Ideas for Self-Practicing
- Write a divide and conquer algorithm to find the maximum in a list.
- Try implementing quick sort with a random pivot.
- Draw a flowchart of merge sort using Mermaid.

## Further Readings & Connections
- [Khan Academy: Divide and Conquer](https://www.khanacademy.org/computing/computer-science/algorithms)
- [GeeksforGeeks: Merge Sort](https://www.geeksforgeeks.org/merge-sort/)
- Learn about recursion (see Chapter 5) and dynamic programming (see Chapter 9).

---
**Key Terms:**
- **Divide and Conquer:** Breaking a problem into smaller parts, solving, and combining results.
- **Pivot:** The element used to split the list in quick sort.
- **Merge:** Combining two sorted lists into one. 