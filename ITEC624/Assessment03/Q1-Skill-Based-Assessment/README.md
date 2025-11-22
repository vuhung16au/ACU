# Q1 Odd-Even Number Checker

## Overview
This Python script implements a program that takes integer values from the user and checks whether each number is odd or even. The program continues for n times as specified by the user, with proper input validation and error handling.

## Features
- **User Input Validation**: Ensures only valid integers are accepted
- **Error Handling**: Gracefully handles invalid inputs and keyboard interrupts
- **Number Validation**: Checks that entered numbers are greater than 0
- **Clear Output**: Provides formatted output matching the required sample
- **Modular Design**: Well-structured code with separate functions for different tasks

## Code Explanation

### Main Components

1. **`is_even(number)` Function**
   - Takes an integer as input
   - Returns `True` if the number is even, `False` if odd
   - Uses the modulo operator (`%`) to check if remainder is 0 when divided by 2

2. **`get_valid_integer(prompt)` Function**
   - Handles user input with comprehensive error checking
   - Converts string input to integer with try-catch error handling
   - Handles `ValueError` for non-integer inputs
   - Handles `KeyboardInterrupt` (Ctrl+C) gracefully
   - Returns a valid integer or exits the program

3. **`main()` Function**
   - Orchestrates the entire program flow
   - Gets the number of iterations from the user
   - Validates that the number of iterations is positive
   - Loops through the specified number of times
   - For each iteration:
     - Gets a number from the user
     - Validates the number is greater than 0
     - Checks if the number is odd or even
     - Displays the appropriate result

### Key Logic
- **Even Number Check**: `number % 2 == 0`
- **Input Validation**: Uses try-catch blocks to handle invalid inputs
- **Number Validation**: Ensures numbers are greater than 0 as per requirements
- **Loop Control**: Uses `range(1, n_times + 1)` for 1-based numbering

## How to Run the Script

### Prerequisites
- Python 3.x installed on your system
- No additional packages required (uses only built-in Python modules)

### Running the Script

1. **Navigate to the script directory:**
   ```bash
   cd /path/to/Assessment03/Q1
   ```

2. **Make the script executable (optional):**
   ```bash
   chmod +x Q1-odd-even.py
   ```

3. **Run the script:**
   ```bash
   python3 Q1-odd-even.py
   ```
   
   Or if you made it executable:
   ```bash
   ./Q1-odd-even.py
   ```

### Sample Execution

```
==================================================
Odd-Even Number Checker
==================================================
How many times you want to enter the number? 3
Enter Number 1: 10
It is an even number!
Enter Number 2: 7
It is an odd number!
Enter Number 3: 0
Please enter a number greater than 0
```

## What the Script Does

1. **Initialization**: Displays a welcome message and program title
2. **Input Collection**: Asks the user how many numbers they want to check
3. **Validation**: Ensures the number of iterations is positive
4. **Number Processing**: For each iteration:
   - Prompts the user to enter a number
   - Validates the input is a valid integer
   - Checks if the number is greater than 0
   - Determines if the number is odd or even
   - Displays the result
5. **Completion**: Shows a completion message when done

## Error Handling

- **Invalid Input**: Handles non-integer inputs gracefully
- **Zero/Negative Numbers**: Rejects numbers ≤ 0 with appropriate message
- **Keyboard Interrupt**: Handles Ctrl+C gracefully
- **Input Validation**: Ensures all inputs are valid before processing

## Code Quality Features

- **Detailed Comments**: Every function and major code block is commented
- **Type Hints**: Function parameters and return types are documented
- **Modular Design**: Separates concerns into different functions
- **Error Handling**: Comprehensive error handling throughout
- **User-Friendly**: Clear prompts and error messages
- **Professional Structure**: Follows Python best practices

## Requirements Met

✅ **Python 3**: Written in Python 3 with modern syntax  
✅ **Detailed Comments**: Every function and logic block is thoroughly commented  
✅ **Input Validation**: Handles invalid inputs gracefully  
✅ **Number Validation**: Ensures numbers are greater than 0  
✅ **Sample Output Match**: Output format matches the required sample exactly  
✅ **Loop Functionality**: Continues for n times as specified  
✅ **Error Handling**: Comprehensive error handling throughout
