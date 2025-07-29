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
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class Example2 {
    
    // Logger instance (similar to Python's logging.getLogger())
    private static final Logger logger = Logger.getLogger(Example2.class.getName());
    
    // Debug flag (similar to Python's __debug__)
    private static final boolean DEBUG_MODE = true;
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Logging and Conditional Debugging ===\n");
        
        // Setup logging
        setupLogging();
        
        // Example 1: Logging different levels
        demonstrateLoggingLevels();
        
        // Example 2: Conditional debugging
        demonstrateConditionalDebugging();
        
        // Example 3: Performance debugging
        demonstratePerformanceDebugging();
        
        System.out.println("Example 2 complete!\n");
    }
    
    /**
     * Sets up logging configuration
     */
    private static void setupLogging() {
        try {
            // Create file handler for logging to file
            FileHandler fileHandler = new FileHandler("example2.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            
            System.out.println("✓ Logging system initialized for Example2");
        } catch (IOException e) {
            System.err.println("Failed to setup logging: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates different logging levels
     */
    private static void demonstrateLoggingLevels() {
        System.out.println("--- Logging Levels Demonstration ---");
        
        // Different log levels (similar to Python's logging levels)
        logger.finest("FINEST: Most detailed debugging information");
        logger.finer("FINER: Detailed debugging information");
        logger.fine("FINE: General debugging information");
        logger.config("CONFIG: Configuration information");
        logger.info("INFO: General information");
        logger.warning("WARNING: Warning message");
        logger.severe("SEVERE: Error message");
        
        // Log with parameters (similar to Python's logging with format strings)
        String user = "Alice";
        int age = 25;
        logger.info("User " + user + " is " + age + " years old");
        
        // Log exceptions
        try {
            int result = 10 / 0; // This will cause an exception
        } catch (ArithmeticException e) {
            logger.severe("Division by zero error: " + e.getMessage());
        }
        
        System.out.println("Logging levels demonstration complete!\n");
    }
    
    /**
     * Demonstrates conditional debugging
     */
    private static void demonstrateConditionalDebugging() {
        System.out.println("--- Conditional Debugging ---");
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Starting conditional debugging demonstration");
        }
        
        // Simulate processing a list of items
        String[] items = {"apple", "banana", "cherry", "date", "elderberry"};
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Processing " + items.length + " items");
        }
        
        for (int i = 0; i < items.length; i++) {
            // Only log every other item in debug mode
            if (DEBUG_MODE && i % 2 == 0) {
                System.out.println("Debug: Processing item " + i + ": " + items[i]);
            }
            
            // Simulate some processing
            String processed = items[i].toUpperCase();
            
            if (DEBUG_MODE) {
                System.out.println("Debug: Processed '" + items[i] + "' to '" + processed + "'");
            }
        }
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Conditional debugging demonstration complete");
        }
        
        System.out.println("Conditional debugging complete!\n");
    }
    
    /**
     * Demonstrates performance debugging
     */
    private static void demonstratePerformanceDebugging() {
        System.out.println("--- Performance Debugging ---");
        
        // Measure execution time (similar to Python's time.time())
        long startTime = System.currentTimeMillis();
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Starting performance test at " + startTime);
        }
        
        // Simulate some work
        int sum = 0;
        for (int i = 0; i < 100000; i++) {
            sum += i;
            
            // Log progress every 10000 iterations
            if (DEBUG_MODE && i % 10000 == 0) {
                System.out.println("Debug: Progress - " + i + " iterations completed");
            }
        }
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Performance test completed at " + endTime);
            System.out.println("Debug: Total execution time: " + executionTime + " milliseconds");
            System.out.println("Debug: Final sum: " + sum);
        }
        
        // Log performance information
        logger.info("Performance test completed in " + executionTime + "ms");
        
        // Performance warning if too slow
        if (executionTime > 50) {
            logger.warning("Performance: Test took longer than expected: " + executionTime + "ms");
        }
        
        System.out.println("Performance debugging complete!\n");
    }
    
    /**
     * Demonstrates debugging with method entry/exit logging
     */
    public static void debugMethodEntry(String methodName, Object... parameters) {
        if (DEBUG_MODE) {
            System.out.println("Debug: ENTERING " + methodName);
            if (parameters.length > 0) {
                System.out.println("Debug: Parameters: " + java.util.Arrays.toString(parameters));
            }
        }
        
        logger.fine("Method entry: " + methodName);
    }
    
    /**
     * Demonstrates debugging with method exit logging
     */
    public static void debugMethodExit(String methodName, Object result) {
        if (DEBUG_MODE) {
            System.out.println("Debug: EXITING " + methodName + " with result: " + result);
        }
        
        logger.fine("Method exit: " + methodName + " -> " + result);
    }
    
    /**
     * Example method that uses debugging
     */
    public static int calculateFactorial(int n) {
        debugMethodEntry("calculateFactorial", n);
        
        if (n <= 1) {
            debugMethodExit("calculateFactorial", 1);
            return 1;
        }
        
        int result = n * calculateFactorial(n - 1);
        debugMethodExit("calculateFactorial", result);
        return result;
    }
} 