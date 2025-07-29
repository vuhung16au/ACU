/**
 * Example1.java
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
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Print Debugging ===\n");
        
        // Example 1: Debugging a simple calculation
        debugSimpleCalculation();
        
        // Example 2: Debugging array processing
        debugArrayProcessing();
        
        // Example 3: Debugging method calls
        debugMethodCalls();
    }
    
    /**
     * Demonstrates debugging a simple calculation
     */
    private static void debugSimpleCalculation() {
        System.out.println("--- Debugging Simple Calculation ---");
        
        int a = 10;
        int b = 5;
        
        // Debug: Print initial values
        System.out.println("Debug: a = " + a + ", b = " + b);
        
        int sum = a + b;
        System.out.println("Debug: sum = " + sum);
        
        int difference = a - b;
        System.out.println("Debug: difference = " + difference);
        
        int product = a * b;
        System.out.println("Debug: product = " + product);
        
        // Debug: Division with potential precision loss
        double quotient = (double) a / b; // Cast to double for precise division
        System.out.println("Debug: quotient = " + quotient);
        
        System.out.println("Calculation complete!\n");
    }
    
    /**
     * Demonstrates debugging array processing
     */
    private static void debugArrayProcessing() {
        System.out.println("--- Debugging Array Processing ---");
        
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        // Debug: Print array length
        System.out.println("Debug: Array length = " + numbers.length);
        
        // Debug: Print array contents
        System.out.print("Debug: Array contents = [");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i]);
            if (i < numbers.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        // Debug: Find sum and average
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            System.out.println("Debug: After adding " + numbers[i] + ", sum = " + sum);
        }
        
        double average = (double) sum / numbers.length;
        System.out.println("Debug: Final sum = " + sum);
        System.out.println("Debug: Average = " + average);
        
        System.out.println("Array processing complete!\n");
    }
    
    /**
     * Demonstrates debugging method calls
     */
    private static void debugMethodCalls() {
        System.out.println("--- Debugging Method Calls ---");
        
        // Debug: Method entry
        System.out.println("Debug: Entering debugMethodCalls()");
        
        int result1 = calculateSquare(5);
        System.out.println("Debug: calculateSquare(5) returned " + result1);
        
        int result2 = calculateCube(3);
        System.out.println("Debug: calculateCube(3) returned " + result2);
        
        String result3 = formatNumber(42);
        System.out.println("Debug: formatNumber(42) returned \"" + result3 + "\"");
        
        // Debug: Method exit
        System.out.println("Debug: Exiting debugMethodCalls()");
        System.out.println("Method calls complete!\n");
    }
    
    /**
     * Helper method to calculate square
     */
    private static int calculateSquare(int number) {
        System.out.println("Debug: calculateSquare() called with number = " + number);
        int result = number * number;
        System.out.println("Debug: calculateSquare() returning " + result);
        return result;
    }
    
    /**
     * Helper method to calculate cube
     */
    private static int calculateCube(int number) {
        System.out.println("Debug: calculateCube() called with number = " + number);
        int result = number * number * number;
        System.out.println("Debug: calculateCube() returning " + result);
        return result;
    }
    
    /**
     * Helper method to format a number as string
     */
    private static String formatNumber(int number) {
        System.out.println("Debug: formatNumber() called with number = " + number);
        String result = "Number: " + number;
        System.out.println("Debug: formatNumber() returning \"" + result + "\"");
        return result;
    }
} 