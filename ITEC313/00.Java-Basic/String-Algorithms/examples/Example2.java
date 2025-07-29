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
// Demonstrates substring search, replace, split, and join in Java
// For ITEC313 - Object-Oriented Programming

public class Example2 {
    public static void main(String[] args) {
        String text = "Java is fun and powerful";

        // Substring search
        boolean containsFun = text.contains("fun");
        System.out.println("Contains 'fun': " + containsFun); // true

        // Python equivalent:
        // 'fun' in text

        // Replace
        String replaced = text.replace("fun", "awesome");
        System.out.println(replaced); // Java is awesome and powerful

        // Python equivalent:
        // text.replace('fun', 'awesome')

        // Split
        String[] words = text.split(" ");
        for (String word : words) {
            System.out.println(word);
        }
        // Python equivalent:
        // words = text.split(' ')

        // Join (Java 8+)
        String joined = String.join("-", words);
        System.out.println(joined); // Java-is-fun-and-powerful

        // Python equivalent:
        // '-'.join(words)
    }
} 