/**
 * MultidimensionalArrays.java
 * 
 * This program demonstrates multidimensional arrays in Java:
 * - 2D and 3D array creation
 * - Array initialization techniques
 * - Nested loop traversal
 * - Array manipulation operations
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// MultidimensionalArrays.java
// Main source file for demonstrating multidimensional arrays in Java
// For students transitioning from Python

/**
 * This class demonstrates:
 * - Declaration and initialization of 2D arrays
 * - Usage of jagged arrays (arrays of arrays with different lengths)
 * - Iteration and manipulation of multidimensional data
 * - Comparisons with Python lists of lists
 */
public class MultidimensionalArrays {
    public static void main(String[] args) {
        // --- 1. 2D Array (Rectangular) ---
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        System.out.println("2D Array (Rectangular):");
        print2DArray(matrix);

        // --- 2. Jagged Array (Rows of different lengths) ---
        int[][] jagged = new int[3][];
        jagged[0] = new int[] {10, 20};
        jagged[1] = new int[] {30, 40, 50};
        jagged[2] = new int[] {60};

        System.out.println("\nJagged Array:");
        print2DArray(jagged);

        // --- 3. Error Handling Example ---
        try {
            int value = matrix[5][0]; // This will throw ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\nError: Tried to access an invalid index in the array.");
        }

        // --- Python Comparison ---
        // In Python, you can use lists of lists:
        // matrix = [[1,2,3],[4,5,6],[7,8,9]]
        // for row in matrix:
        //     for val in row:
        //         print(val, end=' ')
        //     print()
        //
        // Java arrays are fixed-size; Python lists are dynamic.
    }

    /**
     * Prints a 2D array (rectangular or jagged) to the console.
     * @param arr The 2D array to print
     */
    public static void print2DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
} 