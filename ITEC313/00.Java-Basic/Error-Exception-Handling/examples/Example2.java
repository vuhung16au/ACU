/**
 * Example2.java
 * 
 * This program demonstrates example in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.io.*;
import java.util.*;

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced Exception Handling ===\n");
        
        // Example 1: File Handling with Exceptions
        demonstrateFileHandling();
        
        // Example 2: Custom Exceptions
        demonstrateCustomExceptions();
        
        // Example 3: Exception Propagation
        demonstrateExceptionPropagation();
        
        // Example 4: Finally Block
        demonstrateFinallyBlock();
        
        System.out.println("=== Example 2 Complete ===\n");
    }
    
    /**
     * Demonstrates file handling with comprehensive exception handling
     * In Python: with open() as f, in Java: try-with-resources or manual handling
     */
    public static void demonstrateFileHandling() {
        System.out.println("1. File Handling with Exceptions:");
        
        // Method 1: Traditional try-catch-finally
        BufferedReader reader1 = null;
        try {
            reader1 = new BufferedReader(new FileReader("data/input.txt"));
            String line = reader1.readLine();
            System.out.println("Traditional method - Read: " + line);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } finally {
            if (reader1 != null) {
                try {
                    reader1.close();
                    System.out.println("File closed (traditional method)");
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
        
        // Method 2: Try-with-resources (Java 7+)
        try (BufferedReader reader2 = new BufferedReader(new FileReader("data/input.txt"))) {
            String line = reader2.readLine();
            System.out.println("Try-with-resources - Read: " + line);
        } catch (FileNotFoundException e) {
            System.out.println("File not found (try-with-resources): " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error (try-with-resources): " + e.getMessage());
        }
        // File automatically closed here
        
        System.out.println();
    }
    
    /**
     * Demonstrates custom exception creation and usage
     * In Python: class CustomException(Exception), in Java: extends Exception
     */
    public static void demonstrateCustomExceptions() {
        System.out.println("2. Custom Exceptions:");
        
        // Test different scenarios
        String[] testData = {"john", "JANE", "bob", "ALICE", "invalid"};
        
        for (String name : testData) {
            try {
                validateName(name);
                System.out.println("Name '" + name + "' is valid");
            } catch (InvalidNameException e) {
                System.out.println("Invalid name '" + name + "': " + e.getMessage());
            }
        }
        
        // Test age validation
        int[] ages = {25, -5, 200, 0, 150};
        
        for (int age : ages) {
            try {
                validateAge(age);
                System.out.println("Age " + age + " is valid");
            } catch (InvalidAgeException e) {
                System.out.println("Invalid age " + age + ": " + e.getMessage());
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates exception propagation through method calls
     * In Python: exceptions bubble up automatically, in Java: same concept
     */
    public static void demonstrateExceptionPropagation() {
        System.out.println("3. Exception Propagation:");
        
        try {
            // Call method that might throw an exception
            processData("valid");
            processData("invalid");
        } catch (DataProcessingException e) {
            System.out.println("Data processing failed: " + e.getMessage());
            // Print the stack trace for debugging
            System.out.println("Stack trace:");
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates the finally block in various scenarios
     * In Python: finally, in Java: finally (same concept)
     */
    public static void demonstrateFinallyBlock() {
        System.out.println("4. Finally Block Examples:");
        
        // Scenario 1: No exception
        try {
            System.out.println("Executing normal code");
            int result = 10 / 2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed (no exception)");
        }
        
        // Scenario 2: Exception occurs
        try {
            System.out.println("Executing code that throws exception");
            int result = 10 / 0;
            System.out.println("This line won't execute");
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed (with exception)");
        }
        
        // Scenario 3: Return statement in try
        System.out.println("Method result: " + demonstrateFinallyWithReturn());
        
        System.out.println();
    }
    
    /**
     * Custom validation method that throws custom exception
     * In Python: raise CustomException(), in Java: throw new CustomException()
     */
    public static void validateName(String name) throws InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Name cannot be null or empty");
        }
        
        if (name.length() < 2) {
            throw new InvalidNameException("Name must be at least 2 characters long");
        }
        
        if (!name.matches("^[a-zA-Z]+$")) {
            throw new InvalidNameException("Name must contain only letters");
        }
        
        // Check if name is all uppercase (business rule)
        if (name.equals(name.toUpperCase())) {
            throw new InvalidNameException("Name cannot be all uppercase");
        }
    }
    
    /**
     * Custom validation method for age
     */
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new InvalidAgeException("Age seems unrealistic: " + age);
        }
        if (age == 0) {
            throw new InvalidAgeException("Age cannot be zero");
        }
    }
    
    /**
     * Method that demonstrates exception propagation
     */
    public static void processData(String data) throws DataProcessingException {
        if ("invalid".equals(data)) {
            throw new DataProcessingException("Cannot process invalid data: " + data);
        }
        System.out.println("Processing data: " + data);
    }
    
    /**
     * Demonstrates finally block with return statement
     */
    public static int demonstrateFinallyWithReturn() {
        try {
            System.out.println("In try block");
            return 42;
        } finally {
            System.out.println("Finally block executed even with return");
        }
    }
}

/**
 * Custom exception for name validation
 * In Python: class CustomException(Exception), in Java: extends Exception
 */
class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

/**
 * Custom exception for age validation
 */
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

/**
 * Custom exception for data processing
 */
class DataProcessingException extends Exception {
    public DataProcessingException(String message) {
        super(message);
    }
} 