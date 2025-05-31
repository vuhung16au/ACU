## Python Lambda Functions Tutorial for Beginners

## What is a lambda function?
# A lambda function is a small, anonymous function (no name).
# It can have any number of arguments, but only one expression.

## Basic lambda function
add = lambda x, y: x + y  # Adds two numbers
print("Sum using lambda:", add(3, 5))

## Lambda with no arguments
greet = lambda: "Hello, world!"
print(greet())

## Lambda inside another function
def make_multiplier(n):
    """Return a function that multiplies its input by n."""
    return lambda x: x * n

triple = make_multiplier(3)
print("Triple of 4:", triple(4))

## Using lambda with built-in functions
numbers = [1, 2, 3, 4, 5]
squared = list(map(lambda x: x ** 2, numbers))  # Squares each number
print("Squared numbers:", squared)

even_numbers = list(filter(lambda x: x % 2 == 0, numbers))  # Filters even numbers
print("Even numbers:", even_numbers)

## Summary
# - Use lambda for small, simple functions
# - Syntax: lambda arguments: expression
# - Often used with map(), filter(), and sorted()
