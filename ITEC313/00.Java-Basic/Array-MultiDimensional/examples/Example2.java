/**
 * Example2.java - Matrix Operations and Transformations
 * 
 * This example demonstrates advanced matrix operations:
 * - Matrix transpose
 * - Matrix addition and subtraction
 * - Matrix multiplication
 * - Matrix rotation
 * - Finding matrix properties
 */

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Matrix Operations and Transformations ===\n");
        
        // Create test matrices
        int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] matrixB = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };
        
        System.out.println("Matrix A:");
        printMatrix(matrixA);
        
        System.out.println("Matrix B:");
        printMatrix(matrixB);
        
        // 1. Matrix Transpose
        System.out.println("1. Matrix Transpose:");
        System.out.println("====================");
        
        int[][] transposedA = transposeMatrix(matrixA);
        System.out.println("Transpose of Matrix A:");
        printMatrix(transposedA);
        
        // 2. Matrix Addition
        System.out.println("2. Matrix Addition:");
        System.out.println("===================");
        
        int[][] sum = addMatrices(matrixA, matrixB);
        System.out.println("Matrix A + Matrix B:");
        printMatrix(sum);
        
        // 3. Matrix Subtraction
        System.out.println("3. Matrix Subtraction:");
        System.out.println("======================");
        
        int[][] difference = subtractMatrices(matrixA, matrixB);
        System.out.println("Matrix A - Matrix B:");
        printMatrix(difference);
        
        // 4. Matrix Multiplication
        System.out.println("4. Matrix Multiplication:");
        System.out.println("=========================");
        
        int[][] product = multiplyMatrices(matrixA, matrixB);
        System.out.println("Matrix A × Matrix B:");
        printMatrix(product);
        
        // 5. Matrix Properties
        System.out.println("5. Matrix Properties:");
        System.out.println("=====================");
        
        System.out.println("Matrix A properties:");
        System.out.println("- Number of rows: " + matrixA.length);
        System.out.println("- Number of columns: " + matrixA[0].length);
        System.out.println("- Is square: " + isSquare(matrixA));
        System.out.println("- Maximum value: " + findMaxValue(matrixA));
        System.out.println("- Minimum value: " + findMinValue(matrixA));
        System.out.println("- Sum of all elements: " + calculateSum(matrixA));
        System.out.println("- Average value: " + calculateAverage(matrixA));
        
        // 6. Matrix Rotation
        System.out.println("\n6. Matrix Rotation:");
        System.out.println("===================");
        
        System.out.println("Original Matrix:");
        printMatrix(matrixA);
        
        System.out.println("Rotated 90 degrees clockwise:");
        int[][] rotated90 = rotateMatrix90(matrixA);
        printMatrix(rotated90);
        
        System.out.println("Rotated 180 degrees:");
        int[][] rotated180 = rotateMatrix180(matrixA);
        printMatrix(rotated180);
        
        // 7. Identity Matrix
        System.out.println("7. Identity Matrix:");
        System.out.println("===================");
        
        int[][] identity = createIdentityMatrix(3);
        System.out.println("3x3 Identity Matrix:");
        printMatrix(identity);
        
        // 8. Matrix Copy and Clone
        System.out.println("8. Matrix Copy and Clone:");
        System.out.println("=========================");
        
        int[][] copied = copyMatrix(matrixA);
        System.out.println("Copied Matrix:");
        printMatrix(copied);
        
        // Modify original and show copy is independent
        matrixA[0][0] = 100;
        System.out.println("After modifying original matrix:");
        System.out.println("Original Matrix A:");
        printMatrix(matrixA);
        System.out.println("Copied Matrix (should be unchanged):");
        printMatrix(copied);
        
        // 9. Matrix Symmetry
        System.out.println("9. Matrix Symmetry:");
        System.out.println("===================");
        
        int[][] symmetricMatrix = {
            {1, 2, 3},
            {2, 5, 6},
            {3, 6, 9}
        };
        
        System.out.println("Symmetric Matrix:");
        printMatrix(symmetricMatrix);
        System.out.println("Is symmetric: " + isSymmetric(symmetricMatrix));
        System.out.println("Is symmetric (Matrix A): " + isSymmetric(matrixA));
    }
    
    /**
     * Transpose a matrix (swap rows and columns)
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        
        return transposed;
    }
    
    /**
     * Add two matrices
     */
    public static int[][] addMatrices(int[][] matrixA, int[][] matrixB) {
        int rows = matrixA.length;
        int cols = matrixA[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * Subtract two matrices
     */
    public static int[][] subtractMatrices(int[][] matrixA, int[][] matrixB) {
        int rows = matrixA.length;
        int cols = matrixA[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * Multiply two matrices
     */
    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;
        int[][] result = new int[rowsA][colsB];
        
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        
        return result;
    }
    
    /**
     * Check if matrix is square
     */
    public static boolean isSquare(int[][] matrix) {
        return matrix.length == matrix[0].length;
    }
    
    /**
     * Find maximum value in matrix
     */
    public static int findMaxValue(int[][] matrix) {
        int max = matrix[0][0];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        
        return max;
    }
    
    /**
     * Find minimum value in matrix
     */
    public static int findMinValue(int[][] matrix) {
        int min = matrix[0][0];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
        }
        
        return min;
    }
    
    /**
     * Calculate sum of all elements
     */
    public static int calculateSum(int[][] matrix) {
        int sum = 0;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
        }
        
        return sum;
    }
    
    /**
     * Calculate average of all elements
     */
    public static double calculateAverage(int[][] matrix) {
        int sum = calculateSum(matrix);
        int totalElements = matrix.length * matrix[0].length;
        return (double) sum / totalElements;
    }
    
    /**
     * Rotate matrix 90 degrees clockwise
     */
    public static int[][] rotateMatrix90(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j];
            }
        }
        
        return rotated;
    }
    
    /**
     * Rotate matrix 180 degrees
     */
    public static int[][] rotateMatrix180(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[rows - 1 - i][cols - 1 - j] = matrix[i][j];
            }
        }
        
        return rotated;
    }
    
    /**
     * Create identity matrix
     */
    public static int[][] createIdentityMatrix(int size) {
        int[][] identity = new int[size][size];
        
        for (int i = 0; i < size; i++) {
            identity[i][i] = 1;
        }
        
        return identity;
    }
    
    /**
     * Copy matrix
     */
    public static int[][] copyMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] copied = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copied[i][j] = matrix[i][j];
            }
        }
        
        return copied;
    }
    
    /**
     * Check if matrix is symmetric
     */
    public static boolean isSymmetric(int[][] matrix) {
        if (!isSquare(matrix)) {
            return false;
        }
        
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Print a matrix in a formatted way
     */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" ]");
        }
        System.out.println();
    }
}
