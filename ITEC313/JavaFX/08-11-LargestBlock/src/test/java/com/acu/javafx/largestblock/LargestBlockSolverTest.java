package com.acu.javafx.largestblock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LargestBlockSolver.
 * 
 * Tests the dynamic programming algorithm for finding the largest square block
 * of 1s in a binary matrix.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class LargestBlockSolverTest {
    
    private LargestBlockSolver.BlockResult result;
    
    @BeforeEach
    void setUp() {
        // Reset result before each test
        result = null;
    }
    
    @Test
    void testEmptyMatrix() {
        int[][] matrix = {};
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(0, result.getSize());
        assertEquals(-1, result.getRow());
        assertEquals(-1, result.getCol());
    }
    
    @Test
    void testNullMatrix() {
        result = LargestBlockSolver.findLargestBlock(null);
        assertEquals(0, result.getSize());
        assertEquals(-1, result.getRow());
        assertEquals(-1, result.getCol());
    }
    
    @Test
    void testSingleCellWithOne() {
        int[][] matrix = {{1}};
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(1, result.getSize());
        assertEquals(0, result.getRow());
        assertEquals(0, result.getCol());
    }
    
    @Test
    void testSingleCellWithZero() {
        int[][] matrix = {{0}};
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(0, result.getSize());
        assertEquals(-1, result.getRow());
        assertEquals(-1, result.getCol());
    }
    
    @Test
    void testAllZerosMatrix() {
        int[][] matrix = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(0, result.getSize());
        assertEquals(-1, result.getRow());
        assertEquals(-1, result.getCol());
    }
    
    @Test
    void testAllOnesMatrix() {
        int[][] matrix = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(3, result.getSize());
        assertEquals(2, result.getRow());
        assertEquals(2, result.getCol());
    }
    
    @Test
    void testSingleOneInMatrix() {
        int[][] matrix = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(1, result.getSize());
        assertEquals(1, result.getRow());
        assertEquals(1, result.getCol());
    }
    
    @Test
    void testTwoByTwoBlock() {
        int[][] matrix = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 0}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(2, result.getSize());
        assertEquals(1, result.getRow());
        assertEquals(1, result.getCol());
    }
    
    @Test
    void testThreeByThreeBlock() {
        int[][] matrix = {
            {1, 1, 1, 0},
            {1, 1, 1, 0},
            {1, 1, 1, 0},
            {0, 0, 0, 0}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(3, result.getSize());
        assertEquals(2, result.getRow());
        assertEquals(2, result.getCol());
    }
    
    @Test
    void testMultipleBlocks() {
        int[][] matrix = {
            {1, 1, 0, 0},
            {1, 1, 0, 0},
            {0, 0, 1, 1},
            {0, 0, 1, 1}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(2, result.getSize());
        // Should find one of the 2x2 blocks
        assertTrue((result.getRow() == 1 && result.getCol() == 1) || 
                  (result.getRow() == 3 && result.getCol() == 3));
    }
    
    @Test
    void testLShape() {
        int[][] matrix = {
            {1, 1, 0},
            {1, 0, 0},
            {0, 0, 0}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(1, result.getSize());
        // Should find one of the individual 1s
        assertTrue((result.getRow() == 0 && result.getCol() == 0) ||
                  (result.getRow() == 0 && result.getCol() == 1) ||
                  (result.getRow() == 1 && result.getCol() == 0));
    }
    
    @Test
    void testSampleMatrix() {
        int[][] matrix = LargestBlockSolver.createSampleMatrix();
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(2, result.getSize());
        assertEquals(1, result.getRow());
        assertEquals(2, result.getCol());
    }
    
    @Test
    void testIsValidMatrixWithValidMatrix() {
        int[][] matrix = {
            {1, 0, 1},
            {0, 1, 0},
            {1, 1, 0}
        };
        assertTrue(LargestBlockSolver.isValidMatrix(matrix));
    }
    
    @Test
    void testIsValidMatrixWithInvalidValues() {
        int[][] matrix = {
            {1, 0, 2},
            {0, 1, 0},
            {1, 1, 0}
        };
        assertFalse(LargestBlockSolver.isValidMatrix(matrix));
    }
    
    @Test
    void testIsValidMatrixWithNull() {
        assertFalse(LargestBlockSolver.isValidMatrix(null));
    }
    
    @Test
    void testIsValidMatrixWithEmptyMatrix() {
        int[][] matrix = {};
        assertFalse(LargestBlockSolver.isValidMatrix(matrix));
    }
    
    @Test
    void testIsValidMatrixWithInconsistentRows() {
        int[][] matrix = {
            {1, 0},
            {0, 1, 0}
        };
        assertFalse(LargestBlockSolver.isValidMatrix(matrix));
    }
    
    @Test
    void testCreateRandomMatrix() {
        int[][] matrix = LargestBlockSolver.createRandomMatrix(5, 0.5);
        assertEquals(5, matrix.length);
        assertEquals(5, matrix[0].length);
        
        // Verify all values are 0 or 1
        for (int[] row : matrix) {
            for (int cell : row) {
                assertTrue(cell == 0 || cell == 1);
            }
        }
    }
    
    @Test
    void testCreateRandomMatrixWithInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            LargestBlockSolver.createRandomMatrix(0, 0.5);
        });
    }
    
    @Test
    void testCreateRandomMatrixWithInvalidProbability() {
        assertThrows(IllegalArgumentException.class, () -> {
            LargestBlockSolver.createRandomMatrix(5, 1.5);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            LargestBlockSolver.createRandomMatrix(5, -0.1);
        });
    }
    
    @Test
    void testBlockResultToString() {
        LargestBlockSolver.BlockResult result = new LargestBlockSolver.BlockResult(3, 2, 4);
        String expected = "Largest block: size=3 at position (2,4)";
        assertEquals(expected, result.toString());
    }
    
    @Test
    void testBlockResultGetters() {
        LargestBlockSolver.BlockResult result = new LargestBlockSolver.BlockResult(5, 3, 7);
        assertEquals(5, result.getSize());
        assertEquals(3, result.getRow());
        assertEquals(7, result.getCol());
    }
    
    @Test
    void testComplexMatrix() {
        int[][] matrix = {
            {1, 1, 1, 0, 0},
            {1, 1, 1, 0, 0},
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 1, 1}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(3, result.getSize());
        assertEquals(2, result.getRow());
        assertEquals(2, result.getCol());
    }
    
    @Test
    void testEdgeCaseWithSingleRow() {
        int[][] matrix = {{1, 1, 1, 0, 1}};
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(1, result.getSize());
        // Should find one of the 1s
        assertTrue(result.getRow() == 0);
        assertTrue(result.getCol() >= 0 && result.getCol() <= 4);
    }
    
    @Test
    void testEdgeCaseWithSingleColumn() {
        int[][] matrix = {
            {1},
            {1},
            {0},
            {1}
        };
        result = LargestBlockSolver.findLargestBlock(matrix);
        assertEquals(1, result.getSize());
        // Should find one of the 1s
        assertTrue(result.getRow() >= 0 && result.getRow() <= 3);
        assertEquals(0, result.getCol());
    }
}
