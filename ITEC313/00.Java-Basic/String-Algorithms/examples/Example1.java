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
// Example1.java
// Demonstrates basic string reversal and comparison in Java
// For ITEC313 - Object-Oriented Programming

public class Example1 {
    public static void main(String[] args) {
        // String reversal using StringBuilder (Java)
        String original = "hello";
        String reversed = new StringBuilder(original).reverse().toString();
        System.out.println("Reversed: " + reversed); // Output: olleh

        // Python equivalent:
        // reversed = original[::-1]

        // String comparison in Java
        String a = "test";
        String b = "test";
        System.out.println("a == b: " + (a == b)); // true (but not always reliable)
        System.out.println("a.equals(b): " + a.equals(b)); // true (correct way)

        // Python equivalent:
        // a == b  # True
    }
} 