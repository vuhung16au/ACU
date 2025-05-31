## Python User Input Tutorial for Beginners

## Getting user input

# Ask the user to enter their name
user_name = input("What is your name? ")  # input() always returns a string
print("Hello,", user_name)

# Ask the user to enter their age
user_age_str = input("How old are you? ")  # This is a string

# Convert (cast) the string to an integer using int()
user_age = int(user_age_str)

# Now you can use user_age as a number
print("Next year, you will be", user_age + 1, "years old.")

# You can also do the conversion in one line:
user_height = int(input("What is your height in centimeters? "))
print("Your height is:", user_height, "cm")

# Summary:
# - input() always returns a string
# - Use int() to convert a string to an integer
# - Always check that the input is a valid number before converting (not shown here for simplicity)
