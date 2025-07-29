/**
 * VariableScope.java
 * 
 * This program demonstrates variable scope in Java:
 * - Local variables
 * - Instance variables
 * - Class variables
 * - Scope rules and visibility
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// VariableScope.java
// Demonstrates local, instance, and class (static) variable scope in Java
// For students transitioning from Python to Java

public class VariableScope {
    // Instance variable: Each object has its own copy
    // In Python: self.instance_var
    private int instanceVar = 10;

    // Class (static) variable: Shared among all instances
    // In Python: class_var at the class level
    private static int staticVar = 20;

    // Main method: Entry point
    public static void main(String[] args) {
        // Local variable: Only accessible within this method
        // In Python: local_var inside a function
        int localVar = 5;
        System.out.println("Local variable (main): " + localVar);

        // Create an object to access instance variables
        VariableScope obj = new VariableScope();
        obj.showInstanceScope();
        showStaticScope();
    }

    // Instance method: Can access instance and static variables
    public void showInstanceScope() {
        int localVar = 100; // Local to this method
        System.out.println("Instance variable: " + instanceVar);
        System.out.println("Local variable (showInstanceScope): " + localVar);
        System.out.println("Static variable (from instance method): " + staticVar);
    }

    // Static method: Can only access static variables directly
    public static void showStaticScope() {
        // int x = instanceVar; // Error: Cannot access instanceVar from static context
        System.out.println("Static variable (from static method): " + staticVar);
    }
} 