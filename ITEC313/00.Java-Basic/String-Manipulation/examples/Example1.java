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
// Simple string reversal in Java
// Demonstrates basic string manipulation
// Python comparison included

public class Example1 {
    public static void main(String[] args) {
        String original = "Java";
        String reversed = "";
        // Reverse string using a loop (no built-in reverse for String)
        for (int i = original.length() - 1; i >= 0; i--) {
            reversed += original.charAt(i); // Inefficient for many concatenations
        }
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);
        // In Python: reversed = original[::-1]
    }
} 