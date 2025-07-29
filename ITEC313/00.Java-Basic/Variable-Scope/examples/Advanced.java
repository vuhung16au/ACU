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
// Combines local, instance, and class (static) variable scope in Java
// For students transitioning from Python to Java

class Advanced {
    private int instanceValue = 50; // Instance variable
    private static int staticValue = 100; // Static variable

    public void demonstrateScopes() {
        int localValue = 10; // Local variable
        System.out.println("Local value: " + localValue);
        System.out.println("Instance value: " + instanceValue);
        System.out.println("Static value: " + staticValue);
    }

    public static void main(String[] args) {
        Advanced obj1 = new Advanced();
        Advanced obj2 = new Advanced();
        obj1.demonstrateScopes();
        obj2.instanceValue = 75; // Change instance variable for obj2
        staticValue = 200; // Change static variable for all
        obj2.demonstrateScopes();
        obj1.demonstrateScopes(); // Shows staticValue changed for all
    }
} 