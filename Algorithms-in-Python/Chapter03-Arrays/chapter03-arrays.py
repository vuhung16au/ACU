## Chapter 3: One-Dimensional and Two-Dimensional Arrays
## This tutorial introduces arrays (lists) in Python for beginners.

## 1. One-Dimensional Arrays (Lists)
# Creating a list of numbers
numbers = [10, 20, 30, 40, 50]
print("Original list:", numbers)

# Accessing elements by index
print("First element:", numbers[0])
print("Last element:", numbers[-1])

# Modifying elements
numbers[2] = 35  # Change the third element
print("After modification:", numbers)

# Adding elements
numbers.append(60)  # Add to the end
print("After appending 60:", numbers)

# Removing elements
numbers.remove(20)  # Remove the value 20
print("After removing 20:", numbers)

# Iterating through a list
print("All elements in the list:")
for num in numbers:
    print(num)

# Finding the sum and average
total = sum(numbers)
average = total / len(numbers)
print("Sum:", total)
print("Average:", average)

## 2. Two-Dimensional Arrays (Lists of Lists)
# Creating a 2D array (matrix)
matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]
print("2D Array (Matrix):")
for row in matrix:
    print(row)

# Accessing elements in a 2D array
print("Element at row 1, column 2:", matrix[0][1])  # Output: 2

# Modifying elements in a 2D array
matrix[1][1] = 0  # Set the center element to 0
print("Matrix after modification:")
for row in matrix:
    print(row)

# Iterating through all elements
print("All elements in the matrix:")
for row in matrix:
    for value in row:
        print(value, end=" ")
    print()  # Newline after each row

## 3. Common Applications
# Example: Find the maximum value in a list

def find_max_in_list(numbers_list):
    """Return the maximum value in a list."""
    max_value = numbers_list[0]
    for number in numbers_list:
        if number > max_value:
            max_value = number
    return max_value

max_in_numbers = find_max_in_list(numbers)
print("Maximum value in the list:", max_in_numbers)

# Example: Sum of each row in a 2D array
print("Sum of each row in the matrix:")
for row_index, row in enumerate(matrix):
    row_sum = sum(row)
    print(f"Row {row_index + 1} sum:", row_sum)

## End of Chapter 3: Arrays tutorial
