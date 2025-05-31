# Introduction to Sorting Algorithms

Sorting algorithms are methods used to arrange items (like numbers or words) in a certain order, usually from smallest to largest or alphabetically. Sorting is important because it makes data easier to search, analyze, and visualize.

Below are some common sorting algorithms, explained simply for beginners:

## Bubble Sort
- Compares each pair of adjacent items and swaps them if they are in the wrong order.
- Repeats this process until the list is sorted.
- Simple but slow for large lists.

## Selection Sort
- Finds the smallest item in the list and moves it to the front.
- Repeats for the next smallest, and so on.
- Easy to understand, but not very fast for big lists.

## Insertion Sort
- Builds the sorted list one item at a time.
- Takes each item and inserts it into its correct position among the already-sorted items.
- Good for small or nearly-sorted lists.

## Quick Sort
- Picks a "pivot" item and splits the list into two groups: less than and greater than the pivot.
- Recursively sorts the groups and combines them.
- Very fast for large lists in practice.

## Counting Sort
- Works only for non-negative integers.
- Counts how many times each value appears, then uses this information to place items in order.
- Very fast for small ranges of numbers.

## Radix Sort
- Sorts numbers digit by digit, starting from the least significant digit.
- Uses a stable sort (like counting sort) for each digit.
- Efficient for sorting large numbers with a fixed number of digits.

## Merge Sort
- Splits the list into halves, sorts each half, and then merges them back together.
- Uses a divide-and-conquer approach.
- Very efficient and always takes the same amount of time for a given list size.

---

# Comparing Time and Space Complexity

## What is Big O Notation?
Big O notation is a way to describe how the running time or memory usage of an algorithm grows as the size of the input (number of items to sort) increases. It helps us compare algorithms regardless of the computer or programming language used.

- **O(1)**: Constant time – does not depend on input size.
- **O(n)**: Linear time – grows directly with the number of items.
- **O(n^2)**: Quadratic time – grows with the square of the number of items.
- **O(n log n)**: Grows faster than linear but much slower than quadratic.

## Time and Space Complexity Table

| Algorithm       | Best Time | Average Time | Worst Time | Space    |
|----------------|-----------|--------------|------------|----------|
| Bubble Sort    | O(n^2)    | O(n^2)       | O(n^2)     | O(1)     |
| Selection Sort | O(n^2)    | O(n^2)       | O(n^2)     | O(1)     |
| Insertion Sort | O(n)      | O(n^2)       | O(n^2)     | O(1)     |
| Quick Sort     | O(n log n)| O(n log n)   | O(n^2)     | O(log n) |
| Counting Sort  | O(n + k)  | O(n + k)     | O(n + k)   | O(n + k) |
| Radix Sort     | O(nk)     | O(nk)        | O(nk)      | O(n + k) |
| Merge Sort     | O(n log n)| O(n log n)   | O(n log n) | O(n)     |

- **n** = number of items to sort
- **k** = range of input (for counting/radix sort)

## Summary
- Simple sorts (bubble, selection, insertion) are easy to understand but slow for large lists.
- Quick sort and merge sort are much faster for big lists.
- Counting and radix sort are very fast for numbers with a small range or fixed number of digits.
- Always consider the size and type of your data when choosing a sorting algorithm!
