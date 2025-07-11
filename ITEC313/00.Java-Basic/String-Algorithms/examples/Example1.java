// Example1.java
// Demonstrates basic string reversal and comparison in Java
// For ITEC313 - Object-Oriented Programming

public class Example1 {
    public static void main(String[] args) {
        // String reversal using StringBuilder (Java)
        String original = "hello";
        String reversed = new StringBuilder(original).reverse().toString();
        System.out.println("Reversed: " + reversed); // Output: olleh

        // Python equivalent:
        // reversed = original[::-1]

        // String comparison in Java
        String a = "test";
        String b = "test";
        System.out.println("a == b: " + (a == b)); // true (but not always reliable)
        System.out.println("a.equals(b): " + a.equals(b)); // true (correct way)

        // Python equivalent:
        // a == b  # True
    }
} 