/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced For Loop Examples ===\n");
        
        // Example 1: Nested for loops - multiplication table
        System.out.println("1. Nested For Loops - Multiplication Table:");
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                System.out.printf("   %2d", i * j);
            }
            System.out.println();
        }
        
        // Example 2: Pattern printing with nested loops
        System.out.println("\n2. Pattern Printing:");
        for (int i = 1; i <= 5; i++) {
            // Print spaces
            for (int j = 1; j <= 5 - i; j++) {
                System.out.print("   ");
            }
            // Print stars
            for (int k = 1; k <= i; k++) {
                System.out.print("   *");
            }
            System.out.println();
        }
        
        // Example 3: Break statement in for loop
        System.out.println("\n3. Break Statement Example:");
        System.out.println("   Finding first number divisible by 7:");
        for (int i = 1; i <= 20; i++) {
            if (i % 7 == 0) {
                System.out.println("   Found: " + i + " is divisible by 7");
                break; // Exit the loop immediately
            }
        }
        
        // Example 4: Continue statement in for loop
        System.out.println("\n4. Continue Statement Example:");
        System.out.println("   Skipping multiples of 3:");
        for (int i = 1; i <= 10; i++) {
            if (i % 3 == 0) {
                continue; // Skip this iteration
            }
            System.out.println("   Number: " + i);
        }
        
        // Example 5: Complex nested loop with conditions
        System.out.println("\n5. Complex Nested Loop:");
        System.out.println("   Printing coordinates (excluding diagonal):");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    continue; // Skip diagonal elements
                }
                System.out.printf("   (%d,%d)", i, j);
            }
            System.out.println();
        }
        
        // Example 6: Multi-dimensional array processing
        System.out.println("\n6. Multi-dimensional Array Processing:");
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("   Matrix elements:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("   %d", matrix[i][j]);
            }
            System.out.println();
        }
        
        // Example 7: Sum of diagonal elements
        System.out.println("\n7. Sum of Diagonal Elements:");
        int diagonalSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            diagonalSum += matrix[i][i];
        }
        System.out.println("   Sum of diagonal: " + diagonalSum);
        
        // Example 8: Loop with multiple variables
        System.out.println("\n8. Loop with Multiple Variables:");
        for (int i = 0, j = 10; i < 5; i++, j--) {
            System.out.println("   i = " + i + ", j = " + j);
        }
        
        // Example 9: Infinite loop with break condition
        System.out.println("\n9. Controlled Infinite Loop:");
        int count = 0;
        for (;;) { // Infinite loop
            count++;
            if (count > 5) {
                break; // Exit after 5 iterations
            }
            System.out.println("   Iteration: " + count);
        }
        
        // Example 10: Loop with early exit
        System.out.println("\n10. Early Exit Pattern:");
        int[] numbers = {2, 4, 6, 8, 10, 12, 14, 16};
        boolean found = false;
        for (int num : numbers) {
            if (num > 10) {
                System.out.println("   Found number > 10: " + num);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("   No number > 10 found");
        }
        
        // Example 11: Nested loop with labeled break
        System.out.println("\n11. Labeled Break Example:");
        outerLoop: for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("   Breaking from outer loop at i=" + i + ", j=" + j);
                    break outerLoop; // Break from outer loop
                }
                System.out.printf("   (%d,%d)", i, j);
            }
            System.out.println();
        }
        
        // Example 12: Loop optimization - avoiding repeated calculations
        System.out.println("\n12. Loop Optimization Example:");
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int length = data.length; // Store length to avoid repeated calls
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += data[i];
        }
        System.out.println("   Sum of array: " + sum);
        System.out.println("   Average: " + (double) sum / length);
        
        System.out.println("\n=== Advanced Examples Complete ===");
    }
} 