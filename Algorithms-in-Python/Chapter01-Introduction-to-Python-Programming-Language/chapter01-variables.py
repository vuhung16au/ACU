# Python Variables Tutorial for Beginners

# A variable is a name that stores a value. You can use variables to store numbers, text, and more.

# --- Assigning values to variables ---
user_name = "Alice"  # string variable
user_age = 20         # integer variable
user_height = 1.65    # float variable (meters)
is_student = True     # boolean variable

# Print the values of the variables
print("Name:", user_name)
print("Age:", user_age)
print("Height:", user_height, "meters")
print("Is a student:", is_student)

# --- Variable names should be meaningful ---
# Good: total_price, user_score, max_value
# Bad: x, y, z (unless for short examples)

# --- Variables can be changed (reassigned) ---
user_age = 21  # Now the value is updated
print("Updated age:", user_age)

# --- Multiple variables in one line ---
math_score, english_score = 85, 90
print("Math score:", math_score)
print("English score:", english_score)

# --- Swapping values between variables ---
a = 5
b = 10
a, b = b, a  # swap values
def swap_example():
    print("After swapping: a =", a, ", b =", b)
swap_example()

# --- Summary ---
# - Variables store values you want to use later
# - Use meaningful names
# - You can change (reassign) variable values
# - Python is case-sensitive: 'age' and 'Age' are different variables
