// Example2.java
// Demonstrates logical, comparison, and ternary operators in Java
// For Python programmers: note the use of &&, ||, ! instead of 'and', 'or', 'not'

public class Example2 {
    public static void main(String[] args) {
        int x = 7, y = 10;
        boolean isGreater = x > y; // Comparison
        boolean result = (x < y) && (y > 5); // Logical AND
        String message = isGreater ? "x is greater" : "x is not greater"; // Ternary operator
        System.out.println("isGreater: " + isGreater);
        System.out.println("Logical AND: " + result);
        System.out.println("Ternary result: " + message);
    }
} 