/**
 * DebuggingTechniques.java
 * 
 * This program demonstrates debugging techniques in Java:
 * - Using debugger tools
 * - Logging and tracing
 * - Exception debugging
 * - Performance debugging
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class DebuggingTechniques {
    
    // Logger for professional debugging (similar to Python's logging module)
    private static final Logger logger = Logger.getLogger(DebuggingTechniques.class.getName());
    
    // Sample data for debugging demonstrations
    private static final int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
    
    public static void main(String[] args) {
        System.out.println("=== Java Debugging Techniques Demo ===\n");
        
        // Initialize logging
        setupLogging();
        
        // Demonstrate different debugging techniques
        demonstratePrintDebugging();
        demonstrateLoggingDebugging();
        demonstrateConditionalDebugging();
        demonstrateExceptionDebugging();
        demonstratePerformanceDebugging();
        
        System.out.println("\n=== Debugging Demo Complete ===");
    }
    
    /**
     * Sets up logging configuration
     * Similar to Python's logging.basicConfig()
     */
    private static void setupLogging() {
        try {
            // Create a file handler to write logs to a file
            FileHandler fileHandler = new FileHandler("debug.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            
            System.out.println("✓ Logging system initialized");
        } catch (IOException e) {
            System.err.println("Failed to setup logging: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates basic print debugging
     * Similar to Python's print() statements for debugging
     */
    private static void demonstratePrintDebugging() {
        System.out.println("\n--- Print Debugging ---");
        
        // Basic print debugging
        System.out.println("Debug: Starting array processing");
        
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            // Print current state
            System.out.println("Debug: Processing number at index " + i + ": " + numbers[i]);
            
            sum += numbers[i];
            
            // Print running total
            System.out.println("Debug: Running sum: " + sum);
        }
        
        System.out.println("Debug: Final sum: " + sum);
        System.out.println("Debug: Average: " + (double) sum / numbers.length);
    }
    
    /**
     * Demonstrates professional logging debugging
     * Similar to Python's logging module but more integrated in Java
     */
    private static void demonstrateLoggingDebugging() {
        System.out.println("\n--- Logging Debugging ---");
        
        logger.info("Starting logging demonstration");
        
        // Different log levels (similar to Python's logging levels)
        logger.fine("Fine detail: Processing names array");
        logger.info("Info: Found " + names.length + " names");
        logger.warning("Warning: This is a sample warning");
        
        for (int i = 0; i < names.length; i++) {
            logger.info("Processing name " + (i + 1) + ": " + names[i]);
            
            // Simulate some processing
            if (names[i].length() > 4) {
                logger.fine("Long name detected: " + names[i]);
            }
        }
        
        logger.info("Logging demonstration complete");
    }
    
    /**
     * Demonstrates conditional debugging
     * Similar to Python's if __debug__: pattern
     */
    private static void demonstrateConditionalDebugging() {
        System.out.println("\n--- Conditional Debugging ---");
        
        // Define debug flag (similar to Python's __debug__)
        boolean DEBUG_MODE = true;
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Entering conditional debugging section");
        }
        
        // Simulate complex calculation
        int result = 0;
        for (int i = 0; i < 1000; i++) {
            result += i;
            
            // Only print every 100th iteration if in debug mode
            if (DEBUG_MODE && i % 100 == 0) {
                System.out.println("Debug: Iteration " + i + ", partial result: " + result);
            }
        }
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Final result: " + result);
        }
        
        System.out.println("Calculation complete");
    }
    
    /**
     * Demonstrates debugging with exceptions
     * Shows how to use exceptions for debugging
     */
    private static void demonstrateExceptionDebugging() {
        System.out.println("\n--- Exception Debugging ---");
        
        try {
            // Simulate a problematic operation
            int[] testArray = {1, 2, 3};
            
            // This will cause an exception
            System.out.println("Debug: Attempting to access index 5");
            int value = testArray[5]; // ArrayIndexOutOfBoundsException
            
        } catch (ArrayIndexOutOfBoundsException e) {
            // Debug information in exception handling
            System.err.println("Debug: Caught ArrayIndexOutOfBoundsException");
            System.err.println("Debug: Exception message: " + e.getMessage());
            System.err.println("Debug: Stack trace:");
            e.printStackTrace();
            
            logger.severe("Array index out of bounds: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Debug: Unexpected exception: " + e.getMessage());
            logger.severe("Unexpected exception: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates performance debugging
     * Shows how to measure execution time
     */
    private static void demonstratePerformanceDebugging() {
        System.out.println("\n--- Performance Debugging ---");
        
        // Measure execution time (similar to Python's time.time())
        long startTime = System.currentTimeMillis();
        
        // Simulate some work
        int result = 0;
        for (int i = 0; i < 1000000; i++) {
            result += i;
        }
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        System.out.println("Debug: Execution time: " + executionTime + " milliseconds");
        System.out.println("Debug: Result: " + result);
        
        // Log performance information
        logger.info("Performance: Loop completed in " + executionTime + "ms");
        
        // Performance warning if too slow
        if (executionTime > 100) {
            logger.warning("Performance: Loop took longer than expected: " + executionTime + "ms");
        }
    }
    
    /**
     * Utility method to demonstrate debugging with method calls
     * Shows how to debug method execution flow
     */
    public static void debugMethodCall(String methodName, Object... parameters) {
        System.out.println("Debug: Calling method: " + methodName);
        System.out.println("Debug: Parameters: " + java.util.Arrays.toString(parameters));
        
        logger.fine("Method call: " + methodName + " with parameters: " + java.util.Arrays.toString(parameters));
    }
    
    /**
     * Demonstrates debugging with assertions
     * Similar to Python's assert statements
     */
    public static void demonstrateAssertions() {
        System.out.println("\n--- Assertion Debugging ---");
        
        int x = 5;
        int y = 10;
        
        // Assertions (disabled by default in Java, unlike Python)
        assert x < y : "x should be less than y";
        assert x > 0 : "x should be positive";
        
        System.out.println("Debug: All assertions passed");
        logger.info("Assertions validation completed successfully");
    }
} 