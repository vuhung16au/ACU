# Python Dictionary and Tuple Tutorial for Beginners

# --- Tuples ---
# A tuple is an ordered, immutable collection of items.
# You cannot change the items in a tuple after it is created.

# Example: coordinates of a point
point_coordinates = (10, 20)
print("Tuple example (coordinates):", point_coordinates)

# Example: days of the week
week_days = ("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
print("Tuple example (week days):", week_days)

# Accessing tuple elements
first_day = week_days[0]
print("The first day of the week is:", first_day)

# --- Dictionaries ---
# A dictionary is a collection of key-value pairs.
# Keys must be unique and immutable (like strings or numbers).

# Example: student information
student_info = {
    "name": "Alice",
    "age": 20,
    "grade": "A"
}
print("Dictionary example (student info):", student_info)

# Accessing values by key
student_name = student_info["name"]
print("Student's name is:", student_name)

# Adding a new key-value pair
student_info["major"] = "Computer Science"
print("Updated student info:", student_info)

# Example: phone book
phone_book = {
    "Bob": "123-4567",
    "Carol": "987-6543"
}
print("Dictionary example (phone book):", phone_book)

# Summary:
# - Tuples are ordered and immutable (cannot be changed)
# - Dictionaries store data as key-value pairs and are mutable (can be changed)
