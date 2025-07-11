package Recursion;
// Recursion.java
// Demonstrates recursion in Java for educational purposes
// For Python developers: Java requires explicit types and has stricter recursion limits

public class Recursion {
    /**
     * Calculates factorial of n recursively.
     * @param n Non-negative integer
     * @return n!
     * Python equivalent: def factorial(n): return 1 if n == 0 else n * factorial(n-1)
     */
    public static int factorial(int n) {
        if (n < 0) {
            // Error handling: negative input
            throw new IllegalArgumentException("n must be non-negative");
        }
        if (n == 0) return 1; // base case
        return n * factorial(n - 1); // recursive case
    }

    /**
     * Calculates nth Fibonacci number recursively (inefficient version).
     * @param n Index (0-based)
     * @return nth Fibonacci number
     * Python equivalent: def fib(n): return n if n <= 1 else fib(n-1) + fib(n-2)
     */
    public static int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Advanced: Recursively print all files in a directory (simulated with array)
     * In Java, recursion is often used for tree/graph traversal.
     * Python equivalent: os.walk or recursive function
     */
    public static void printArrayRecursively(String[] arr, int index) {
        if (index >= arr.length) return; // base case
        System.out.println(arr[index]);
        printArrayRecursively(arr, index + 1); // recursive case
    }

    public static void main(String[] args) {
        // Factorial example
        System.out.println("5! = " + factorial(5)); // 120

        // Fibonacci example
        System.out.println("Fibonacci(6) = " + fibonacci(6)); // 8

        // Advanced: Print array recursively
        String[] files = {"file1.txt", "file2.txt", "file3.txt"};
        System.out.println("Files in directory:");
        printArrayRecursively(files, 0);
    }
} 