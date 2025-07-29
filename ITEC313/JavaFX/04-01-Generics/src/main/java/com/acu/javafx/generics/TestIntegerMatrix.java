package com.acu.javafx.generics;

import javafx.scene.control.TextArea;

/**
 * Test class for Integer matrix operations.
 * Demonstrates how the generic matrix can be used with Integer type.
 */
public class TestIntegerMatrix {
    
    /**
     * Runs the Integer matrix demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== Integer Matrix Operations Demonstration ===\n\n");
        
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
        
        output.append("Matrix 1:\n");
        printMatrix(m1, output);
        output.append("\nMatrix 2:\n");
        printMatrix(m2, output);
        
        // Demonstrate matrix addition
        output.append("\n=== Matrix Addition ===\n");
        try {
            Integer[][] result = integerMatrix.addMatrix(m1, m2);
            output.append("Result of Matrix 1 + Matrix 2:\n");
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error in matrix addition: ").append(e.getMessage()).append("\n");
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
        
        output.append("\nMatrix 3 (3x2):\n");
        printMatrix(m3, output);
        output.append("\nMatrix 4 (2x3):\n");
        printMatrix(m4, output);
        
        // Demonstrate matrix multiplication
        output.append("\n=== Matrix Multiplication ===\n");
        try {
            Integer[][] result = integerMatrix.multiplyMatrix(m3, m4);
            output.append("Result of Matrix 3 * Matrix 4:\n");
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error in matrix multiplication: ").append(e.getMessage()).append("\n");
        }
        
        // Demonstrate error handling
        output.append("\n=== Error Handling ===\n");
        Integer[][] incompatible1 = new Integer[][] {
            {1, 2},
            {3, 4}
        };
        
        Integer[][] incompatible2 = new Integer[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        output.append("Attempting to add incompatible matrices:\n");
        try {
            Integer[][] result = integerMatrix.addMatrix(incompatible1, incompatible2);
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage()).append("\n");
        }
        
        output.append("\n=== Key Benefits of Generic Matrix ===\n");
        output.append("1. Type Safety: Compile-time checking for matrix operations\n");
        output.append("2. Code Reuse: Same matrix logic works with different numeric types\n");
        output.append("3. Extensibility: Easy to add new numeric types\n");
        output.append("4. Performance: No runtime type checking overhead\n");
        output.append("5. Maintainability: Single implementation for all numeric types\n");
        
        outputArea.setText(output.toString());
    }
    
    /**
     * Prints a matrix to the output StringBuilder.
     * @param matrix the matrix to print
     * @param output the StringBuilder to append to
     */
    private static void printMatrix(Integer[][] matrix, StringBuilder output) {
        for (int i = 0; i < matrix.length; i++) {
            output.append("[");
            for (int j = 0; j < matrix[i].length; j++) {
                output.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    output.append(", ");
                }
            }
            output.append("]\n");
        }
    }
} 