/**
 * Example1.java - Basic Multi-Dimensional Array Operations
 * 
 * This example demonstrates fundamental operations with 2D arrays:
 * - Creating and initializing arrays
 * - Accessing and modifying elements
 * - Basic traversal patterns
 * - Array bounds and validation
 */

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Basic Multi-Dimensional Array Operations ===\n");
        
        // 1. Creating and Initializing Arrays
        System.out.println("1. Creating and Initializing Arrays:");
        System.out.println("=====================================");
        
        // Method 1: Declare and initialize in one step
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        // Method 2: Declare first, then initialize
        int[][] matrix2 = new int[3][3];
        matrix2[0][0] = 1; matrix2[0][1] = 2; matrix2[0][2] = 3;
        matrix2[1][0] = 4; matrix2[1][1] = 5; matrix2[1][2] = 6;
        matrix2[2][0] = 7; matrix2[2][1] = 8; matrix2[2][2] = 9;
        
        // Method 3: Using nested loops
        int[][] matrix3 = new int[3][3];
        int value = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix3[i][j] = value++;
            }
        }
        
        System.out.println("Matrix 1 (direct initialization):");
        printMatrix(matrix1);
        
        System.out.println("Matrix 2 (element by element):");
        printMatrix(matrix2);
        
        System.out.println("Matrix 3 (nested loops):");
        printMatrix(matrix3);
        
        // 2. Accessing Elements
        System.out.println("2. Accessing Elements:");
        System.out.println("=======================");
        
        System.out.println("Element at matrix1[1][1]: " + matrix1[1][1]);
        System.out.println("Element at matrix1[0][2]: " + matrix1[0][2]);
        System.out.println("Element at matrix1[2][0]: " + matrix1[2][0]);
        
        // 3. Array Properties
        System.out.println("\n3. Array Properties:");
        System.out.println("====================");
        
        System.out.println("Number of rows: " + matrix1.length);
        System.out.println("Number of columns in first row: " + matrix1[0].length);
        System.out.println("Total elements: " + (matrix1.length * matrix1[0].length));
        
        // 4. Traversal Patterns
        System.out.println("\n4. Traversal Patterns:");
        System.out.println("======================");
        
        System.out.println("Row-wise traversal:");
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                System.out.print(matrix1[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nColumn-wise traversal:");
        for (int j = 0; j < matrix1[0].length; j++) {
            for (int i = 0; i < matrix1.length; i++) {
                System.out.print(matrix1[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nDiagonal traversal (main diagonal):");
        for (int i = 0; i < matrix1.length; i++) {
            System.out.print(matrix1[i][i] + " ");
        }
        System.out.println();
        
        // 5. Modifying Elements
        System.out.println("\n5. Modifying Elements:");
        System.out.println("======================");
        
        System.out.println("Original matrix:");
        printMatrix(matrix1);
        
        // Modify some elements
        matrix1[0][0] = 10;
        matrix1[1][1] = 20;
        matrix1[2][2] = 30;
        
        System.out.println("After modification:");
        printMatrix(matrix1);
        
        // 6. Creating Different Sized Arrays
        System.out.println("6. Different Sized Arrays:");
        System.out.println("==========================");
        
        // Rectangular array
        int[][] rectangle = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        
        System.out.println("Rectangular array (3x4):");
        printMatrix(rectangle);
        
        // Jagged array (different row lengths)
        int[][] jagged = {
            {1, 2, 3},
            {4, 5},
            {6, 7, 8, 9}
        };
        
        System.out.println("Jagged array:");
        printJaggedArray(jagged);
        
        // 7. Array Bounds and Safety
        System.out.println("7. Array Bounds and Safety:");
        System.out.println("============================");
        
        try {
            System.out.println("Accessing valid element: " + matrix1[1][1]);
            System.out.println("Accessing invalid element: " + matrix1[5][5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        
        // Safe access with bounds checking
        System.out.println("\nSafe access with bounds checking:");
        int row = 1, col = 1;
        if (row >= 0 && row < matrix1.length && 
            col >= 0 && col < matrix1[row].length) {
            System.out.println("Safe access: " + matrix1[row][col]);
        } else {
            System.out.println("Invalid indices: [" + row + "][" + col + "]");
        }
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
    
    /**
     * Print a jagged array in a formatted way
     */
    public static void printJaggedArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("Row " + i + ": [ ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
                if (j < array[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" ]");
        }
        System.out.println();
    }
}
