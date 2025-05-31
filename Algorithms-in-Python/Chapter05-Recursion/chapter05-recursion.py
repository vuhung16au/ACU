## Chapter 5: Recursion
## This tutorial introduces recursion for beginners with simple, well-commented examples.

## 1. What is Recursion?
# Recursion is when a function calls itself to solve a smaller part of the problem.

## 2. Example: Factorial of a Number
## The factorial of n (written as n!) is the product of all positive integers up to n.
## For example, 5! = 5 * 4 * 3 * 2 * 1 = 120

def factorial(n):
    """Return the factorial of n using recursion."""
    if n == 0 or n == 1:
        return 1  # Base case: factorial of 0 or 1 is 1
    else:
        return n * factorial(n - 1)  # Recursive case

print("Factorial of 5:", factorial(5))

## 3. Example: Fibonacci Sequence
## The Fibonacci sequence is a series where each number is the sum of the two before it.
## Example: 0, 1, 1, 2, 3, 5, 8, ...

def fibonacci(n):
    """Return the nth Fibonacci number using recursion."""
    if n == 0:
        return 0  # Base case
    elif n == 1:
        return 1  # Base case
    else:
        return fibonacci(n - 1) + fibonacci(n - 2)  # Recursive case

print("Fibonacci number at position 6:", fibonacci(6))

## 4. Example: Sum of a List Using Recursion

def sum_list(numbers):
    """Return the sum of a list of numbers using recursion."""
    if not numbers:
        return 0  # Base case: empty list
    else:
        return numbers[0] + sum_list(numbers[1:])  # Recursive case

sample_list = [2, 4, 6, 8]
print("Sum of the list [2, 4, 6, 8]:", sum_list(sample_list))

## 5. Key Points About Recursion
## - Always define a base case to stop recursion.
## - Each recursive call should bring the problem closer to the base case.
## - Recursion is useful for problems that can be broken down into similar subproblems.

## End of Chapter 5: Recursion tutorial
