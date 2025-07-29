/**
 * Example2.java
 * 
 * This program demonstrates example in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// Example2.java
// Demonstrates jagged array (array of arrays with different lengths) in Java
// For students transitioning from Python

public class Example2 {
    public static void main(String[] args) {
        // Declare a jagged array (3 rows, different column sizes)
        int[][] jagged = new int[3][];
        jagged[0] = new int[] {1, 2};
        jagged[1] = new int[] {3, 4, 5, 6};
        jagged[2] = new int[] {7, 8, 9};

        // Print the jagged array
        for (int i = 0; i < jagged.length; i++) {
            for (int j = 0; j < jagged[i].length; j++) {
                System.out.print(jagged[i][j] + " ");
            }
            System.out.println();
        }

        // Python comparison:
        // In Python, you can have lists of different lengths:
        // jagged = [[1,2],[3,4,5,6],[7,8,9]]
        // for row in jagged:
        //     for val in row:
        //         print(val, end=' ')
        //     print()
    }
} 