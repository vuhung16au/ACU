// Example2.java
// Demonstrates error handling and assertions in Java
// For students familiar with Python

public class Example2 {
    public static void main(String[] args) {
        System.out.println("=== Example 2: Error Handling and Assertions ===\n");
        testDivision();
    }

    // Test division with error handling
    static void testDivision() {
        try {
            int result = divide(10, 2);
            System.out.println("10 / 2 = " + result + " (Expected: 5)");
            // In Python: print(10 // 2)
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Now test division by zero
        try {
            int result = divide(10, 0);
            System.out.println("10 / 0 = " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception (as expected): " + e.getMessage());
        }
        // Assertion example
        int expected = 5;
        int actual = divide(10, 2);
        assert actual == expected : "Division result should be 5";
        System.out.println("Assertion passed for 10 / 2 == 5\n");
    }

    static int divide(int a, int b) {
        return a / b;
    }
} 