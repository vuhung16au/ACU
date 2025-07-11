import java.util.regex.*;

/**
 * RegularExpressions.java
 * Demonstrates the basics of regular expressions in Java.
 * For graduate students transitioning from Python.
 *
 * Key differences from Python:
 * - Java uses Pattern and Matcher classes
 * - Regex syntax is similar, but API is more verbose
 */
public class RegularExpressions {
    public static void main(String[] args) {
        // Example 1: Simple pattern match
        String ssn = "123-45-6789";
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{4}"); // SSN format
        Matcher matcher = pattern.matcher(ssn);
        System.out.println("SSN matches pattern: " + matcher.matches()); // true
        // Python equivalent:
        // import re; print(bool(re.match(r"\\d{3}-\\d{2}-\\d{4}", "123-45-6789")))

        // Example 2: Find all words starting with 'J'
        String text = "Java and JavaScript are different.";
        pattern = Pattern.compile("\\bJ\\w+");
        matcher = pattern.matcher(text);
        System.out.print("Words starting with 'J': ");
        while (matcher.find()) {
            System.out.print(matcher.group() + " ");
        }
        System.out.println();
        // Python equivalent:
        // print(re.findall(r"\\bJ\\w+", "Java and JavaScript are different."))

        // Example 3: Validate email address
        String email = "student@university.edu";
        pattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        matcher = pattern.matcher(email);
        System.out.println("Email valid: " + matcher.matches());
        // Python equivalent:
        // print(bool(re.match(r"^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", "student@university.edu")))
    }
} 