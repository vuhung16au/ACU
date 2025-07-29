/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
import java.util.Random;

public class Advanced {
    
    // Logger for advanced debugging
    private static final Logger logger = Logger.getLogger(Advanced.class.getName());
    
    // Debug flags for different types of debugging
    private static final boolean DEBUG_MODE = true;
    private static final boolean ASSERTIONS_ENABLED = true;
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Debugging Techniques ===\n");
        
        // Setup logging
        setupLogging();
        
        // Example 1: IDE debugging simulation
        demonstrateIDEDebugging();
        
        // Example 2: Assertions and validation
        demonstrateAssertions();
        
        // Example 3: Exception debugging
        demonstrateExceptionDebugging();
        
        // Example 4: Memory and performance debugging
        demonstrateMemoryDebugging();
        
        System.out.println("Advanced debugging demonstration complete!\n");
    }
    
    /**
     * Sets up logging for advanced debugging
     */
    private static void setupLogging() {
        try {
            FileHandler fileHandler = new FileHandler("advanced.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            
            System.out.println("✓ Advanced logging system initialized");
        } catch (IOException e) {
            System.err.println("Failed to setup advanced logging: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates IDE debugging concepts
     * Shows how to use breakpoints and step-through debugging
     */
    private static void demonstrateIDEDebugging() {
        System.out.println("--- IDE Debugging Simulation ---");
        
        // This section simulates what you would do in an IDE debugger
        
        // Breakpoint 1: Variable inspection
        int counter = 0;
        String message = "Hello Debugger";
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Breakpoint 1 - Variables:");
            System.out.println("  counter = " + counter);
            System.out.println("  message = " + message);
        }
        
        // Breakpoint 2: Loop debugging
        for (int i = 0; i < 5; i++) {
            counter += i;
            
            if (DEBUG_MODE) {
                System.out.println("Debug: Loop iteration " + i + ":");
                System.out.println("  i = " + i);
                System.out.println("  counter = " + counter);
            }
            
            // Simulate conditional breakpoint
            if (i == 3) {
                if (DEBUG_MODE) {
                    System.out.println("Debug: Conditional breakpoint hit at i == 3");
                }
            }
        }
        
        // Breakpoint 3: Method call debugging
        int result = processData(counter, message);
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Method call result = " + result);
        }
        
        System.out.println("IDE debugging simulation complete!\n");
    }
    
    /**
     * Demonstrates assertions and validation debugging
     */
    private static void demonstrateAssertions() {
        System.out.println("--- Assertions and Validation ---");
        
        // Basic assertions (disabled by default in Java)
        int positiveNumber = 5;
        int negativeNumber = -3;
        
        // These assertions would only run if assertions are enabled
        // In Java, you need to run with -ea flag to enable assertions
        if (ASSERTIONS_ENABLED) {
            assert positiveNumber > 0 : "positiveNumber should be positive";
            assert negativeNumber < 0 : "negativeNumber should be negative";
            
            System.out.println("Debug: Basic assertions passed");
        }
        
        // Validation with debug information
        validateArray(new int[]{1, 2, 3, 4, 5});
        validateArray(new int[]{1, -2, 3, 4, 5}); // This will fail validation
        
        // Complex validation
        validateUserData("Alice", 25, "alice@example.com");
        validateUserData("Bob", -5, "invalid-email"); // This will fail validation
        
        System.out.println("Assertions and validation complete!\n");
    }
    
    /**
     * Demonstrates exception debugging
     */
    private static void demonstrateExceptionDebugging() {
        System.out.println("--- Exception Debugging ---");
        
        // Debug exception handling
        try {
            // Simulate different types of exceptions
            throwRandomException();
            
        } catch (ArithmeticException e) {
            if (DEBUG_MODE) {
                System.out.println("Debug: Caught ArithmeticException");
                System.out.println("Debug: Exception message: " + e.getMessage());
                System.out.println("Debug: Stack trace:");
                e.printStackTrace();
            }
            logger.severe("Arithmetic exception: " + e.getMessage());
            
        } catch (ArrayIndexOutOfBoundsException e) {
            if (DEBUG_MODE) {
                System.out.println("Debug: Caught ArrayIndexOutOfBoundsException");
                System.out.println("Debug: Exception message: " + e.getMessage());
            }
            logger.severe("Array index exception: " + e.getMessage());
            
        } catch (NullPointerException e) {
            if (DEBUG_MODE) {
                System.out.println("Debug: Caught NullPointerException");
                System.out.println("Debug: Exception message: " + e.getMessage());
            }
            logger.severe("Null pointer exception: " + e.getMessage());
            
        } catch (Exception e) {
            if (DEBUG_MODE) {
                System.out.println("Debug: Caught unexpected exception: " + e.getClass().getSimpleName());
                System.out.println("Debug: Exception message: " + e.getMessage());
            }
            logger.severe("Unexpected exception: " + e.getMessage());
        }
        
        System.out.println("Exception debugging complete!\n");
    }
    
    /**
     * Demonstrates memory and performance debugging
     */
    private static void demonstrateMemoryDebugging() {
        System.out.println("--- Memory and Performance Debugging ---");
        
        // Memory usage debugging
        Runtime runtime = Runtime.getRuntime();
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Initial memory usage:");
            System.out.println("  Total memory: " + runtime.totalMemory() + " bytes");
            System.out.println("  Free memory: " + runtime.freeMemory() + " bytes");
            System.out.println("  Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) + " bytes");
        }
        
        // Simulate memory-intensive operation
        int[] largeArray = new int[100000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        
        if (DEBUG_MODE) {
            System.out.println("Debug: After creating large array:");
            System.out.println("  Total memory: " + runtime.totalMemory() + " bytes");
            System.out.println("  Free memory: " + runtime.freeMemory() + " bytes");
            System.out.println("  Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) + " bytes");
        }
        
        // Performance debugging with timing
        long startTime = System.nanoTime();
        
        // Simulate some work
        int sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Performance metrics:");
            System.out.println("  Execution time: " + duration + " nanoseconds");
            System.out.println("  Execution time: " + (duration / 1_000_000.0) + " milliseconds");
            System.out.println("  Final sum: " + sum);
        }
        
        // Clean up
        largeArray = null;
        System.gc(); // Request garbage collection
        
        if (DEBUG_MODE) {
            System.out.println("Debug: After cleanup:");
            System.out.println("  Free memory: " + runtime.freeMemory() + " bytes");
        }
        
        System.out.println("Memory and performance debugging complete!\n");
    }
    
    /**
     * Helper method for IDE debugging simulation
     */
    private static int processData(int count, String text) {
        if (DEBUG_MODE) {
            System.out.println("Debug: Entering processData()");
            System.out.println("Debug: Parameters - count: " + count + ", text: " + text);
        }
        
        int result = count * text.length();
        
        if (DEBUG_MODE) {
            System.out.println("Debug: processData() returning: " + result);
        }
        
        return result;
    }
    
    /**
     * Validates array with debug information
     */
    private static void validateArray(int[] array) {
        if (DEBUG_MODE) {
            System.out.println("Debug: Validating array of length " + array.length);
        }
        
        boolean isValid = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                if (DEBUG_MODE) {
                    System.out.println("Debug: Validation failed at index " + i + ": " + array[i]);
                }
                isValid = false;
                break;
            }
        }
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Array validation result: " + isValid);
        }
        
        if (ASSERTIONS_ENABLED) {
            assert isValid : "Array should contain only positive numbers";
        }
    }
    
    /**
     * Validates user data with debug information
     */
    private static void validateUserData(String name, int age, String email) {
        if (DEBUG_MODE) {
            System.out.println("Debug: Validating user data:");
            System.out.println("  Name: " + name);
            System.out.println("  Age: " + age);
            System.out.println("  Email: " + email);
        }
        
        boolean isValid = true;
        String errorMessage = "";
        
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            errorMessage += "Name cannot be empty. ";
            isValid = false;
        }
        
        // Validate age
        if (age < 0 || age > 150) {
            errorMessage += "Age must be between 0 and 150. ";
            isValid = false;
        }
        
        // Validate email (basic check)
        if (email == null || !email.contains("@")) {
            errorMessage += "Email must contain @ symbol. ";
            isValid = false;
        }
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Validation result: " + isValid);
            if (!isValid) {
                System.out.println("Debug: Error message: " + errorMessage);
            }
        }
        
        if (ASSERTIONS_ENABLED) {
            assert isValid : "User data validation failed: " + errorMessage;
        }
    }
    
    /**
     * Throws random exceptions for debugging demonstration
     */
    private static void throwRandomException() {
        Random random = new Random();
        int choice = random.nextInt(3);
        
        if (DEBUG_MODE) {
            System.out.println("Debug: Random exception choice: " + choice);
        }
        
        switch (choice) {
            case 0:
                if (DEBUG_MODE) {
                    System.out.println("Debug: Throwing ArithmeticException");
                }
                throw new ArithmeticException("Division by zero");
                
            case 1:
                if (DEBUG_MODE) {
                    System.out.println("Debug: Throwing ArrayIndexOutOfBoundsException");
                }
                int[] array = {1, 2, 3};
                int value = array[5]; // This will throw the exception
                break;
                
            case 2:
                if (DEBUG_MODE) {
                    System.out.println("Debug: Throwing NullPointerException");
                }
                String str = null;
                int length = str.length(); // This will throw the exception
                break;
        }
    }
} 