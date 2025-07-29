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
// Method overloading with arrays
// Demonstrates how Java requires explicit overloads for different parameter types
// Python comparison: Python functions can accept any type due to dynamic typing

public class Example2 {
    // Overloaded sum method: sums an array of integers
    public int sum(int[] arr) {
        int total = 0;
        for (int n : arr) {
            total += n;
        }
        return total;
    }

    // Overloaded sum method: sums an array of doubles
    public double sum(double[] arr) {
        double total = 0.0;
        for (double n : arr) {
            total += n;
        }
        return total;
    }

    public static void main(String[] args) {
        Example2 ex = new Example2();
        int[] intArr = {1, 2, 3, 4};
        double[] doubleArr = {1.5, 2.5, 3.5};
        System.out.println("Sum of int array: " + ex.sum(intArr));      // 10
        System.out.println("Sum of double array: " + ex.sum(doubleArr)); // 7.5
        // In Python, you could use a single function: sum(arr)
    }
} 