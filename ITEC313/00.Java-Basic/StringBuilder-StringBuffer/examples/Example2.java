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
// Basic usage of StringBuffer in Java
// Shows thread-safe mutable string operations
//
// Python comparison: No direct equivalent to StringBuffer in Python.

public class Example2 {
    public static void main(String[] args) {
        StringBuffer sbf = new StringBuffer("Safe");
        sbf.append(" & Sound");
        System.out.println(sbf.toString()); // Output: Safe & Sound
    }
} 