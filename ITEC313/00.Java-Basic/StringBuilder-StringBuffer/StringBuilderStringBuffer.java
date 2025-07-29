/**
 * StringBuilderStringBuffer.java
 * 
 * This program demonstrates StringBuilder and StringBuffer in Java:
 * - Mutable string classes
 * - Performance optimization
 * - String building techniques
 * - Thread safety considerations
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// StringBuilderStringBuffer.java
// Demonstrates usage of StringBuilder and StringBuffer in Java
// For graduate students transitioning from Python
//
// Key differences from Python are explained in comments
//
// Author: XYZ ITEC313
// Date: July 2025

public class StringBuilderStringBuffer {
    /**
     * Entry point of the program.
     * Demonstrates basic and advanced usage of StringBuilder and StringBuffer.
     */
    public static void main(String[] args) {
        // --- StringBuilder Example ---
        System.out.println("== StringBuilder Example ==");
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(", World");
        sb.insert(5, " Java");
        sb.replace(0, 5, "Hi");
        sb.delete(3, 8);
        System.out.println("StringBuilder result: " + sb.toString());
        // Python equivalent: s = "Hello"; s += ", World"; # Strings are immutable in Python

        // --- StringBuffer Example ---
        System.out.println("\n== StringBuffer Example ==");
        StringBuffer sbf = new StringBuffer("Thread-safe");
        sbf.append(" StringBuffer");
        sbf.reverse();
        System.out.println("StringBuffer result: " + sbf.toString());
        // Python has no direct equivalent to StringBuffer (thread-safe mutable string)

        // --- Performance Comparison ---
        System.out.println("\n== Performance Comparison ==");
        int n = 10000;
        long start, end;

        // String concatenation (inefficient)
        start = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < n; i++) {
            s += i;
        }
        end = System.currentTimeMillis();
        System.out.println("String concat time: " + (end - start) + " ms");

        // StringBuilder (efficient)
        start = System.currentTimeMillis();
        StringBuilder sbPerf = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sbPerf.append(i);
        }
        end = System.currentTimeMillis();
        System.out.println("StringBuilder time: " + (end - start) + " ms");

        // StringBuffer (thread-safe, slightly slower)
        start = System.currentTimeMillis();
        StringBuffer sbfPerf = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sbfPerf.append(i);
        }
        end = System.currentTimeMillis();
        System.out.println("StringBuffer time: " + (end - start) + " ms");

        // Error handling example
        try {
            StringBuilder errorSb = new StringBuilder(null); // Will throw NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: Cannot initialize StringBuilder with null");
        }
    }
} 