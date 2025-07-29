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
package Recursion.examples;
// Example1.java
// Simple recursion: sum of numbers from 1 to n
// Python equivalent: def sum_to_n(n): return 0 if n == 0 else n + sum_to_n(n-1)

public class Example1 {
    /**
     * Recursively sums numbers from 1 to n.
     * @param n Positive integer
     * @return Sum from 1 to n
     */
    public static int sumToN(int n) {
        if (n <= 0) return 0; // base case
        return n + sumToN(n - 1); // recursive case
    }

    public static void main(String[] args) {
        System.out.println("Sum 1..10 = " + sumToN(10)); // 55
    }
} 