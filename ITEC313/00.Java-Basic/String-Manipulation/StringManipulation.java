// StringManipulation.java
// Demonstrates string manipulation in Java for Python developers
// Course: ITEC313 - Object-Oriented Programming
// Author: ACU
//
// This file is educational and includes detailed comments and Python comparisons.

public class StringManipulation {
    // Entry point of the program
    public static void main(String[] args) {
        // 1. String creation and immutability
        String s1 = "Hello, World!";
        System.out.println("Original string: " + s1);
        // In Java, strings are immutable (like Python)
        // s1.toUpperCase() returns a new string, does not modify s1
        String s2 = s1.toUpperCase();
        System.out.println("Uppercase: " + s2);
        System.out.println("s1 after toUpperCase: " + s1); // s1 unchanged

        // 2. String concatenation
        String s3 = s1 + " Welcome to Java.";
        System.out.println("Concatenated: " + s3);
        // In Python: s3 = s1 + " Welcome to Java."

        // 3. String length
        System.out.println("Length of s1: " + s1.length());
        // In Python: len(s1)

        // 4. Substring
        String sub = s1.substring(7, 12);
        System.out.println("Substring (7,12): " + sub);
        // In Python: s1[7:12]

        // 5. Character access
        char c = s1.charAt(1);
        System.out.println("Character at index 1: " + c);
        // In Python: s1[1]

        // 6. String comparison
        String s4 = "hello, world!";
        System.out.println("s1 equals s4: " + s1.equals(s4));
        System.out.println("s1 equalsIgnoreCase s4: " + s1.equalsIgnoreCase(s4));
        // In Python: s1 == s4, s1.lower() == s4.lower()

        // 7. Splitting strings
        String csv = "apple,banana,cherry";
        String[] fruits = csv.split(",");
        System.out.println("Split CSV:");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }
        // In Python: csv.split(',')

        // 8. Trimming whitespace
        String padded = "   padded string   ";
        System.out.println("Trimmed: '" + padded.trim() + "'");
        // In Python: padded.strip()

        // 9. StringBuilder for efficient concatenation
        // In Java, use StringBuilder for many concatenations (unlike Python, where str is mutable)
        StringBuilder sb = new StringBuilder();
        sb.append("Java ");
        sb.append("String ");
        sb.append("Builder");
        System.out.println("StringBuilder result: " + sb.toString());
        // In Python: result = ""; result += "Java "; ...

        // 10. Error handling: Null strings
        String nullStr = null;
        try {
            System.out.println(nullStr.length());
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: String is null");
        }
        // In Python: AttributeError if NoneType has no attribute
    }
} 