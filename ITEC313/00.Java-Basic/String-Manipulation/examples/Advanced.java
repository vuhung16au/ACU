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
// Advanced string manipulation in Java
// Includes regex, joining, splitting, and Python comparisons

import java.util.regex.*;
import java.util.Arrays;

public class Advanced {
    public static void main(String[] args) {
        // 1. Regex: Extract numbers from a string
        String text = "Order 123: 5 apples, 7 bananas, 42 oranges.";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(text);
        System.out.print("Numbers found: ");
        while (m.find()) {
            System.out.print(m.group() + " ");
        }
        System.out.println();
        // In Python: re.findall(r'\\d+', text)

        // 2. Joining strings
        String[] words = {"Java", "is", "fun"};
        String joined = String.join(" ", words);
        System.out.println("Joined: " + joined);
        // In Python: ' '.join(words)

        // 3. Splitting with regex
        String data = "apple;banana|cherry orange";
        String[] items = data.split("[;| ]");
        System.out.println("Split: " + Arrays.toString(items));
        // In Python: re.split(r'[;| ]', data)
    }
} 