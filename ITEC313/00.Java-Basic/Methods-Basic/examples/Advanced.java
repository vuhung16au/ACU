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
// Demonstrates advanced method concepts in Java
// For students transitioning from Python to Java

public class Advanced {
    public static void main(String[] args) {
        Advanced adv = new Advanced();
        adv.demo();
    }

    // Demonstrates parameter passing and access modifiers
    public void demo() {
        int x = 10;
        System.out.println("Before call: x = " + x);
        modifyValue(x);
        System.out.println("After call: x = " + x); // x is unchanged (pass by value)

        int[] arr = {1, 2, 3};
        System.out.println("Before call: arr[0] = " + arr[0]);
        modifyArray(arr);
        System.out.println("After call: arr[0] = " + arr[0]); // arr[0] is changed

        // Error handling
        int result = safeDivide(10, 0);
        System.out.println("Safe divide result: " + result);
    }

    // Private method (not accessible outside this class)
    private void modifyValue(int a) {
        a = 100; // Only changes local copy
    }

    // Modifies the first element of the array
    private void modifyArray(int[] array) {
        if (array != null && array.length > 0) {
            array[0] = 99;
        }
    }

    // Error handling with return value
    public int safeDivide(int a, int b) {
        if (b == 0) {
            System.out.println("Error: Division by zero");
            return 0;
        }
        return a / b;
    }

    // Python comparison:
    // - Java passes primitives by value, objects by reference to the object (but the reference itself is passed by value)
    // - Access modifiers (private, public) are explicit in Java; Python uses naming conventions
    // - Error handling in Java often uses exceptions, but here we use return values for simplicity
} 