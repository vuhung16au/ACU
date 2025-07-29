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
import java.util.regex.*;

/**
 * Example1.java
 * Simple regex pattern matching in Java.
 * Shows how to check if a string contains a date in YYYY-MM-DD format.
 *
 * Python comparison:
 * import re; print(bool(re.match(r"\\d{4}-\\d{2}-\\d{2}", "2024-06-01")))
 */
public class Example1 {
    public static void main(String[] args) {
        String date = "2024-06-01";
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(date);
        System.out.println("Date matches YYYY-MM-DD: " + matcher.matches()); // true
    }
} 