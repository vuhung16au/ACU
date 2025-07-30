package com.acu.javafx.fibonacci;

/**
 * ImprovedFibonacci - Iterative implementation
 * Demonstrates an efficient iterative approach to computing Fibonacci numbers
 * 
 * This implementation uses an iterative approach with O(n) time complexity,
 * which is much more efficient than the recursive approach for large values.
 * 
 * The algorithm uses three variables to keep track of the previous two
 * Fibonacci numbers and computes the next one iteratively.
 */
public class ImprovedFibonacci {
    
    /**
     * The method for finding the Fibonacci number using iteration
     * 
     * @param n the index of the Fibonacci number to compute
     * @return the Fibonacci number at the given index
     */
    public static long fib(long n) {   
        long f0 = 0; // For fib(0)
        long f1 = 1; // For fib(1)
        long f2 = 1; // For fib(2)
        
        if (n == 0) 
            return f0;
        else if (n == 1) 
            return f1;
        else if (n == 2) 
            return f2;

        for (int i = 3; i <= n; i++) {
            f0 = f1;
            f1 = f2;
            f2 = f0 + f1;
        }
        
        return f2;
    }
    
    /**
     * Main method for testing the iterative Fibonacci implementation
     * This method is kept for compatibility with the original example
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a Scanner
        java.util.Scanner input = new java.util.Scanner(System.in);
        System.out.print("Enter an index for the Fibonacci number: ");
        int index = input.nextInt();
        
        // Find and display the Fibonacci number
        System.out.println(
            "Fibonacci number at index " + index + " is " + fib(index));
    }
} 