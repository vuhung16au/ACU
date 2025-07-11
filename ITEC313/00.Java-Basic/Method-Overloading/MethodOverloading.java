// MethodOverloading.java
// Demonstrates method overloading in Java
// Course: ITEC313 - Object-Oriented Programming
// Target audience: Python developers transitioning to Java
//
// Key points:
// - Method overloading: multiple methods with the same name but different parameter lists
// - No user input, no test code
// - Detailed comments and Python comparisons

public class MethodOverloading {
    /**
     * Adds two integers.
     * In Python, you would use dynamic typing and a single function.
     * In Java, you can have multiple methods with the same name but different parameter types.
     */
    public int add(int a, int b) {
        // In Python: def add(a, b): return a + b
        return a + b;
    }

    /**
     * Adds three integers (overloaded method).
     */
    public int add(int a, int b, int c) {
        // In Python: def add(a, b, c): return a + b + c
        return a + b + c;
    }

    /**
     * Adds two double values (overloaded method with different parameter types).
     */
    public double add(double a, double b) {
        // In Python: def add(a, b): return a + b
        return a + b;
    }

    /**
     * Concatenates two strings (overloaded method with different parameter types).
     */
    public String add(String a, String b) {
        // In Python: def add(a, b): return str(a) + str(b)
        return a + b;
    }

    /**
     * Main method to demonstrate method overloading.
     */
    public static void main(String[] args) {
        MethodOverloading mo = new MethodOverloading();

        // Integer addition
        System.out.println("add(int, int): " + mo.add(2, 3)); // 5
        System.out.println("add(int, int, int): " + mo.add(1, 2, 3)); // 6

        // Double addition
        System.out.println("add(double, double): " + mo.add(2.5, 3.7)); // 6.2

        // String concatenation
        System.out.println("add(String, String): " + mo.add("Hello, ", "world!")); // Hello, world!

        // Note: In Python, you would typically use a single function with dynamic typing.
        // In Java, method overloading is resolved at compile time based on parameter types.
    }
} 