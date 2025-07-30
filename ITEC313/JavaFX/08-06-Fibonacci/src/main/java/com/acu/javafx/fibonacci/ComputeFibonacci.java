package com.acu.javafx.fibonacci;

/**
 * ComputeFibonacci - Original recursive implementation
 * Demonstrates the basic recursive approach to computing Fibonacci numbers
 * 
 * This implementation uses the classic recursive definition:
 * fib(n) = fib(n-1) + fib(n-2) for n > 1
 * fib(0) = 0, fib(1) = 1
 * 
 * Note: This approach has exponential time complexity O(2^n) and is not efficient
 * for large values of n.
 */
public class ComputeFibonacci {
    
    /**
     * The method for finding the Fibonacci number using recursion
     * 
     * @param index the index of the Fibonacci number to compute
     * @return the Fibonacci number at the given index
     */
    public static long fib(long index) {
        if (index == 0) // Base case
            return 0;
        else if (index == 1) // Base case
            return 1;
        else  // Reduction and recursive calls
            return fib(index - 1) + fib(index - 2);
    }
    
    /**
     * Main method for testing the recursive Fibonacci implementation
     * This method is kept for compatibility with the original example
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a Scanner
        java.util.Scanner input = new java.util.Scanner(System.in);
        System.out.print("Enter an index for a Fibonacci number: ");
        int index = input.nextInt();

        // Find and display the Fibonacci number
        System.out.println("The Fibonacci number at index "  
            + index + " is " + fib(index));
    }
} 