#!/usr/bin/env python3
"""
Q1 Odd-Even Number Checker

This Python script takes integer values from the user and checks whether
each number is odd or even. The program continues for n times as specified
by the user.

Author: Assessment 03 - Q1
Date: 2024
"""

def is_even(number):
    """
    Check if a number is even.
    
    Args:
        number (int): The number to check
        
    Returns:
        bool: True if the number is even, False if odd
    """
    return number % 2 == 0


def get_valid_integer(prompt):
    """
    Get a valid integer input from the user with error handling.
    
    Args:
        prompt (str): The prompt message to display to the user
        
    Returns:
        int: A valid integer entered by the user
    """
    while True:
        try:
            # Get input from user and convert to integer
            user_input = input(prompt)
            number = int(user_input)
            return number
        except ValueError:
            # Handle non-integer input
            print("Invalid input! Please enter a valid integer.")
        except KeyboardInterrupt:
            # Handle Ctrl+C gracefully
            print("\nProgram interrupted by user. Exiting...")
            exit(0)


def main():
    """
    Main function that orchestrates the odd-even checking program.
    """
    print("=" * 50)
    print("Odd-Even Number Checker")
    print("=" * 50)

    # Get the number of times the user wants to check numbers
    n_times = get_valid_integer("How many times you want to enter the number? ")

    # Validate that n_times is positive
    if n_times <= 0:
        print("Please enter a number greater than 0")
        return

    # Loop for n_times to get numbers and check if they are odd or even
    for i in range(1, n_times + 1):
        # Get the number from user
        number = get_valid_integer(f"Enter Number {i}: ")

        # Check if the number is greater than 0 (as per requirements)
        if number <= 0:
            print("Please enter a number greater than 0")
            continue

        # Check if the number is even or odd and display the result
        if is_even(number):
            print(f"It is an even number!")
        else:
            print(f"It is an odd number!")

    print("\nProgram completed successfully!")


if __name__ == "__main__":
    # Entry point of the program
    main()
