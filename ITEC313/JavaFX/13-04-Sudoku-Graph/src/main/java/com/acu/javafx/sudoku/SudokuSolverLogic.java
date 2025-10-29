package com.acu.javafx.sudoku;

/**
 * Core logic for Sudoku solving using backtracking algorithm
 * 
 * This class contains the pure logic for solving Sudoku puzzles without UI dependencies.
 * It can be easily tested and reused in different contexts.
 */
public class SudokuSolverLogic {
    
    private static final int GRID_SIZE = 9;
    
    /**
     * Solves a Sudoku puzzle using backtracking algorithm
     * 
     * Time Complexity: O(9^(n*n)) where n is the number of empty cells
     * Space Complexity: O(n*n) for the recursion stack
     * 
     * @param grid The Sudoku grid to solve (modified in place)
     * @return true if a solution is found, false otherwise
     */
    public boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == 0) {
                    // Try each number 1-9
                    for (int num = 1; num <= 9; num++) {
                        if (isValidPlacement(grid, row, col, num)) {
                            grid[row][col] = num;
                            
                            // Recursively solve the rest
                            if (solveSudoku(grid)) {
                                return true;
                            }
                            
                            // Backtrack if no solution found
                            grid[row][col] = 0;
                        }
                    }
                    return false; // No valid number found
                }
            }
        }
        return true; // All cells filled
    }
    
    /**
     * Checks if placing a number at given position is valid according to Sudoku rules
     * 
     * @param grid The Sudoku grid
     * @param row Row index
     * @param col Column index
     * @param num Number to place
     * @return true if placement is valid, false otherwise
     */
    public boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        // Check row
        for (int c = 0; c < GRID_SIZE; c++) {
            if (grid[row][c] == num) {
                return false;
            }
        }
        
        // Check column
        for (int r = 0; r < GRID_SIZE; r++) {
            if (grid[r][col] == num) {
                return false;
            }
        }
        
        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Validates if a complete Sudoku solution is valid
     * 
     * @param grid The complete Sudoku grid
     * @return true if the solution is valid, false otherwise
     */
    public boolean isValidSolution(int[][] grid) {
        // Check all rows
        for (int row = 0; row < GRID_SIZE; row++) {
            boolean[] used = new boolean[10];
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];
                if (value < 1 || value > 9) {
                    return false; // Invalid number
                }
                if (used[value]) {
                    return false; // Duplicate in row
                }
                used[value] = true;
            }
        }
        
        // Check all columns
        for (int col = 0; col < GRID_SIZE; col++) {
            boolean[] used = new boolean[10];
            for (int row = 0; row < GRID_SIZE; row++) {
                int value = grid[row][col];
                if (used[value]) {
                    return false; // Duplicate in column
                }
                used[value] = true;
            }
        }
        
        // Check all 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] used = new boolean[10];
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int value = grid[row][col];
                        if (used[value]) {
                            return false; // Duplicate in 3x3 box
                        }
                        used[value] = true;
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Validates the current input for Sudoku rules (allows empty cells)
     * 
     * @param grid The Sudoku grid to validate
     * @return true if the input is valid, false otherwise
     */
    public boolean isValidInput(int[][] grid) {
        // Check rows
        for (int row = 0; row < GRID_SIZE; row++) {
            boolean[] used = new boolean[10];
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (value < 1 || value > 9) {
                        return false; // Invalid number
                    }
                    if (used[value]) {
                        return false; // Duplicate in row
                    }
                    used[value] = true;
                }
            }
        }
        
        // Check columns
        for (int col = 0; col < GRID_SIZE; col++) {
            boolean[] used = new boolean[10];
            for (int row = 0; row < GRID_SIZE; row++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (used[value]) {
                        return false; // Duplicate in column
                    }
                    used[value] = true;
                }
            }
        }
        
        // Check 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] used = new boolean[10];
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int value = grid[row][col];
                        if (value != 0) {
                            if (used[value]) {
                                return false; // Duplicate in 3x3 box
                            }
                            used[value] = true;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Counts the number of empty cells in the grid
     * 
     * @param grid The Sudoku grid
     * @return Number of empty cells (0s)
     */
    public int countEmptyCells(int[][] grid) {
        int count = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == 0) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Creates a deep copy of the grid
     * 
     * @param grid The grid to copy
     * @return A new grid with the same values
     */
    public int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, GRID_SIZE);
        }
        return copy;
    }
}
