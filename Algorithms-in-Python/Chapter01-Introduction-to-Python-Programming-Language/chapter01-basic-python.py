## Basic Python Tutorial for Beginners

## 1. Variables and Data Types
age = 25  # int
height = 1.75  # float
name = "Alice"  # str
is_student = True  # bool

print("Age:", age)
print("Height:", height)
print("Name:", name)
print("Is student:", is_student)

## 2. Basic Operators
num1 = 10
num2 = 3
print("Addition:", num1 + num2)
print("Subtraction:", num1 - num2)
print("Multiplication:", num1 * num2)
print("Division:", num1 / num2)
print("Equal:", num1 == num2)
print("Not equal:", num1 != num2)
print("Greater than:", num1 > num2)
print("Less than:", num1 < num2)
print("Both positive:", (num1 > 0) and (num2 > 0))
print("At least one positive:", (num1 > 0) or (num2 > 0))
print("Not equal:", not (num1 == num2))

## 3. Input and Output
favorite_color = input("What is your favorite color? ")  # Get user input
print("Your favorite color is:", favorite_color)

## 4. Control Flow
# Conditional Statements
number = int(input("Enter a number: "))
if number > 0:
    print("The number is positive.")
elif number == 0:
    print("The number is zero.")
else:
    print("The number is negative.")

# Loops
print("Counting from 1 to 5 using a for loop:")
for i in range(1, 6):
    print(i)

print("Counting down from 5 to 1 using a while loop:")
count = 5
while count > 0:
    print(count)
    count -= 1

## 5. Functions
def greet_user(username):
    # This function greets the user by name
    print(f"Hello, {username}! Welcome to Python.")

greet_user(name)

## 6. Lists and Basic Data Structures
fruits = ["apple", "banana", "cherry"]  # list
dimensions = (1920, 1080)  # tuple
unique_numbers = {1, 2, 3}  # set
person = {"name": "Alice", "age": 25}  # dictionary

print("Fruits:", fruits)
print("Dimensions:", dimensions)
print("Unique numbers:", unique_numbers)
print("Person info:", person)

## 7. String Manipulation
message = "Python is fun!"
print("First 6 characters:", message[:6])
print("Uppercase:", message.upper())
print("Replace 'fun' with 'awesome':", message.replace("fun", "awesome"))
print(f"{name} says: {message}")

## 8. Error Handling
try:
    divisor = int(input("Enter a number to divide 100 by: "))
    result = 100 / divisor
    print("Result:", result)
except ZeroDivisionError:
    print("Error: Cannot divide by zero.")
except ValueError:
    print("Error: Please enter a valid integer.")

# End of basic Python tutorial
