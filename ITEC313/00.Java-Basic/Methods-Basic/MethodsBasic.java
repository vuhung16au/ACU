// MethodsBasic.java
// Demonstrates method creation, parameters, return types, and best practices in Java
// For students transitioning from Python to Java

public class MethodsBasic {
    // Entry point of the program
    public static void main(String[] args) {
        // Call static method
        int sum = add(5, 7);
        System.out.println("Sum: " + sum); // Output: Sum: 12

        // Call overloaded method
        double sumDouble = add(3.5, 2.5);
        System.out.println("Sum (double): " + sumDouble); // Output: Sum (double): 6.0

        // Call instance method
        MethodsBasic mb = new MethodsBasic();
        mb.printGreeting();

        // Call method with error handling
        int quotient = safeDivide(10, 0);
        System.out.println("Quotient: " + quotient); // Output: Quotient: 0 (error handled)
    }

    // Simple static method with parameters and return type
    // Python equivalent: def add(a, b): return a + b
    public static int add(int a, int b) {
        return a + b;
    }

    // Overloaded static method (different parameter types)
    public static double add(double a, double b) {
        return a + b;
    }

    // Instance method (non-static)
    public void printGreeting() {
        System.out.println("Hello from an instance method!");
    }

    // Method with basic error handling (no exceptions thrown)
    public static int safeDivide(int numerator, int denominator) {
        if (denominator == 0) {
            System.out.println("Error: Division by zero. Returning 0.");
            return 0;
        }
        return numerator / denominator;
    }

    // Method with no return value (void)
    public static void printMessage(String message) {
        System.out.println(message);
    }

    // Python comparison:
    // - All methods must be inside a class in Java
    // - Java requires explicit types for parameters and return values
    // - No default arguments; use overloading instead
    // - Use 'void' for methods that do not return a value
} 