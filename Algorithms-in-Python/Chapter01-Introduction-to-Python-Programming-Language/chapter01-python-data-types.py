## Demonstration of Python's basic data types

## Integer: whole numbers, positive or negative, without decimals
user_age = 25
current_year = 2025
negative_number = -10
print("Integer examples:", user_age, current_year, negative_number)

## Float: numbers with decimal points
circle_radius = 3.5
pi_value = 3.14159
negative_float = -0.001
print("Float examples:", circle_radius, pi_value, negative_float)

## Boolean: True or False values
is_student = True
has_graduated = False
print("Boolean examples:", is_student, has_graduated)

## String: sequence of characters, used for text
welcome_message = "Hello, Python beginner!"
language_name = 'Python'
print("String examples:", welcome_message, language_name)

## List: ordered, mutable collection of items
favorite_fruits = ["apple", "banana", "cherry"]
prime_numbers = [2, 3, 5, 7, 11]
print("List examples:", favorite_fruits, prime_numbers)

## Tuple: ordered, immutable collection of items
coordinates = (10, 20)
color_rgb = (255, 0, 0)
print("Tuple examples:", coordinates, color_rgb)

## Dictionary: collection of key-value pairs
student_info = {"name": "Alice", "age": 20, "grade": "A"}
country_capitals = {"France": "Paris", "Japan": "Tokyo"}
print("Dictionary examples:", student_info, country_capitals)

## Set: unordered collection of unique items
unique_numbers = {1, 2, 3, 2, 1}  # duplicates are removed automatically
colors_set = {"red", "green", "blue", "red"}
print("Set examples:", unique_numbers, colors_set)

## You can check the type of any variable using the type() function
print("Type of 'user_age':", type(user_age))
print("Type of 'favorite_fruits':", type(favorite_fruits))
print("Type of 'student_info':", type(student_info))
