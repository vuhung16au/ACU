# Chapter 2: Concepts of Algorithms

## Introduction
This chapter introduces the fundamental concepts of algorithms. You will learn what an algorithm is, the properties of a good algorithm, and how to analyze algorithms using time and space complexity. The chapter also provides simple examples to help you understand how algorithms work in practice.

## Algorithms Implemented

### 1. Adding Two Numbers
This is a basic algorithm that takes two numbers as input and returns their sum.

```python
def add_two_numbers(number1, number2):
    """Return the sum of two numbers."""
    return number1 + number2
```

**Step-by-step:**
1. Take two numbers as input.
2. Add them together.
3. Return the result.

### 2. Finding the Maximum of Three Numbers
This algorithm finds the largest of three given numbers.

```python
def find_maximum(a, b, c):
    """Return the largest of three numbers."""
    if a >= b and a >= c:
        return a
    elif b >= a and b >= c:
        return b
    else:
        return c
```

**Step-by-step:**
1. Compare the first number to the other two.
2. If it is greater than or equal to both, return it.
3. Otherwise, compare the second number to the others.
4. If it is greater than or equal to both, return it.
5. Otherwise, return the third number.

### 3. Linear Search
Linear search is a simple algorithm to find a target value in a list by checking each element one by one.

```python
def linear_search(numbers, target):
    """Return the index of target in numbers, or -1 if not found."""
    for index, value in enumerate(numbers):
        if value == target:
            return index  # Found target
    return -1  # Not found
```

**Step-by-step:**
1. Go through each element in the list.
2. If the current element matches the target, return its index.
3. If the end of the list is reached without finding the target, return -1.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity** measures how the number of steps in an algorithm increases as the input size grows. It helps us predict how fast an algorithm will run.
- **Space complexity** measures how much extra memory (RAM) an algorithm needs as the input size grows.

### Complexity Analysis
- **Adding Two Numbers:**
  - Time: $O(1)$ (constant time, does not depend on input size)
  - Space: $O(1)$
- **Finding Maximum of Three Numbers:**
  - Time: $O(1)$
  - Space: $O(1)$
- **Linear Search:**
  - Time: $O(n)$ (where $n$ is the number of items in the list)
  - Space: $O(1)$

#### Proof & Cases for Linear Search
- **Best case:** The target is the first element ($O(1)$).
- **Worst case:** The target is the last element or not present ($O(n)$).
- **Average case:** On average, about half the list is checked ($O(n)$).

## Important Notes
- Simple algorithms are easy to understand and implement.
- Linear search is not efficient for large lists; more advanced algorithms (like binary search) are faster for sorted data.
- Always consider both time and space when choosing an algorithm.

## Real-World Applications
- **Adding numbers:** Used in calculators, spreadsheets, and any software that processes numbers.
- **Finding maximum:** Used in grading systems to find the highest score, or in sports to find the winner.
- **Linear search:** Used in searching for a contact in a phone list or a word in a document.

## Ideas for Self-Practicing
- Write an algorithm to find the minimum of three numbers.
- Modify the linear search to return all indices where the target appears.
- Try writing pseudocode for a real-life task (like making a sandwich).

## Further Readings & Connections
- [Khan Academy: Algorithms](https://www.khanacademy.org/computing/computer-science/algorithms)
- [GeeksforGeeks: Introduction to Algorithms](https://www.geeksforgeeks.org/fundamentals-of-algorithms/)
- Explore more advanced searching algorithms like binary search (see Chapter 4).
- Learn about sorting algorithms, which are closely related to searching (see Chapter 4).

---
**Key Terms:**
- **Algorithm:** A step-by-step procedure to solve a problem.
- **Time Complexity:** How fast an algorithm runs as input grows.
- **Space Complexity:** How much memory an algorithm uses.
- **Pseudocode:** A way to describe algorithms using plain language. 