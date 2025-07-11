/**
 * Example2.java - Interface with Default Methods and Functional Interfaces
 * 
 * This example demonstrates Java 8+ features in interfaces:
 * - Default methods (similar to Python mixins)
 * - Functional interfaces (similar to Python callable objects)
 * - Lambda expressions with interfaces
 * 
 * Key concepts:
 * - Default methods in interfaces
 * - Functional interfaces
 * - Lambda expressions
 * - Method references
 */

// Interface with default methods (Java 8+ feature)
// Similar to Python mixins with concrete methods
interface Calculator {
    // Default method - provides implementation
    default double add(double a, double b) {
        return a + b;
    }
    
    default double subtract(double a, double b) {
        return a - b;
    }
    
    // Abstract method that must be implemented
    double multiply(double a, double b);
    
    // Another abstract method
    double divide(double a, double b);
}

// Functional interface (has exactly one abstract method)
// Similar to Python's callable objects or single-method interfaces
@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
}

// Interface for data processing with default methods
interface DataProcessor {
    // Default method for basic processing
    default String process(String data) {
        return "Processed: " + data;
    }
    
    // Default method for validation
    default boolean isValid(String data) {
        return data != null && !data.trim().isEmpty();
    }
    
    // Abstract method for custom processing
    String customProcess(String data);
}

// Concrete class implementing Calculator interface
class BasicCalculator implements Calculator {
    @Override
    public double multiply(double a, double b) {
        return a * b;
    }
    
    @Override
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}

// Advanced calculator that overrides default methods
class AdvancedCalculator implements Calculator {
    @Override
    public double add(double a, double b) {
        System.out.println("Advanced addition with logging");
        return a + b;
    }
    
    @Override
    public double multiply(double a, double b) {
        return a * b;
    }
    
    @Override
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}

// Class implementing DataProcessor
class TextProcessor implements DataProcessor {
    @Override
    public String customProcess(String data) {
        if (!isValid(data)) {
            return "Invalid data";
        }
        return data.toUpperCase();
    }
}

// Main class to demonstrate the examples
public class Example2 {
    public static void main(String[] args) {
        System.out.println("=== Interface Default Methods and Functional Interfaces ===\n");
        
        // Demonstrate default methods
        demonstrateDefaultMethods();
        
        // Demonstrate functional interfaces and lambdas
        demonstrateFunctionalInterfaces();
        
        // Demonstrate data processing with default methods
        demonstrateDataProcessing();
    }
    
    private static void demonstrateDefaultMethods() {
        System.out.println("1. Default Methods in Interfaces:");
        System.out.println("--------------------------------");
        
        Calculator basic = new BasicCalculator();
        Calculator advanced = new AdvancedCalculator();
        
        // Use default methods
        System.out.println("Basic calculator:");
        System.out.println("5 + 3 = " + basic.add(5, 3));
        System.out.println("5 - 3 = " + basic.subtract(5, 3));
        System.out.println("5 * 3 = " + basic.multiply(5, 3));
        System.out.println("6 / 2 = " + basic.divide(6, 2));
        
        System.out.println("\nAdvanced calculator:");
        System.out.println("5 + 3 = " + advanced.add(5, 3)); // Uses overridden method
        System.out.println("5 * 3 = " + advanced.multiply(5, 3));
        System.out.println();
    }
    
    private static void demonstrateFunctionalInterfaces() {
        System.out.println("2. Functional Interfaces and Lambda Expressions:");
        System.out.println("----------------------------------------------");
        
        // Lambda expressions with functional interface
        MathOperation addition = (a, b) -> a + b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation power = (a, b) -> Math.pow(a, b);
        
        // Use lambda expressions
        System.out.println("Lambda expressions:");
        System.out.println("10 + 5 = " + addition.operate(10, 5));
        System.out.println("4 * 7 = " + multiplication.operate(4, 7));
        System.out.println("2 ^ 8 = " + power.operate(2, 8));
        
        // Method reference example
        MathOperation max = Math::max;
        System.out.println("Max of 15 and 8 = " + max.operate(15, 8));
        
        // Array of operations
        MathOperation[] operations = {addition, multiplication, power, max};
        double x = 10, y = 3;
        
        System.out.println("\nAll operations on " + x + " and " + y + ":");
        for (int i = 0; i < operations.length; i++) {
            System.out.println("Operation " + (i + 1) + ": " + operations[i].operate(x, y));
        }
        System.out.println();
    }
    
    private static void demonstrateDataProcessing() {
        System.out.println("3. Data Processing with Default Methods:");
        System.out.println("----------------------------------------");
        
        DataProcessor processor = new TextProcessor();
        
        // Test with valid data
        String validData = "hello world";
        System.out.println("Valid data: '" + validData + "'");
        System.out.println("Default process: " + processor.process(validData));
        System.out.println("Custom process: " + processor.customProcess(validData));
        System.out.println("Is valid: " + processor.isValid(validData));
        
        // Test with invalid data
        String invalidData = "";
        System.out.println("\nInvalid data: '" + invalidData + "'");
        System.out.println("Default process: " + processor.process(invalidData));
        System.out.println("Custom process: " + processor.customProcess(invalidData));
        System.out.println("Is valid: " + processor.isValid(invalidData));
        
        // Test with null data
        String nullData = null;
        System.out.println("\nNull data: " + nullData);
        System.out.println("Default process: " + processor.process(nullData));
        System.out.println("Custom process: " + processor.customProcess(nullData));
        System.out.println("Is valid: " + processor.isValid(nullData));
    }
} 