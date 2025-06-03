# Chapter 3: One-Dimensional and Two-Dimensional Arrays

## Introduction
This chapter introduces arrays (called lists in Python) and their basic operations. You will learn how to create, modify, and use one-dimensional and two-dimensional arrays, and see simple algorithms that work with them.

## Algorithms Implemented

### 1. One-Dimensional Arrays (Lists)
- **Creating a list:**
  ```python
  numbers = [10, 20, 30, 40, 50]
  ```
- **Accessing elements:**
  ```python
  print(numbers[0])  # First element
  print(numbers[-1]) # Last element
  ```
- **Modifying elements:**
  ```python
  numbers[2] = 35
  ```
- **Adding elements:**
  ```python
  numbers.append(60)
  ```
- **Removing elements:**
  ```python
  numbers.remove(20)
  ```
- **Iterating through a list:**
  ```python
  for num in numbers:
      print(num)
  ```
- **Finding sum and average:**
  ```python
  total = sum(numbers)
  average = total / len(numbers)
  ```

### 2. Two-Dimensional Arrays (Lists of Lists)
- **Creating a 2D array (matrix):**
  ```python
  matrix = [
      [1, 2, 3],
      [4, 5, 6],
      [7, 8, 9]
  ]
  ```
- **Accessing and modifying elements:**
  ```python
  print(matrix[0][1])  # Element at row 1, column 2
  matrix[1][1] = 0     # Set center element to 0
  ```
- **Iterating through all elements:**
  ```python
  for row in matrix:
      for value in row:
          print(value, end=" ")
      print()
  ```

### 3. Find Maximum in a List
```python
def find_max_in_list(numbers_list):
    """Return the maximum value in a list."""
    max_value = numbers_list[0]
    for number in numbers_list:
        if number > max_value:
            max_value = number
    return max_value
```

### 4. Sum of Each Row in a 2D Array
```python
for row_index, row in enumerate(matrix):
    row_sum = sum(row)
    print(f"Row {row_index + 1} sum:", row_sum)
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Accessing, modifying, or adding/removing an element:** $O(1)$ (constant time)
- **Iterating through a list of $n$ elements:** $O(n)$
- **Finding the maximum:** $O(n)$
- **Sum of each row in a matrix:** $O(n \times m)$ for an $n \times m$ matrix

#### Proof & Cases
- **Best, average, and worst case** for iterating or finding max: All are $O(n)$ because every element must be checked.

## Important Notes
- Lists are flexible and easy to use, but not always the most efficient for very large data.
- 2D arrays (lists of lists) are useful for representing tables, grids, or matrices.
- Python lists can store any type of data, but mixing types can make code harder to understand.

## Real-World Applications
- **One-dimensional arrays:** Storing grades, temperatures, or any sequence of values.
- **Two-dimensional arrays:** Representing game boards, spreadsheets, or images.

## Ideas for Self-Practicing
- Write a function to find the minimum value in a list.
- Modify the code to find the sum of each column in a matrix.
- Try creating a 3D array (list of lists of lists) and accessing its elements.

## Further Readings & Connections
- [Python Lists (W3Schools)](https://www.w3schools.com/python/python_lists.asp)
- [Arrays vs. Lists (GeeksforGeeks)](https://www.geeksforgeeks.org/difference-between-list-and-array-in-python/)
- Learn about more advanced data structures like stacks and queues (see Chapter 10).
- Explore algorithms for searching and sorting arrays (see Chapter 4).

---
**Key Terms:**
- **Array (List):** An ordered collection of items.
- **Matrix:** A 2D array (list of lists).
- **Index:** The position of an item in a list (starts at 0 in Python). 