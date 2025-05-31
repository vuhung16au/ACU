## Python Casting Tutorial for Beginners

## Casting means converting a value from one data type to another.

## --- String to Integer ---
user_age_str = "25"  # This is a string
user_age_int = int(user_age_str)  # Convert string to integer
print("String to int:", user_age_int, type(user_age_int))

## --- String to Float ---
price_str = "19.99"
price_float = float(price_str)
print("String to float:", price_float, type(price_float))

## --- Integer to String ---
score = 100
score_str = str(score)
print("Int to string:", score_str, type(score_str))

## --- Float to Integer ---
height = 175.8
height_int = int(height)  # This will remove the decimal part
print("Float to int:", height_int, type(height_int))

## --- Integer to Float ---
count = 7
count_float = float(count)
print("Int to float:", count_float, type(count_float))

## --- List to Tuple ---
fruits_list = ["apple", "banana", "cherry"]
fruits_tuple = tuple(fruits_list)
print("List to tuple:", fruits_tuple, type(fruits_tuple))

## --- Tuple to List ---
colors_tuple = ("red", "green", "blue")
colors_list = list(colors_tuple)
print("Tuple to list:", colors_list, type(colors_list))

## --- Summary ---
# - Use int(), float(), str(), list(), tuple() to convert between types
# - Always make sure the value can be converted (e.g., 'abc' cannot be converted to int)
