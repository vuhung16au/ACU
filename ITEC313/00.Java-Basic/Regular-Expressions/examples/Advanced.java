import java.util.regex.*;

/**
 * Advanced.java
 * Demonstrates advanced regex features in Java, such as groups and replacements.
 *
 * Python comparison:
 * import re; print(re.sub(r"(\\d{3})-(\\d{2})-(\\d{4})", r"***-**-\\3", "123-45-6789"))
 */
public class Advanced {
    public static void main(String[] args) {
        // Mask SSN except last 4 digits
        String ssn = "123-45-6789";
        Pattern pattern = Pattern.compile("(\\d{3})-(\\d{2})-(\\d{4})");
        Matcher matcher = pattern.matcher(ssn);
        String masked = matcher.replaceAll("***-**-$3");
        System.out.println("Masked SSN: " + masked); // ***-**-6789

        // Extract domain from email
        String email = "student@university.edu";
        pattern = Pattern.compile("@([\\w.-]+)");
        matcher = pattern.matcher(email);
        if (matcher.find()) {
            System.out.println("Email domain: " + matcher.group(1)); // university.edu
        }
    }
} 