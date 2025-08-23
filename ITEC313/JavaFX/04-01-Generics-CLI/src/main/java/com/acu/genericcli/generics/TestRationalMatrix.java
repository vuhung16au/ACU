package com.acu.genericcli.generics;

/**
 * Test class for Rational matrix operations.
 * Demonstrates how the generic matrix can be used with Rational type.
 */
public class TestRationalMatrix {
    
    /**
     * Runs the Rational matrix demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== Rational Matrix Operations Demonstration ===\n");
        
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
        
        System.out.println("Matrix 1 (Rational):");
        printMatrix(m1);
        System.out.println("\nMatrix 2 (Rational):");
        printMatrix(m2);
        
        // Demonstrate matrix addition
        System.out.println("\n=== Matrix Addition ===");
        try {
            Rational[][] result = rationalMatrix.addMatrix(m1, m2);
            System.out.println("Result of Matrix 1 + Matrix 2:");
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error in matrix addition: " + e.getMessage());
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
        
        System.out.println("\nMatrix 3 (3x2 Rational):");
        printMatrix(m3);
        System.out.println("\nMatrix 4 (2x3 Rational):");
        printMatrix(m4);
        
        // Demonstrate matrix multiplication
        System.out.println("\n=== Matrix Multiplication ===");
        try {
            Rational[][] result = rationalMatrix.multiplyMatrix(m3, m4);
            System.out.println("Result of Matrix 3 * Matrix 4:");
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error in matrix multiplication: " + e.getMessage());
        }
        
        // Demonstrate with mixed rational numbers
        System.out.println("\n=== Mixed Rational Numbers ===");
        Rational[][] m5 = new Rational[][] {
            {new Rational(2, 1), new Rational(3, 2)},  // 2, 3/2
            {new Rational(4, 3), new Rational(5, 4)}   // 4/3, 5/4
        };
        
        Rational[][] m6 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3)},  // 1/2, 1/3
            {new Rational(2, 3), new Rational(3, 4)}   // 2/3, 3/4
        };
        
        System.out.println("Matrix 5:");
        printMatrix(m5);
        System.out.println("\nMatrix 6:");
        printMatrix(m6);
        
        System.out.println("\nMatrix 5 + Matrix 6:");
        try {
            Rational[][] result = rationalMatrix.addMatrix(m5, m6);
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Demonstrate precision benefits
        System.out.println("\n=== Precision Benefits ===");
        System.out.println("Rational numbers maintain exact precision:");
        System.out.println("1/3 + 1/3 + 1/3 = 1 (exact)");
        System.out.println("0.333... + 0.333... + 0.333... = 0.999... (approximate)\n");
        
        Rational oneThird = new Rational(1, 3);
        Rational sum = oneThird.add(oneThird).add(oneThird);
        System.out.println("Demonstration: 1/3 + 1/3 + 1/3 = " + sum);
        
        // Demonstrate error handling
        System.out.println("\n=== Error Handling ===");
        Rational[][] incompatible1 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3)},
            {new Rational(1, 4), new Rational(1, 5)}
        };
        
        Rational[][] incompatible2 = new Rational[][] {
            {new Rational(1, 2), new Rational(1, 3), new Rational(1, 4)},
            {new Rational(1, 5), new Rational(1, 6), new Rational(1, 7)},
            {new Rational(1, 8), new Rational(1, 9), new Rational(1, 10)}
        };
        
        System.out.println("Attempting to add incompatible matrices:");
        try {
            Rational[][] result = rationalMatrix.addMatrix(incompatible1, incompatible2);
            printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Key Benefits of Generic Matrix with Rational ===");
        System.out.println("1. Exact Precision: No floating-point rounding errors");
        System.out.println("2. Type Safety: Compile-time checking for matrix operations");
        System.out.println("3. Code Reuse: Same matrix logic works with different numeric types");
        System.out.println("4. Extensibility: Easy to add new numeric types");
        System.out.println("5. Mathematical Accuracy: Perfect for symbolic computations");
    }
    
    /**
     * Prints a matrix to the console.
     * @param matrix the matrix to print
     */
    private static void printMatrix(Rational[][] matrix) {
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