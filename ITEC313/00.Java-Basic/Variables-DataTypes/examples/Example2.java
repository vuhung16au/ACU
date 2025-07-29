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
// Demonstrates wrapper classes and autoboxing/unboxing in Java
// Python comparison: Python does not distinguish between primitive and wrapper types.

public class Example2 {
    public static void main(String[] args) {
        // Wrapper class for int
        Integer num = 10; // Autoboxing: primitive int to Integer object
        // Wrapper class for double
        Double pi = 3.14159; // Autoboxing
        // Unboxing: Integer to int
        int n = num;
        // Unboxing: Double to double
        double p = pi;

        System.out.println("Integer object: " + num);
        System.out.println("Double object: " + pi);
        System.out.println("Unboxed int: " + n);
        System.out.println("Unboxed double: " + p);
    }
} 