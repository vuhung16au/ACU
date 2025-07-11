import java.util.regex.*;

/**
 * Example2.java
 * Extracts all numbers from a string using regex in Java.
 *
 * Python comparison:
 * import re; print(re.findall(r"\\d+", "Order 123: 4 apples, 56 bananas"))
 */
public class Example2 {
    public static void main(String[] args) {
        String text = "Order 123: 4 apples, 56 bananas";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        System.out.print("Numbers found: ");
        while (matcher.find()) {
            System.out.print(matcher.group() + " ");
        }
        System.out.println();
    }
} 