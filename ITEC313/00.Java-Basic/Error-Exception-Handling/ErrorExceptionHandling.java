/**
 * Error-Exception-Handling.java
 * 
 * This program demonstrates Java error and exception handling concepts.
 * It covers try-catch blocks, finally clauses, exception hierarchy,
 * and custom exception creation.
 * 
 * Key differences from Python:
 * - Java uses checked exceptions (must be declared or caught)
 * - Python uses try/except/finally, Java uses try/catch/finally
 * - Java has a more structured exception hierarchy
 * - Java requires explicit exception handling for many operations
 * 
 * @author ITEC313 Course
 * @version 1.0
 */

import java.io.*;
import java.util.*;

public class ErrorExceptionHandling {
    
    /**
     * Main method demonstrating various exception handling scenarios
     */
    public static void main(String[] args) {
        System.out.println("=== Java Error and Exception Handling Demo ===\n");
        
        // Demonstrate basic try-catch
        demonstrateBasicTryCatch();
        
        // Demonstrate multiple catch blocks
        demonstrateMultipleCatch();
        
        // Demonstrate finally block
        demonstrateFinally();
        
        // Demonstrate custom exceptions
        demonstrateCustomExceptions();
        
        // Demonstrate file handling with exceptions
        demonstrateFileHandling();
        
        // Demonstrate resource management with try-with-resources
        demonstrateTryWithResources();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Demonstrates basic try-catch exception handling
     * In Python: try/except, in Java: try/catch
     */
    public static void demonstrateBasicTryCatch() {
        System.out.println("1. Basic Try-Catch Example:");
        
        try {
            // Simulate an exception by dividing by zero
            int result = 10 / 0; // This will throw ArithmeticException
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            // Handle the specific exception
            System.out.println("Caught ArithmeticException: " + e.getMessage());
            // In Python: print(f"Caught exception: {e}")
        }
        
        System.out.println("Program continues after exception handling\n");
    }
    
    /**
     * Demonstrates multiple catch blocks for different exception types
     * In Python: multiple except clauses, in Java: multiple catch blocks
     */
    public static void demonstrateMultipleCatch() {
        System.out.println("2. Multiple Catch Blocks Example:");
        
        try {
            // Simulate different types of exceptions
            String[] array = {"one", "two", "three"};
            
            // This could throw ArrayIndexOutOfBoundsException
            System.out.println("Array element: " + array[5]);
            
            // This could throw NumberFormatException
            int number = Integer.parseInt("abc");
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exception (general exception handler)
            System.out.println("Caught general Exception: " + e.getMessage());
        }
        
        System.out.println("Program continues after multiple catch handling\n");
    }
    
    /**
     * Demonstrates the finally block which always executes
     * In Python: finally, in Java: finally (same concept)
     */
    public static void demonstrateFinally() {
        System.out.println("3. Finally Block Example:");
        
        try {
            System.out.println("Inside try block");
            // Simulate an exception
            throw new RuntimeException("Simulated exception");
        } catch (RuntimeException e) {
            System.out.println("Caught RuntimeException: " + e.getMessage());
        } finally {
            // This block always executes, regardless of exception
            System.out.println("Finally block executed - cleanup code here");
        }
        
        System.out.println("Program continues after finally\n");
    }
    
    /**
     * Demonstrates custom exception creation and usage
     * In Python: class CustomException(Exception), in Java: extends Exception
     */
    public static void demonstrateCustomExceptions() {
        System.out.println("4. Custom Exception Example:");
        
        try {
            // Simulate a business logic validation
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Caught custom exception: " + e.getMessage());
        }
        
        try {
            validateAge(25);
            System.out.println("Age validation successful");
        } catch (InvalidAgeException e) {
            System.out.println("This should not execute: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates file handling with proper exception handling
     * In Python: with open() as f, in Java: try-with-resources or manual handling
     */
    public static void demonstrateFileHandling() {
        System.out.println("5. File Handling with Exceptions:");
        
        // Traditional file handling with try-catch-finally
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("data/input.txt"));
            String line = reader.readLine();
            System.out.println("Read from file: " + line);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } finally {
            // Always close the resource
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("File closed successfully");
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates try-with-resources (Java 7+)
     * This is similar to Python's 'with' statement
     */
    public static void demonstrateTryWithResources() {
        System.out.println("6. Try-With-Resources Example:");
        
        // Try-with-resources automatically closes resources
        // In Python: with open('file.txt') as f:
        try (BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("data/output.txt"))) {
            
            String line = reader.readLine();
            if (line != null) {
                writer.write("Processed: " + line);
                System.out.println("File processed successfully");
            }
            
        } catch (IOException e) {
            System.out.println("IO error with resources: " + e.getMessage());
        }
        // Resources are automatically closed here
        
        System.out.println();
    }
    
    /**
     * Custom method that throws a custom exception
     * In Python: raise CustomException(), in Java: throw new CustomException()
     */
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new InvalidAgeException("Age seems unrealistic: " + age);
        }
    }
}

/**
 * Custom exception class
 * In Python: class CustomException(Exception), in Java: extends Exception
 */
class InvalidAgeException extends Exception {
    
    /**
     * Constructor with message
     * In Python: super().__init__(message), in Java: super(message)
     */
    public InvalidAgeException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * In Python: super().__init__(message, cause), in Java: super(message, cause)
     */
    public InvalidAgeException(String message, Throwable cause) {
        super(message, cause);
    }
} 