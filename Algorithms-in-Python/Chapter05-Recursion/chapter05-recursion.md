# Chapter 5: Recursion

## Introduction
This chapter introduces recursion, a powerful technique where a function calls itself to solve a problem. You will learn what recursion is, see classic examples, and understand how to analyze recursive algorithms.

## Algorithms Implemented

### 1. Factorial (Recursive)
Calculates the product of all positive integers up to $n$ (written as $n!$).
```python
def factorial(n):
    if n == 0 or n == 1:
        return 1
    else:
        return n * factorial(n - 1)
```
**Step-by-step:**
1. If $n$ is 0 or 1, return 1 (base case).
2. Otherwise, return $n$ times the factorial of $n-1$ (recursive case).

### 2. Fibonacci Sequence (Recursive)
Each number is the sum of the two before it.
```python
def fibonacci(n):
    if n == 0:
        return 0
    elif n == 1:
        return 1
    else:
        return fibonacci(n - 1) + fibonacci(n - 2)
```
**Step-by-step:**
1. If $n$ is 0, return 0. If $n$ is 1, return 1 (base cases).
2. Otherwise, return the sum of the previous two Fibonacci numbers (recursive case).

### 3. Sum of a List (Recursive)
Adds up all numbers in a list.
```python
def sum_list(numbers):
    if not numbers:
        return 0
    else:
        return numbers[0] + sum_list(numbers[1:])
```
**Step-by-step:**
1. If the list is empty, return 0 (base case).
2. Otherwise, add the first number to the sum of the rest of the list (recursive case).

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Factorial:** $O(n)$ time, $O(n)$ space (due to recursion stack)
- **Fibonacci (recursive):** $O(2^n)$ time, $O(n)$ space (very inefficient for large $n$)
- **Sum of list:** $O(n)$ time, $O(n)$ space

#### Proof & Cases
- **Factorial:** Each call reduces $n$ by 1, so $n$ calls.
- **Fibonacci:** Each call makes two more calls, leading to exponential growth.
- **Sum of list:** Each call processes one element.

## Important Notes
- Recursion is elegant but can be inefficient if not used carefully.
- Always define a base case to avoid infinite recursion.
- Some problems are easier to solve with recursion (like tree traversal).

## Real-World Applications
- Calculating factorials, Fibonacci numbers, and sums.
- Traversing file systems or tree structures.
- Solving puzzles (like the Tower of Hanoi).

## Ideas for Self-Practicing
- Write a recursive function to reverse a list.
- Modify the Fibonacci function to use memoization (store results).
- Try writing a recursive function for binary search.

## Further Readings & Connections
- [Khan Academy: Recursion](https://www.khanacademy.org/computing/computer-science/algorithms/recursive-algorithms)
- [GeeksforGeeks: Recursion](https://www.geeksforgeeks.org/recursion/)
- Learn about divide and conquer (see Chapter 6) and dynamic programming (see Chapter 9).

---
**Key Terms:**
- **Recursion:** A function calling itself.
- **Base Case:** The condition that stops recursion.
- **Recursive Case:** The part where the function calls itself. 