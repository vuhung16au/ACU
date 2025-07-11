// UnitTestingBasic.java
// Demonstrates manual code verification in Java (no JUnit or test framework)
// For students transitioning from Python to Java
//
// Key points:
// - Use of assertions (assert keyword)
// - Manual output checking
// - Error handling with try-catch
// - Detailed comments and Python comparisons

public class UnitTestingBasic {
    public static void main(String[] args) {
        System.out.println("=== Unit Testing Basics in Java (No Framework) ===\n");
        demonstrateAssertions();
        demonstrateErrorHandling();
        demonstrateManualCheck();
    }

    // Demonstrates Java's assert keyword
    // Note: Assertions are disabled by default. Run with: java -ea UnitTestingBasic
    // Python: assert is always enabled
    static void demonstrateAssertions() {
        System.out.println("-- Assertions --");
        int expected = 10;
        int actual = 5 + 5;
        assert actual == expected : "Sum should be 10"; // Will throw AssertionError if false
        System.out.println("Assertion passed: 5 + 5 == 10\n");
    }

    // Demonstrates error handling in Java
    // Python: uses try-except
    static void demonstrateErrorHandling() {
        System.out.println("-- Error Handling --");
        try {
            int result = divide(10, 0);
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        System.out.println();
    }

    // Helper method for division
    static int divide(int a, int b) {
        // In Python, ZeroDivisionError is raised
        return a / b;
    }

    // Demonstrates manual output checking (common in educational code)
    static void demonstrateManualCheck() {
        System.out.println("-- Manual Output Check --");
        int[] numbers = {1, 2, 3, 4, 5};
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        System.out.println("Sum of array: " + sum + " (Expected: 15)");
        // In Python, you might use print() and visually check output
        System.out.println();
    }
} 