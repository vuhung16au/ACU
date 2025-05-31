# Python Functions Tutorial for Beginners

# --- What is a function? ---
# A function is a block of code that performs a specific task.
# You can reuse functions to avoid repeating code.

# --- Defining a simple function ---
def greet_user(name):
    """Print a greeting message to the user."""
    print(f"Hello, {name}! Welcome to Python functions.")

# --- Calling a function ---
greet_user("Alice")
greet_user("Bob")

# --- Function with return value ---
def add_numbers(a, b):
    """Return the sum of two numbers."""
    return a + b

result = add_numbers(5, 3)
print("Sum of 5 and 3 is:", result)

# --- Function with default argument ---
def print_message(message, times=2):
    """Print a message a given number of times (default is 2)."""
    for _ in range(times):
        print(message)

print_message("Python is fun!")  # Uses default times=2
print_message("Let's learn functions!", 3)  # Prints 3 times

# --- Function with multiple return values ---
def get_min_max(numbers):
    """Return the minimum and maximum from a list of numbers."""
    return min(numbers), max(numbers)

numbers_list = [4, 7, 1, 9, 2]
minimum, maximum = get_min_max(numbers_list)
print("Minimum:", minimum)
print("Maximum:", maximum)

# --- Summary ---
# - Use def to define a function
# - Use meaningful names for functions and parameters
# - Functions can take arguments and return values
# - You can have default arguments and multiple return values
