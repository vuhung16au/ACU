package com.acu.javafx.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Sudoku Solver functionality
 * 
 * This class tests the core logic of the Sudoku solver including:
 * - Input validation
 * - Backtracking algorithm
 * - Puzzle solving
 * - Edge cases
 */
@DisplayName("Sudoku Solver Tests")
class SudokuSolverTest {
    
    private SudokuSolverLogic solver;
    
    @BeforeEach
    void setUp() {
        solver = new SudokuSolverLogic();
    }
    
    @Test
    @DisplayName("Should solve a valid Sudoku puzzle")
    void testSolveValidPuzzle() {
        // Arrange: Create a valid Sudoku puzzle
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        
        // Act: Solve the puzzle
        boolean result = solver.solveSudoku(puzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Should find a solution for valid puzzle");
        assertTrue(solver.isValidSolution(puzzle), "Solution should be valid");
    }
    
    @Test
    @DisplayName("Should not solve an invalid puzzle with conflicts")
    void testSolveInvalidPuzzle() {
        // Arrange: Create an invalid puzzle with conflicts
        int[][] puzzle = {
            {1, 1, 0, 0, 0, 0, 0, 0, 0}, // Two 1s in first row
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        
        // Act: Try to solve the puzzle
        boolean result = solver.solveSudoku(puzzle);
        
        // Assert: Should not find a solution
        assertFalse(result, "Should not find a solution for invalid puzzle");
    }
    
    @Test
    @DisplayName("Should validate correct Sudoku solution")
    void testValidateCorrectSolution() {
        // Arrange: Create a complete valid Sudoku solution
        int[][] solution = {
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
        
        // Act & Assert: Should be valid
        assertTrue(solver.isValidSolution(solution), "Complete valid solution should be valid");
    }
    
    @Test
    @DisplayName("Should detect invalid solution with duplicate in row")
    void testValidateInvalidSolutionRow() {
        // Arrange: Create solution with duplicate in row
        int[][] invalidSolution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 1}, // Two 1s in last row
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        
        // Act & Assert: Should be invalid
        assertFalse(solver.isValidSolution(invalidSolution), "Solution with duplicate in row should be invalid");
    }
    
    @Test
    @DisplayName("Should detect invalid solution with duplicate in column")
    void testValidateInvalidSolutionColumn() {
        // Arrange: Create solution with duplicate in column
        int[][] invalidSolution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {5, 4, 5, 2, 8, 6, 1, 7, 9} // Two 5s in first column
        };
        
        // Act & Assert: Should be invalid
        assertFalse(solver.isValidSolution(invalidSolution), "Solution with duplicate in column should be invalid");
    }
    
    @Test
    @DisplayName("Should detect invalid solution with duplicate in 3x3 box")
    void testValidateInvalidSolutionBox() {
        // Arrange: Create solution with duplicate in 3x3 box
        int[][] invalidSolution = {
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
        // Make first 3x3 box invalid by adding duplicate
        invalidSolution[0][0] = 1; // This creates a duplicate with invalidSolution[2][0]
        
        // Act & Assert: Should be invalid
        assertFalse(solver.isValidSolution(invalidSolution), "Solution with duplicate in 3x3 box should be invalid");
    }
    
    @Test
    @DisplayName("Should solve empty puzzle")
    void testSolveEmptyPuzzle() {
        // Arrange: Create empty puzzle
        int[][] emptyPuzzle = new int[9][9];
        
        // Act: Solve the puzzle
        boolean result = solver.solveSudoku(emptyPuzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Should find a solution for empty puzzle");
        assertTrue(solver.isValidSolution(emptyPuzzle), "Solution should be valid");
    }
    
    @Test
    @DisplayName("Should solve puzzle with single empty cell")
    void testSolveSingleEmptyCell() {
        // Arrange: Create puzzle with only one empty cell
        int[][] puzzle = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 0} // Only one empty cell
        };
        
        // Act: Solve the puzzle
        boolean result = solver.solveSudoku(puzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Should find a solution for puzzle with single empty cell");
        assertTrue(solver.isValidSolution(puzzle), "Solution should be valid");
        assertEquals(9, puzzle[8][8], "Empty cell should be filled with 9");
    }
    
    @Test
    @DisplayName("Should validate input with valid numbers")
    void testValidateInputValid() {
        // Arrange: Create valid input
        int[][] validInput = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        
        // Act & Assert: Should be valid
        assertTrue(solver.isValidInput(validInput), "Valid input should pass validation");
    }
    
    @Test
    @DisplayName("Should reject input with invalid numbers")
    void testValidateInputInvalid() {
        // Arrange: Create input with invalid numbers
        int[][] invalidInput = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        // Add duplicate in first row
        invalidInput[0][1] = 5; // This creates a duplicate with invalidInput[0][0]
        
        // Act & Assert: Should be invalid
        assertFalse(solver.isValidInput(invalidInput), "Input with duplicates should fail validation");
    }
}
