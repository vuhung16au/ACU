# Example 1: Basic Syntax
# Regular function
def add(x, y):
    return x + y

# Equivalent lambda function
lambda_add = lambda x, y: x + y

# Using both functions
result_regular = add(3, 5)
result_lambda = lambda_add(3, 5)
print("Result (Regular Function):", result_regular)
print("Result (Lambda Function):", result_lambda)

# Example 2: Sorting with Lambda
# List of tuples
students = [("Alice", 25), ("Bob", 30), ("Charlie", 22)]
# Sort by age using a Lambda function
sorted_students = sorted(students, key=lambda student: student[1])
print("Sorted Students by Age:", sorted_students)

# Example 3: Filtering with Lambda
# List of numbers
numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9]
# Filter even numbers using a Lambda function
even_numbers = list(filter(lambda x: x % 2 == 0, numbers))
print("Even Numbers:", even_numbers)

# Example 4: Mapping with Lambda
# List of numbers
numbers = [1, 2, 3, 4, 5]
# Square each number using a Lambda function
squared_numbers = list(map(lambda x: x**2, numbers))
print("Squared Numbers:", squared_numbers)

# Example 5: Using Lambda with max function
# List of numbers
numbers = [10, 5, 8, 20, 15]
# Find the maximum number using a Lambda function
max_number = max(numbers, key=lambda x: -x)  # Use negation for finding minimum
print("Maximum Number:", max_number)

# Example 6: Using Lambda with sorted and Multiple Criteria
# List of dictionaries representing people with names and ages
people = [{"name": "Alice", "age": 25}, {"name": "Bob", "age": 30}, {"name": "Charlie", "age": 22}]
# Sort by age and then by name using a Lambda function
sorted_people = sorted(people, key=lambda person: (person["age"], person["name"]))
print("Sorted People:", sorted_people)

# Example 7: Using Lambda with reduce from functools
from functools import reduce
# List of numbers
numbers = [1, 2, 3, 4, 5]
# Calculate the product of all numbers using a Lambda function and reduce
product = reduce(lambda x, y: x * y, numbers)
print("Product of Numbers:", product)

# Example 8: Using Lambda with Conditional Expressions
# List of numbers
numbers = [10, 5, 8, 20, 15]
# Use a Lambda function with a conditional expression to filter and square even numbers
filtered_and_squared = list(map(lambda x: x**2 if x % 2 == 0 else x, numbers))
print("Filtered and Squared Numbers:", filtered_and_squared)

# Example 9: Using Lambda with key in max and min to Find Extremes
# List of tuples representing products with names and prices
products = [("Laptop", 1200), ("Phone", 800), ("Tablet", 500), ("Smartwatch", 200)]
# Find the most and least expensive products using Lambda functions
most_expensive = max(products, key=lambda item: item[1])
least_expensive = min(products, key=lambda item: item[1])
print("Most Expensive Product:", most_expensive)
print("Least Expensive Product:", least_expensive)