## Python Looping Tutorial for Beginners

## For Loop Example
# A for loop is used to repeat actions a specific number of times or to go through items in a list.

# Print numbers from 1 to 5 using a for loop
for number in range(1, 6):  # range(1, 6) gives numbers 1, 2, 3, 4, 5
    print("For loop number:", number)

# Loop through a list of fruits
fruits = ["apple", "banana", "cherry"]
for fruit in fruits:
    print("I like", fruit)

## While Loop Example
# A while loop repeats as long as a condition is True.

# Print numbers from 1 to 5 using a while loop
count = 1  # start at 1
while count <= 5:  # keep looping while count is less than or equal to 5
    print("While loop count:", count)
    count += 1  # increase count by 1 each time

# While loop with user input (optional, for demonstration)
# Uncomment the lines below to try it interactively
# keep_going = "yes"
# while keep_going == "yes":
#     print("This loop will keep going until you type 'no'.")
#     keep_going = input("Do you want to continue? (yes/no): ")

# Summary:
# - Use for loops to repeat actions a set number of times or go through items in a list
# - Use while loops to repeat actions as long as a condition is True
