# Python Operators Tutorial for Beginners

# --- Arithmetic Operators ---
# Used for basic math operations
num1 = 10
num2 = 3
print("Addition:", num1 + num2)        # 13
print("Subtraction:", num1 - num2)     # 7
print("Multiplication:", num1 * num2)  # 30
print("Division:", num1 / num2)        # 3.333...
print("Floor Division:", num1 // num2) # 3 (no decimals)
print("Modulus (remainder):", num1 % num2) # 1
print("Exponentiation:", num1 ** num2) # 1000

# --- Assignment Operators ---
# Used to assign values to variables
x = 5
x += 2  # same as x = x + 2
print("x after += 2:", x)
x *= 3  # same as x = x * 3
print("x after *= 3:", x)

# --- Comparison Operators ---
# Used to compare values, result is True or False
age = 18
print("Is age equal to 18?", age == 18)
print("Is age not equal to 21?", age != 21)
print("Is age greater than 16?", age > 16)
print("Is age less than or equal to 18?", age <= 18)

# --- Logical Operators ---
# Used to combine conditional statements
is_student = True
has_id = False
print("Is student and has ID?", is_student and has_id)
print("Is student or has ID?", is_student or has_id)
print("Not a student?", not is_student)

# --- Membership Operators ---
# Used to check if a value is in a sequence (like a list)
fruits = ["apple", "banana", "cherry"]
print("Is 'banana' in fruits?", "banana" in fruits)
print("Is 'orange' not in fruits?", "orange" not in fruits)

# --- Identity Operators ---
# Used to check if two variables refer to the same object
list1 = [1, 2, 3]
list2 = list1
list3 = [1, 2, 3]
print("list1 is list2?", list1 is list2)   # True
print("list1 is list3?", list1 is list3)   # False
print("list1 == list3?", list1 == list3)   # True (values are equal)

# Summary:
# - Arithmetic: +, -, *, /, //, %, **
# - Assignment: =, +=, -=, etc.
# - Comparison: ==, !=, >, <, >=, <=
# - Logical: and, or, not
# - Membership: in, not in
# - Identity: is, is not
