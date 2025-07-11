/**
 * CustomExceptions.java
 * 
 * This program demonstrates how to create and use custom exceptions in Java.
 * Custom exceptions allow you to create specific error types for your application.
 * 
 * Key differences from Python:
 * - Java requires explicit exception class definition extending Exception or RuntimeException
 * - Java exceptions are checked (must be declared) or unchecked (RuntimeException)
 * - Java uses 'throw' and 'throws' keywords instead of Python's 'raise'
 * 
 * @author ITEC313 - Object-Oriented Programming
 * @version 1.0
 */

// Custom exception classes
// In Java, custom exceptions typically extend Exception (checked) or RuntimeException (unchecked)
// This is different from Python where you can raise any object as an exception

/**
 * Custom checked exception for invalid age scenarios
 * Extends Exception, so it must be declared in method signatures or caught
 */
class InvalidAgeException extends Exception {
    // Constructor with message
    public InvalidAgeException(String message) {
        super(message); // Call parent constructor
    }
    
    // Constructor with message and cause
    public InvalidAgeException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Custom unchecked exception for invalid email format
 * Extends RuntimeException, so it doesn't need to be declared
 * Similar to Python's built-in exceptions that don't require explicit handling
 */
class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}

/**
 * Custom exception for business logic errors
 * Demonstrates exception with additional data fields
 */
class BusinessLogicException extends Exception {
    private String errorCode;
    private int severity;
    
    public BusinessLogicException(String message, String errorCode, int severity) {
        super(message);
        this.errorCode = errorCode;
        this.severity = severity;
    }
    
    // Getters for additional data
    public String getErrorCode() {
        return errorCode;
    }
    
    public int getSeverity() {
        return severity;
    }
    
    @Override
    public String toString() {
        return "BusinessLogicException{" +
               "message='" + getMessage() + '\'' +
               ", errorCode='" + errorCode + '\'' +
               ", severity=" + severity +
               '}';
    }
}

/**
 * Main class demonstrating custom exception usage
 */
public class CustomExceptions {
    
    /**
     * Method that throws a checked exception
     * Note: In Java, checked exceptions must be declared with 'throws'
     * This is different from Python where you can raise any exception without declaration
     */
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new InvalidAgeException("Age seems unrealistic: " + age);
        }
        System.out.println("Age " + age + " is valid");
    }
    
    /**
     * Method that throws an unchecked exception
     * No 'throws' declaration needed for RuntimeException
     */
    public static void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        System.out.println("Email " + email + " is valid");
    }
    
    /**
     * Method demonstrating business logic exception with additional data
     */
    public static void processOrder(String orderId, double amount) throws BusinessLogicException {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new BusinessLogicException("Order ID is required", "ORDER_001", 1);
        }
        
        if (amount <= 0) {
            throw new BusinessLogicException("Order amount must be positive", "ORDER_002", 2);
        }
        
        if (amount > 10000) {
            throw new BusinessLogicException("Order amount exceeds limit", "ORDER_003", 3);
        }
        
        System.out.println("Order " + orderId + " processed successfully: $" + amount);
    }
    
    /**
     * Demonstrates exception chaining (similar to Python's 'raise ... from ...')
     */
    public static void processData(String data) throws InvalidAgeException {
        try {
            int age = Integer.parseInt(data);
            validateAge(age);
        } catch (NumberFormatException e) {
            // Chain the exception - preserve the original cause
            throw new InvalidAgeException("Invalid age format: " + data, e);
        }
    }
    
    /**
     * Demonstrates multiple exception handling
     * Java uses try-catch-finally blocks (similar to Python's try-except-finally)
     */
    public static void demonstrateExceptionHandling() {
        System.out.println("\n=== Exception Handling Examples ===");
        
        // Example 1: Handling checked exceptions
        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Caught InvalidAgeException: " + e.getMessage());
        }
        
        // Example 2: Handling unchecked exceptions
        try {
            validateEmail("invalid-email");
        } catch (InvalidEmailException e) {
            System.out.println("Caught InvalidEmailException: " + e.getMessage());
        }
        
        // Example 3: Handling business logic exceptions
        try {
            processOrder("", 100);
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Severity: " + e.getSeverity());
        }
        
        // Example 4: Exception chaining
        try {
            processData("abc");
        } catch (InvalidAgeException e) {
            System.out.println("Chained Exception: " + e.getMessage());
            System.out.println("Original Cause: " + e.getCause().getMessage());
        }
        
        // Example 5: Multiple catch blocks (Java 7+)
        try {
            validateAge(200);
            validateEmail(null);
        } catch (InvalidAgeException e) {
            System.out.println("Age Error: " + e.getMessage());
        } catch (InvalidEmailException e) {
            System.out.println("Email Error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates try-with-resources (Java 7+)
     * Similar to Python's 'with' statement for resource management
     */
    public static void demonstrateResourceManagement() {
        System.out.println("\n=== Resource Management Example ===");
        
        // In a real application, this would be a resource that implements AutoCloseable
        // For demonstration, we'll show the concept
        try {
            System.out.println("Resource acquired");
            // Simulate some work
            if (Math.random() > 0.5) {
                throw new RuntimeException("Simulated error during resource usage");
            }
            System.out.println("Resource used successfully");
        } catch (Exception e) {
            System.out.println("Error during resource usage: " + e.getMessage());
        } finally {
            System.out.println("Resource cleaned up (finally block)");
        }
    }
    
    /**
     * Main method demonstrating all custom exception concepts
     */
    public static void main(String[] args) {
        System.out.println("=== Custom Exceptions in Java ===");
        System.out.println("This program demonstrates custom exception creation and usage.");
        System.out.println("Key differences from Python:");
        System.out.println("- Custom exceptions must extend Exception or RuntimeException");
        System.out.println("- Checked exceptions must be declared with 'throws'");
        System.out.println("- Unchecked exceptions (RuntimeException) don't need declaration");
        System.out.println("- Exception chaining preserves original cause");
        
        // Demonstrate valid cases first
        System.out.println("\n=== Valid Cases ===");
        try {
            validateAge(25);
            validateEmail("user@example.com");
            processOrder("ORD-001", 100.50);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        // Demonstrate exception handling
        demonstrateExceptionHandling();
        
        // Demonstrate resource management
        demonstrateResourceManagement();
        
        System.out.println("\n=== Program Complete ===");
        System.out.println("Custom exceptions provide:");
        System.out.println("1. Specific error types for your application");
        System.out.println("2. Better error handling and debugging");
        System.out.println("3. Clear separation between checked and unchecked exceptions");
        System.out.println("4. Ability to carry additional error information");
    }
} 