/**
 * Example 1: Basic Break and Continue Statements
 * 
 * This example demonstrates the fundamental usage of break and continue
 * statements in simple loop scenarios.
 * 
 * Python comparison:
 * - break: Same as Python's break
 * - continue: Same as Python's continue
 */
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Break and Continue ===\n");
        
        demonstrateBreak();
        demonstrateContinue();
    }
    
    /**
     * Demonstrates basic break statement
     * Finds the first number greater than 50 in a range
     */
    public static void demonstrateBreak() {
        System.out.println("Break Example - Finding first number > 50:");
        
        for (int i = 1; i <= 100; i++) {
            if (i > 50) {
                System.out.println("Found: " + i);
                break; // Exit loop when condition is met
            }
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }
    
    /**
     * Demonstrates basic continue statement
     * Prints only numbers that are not multiples of 3
     */
    public static void demonstrateContinue() {
        System.out.println("Continue Example - Skipping multiples of 3:");
        
        for (int i = 1; i <= 20; i++) {
            if (i % 3 == 0) {
                continue; // Skip multiples of 3
            }
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }
} 