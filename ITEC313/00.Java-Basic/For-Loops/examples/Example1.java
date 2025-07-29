/**
 * Example1.java
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
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic For Loops ===\n");
        
        // Example 1: Basic counting loop
        System.out.println("1. Basic counting (0 to 4):");
        for (int i = 0; i < 5; i++) {
            System.out.println("   Count: " + i);
        }
        
        // Example 2: Counting with step
        System.out.println("\n2. Counting with step (0 to 10, step 2):");
        for (int i = 0; i <= 10; i += 2) {
            System.out.println("   Even number: " + i);
        }
        
        // Example 3: Counting backwards
        System.out.println("\n3. Counting backwards (10 to 1):");
        for (int i = 10; i >= 1; i--) {
            System.out.println("   Countdown: " + i);
        }
        
        // Example 4: Loop with calculation
        System.out.println("\n4. Loop with calculation (squares):");
        for (int i = 1; i <= 5; i++) {
            int square = i * i;
            System.out.println("   " + i + " squared = " + square);
        }
        
        // Example 5: Sum calculation
        System.out.println("\n5. Sum calculation (1 to 5):");
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
            System.out.println("   Adding " + i + ", sum = " + sum);
        }
        System.out.println("   Final sum: " + sum);
        
        // Example 6: Factorial calculation
        System.out.println("\n6. Factorial calculation (5!):");
        int factorial = 1;
        for (int i = 1; i <= 5; i++) {
            factorial *= i;
            System.out.println("   " + i + "! = " + factorial);
        }
        
        System.out.println("\n=== Example 1 Complete ===");
    }
} 