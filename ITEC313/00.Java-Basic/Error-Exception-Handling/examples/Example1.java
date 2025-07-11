/**
 * Example1.java - Basic Exception Handling
 * 
 * This example demonstrates fundamental exception handling concepts:
 * - Arithmetic exceptions (division by zero)
 * - Array index exceptions
 * - Basic try-catch structure
 * 
 * Key differences from Python:
 * - Java requires explicit exception handling for checked exceptions
 * - Python's try/except becomes Java's try/catch
 * - Java has more specific exception types
 */

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Exception Handling ===\n");
        
        // Example 1: Arithmetic Exception
        demonstrateArithmeticException();
        
        // Example 2: Array Index Exception
        demonstrateArrayIndexException();
        
        // Example 3: Multiple Exception Types
        demonstrateMultipleExceptions();
        
        System.out.println("=== Example 1 Complete ===\n");
    }
    
    /**
     * Demonstrates handling of arithmetic exceptions
     * In Python: try/except ZeroDivisionError, in Java: try/catch ArithmeticException
     */
    public static void demonstrateArithmeticException() {
        System.out.println("1. Arithmetic Exception Handling:");
        
        int[] numbers = {10, 5, 0, 2};
        int[] divisors = {2, 0, 5, 0};
        
        for (int i = 0; i < numbers.length; i++) {
            try {
                int result = numbers[i] / divisors[i];
                System.out.println(numbers[i] + " / " + divisors[i] + " = " + result);
            } catch (ArithmeticException e) {
                System.out.println("Cannot divide " + numbers[i] + " by " + divisors[i] + 
                                 " - " + e.getMessage());
            }
        }
        System.out.println();
    }
    
    /**
     * Demonstrates handling of array index exceptions
     * In Python: try/except IndexError, in Java: try/catch ArrayIndexOutOfBoundsException
     */
    public static void demonstrateArrayIndexException() {
        System.out.println("2. Array Index Exception Handling:");
        
        String[] fruits = {"apple", "banana", "orange"};
        
        // Try to access valid and invalid indices
        int[] indices = {0, 2, 5, -1, 1};
        
        for (int index : indices) {
            try {
                String fruit = fruits[index];
                System.out.println("Index " + index + ": " + fruit);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid index " + index + ": " + e.getMessage());
            }
        }
        System.out.println();
    }
    
    /**
     * Demonstrates handling multiple types of exceptions
     * In Python: multiple except clauses, in Java: multiple catch blocks
     */
    public static void demonstrateMultipleExceptions() {
        System.out.println("3. Multiple Exception Types:");
        
        String[] data = {"123", "abc", "456", "def"};
        
        for (String item : data) {
            try {
                // Try to parse string as integer
                int number = Integer.parseInt(item);
                System.out.println("Parsed '" + item + "' as: " + number);
                
                // Try to access array with parsed number
                int[] array = {1, 2, 3};
                int value = array[number];
                System.out.println("Array[" + number + "] = " + value);
                
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse '" + item + "' as number: " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array index out of bounds for '" + item + "': " + e.getMessage());
            } catch (Exception e) {
                // Catch any other unexpected exceptions
                System.out.println("Unexpected error with '" + item + "': " + e.getMessage());
            }
        }
        System.out.println();
    }
} 