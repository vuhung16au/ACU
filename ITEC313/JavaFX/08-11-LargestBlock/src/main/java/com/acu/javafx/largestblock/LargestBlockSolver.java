package com.acu.javafx.largestblock;

/**
 * Solver for the Largest Block Problem using Dynamic Programming.
 * 
 * The Largest Block Problem involves finding the largest square submatrix 
 * consisting entirely of 1s in a binary matrix.
 * 
 * Algorithm:
 * - Uses dynamic programming with O(n²) time complexity
 * - For each cell (i,j), dp[i][j] represents the size of the largest square
 *   with bottom-right corner at (i,j)
 * - Recurrence relation: dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
 *   if matrix[i][j] == 1, otherwise 0
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class LargestBlockSolver {
    
    /**
     * Result class to hold the solution of the largest block problem.
     */
    public static class BlockResult {
        private final int size;
        private final int row;
        private final int col;
        
        public BlockResult(int size, int row, int col) {
            this.size = size;
            this.row = row;
            this.col = col;
        }
        
        public int getSize() { return size; }
        public int getRow() { return row; }
        public int getCol() { return col; }
        
        @Override
        public String toString() {
            return String.format("Largest block: size=%d at position (%d,%d)", size, row, col);
        }
    }
    
    /**
     * Finds the largest square block of 1s in the given binary matrix.
     * 
     * Time Complexity: O(n²) where n is the dimension of the matrix
     * Space Complexity: O(n²) for the DP table
     * 
     * @param matrix Binary matrix (0s and 1s)
     * @return BlockResult containing size and position of largest block
     */
    public static BlockResult findLargestBlock(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new BlockResult(0, -1, -1);
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // DP table: dp[i][j] = size of largest square with bottom-right at (i,j)
        int[][] dp = new int[rows][cols];
        
        // Variables to track the largest block found
        int maxSize = 0;
        int maxRow = -1;
        int maxCol = -1;
        
        // Fill the DP table
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        // First row or column: can only be 1x1 square
                        dp[i][j] = 1;
                    } else {
                        // Take minimum of three neighboring cells and add 1
                        dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    }
                    
                    // Update maximum if we found a larger square
                    if (dp[i][j] > maxSize) {
                        maxSize = dp[i][j];
                        maxRow = i;
                        maxCol = j;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        
        return new BlockResult(maxSize, maxRow, maxCol);
    }
    
    /**
     * Validates if the given matrix is a valid binary matrix.
     * 
     * @param matrix Matrix to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        
        int cols = matrix[0].length;
        if (cols == 0) {
            return false;
        }
        
        // Check if all rows have the same number of columns
        for (int[] row : matrix) {
            if (row.length != cols) {
                return false;
            }
        }
        
        // Check if all elements are 0 or 1
        for (int[] row : matrix) {
            for (int cell : row) {
                if (cell != 0 && cell != 1) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Creates a random binary matrix of the specified size.
     * 
     * @param size Size of the square matrix
     * @param probability Probability of a cell being 1 (0.0 to 1.0)
     * @return Random binary matrix
     */
    public static int[][] createRandomMatrix(int size, double probability) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        if (probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        
        int[][] matrix = new int[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.random() < probability ? 1 : 0;
            }
        }
        
        return matrix;
    }
    
    /**
     * Creates a sample matrix for testing purposes.
     * This matrix matches the data from the provided image.
     * 
     * @return Sample binary matrix
     */
    public static int[][] createSampleMatrix() {
        return new int[][] {
            {1, 1, 1, 1, 1, 0, 1, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 0, 1, 0},
            {0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 1, 0, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
            {1, 1, 0, 1, 1, 0, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 0, 1, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 0, 1, 1, 0, 1, 0}
        };
    }
}
