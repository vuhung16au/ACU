/**
 * Array-MultiDimensional - Sudoku Solution Checker
 * 
 * This program demonstrates the use of multi-dimensional arrays to check
 * if a 9x9 grid represents a valid Sudoku solution. The program includes
 * hardcoded Sudoku puzzles and their solutions for demonstration.
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * 
 * @author ITEC313 Student
 * @version 1.0
 */
public class ArrayMultiDimensional {
    
    public static void main(String[] args) {
        System.out.println("=== Sudoku Solution Checker ===\n");
        
        // Test Case 1: Valid Sudoku Solution
        System.out.println("Test Case 1: Valid Sudoku Solution");
        System.out.println("=================================");
        
        int[][] validSolution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        
        printSudokuGrid(validSolution);
        System.out.println("Result: " + (isValidSudoku(validSolution) ? "VALID SOLUTION" : "INVALID SOLUTION"));
        System.out.println();
        
        // Test Case 2: Invalid Sudoku Solution (duplicate in row)
        System.out.println("Test Case 2: Invalid Sudoku Solution (duplicate in row)");
        System.out.println("=====================================================");
        
        int[][] invalidSolution1 = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 5}  // Duplicate 5 in last row
        };
        
        printSudokuGrid(invalidSolution1);
        System.out.println("Result: " + (isValidSudoku(invalidSolution1) ? "VALID SOLUTION" : "INVALID SOLUTION"));
        System.out.println();
    }
    
    /**
     * Check if a 9x9 grid represents a valid Sudoku solution
     * 
     * @param grid The 9x9 Sudoku grid to validate
     * @return true if the grid is a valid Sudoku solution, false otherwise
     */
    public static boolean isValidSudoku(int[][] grid) {
        // Check if grid is null or wrong size
        if (grid == null || grid.length != 9) {
            return false;
        }
        
        // Check each cell
        for (int i = 0; i < 9; i++) {
            if (grid[i] == null || grid[i].length != 9) {
                return false;
            }
            
            for (int j = 0; j < 9; j++) {
                // Check if value is in valid range (1-9)
                if (grid[i][j] < 1 || grid[i][j] > 9) {
                    return false;
                }
                
                // Check if the value is valid in its position
                if (!isValidCell(i, j, grid)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Check if a specific cell value is valid according to Sudoku rules
     * 
     * @param row The row index of the cell
     * @param col The column index of the cell
     * @param grid The 9x9 Sudoku grid
     * @return true if the cell value is valid, false otherwise
     */
    private static boolean isValidCell(int row, int col, int[][] grid) {
        int value = grid[row][col];
        
        // Check row for duplicates
        for (int j = 0; j < 9; j++) {
            if (j != col && grid[row][j] == value) {
                return false;
            }
        }
        
        // Check column for duplicates
        for (int i = 0; i < 9; i++) {
            if (i != row && grid[i][col] == value) {
                return false;
            }
        }
        
        // Check 3x3 box for duplicates
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if ((i != row || j != col) && grid[i][j] == value) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Print a Sudoku grid in a formatted way
     * 
     * @param grid The 9x9 Sudoku grid to print
     */
    public static void printSudokuGrid(int[][] grid) {
        if (grid == null || grid.length != 9) {
            System.out.println("Invalid grid format");
            return;
        }
        
        System.out.println("┌─────────┬─────────┬─────────┐");
        
        for (int i = 0; i < 9; i++) {
            System.out.print("│ ");
            
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] + " ");
                
                if (j == 2 || j == 5) {
                    System.out.print("│ ");
                }
            }
            
            System.out.println("│");
            
            if (i == 2 || i == 5) {
                System.out.println("├─────────┼─────────┼─────────┤");
            }
        }
        
        System.out.println("└─────────┴─────────┴─────────┘");
    }
}
