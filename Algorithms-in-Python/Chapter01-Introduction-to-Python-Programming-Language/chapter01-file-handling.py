## Python File Handling Tutorial for Beginners

## --- Writing to a File ---
# Open a file in write mode ('w'). This will create the file if it doesn't exist, or overwrite it if it does.
file_name = "example.txt"
with open(file_name, "w") as file:
    file.write("Hello, this is a line of text.\n")
    file.write("This is another line.\n")
print(f"Data written to {file_name}")

## --- Reading from a File ---
# Open the file in read mode ('r') and print its contents
with open(file_name, "r") as file:
    file_contents = file.read()
    print(f"Contents of {file_name}:")
    print(file_contents)

## --- Appending to a File ---
# Open the file in append mode ('a') to add more text without deleting existing content
with open(file_name, "a") as file:
    file.write("This line is added at the end.\n")
print(f"Appended a line to {file_name}")

## --- Deleting a File ---
# Use the os module to delete a file
import os
if os.path.exists(file_name):
    os.remove(file_name)
    print(f"{file_name} has been deleted.")
else:
    print(f"{file_name} does not exist.")

# Summary:
# - Use open() with 'w', 'r', or 'a' for writing, reading, or appending
# - Always close files (with open(...) is best practice)
# - Use os.remove() to delete files
