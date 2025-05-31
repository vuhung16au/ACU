# Python Maths Library Tutorial for Beginners
# Reference: https://www.w3schools.com/python/python_math.asp

# Import the math module to use mathematical functions and constants
import math

# --- Basic math functions ---
number = 16
square_root = math.sqrt(number)  # Square root
print("Square root of", number, "is", square_root)

# --- Power and exponentiation ---
base = 2
exponent = 3
power_result = math.pow(base, exponent)  # 2^3 = 8.0
print(base, "raised to the power of", exponent, "is", power_result)

# --- Absolute value ---
negative_number = -7.5
absolute_value = math.fabs(negative_number)
print("Absolute value of", negative_number, "is", absolute_value)

# --- Rounding numbers ---
value = 3.75
print("Floor of", value, ":", math.floor(value))   # Rounds down
print("Ceil of", value, ":", math.ceil(value))     # Rounds up

# --- Trigonometric functions ---
angle_degrees = 45
angle_radians = math.radians(angle_degrees)  # Convert degrees to radians
sine_value = math.sin(angle_radians)
print("Sine of", angle_degrees, "degrees is", sine_value)

# --- Constants ---
print("Value of pi:", math.pi)
print("Value of e:", math.e)

# --- Logarithms ---
log_value = math.log(100, 10)  # log base 10 of 100
print("Log base 10 of 100 is", log_value)

# --- Min, max, and sum (built-in functions, not in math module) ---
numbers = [4, 7, 1, 9, 2]
print("Minimum:", min(numbers))
print("Maximum:", max(numbers))
print("Sum:", sum(numbers))

# Summary:
# - Use the math module for advanced math functions and constants
# - Use built-in min(), max(), sum() for simple calculations
