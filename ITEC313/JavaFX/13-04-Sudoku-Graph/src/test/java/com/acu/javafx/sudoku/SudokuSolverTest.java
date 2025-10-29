package com.acu.javafx.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

/**
 * Test class for Sudoku Solver functionality
 * 
 * This class tests the core logic of the Sudoku solver including:
 * - Input validation
 * - Traditional backtracking algorithm
 * - Graph theory-based solving
 * - Constraint graph functionality
 * - Puzzle solving
 * - Edge cases
 */
@DisplayName("Sudoku Solver Tests")
class SudokuSolverTest {
    
    private SudokuGraphSolver graphSolver;
    
    @BeforeEach
    void setUp() {
        graphSolver = new SudokuGraphSolver();
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
        
        // Act: Solve the puzzle using Graph Theory
        boolean result = graphSolver.solveSudoku(puzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Should find a solution for valid puzzle");
        assertTrue(graphSolver.isValidSolution(puzzle), "Solution should be valid");
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
        
        // Act: Try to solve the puzzle using Graph Theory
        boolean result = graphSolver.solveSudoku(puzzle);
        
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
        assertTrue(graphSolver.isValidSolution(solution), "Complete valid solution should be valid");
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
        assertFalse(graphSolver.isValidSolution(invalidSolution), "Solution with duplicate in row should be invalid");
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
        assertFalse(graphSolver.isValidSolution(invalidSolution), "Solution with duplicate in column should be invalid");
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
        assertFalse(graphSolver.isValidSolution(invalidSolution), "Solution with duplicate in 3x3 box should be invalid");
    }
    
    @Test
    @DisplayName("Should solve empty puzzle")
    void testSolveEmptyPuzzle() {
        // Arrange: Create empty puzzle
        int[][] emptyPuzzle = new int[9][9];
        
        // Act: Solve the puzzle
        boolean result = graphSolver.solveSudoku(emptyPuzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Should find a solution for empty puzzle");
        assertTrue(graphSolver.isValidSolution(emptyPuzzle), "Solution should be valid");
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
        boolean result = graphSolver.solveSudoku(puzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Should find a solution for puzzle with single empty cell");
        assertTrue(graphSolver.isValidSolution(puzzle), "Solution should be valid");
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
        assertTrue(graphSolver.isValidInput(validInput), "Valid input should pass validation");
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
        assertFalse(graphSolver.isValidInput(invalidInput), "Input with duplicates should fail validation");
    }
    
    // ==================== GRAPH THEORY TESTS ====================
    
    @Test
    @DisplayName("Graph solver should solve a valid Sudoku puzzle")
    void testGraphSolverSolveValidPuzzle() {
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
        
        // Act: Solve the puzzle using graph theory
        boolean result = graphSolver.solveSudoku(puzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Graph solver should find a solution for valid puzzle");
        assertTrue(graphSolver.isValidSolution(puzzle), "Solution should be valid");
    }
    
    @Test
    @DisplayName("Graph solver should not solve an invalid puzzle")
    void testGraphSolverSolveInvalidPuzzle() {
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
        boolean result = graphSolver.solveSudoku(puzzle);
        
        // Assert: Should not find a solution
        assertFalse(result, "Graph solver should not find a solution for invalid puzzle");
    }
    
    @Test
    @DisplayName("Graph solver should solve empty puzzle")
    void testGraphSolverSolveEmptyPuzzle() {
        // Arrange: Create empty puzzle
        int[][] emptyPuzzle = new int[9][9];
        
        // Act: Solve the puzzle
        boolean result = graphSolver.solveSudoku(emptyPuzzle);
        
        // Assert: Should find a solution
        assertTrue(result, "Graph solver should find a solution for empty puzzle");
        assertTrue(graphSolver.isValidSolution(emptyPuzzle), "Solution should be valid");
    }
    
    @Test
    @DisplayName("Graph solver should validate correct solution")
    void testGraphSolverValidateCorrectSolution() {
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
        assertTrue(graphSolver.isValidSolution(solution), "Complete valid solution should be valid");
    }
    
    @Test
    @DisplayName("Graph solver should detect invalid solution")
    void testGraphSolverValidateInvalidSolution() {
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
        assertFalse(graphSolver.isValidSolution(invalidSolution), "Solution with duplicate should be invalid");
    }
    
    @Test
    @DisplayName("Graph solver should build constraint graph")
    void testGraphSolverConstraintGraph() {
        // Arrange: Create a simple puzzle
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
        
        // Act: Solve to build constraint graph
        graphSolver.solveSudoku(puzzle);
        Map<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> constraintGraph = graphSolver.getConstraintGraph();
        
        // Assert: Constraint graph should be built
        assertNotNull(constraintGraph, "Constraint graph should not be null");
        assertTrue(constraintGraph.size() > 0, "Constraint graph should have cells");
        
        // Check that each cell has constraints
        for (Map.Entry<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> entry : constraintGraph.entrySet()) {
            SudokuGraphSolver.Cell cell = entry.getKey();
            Set<SudokuGraphSolver.Cell> neighbors = entry.getValue();
            
            assertNotNull(neighbors, "Neighbors should not be null for cell " + cell);
            assertTrue(neighbors.size() > 0, "Cell " + cell + " should have constraints");
        }
    }
    
    @Test
    @DisplayName("Graph solver should provide domain information")
    void testGraphSolverDomains() {
        // Arrange: Create a simple puzzle
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
        
        // Act: Solve to build domains
        graphSolver.solveSudoku(puzzle);
        Map<SudokuGraphSolver.Cell, Set<Integer>> domains = graphSolver.getDomains();
        
        // Assert: Domains should be built
        assertNotNull(domains, "Domains should not be null");
        assertTrue(domains.size() > 0, "Domains should have cells");
        
        // Check that each cell has a domain
        for (Map.Entry<SudokuGraphSolver.Cell, Set<Integer>> entry : domains.entrySet()) {
            SudokuGraphSolver.Cell cell = entry.getKey();
            Set<Integer> domain = entry.getValue();
            
            assertNotNull(domain, "Domain should not be null for cell " + cell);
            assertTrue(domain.size() > 0, "Cell " + cell + " should have a domain");
        }
    }
    
    @Test
    @DisplayName("Graph solver should provide constraint count")
    void testGraphSolverConstraintCount() {
        // Arrange: Create a simple puzzle
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
        
        // Act: Solve to build constraint graph
        graphSolver.solveSudoku(puzzle);
        int constraintCount = graphSolver.getConstraintCount();
        
        // Assert: Should have a reasonable number of constraints
        assertTrue(constraintCount > 0, "Should have constraints");
        assertTrue(constraintCount < 1000, "Should not have excessive constraints");
    }
    
    @Test
    @DisplayName("Graph solver should calculate cell degree")
    void testGraphSolverCellDegree() {
        // Arrange: Create a simple puzzle
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
        
        // Act: Solve to build constraint graph
        graphSolver.solveSudoku(puzzle);
        SudokuGraphSolver.Cell testCell = new SudokuGraphSolver.Cell(0, 0);
        int degree = graphSolver.getCellDegree(testCell);
        
        // Assert: Should have a reasonable degree
        assertTrue(degree > 0, "Cell should have constraints");
        assertTrue(degree < 50, "Cell should not have excessive constraints");
    }
    
    @Test
    @DisplayName("Both solvers should produce same results")
    void testBothSolversProduceSameResults() {
        // Arrange: Create a valid Sudoku puzzle
        int[][] puzzle1 = {
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
        
        int[][] puzzle2 = {
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
        
        // Act: Solve with both solvers
        boolean result1 = graphSolver.solveSudoku(puzzle1);
        boolean result2 = graphSolver.solveSudoku(puzzle2);
        
        // Assert: Both should find solutions
        assertTrue(result1, "Backtracking solver should find solution");
        assertTrue(result2, "Graph solver should find solution");
        
        // Both solutions should be valid
        assertTrue(graphSolver.isValidSolution(puzzle1), "Backtracking solution should be valid");
        assertTrue(graphSolver.isValidSolution(puzzle2), "Graph solution should be valid");
    }
}
