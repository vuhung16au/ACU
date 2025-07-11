/**
 * Example1.java - Basic Custom Exception Examples
 * 
 * This example demonstrates the fundamentals of creating and using custom exceptions.
 * Shows the difference between checked and unchecked exceptions.
 * 
 * Key concepts:
 * - Creating custom exception classes
 * - Checked vs unchecked exceptions
 * - Basic exception throwing and catching
 */

// Simple custom checked exception
class InputValidationException extends Exception {
    public InputValidationException(String message) {
        super(message);
    }
}

// Simple custom unchecked exception
class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }
}

public class Example1 {
    
    /**
     * Method that throws a checked exception
     * Must be declared with 'throws' or caught
     */
    public static void validateString(String input) throws InputValidationException {
        if (input == null) {
            throw new InputValidationException("Input cannot be null");
        }
        if (input.trim().isEmpty()) {
            throw new InputValidationException("Input cannot be empty");
        }
        System.out.println("Input '" + input + "' is valid");
    }
    
    /**
     * Method that throws an unchecked exception
     * No declaration needed
     */
    public static void processNumber(String numberStr) {
        try {
            int number = Integer.parseInt(numberStr);
            if (number < 0) {
                throw new DataProcessingException("Number must be positive: " + number);
            }
            System.out.println("Processed number: " + number);
        } catch (NumberFormatException e) {
            throw new DataProcessingException("Invalid number format: " + numberStr);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Custom Exceptions ===");
        
        // Example 1: Handling checked exceptions
        System.out.println("\n1. Checked Exception Example:");
        try {
            validateString("Hello");
            validateString(""); // This will throw an exception
        } catch (InputValidationException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        
        // Example 2: Handling unchecked exceptions
        System.out.println("\n2. Unchecked Exception Example:");
        try {
            processNumber("42");
            processNumber("-5"); // This will throw an exception
        } catch (DataProcessingException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        
        // Example 3: Exception propagation
        System.out.println("\n3. Exception Propagation:");
        try {
            processNumber("abc"); // This will throw an exception
        } catch (DataProcessingException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        
        System.out.println("\nExample 1 Complete!");
    }
} 