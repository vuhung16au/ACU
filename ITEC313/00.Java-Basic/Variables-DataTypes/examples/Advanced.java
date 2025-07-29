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
// Advanced.java
// Demonstrates type conversion and error handling in Java
// Python comparison: Python handles type conversion more flexibly and raises exceptions for invalid conversions.

public class Advanced {
    public static void main(String[] args) {
        String numberStr = "123";
        String invalidStr = "abc";
        try {
            // Convert String to int
            int num = Integer.parseInt(numberStr); // In Python: int(numberStr)
            System.out.println("Converted number: " + num);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number string");
        }

        try {
            // Attempt to convert invalid string
            int invalid = Integer.parseInt(invalidStr); // In Python: int(invalidStr) raises ValueError
        } catch (NumberFormatException e) {
            System.out.println("Error: Cannot convert 'abc' to int");
        }
    }
} 