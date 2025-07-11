// Example1.java
// Demonstrates basic 2D array usage in Java
// For students transitioning from Python

public class Example1 {
    public static void main(String[] args) {
        // Declare and initialize a 2D array (3 rows, 4 columns)
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };

        // Print the 2D array
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // Python comparison:
        // In Python, you would use a list of lists:
        // matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
        // for row in matrix:
        //     for val in row:
        //         print(val, end=' ')
        //     print()
    }
} 