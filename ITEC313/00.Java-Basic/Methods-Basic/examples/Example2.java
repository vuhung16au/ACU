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
// Demonstrates method overloading and static vs. instance methods in Java
// For students transitioning from Python to Java

public class Example2 {
    public static void main(String[] args) {
        // Call overloaded methods
        System.out.println("add(2, 3) = " + add(2, 3)); // Output: 5
        System.out.println("add(2.5, 3.5) = " + add(2.5, 3.5)); // Output: 6.0

        // Call instance method
        Example2 ex = new Example2();
        ex.sayHello();
    }

    // Overloaded static methods
    public static int add(int a, int b) {
        return a + b;
    }
    public static double add(double a, double b) {
        return a + b;
    }

    // Instance method
    public void sayHello() {
        System.out.println("Hello from an instance method!");
    }

    // Python comparison:
    // - Java supports method overloading (same name, different parameters); Python does not
    // - Static methods use 'static' keyword; Python uses @staticmethod
    // - Instance methods require an object to call
} 