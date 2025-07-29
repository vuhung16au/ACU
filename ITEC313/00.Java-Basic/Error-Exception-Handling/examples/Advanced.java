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
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Exception Handling Patterns ===\n");
        
        // Example 1: Exception Chaining
        demonstrateExceptionChaining();
        
        // Example 2: Exception Handling in Collections
        demonstrateCollectionExceptionHandling();
        
        // Example 3: Nested Exception Handling
        demonstrateNestedExceptions();
        
        // Example 4: Advanced Resource Management
        demonstrateAdvancedResourceManagement();
        
        // Example 5: Exception Hierarchy
        demonstrateExceptionHierarchy();
        
        System.out.println("=== Advanced Patterns Complete ===\n");
    }
    
    /**
     * Demonstrates exception chaining (Java 1.4+)
     * In Python: raise NewException() from old_exception, in Java: cause parameter
     */
    public static void demonstrateExceptionChaining() {
        System.out.println("1. Exception Chaining:");
        
        try {
            // Simulate a low-level exception
            processComplexData();
        } catch (DataProcessingException e) {
            System.out.println("High-level exception: " + e.getMessage());
            System.out.println("Root cause: " + e.getCause().getMessage());
            
            // Print full stack trace
            System.out.println("Full stack trace:");
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates exception handling in collections and loops
     * In Python: try/except in loops, in Java: same concept but more structured
     */
    public static void demonstrateCollectionExceptionHandling() {
        System.out.println("2. Exception Handling in Collections:");
        
        List<String> data = Arrays.asList("123", "abc", "456", "def", "789");
        List<Integer> results = new ArrayList<>();
        
        // Method 1: Handle exceptions individually
        System.out.println("Method 1: Individual exception handling:");
        for (String item : data) {
            try {
                int number = Integer.parseInt(item);
                results.add(number);
                System.out.println("Parsed: " + number);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse '" + item + "': " + e.getMessage());
            }
        }
        
        // Method 2: Collect exceptions and continue
        System.out.println("\nMethod 2: Collecting exceptions:");
        List<Exception> exceptions = new ArrayList<>();
        List<Integer> validNumbers = new ArrayList<>();
        
        for (String item : data) {
            try {
                int number = Integer.parseInt(item);
                validNumbers.add(number);
            } catch (NumberFormatException e) {
                exceptions.add(e);
                System.out.println("Exception collected for '" + item + "'");
            }
        }
        
        System.out.println("Valid numbers: " + validNumbers);
        System.out.println("Number of exceptions: " + exceptions.size());
        
        System.out.println();
    }
    
    /**
     * Demonstrates nested exception handling patterns
     * In Python: nested try/except, in Java: nested try/catch
     */
    public static void demonstrateNestedExceptions() {
        System.out.println("3. Nested Exception Handling:");
        
        try {
            // Outer try block
            System.out.println("Outer try block - starting file operations");
            
            try {
                // Inner try block for file reading
                BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"));
                String line = reader.readLine();
                System.out.println("Read line: " + line);
                
                try {
                    // Innermost try block for parsing
                    int number = Integer.parseInt(line);
                    System.out.println("Parsed number: " + number);
                    
                    // Simulate an error
                    if (number > 100) {
                        throw new RuntimeException("Number too large: " + number);
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Inner catch: Cannot parse as number: " + e.getMessage());
                } catch (RuntimeException e) {
                    System.out.println("Inner catch: Runtime error: " + e.getMessage());
                } finally {
                    System.out.println("Inner finally: Cleanup parsing resources");
                }
                
            } catch (FileNotFoundException e) {
                System.out.println("Middle catch: File not found: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Middle catch: IO error: " + e.getMessage());
            } finally {
                System.out.println("Middle finally: Cleanup file resources");
            }
            
        } catch (Exception e) {
            System.out.println("Outer catch: Unexpected error: " + e.getMessage());
        } finally {
            System.out.println("Outer finally: Final cleanup");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates advanced resource management patterns
     * In Python: context managers, in Java: try-with-resources
     */
    public static void demonstrateAdvancedResourceManagement() {
        System.out.println("4. Advanced Resource Management:");
        
        // Multiple resources in try-with-resources
        try (BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("data/output.txt"));
             PrintWriter logger = new PrintWriter(new FileWriter("data/log.txt"))) {
            
            String line = reader.readLine();
            if (line != null) {
                writer.write("Processed: " + line);
                logger.println("Processing completed successfully");
                System.out.println("Multiple resources managed successfully");
            }
            
        } catch (IOException e) {
            System.out.println("Resource management error: " + e.getMessage());
        }
        // All resources automatically closed here
        
        // Custom resource management
        try (CustomResource resource = new CustomResource()) {
            resource.performOperation();
            System.out.println("Custom resource operation completed");
        } catch (Exception e) {
            System.out.println("Custom resource error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates exception hierarchy and inheritance
     * In Python: exception inheritance, in Java: more structured hierarchy
     */
    public static void demonstrateExceptionHierarchy() {
        System.out.println("5. Exception Hierarchy:");
        
        // Test different exception types
        Exception[] exceptions = {
            new ArithmeticException("Division by zero"),
            new ArrayIndexOutOfBoundsException("Index out of bounds"),
            new NumberFormatException("Invalid number format"),
            new RuntimeException("Runtime error"),
            new Exception("General exception")
        };
        
        for (Exception e : exceptions) {
            try {
                throw e;
            } catch (ArithmeticException ae) {
                System.out.println("Caught ArithmeticException: " + ae.getMessage());
            } catch (ArrayIndexOutOfBoundsException aie) {
                System.out.println("Caught ArrayIndexOutOfBoundsException: " + aie.getMessage());
            } catch (NumberFormatException nfe) {
                System.out.println("Caught NumberFormatException: " + nfe.getMessage());
            } catch (RuntimeException re) {
                System.out.println("Caught RuntimeException: " + re.getMessage());
            } catch (Exception ex) {
                System.out.println("Caught general Exception: " + ex.getMessage());
            }
        }
        
        System.out.println();
    }
    
    /**
     * Simulates complex data processing with exception chaining
     */
    public static void processComplexData() throws DataProcessingException {
        try {
            // Simulate low-level file operation
            BufferedReader reader = new BufferedReader(new FileReader("nonexistent.txt"));
            String data = reader.readLine();
            
            // Simulate parsing operation
            int number = Integer.parseInt(data);
            
        } catch (FileNotFoundException e) {
            // Chain the exception with additional context
            throw new DataProcessingException("Failed to process data due to missing file", e);
        } catch (IOException e) {
            throw new DataProcessingException("Failed to process data due to IO error", e);
        } catch (NumberFormatException e) {
            throw new DataProcessingException("Failed to process data due to invalid format", e);
        }
    }
}

/**
 * Custom resource class implementing AutoCloseable
 * In Python: context manager with __enter__ and __exit__, in Java: AutoCloseable
 */
class CustomResource implements AutoCloseable {
    
    public CustomResource() {
        System.out.println("CustomResource: Initializing");
    }
    
    public void performOperation() {
        System.out.println("CustomResource: Performing operation");
        // Simulate some work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public void close() {
        System.out.println("CustomResource: Closing and cleaning up");
    }
}

/**
 * Custom exception for data processing with chaining support
 */
class DataProcessingException extends Exception {
    
    public DataProcessingException(String message) {
        super(message);
    }
    
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
} 