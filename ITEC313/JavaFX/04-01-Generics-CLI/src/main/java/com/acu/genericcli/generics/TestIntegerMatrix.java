package com.acu.genericcli.generics;

/**
 * Test class for Integer matrix operations.
 * Demonstrates how the generic matrix can be used with Integer type.
 */
public class TestIntegerMatrix {
    
    /**
     * Runs the Integer matrix demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== Integer Matrix Operations Demonstration ===\n");
        
        // Create Integer matrix
        IntegerMatrix integerMatrix = new IntegerMatrix();
        
        // Create test matrices
        Integer[][] m1 = new Integer[][] {
            {1, 2, 3},
            {4, 5, 6},
            {1, 1, 1}
        };
        
        Integer[][] m2 = new Integer[][] {
            {1, 1, 1},
            {2, 2, 2},
            {0, 0, 0}
        };
        
        System.out.println("Matrix 1:");
        printMatrix(m1);
        System.out.println("\nMatrix 2:");
        printMatrix(m2);
        
        // Demonstrate matrix addition
        System.out.println("\n=== Matrix Addition ===");
        try {
            Integer[][] result = integerMatrix.addMatrix(m1, m2);
            System.out.println("Result of Matrix 1 + Matrix 2:");
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error in matrix addition: " + e.getMessage());
        }
        
        // Create matrices for multiplication
        Integer[][] m3 = new Integer[][] {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        
        Integer[][] m4 = new Integer[][] {
            {1, 2, 3},
            {4, 5, 6}
        };
        
        System.out.println("\nMatrix 3 (3x2):");
        printMatrix(m3);
        System.out.println("\nMatrix 4 (2x3):");
        printMatrix(m4);
        
        // Demonstrate matrix multiplication
        System.out.println("\n=== Matrix Multiplication ===");
        try {
            Integer[][] result = integerMatrix.multiplyMatrix(m3, m4);
            System.out.println("Result of Matrix 3 * Matrix 4:");
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error in matrix multiplication: " + e.getMessage());
        }
        
        // Demonstrate error handling
        System.out.println("\n=== Error Handling ===");
        Integer[][] incompatible1 = new Integer[][] {
            {1, 2},
            {3, 4}
        };
        
        Integer[][] incompatible2 = new Integer[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("Attempting to add incompatible matrices:");
        try {
            Integer[][] result = integerMatrix.addMatrix(incompatible1, incompatible2);
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Key Benefits of Generic Matrix ===");
        System.out.println("1. Type Safety: Compile-time checking for matrix operations");
        System.out.println("2. Code Reuse: Same matrix logic works with different numeric types");
        System.out.println("3. Extensibility: Easy to add new numeric types");
        System.out.println("4. Performance: No runtime type checking overhead");
        System.out.println("5. Maintainability: Single implementation for all numeric types");
    }
    
    /**
     * Prints a matrix to the console.
     * @param matrix the matrix to print
     */
    private static void printMatrix(Integer[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
} 