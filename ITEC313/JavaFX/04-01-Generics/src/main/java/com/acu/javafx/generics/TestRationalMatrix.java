package com.acu.javafx.generics;

import javafx.scene.control.TextArea;

/**
 * Test class for Rational matrix operations.
 * Demonstrates how the generic matrix can be used with Rational type.
 */
public class TestRationalMatrix {
    
    /**
     * Runs the Rational matrix demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== Rational Matrix Operations Demonstration ===\n\n");
        
        // Create Rational matrix
        RationalMatrix rationalMatrix = new RationalMatrix();
        
        // Create test matrices with rational numbers
        Rational[][] m1 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3), new Rational(1, 4)},
            {new Rational(2, 3), new Rational(3, 4), new Rational(4, 5)},
            {new Rational(1, 1), new Rational(1, 1), new Rational(1, 1)}
        };
        
        Rational[][] m2 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 2), new Rational(1, 2)},
            {new Rational(1, 3), new Rational(1, 3), new Rational(1, 3)},
            {new Rational(0, 1), new Rational(0, 1), new Rational(0, 1)}
        };
        
        output.append("Matrix 1 (Rational):\n");
        printMatrix(m1, output);
        output.append("\nMatrix 2 (Rational):\n");
        printMatrix(m2, output);
        
        // Demonstrate matrix addition
        output.append("\n=== Matrix Addition ===\n");
        try {
            Rational[][] result = rationalMatrix.addMatrix(m1, m2);
            output.append("Result of Matrix 1 + Matrix 2:\n");
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error in matrix addition: ").append(e.getMessage()).append("\n");
        }
        
        // Create matrices for multiplication
        Rational[][] m3 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3)},
            {new Rational(1, 3), new Rational(1, 4)},
            {new Rational(1, 4), new Rational(1, 5)}
        };
        
        Rational[][] m4 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3), new Rational(1, 4)},
            {new Rational(1, 3), new Rational(1, 4), new Rational(1, 5)}
        };
        
        output.append("\nMatrix 3 (3x2 Rational):\n");
        printMatrix(m3, output);
        output.append("\nMatrix 4 (2x3 Rational):\n");
        printMatrix(m4, output);
        
        // Demonstrate matrix multiplication
        output.append("\n=== Matrix Multiplication ===\n");
        try {
            Rational[][] result = rationalMatrix.multiplyMatrix(m3, m4);
            output.append("Result of Matrix 3 * Matrix 4:\n");
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error in matrix multiplication: ").append(e.getMessage()).append("\n");
        }
        
        // Demonstrate with mixed rational numbers
        output.append("\n=== Mixed Rational Numbers ===\n");
        Rational[][] m5 = new Rational[][] {
            {new Rational(2, 1), new Rational(3, 2)},  // 2, 3/2
            {new Rational(4, 3), new Rational(5, 4)}   // 4/3, 5/4
        };
        
        Rational[][] m6 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3)},  // 1/2, 1/3
            {new Rational(2, 3), new Rational(3, 4)}   // 2/3, 3/4
        };
        
        output.append("Matrix 5:\n");
        printMatrix(m5, output);
        output.append("\nMatrix 6:\n");
        printMatrix(m6, output);
        
        output.append("\nMatrix 5 + Matrix 6:\n");
        try {
            Rational[][] result = rationalMatrix.addMatrix(m5, m6);
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage()).append("\n");
        }
        
        // Demonstrate precision benefits
        output.append("\n=== Precision Benefits ===\n");
        output.append("Rational numbers maintain exact precision:\n");
        output.append("1/3 + 1/3 + 1/3 = 1 (exact)\n");
        output.append("0.333... + 0.333... + 0.333... = 0.999... (approximate)\n\n");
        
        Rational oneThird = new Rational(1, 3);
        Rational sum = oneThird.add(oneThird).add(oneThird);
        output.append("Demonstration: 1/3 + 1/3 + 1/3 = ").append(sum).append("\n");
        
        // Demonstrate error handling
        output.append("\n=== Error Handling ===\n");
        Rational[][] incompatible1 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3)},
            {new Rational(1, 4), new Rational(1, 5)}
        };
        
        Rational[][] incompatible2 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3), new Rational(1, 4)},
            {new Rational(1, 5), new Rational(1, 6), new Rational(1, 7)},
            {new Rational(1, 8), new Rational(1, 9), new Rational(1, 10)}
        };
        
        output.append("Attempting to add incompatible matrices:\n");
        try {
            Rational[][] result = rationalMatrix.addMatrix(incompatible1, incompatible2);
            printMatrix(result, output);
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage()).append("\n");
        }
        
        output.append("\n=== Key Benefits of Generic Matrix with Rational ===\n");
        output.append("1. Exact Precision: No floating-point rounding errors\n");
        output.append("2. Type Safety: Compile-time checking for matrix operations\n");
        output.append("3. Code Reuse: Same matrix logic works with different numeric types\n");
        output.append("4. Extensibility: Easy to add new numeric types\n");
        output.append("5. Mathematical Accuracy: Perfect for symbolic computations\n");
        
        outputArea.setText(output.toString());
    }
    
    /**
     * Prints a matrix to the output StringBuilder.
     * @param matrix the matrix to print
     * @param output the StringBuilder to append to
     */
    private static void printMatrix(Rational[][] matrix, StringBuilder output) {
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