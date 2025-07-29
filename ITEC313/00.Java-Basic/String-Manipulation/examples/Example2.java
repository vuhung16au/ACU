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
// Demonstrates replacing substrings in Java
// Python comparison included

public class Example2 {
    public static void main(String[] args) {
        String sentence = "I like apples. Apples are tasty.";
        // Replace all occurrences of 'apples' (case-insensitive) with 'bananas'
        String replaced = sentence.replaceAll("(?i)apples", "bananas");
        System.out.println("Original: " + sentence);
        System.out.println("Replaced: " + replaced);
        // In Python: replaced = sentence.replace('apples', 'bananas')
    }
} 