# Chapter 7: Backtracking Techniques
# This tutorial introduces backtracking for beginners with simple, well-commented examples.

# 1. What is Backtracking?
# Backtracking is a problem-solving technique that tries to build a solution step by step,
# removing solutions that fail to satisfy the problem constraints as soon as possible.

# 2. Example: The N-Queens Problem
# Place N queens on an N x N chessboard so that no two queens threaten each other.

# Helper function to print the chessboard

def print_chessboard(board):
    for row in board:
        print(" ".join(row))
    print()

# Function to check if a queen can be placed at board[row][col]
def is_safe(board, row, col, n):
    # Check this column on upper rows
    for i in range(row):
        if board[i][col] == 'Q':
            return False
    # Check upper left diagonal
    i, j = row - 1, col - 1
    while i >= 0 and j >= 0:
        if board[i][j] == 'Q':
            return False
        i -= 1
        j -= 1
    # Check upper right diagonal
    i, j = row - 1, col + 1
    while i >= 0 and j < n:
        if board[i][j] == 'Q':
            return False
        i -= 1
        j += 1
    return True

# Backtracking function to solve N-Queens
def solve_n_queens(board, row, n):
    if row == n:
        print("One solution:")
        print_chessboard(board)
        return True  # Found a solution
    found_solution = False
    for col in range(n):
        if is_safe(board, row, col, n):
            board[row][col] = 'Q'  # Place queen
            # Recur to place rest of the queens
            if solve_n_queens(board, row + 1, n):
                found_solution = True
            board[row][col] = '.'  # Backtrack
    return found_solution

# Main function to start the N-Queens solver
def n_queens_solver(n):
    # Create an empty chessboard
    board = [['.' for _ in range(n)] for _ in range(n)]
    if not solve_n_queens(board, 0, n):
        print(f"No solution exists for {n} queens.")

# Try solving the 4-Queens problem
n_queens_solver(4)
# n_queens_solver(8)

# 3. Key Points About Backtracking
# - Backtracking is useful for constraint satisfaction problems (like puzzles, games, and combinations).
# - It tries all possible options and backtracks when a constraint is violated.
# - The N-Queens problem is a classic example.

# End of Chapter 7: Backtracking Techniques tutorial
