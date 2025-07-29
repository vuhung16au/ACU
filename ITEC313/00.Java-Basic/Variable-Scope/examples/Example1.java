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
// Demonstrates local and instance variable scope in Java
// For students transitioning from Python to Java

class Example1 {
    // Instance variable
    private int count = 0;

    public void increment() {
        // Local variable
        int step = 1; // Only accessible within this method
        count += step;
        System.out.println("Count (instance variable): " + count);
        System.out.println("Step (local variable): " + step);
    }

    public static void main(String[] args) {
        Example1 ex = new Example1();
        ex.increment();
        ex.increment();
    }
} 