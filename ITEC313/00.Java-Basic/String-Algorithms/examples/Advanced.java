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
// Demonstrates palindrome checking and efficient string concatenation in Java
// For ITEC313 - Object-Oriented Programming

public class Advanced {
    // Check if a string is a palindrome
    public static boolean isPalindrome(String s) {
        // Remove non-alphanumeric and convert to lowercase (Java)
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
        // Python equivalent:
        // cleaned = re.sub(r'[^a-zA-Z0-9]', '', s).lower()
        // cleaned == cleaned[::-1]
    }

    public static void main(String[] args) {
        String test = "A man, a plan, a canal: Panama";
        System.out.println("Is palindrome: " + isPalindrome(test)); // true

        // Efficient string concatenation
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i).append(", ");
        }
        String result = sb.toString();
        System.out.println("Concatenated: " + result);
        // Python equivalent:
        // result = ''
        // for i in range(5):
        //     result += str(i) + ', '
        // (But in Python, ''.join() is more efficient for many strings)
    }
} 