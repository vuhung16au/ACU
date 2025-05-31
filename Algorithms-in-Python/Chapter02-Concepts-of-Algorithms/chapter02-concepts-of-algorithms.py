## Chapter 2: Concepts of Algorithms
## This tutorial introduces the basic concepts of algorithms for beginners.

## 1. What is an Algorithm?
# An algorithm is a step-by-step set of instructions to solve a problem or perform a task.
# Example: Algorithm to add two numbers

def add_two_numbers(number1, number2):
    """Return the sum of two numbers."""
    return number1 + number2

result = add_two_numbers(5, 7)
print("Sum of 5 and 7 is:", result)

## 2. Properties of a Good Algorithm
## - Input: Accepts zero or more inputs
## - Output: Produces at least one output
## - Definiteness: Each step is clear and unambiguous
## - Finiteness: The algorithm ends after a finite number of steps
## - Effectiveness: Each step is basic enough to be carried out

## Example: Algorithm to find the maximum of three numbers

def find_maximum(a, b, c):
    """Return the largest of three numbers."""
    if a >= b and a >= c:
        return a
    elif b >= a and b >= c:
        return b
    else:
        return c

max_value = find_maximum(10, 25, 15)
print("The maximum of 10, 25, 15 is:", max_value)

## 3. Algorithm Efficiency (Time and Space Complexity)
## Time complexity: How fast an algorithm runs (number of steps as input grows)
## Space complexity: How much memory an algorithm uses
## Example: Linear search (find an item in a list)

def linear_search(numbers, target):
    """Return the index of target in numbers, or -1 if not found."""
    for index, value in enumerate(numbers):
        if value == target:
            return index  # Found target
    return -1  # Not found

numbers_list = [3, 8, 2, 7, 5]
search_result = linear_search(numbers_list, 7)
if search_result != -1:
    print(f"Number 7 found at index {search_result}.")
else:
    print("Number 7 not found in the list.")

## Time complexity of linear search: O(n), where n is the number of items in the list
## Space complexity: O(1), uses a constant amount of extra memory

## 4. Pseudocode Example (for understanding algorithms)
## Pseudocode is a way to describe algorithms using plain language and simple code-like structure.
## Example: Pseudocode for finding the sum of a list
## sum = 0
## for each number in the list:
##     sum = sum + number
## print sum

## 5. Why Learn Algorithms?
## - To solve problems efficiently
## - To improve logical thinking
## - To write better, faster, and more reliable code

## End of Chapter 2: Concepts of Algorithms
