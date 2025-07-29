/**
 * LoopControl.java
 * 
 * This program demonstrates loop control statements in Java:
 * - Break and continue statements
 * - Loop termination conditions
 * - Nested loop control
 * - Loop optimization techniques
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class LoopControl {
    
    public static void main(String[] args) {
        System.out.println("=== Loop Control Statements in Java ===\n");
        
        // Demonstrate basic break statement
        demonstrateBreak();
        
        // Demonstrate basic continue statement
        demonstrateContinue();
        
        // Demonstrate labeled break statement
        demonstrateLabeledBreak();
        
        // Demonstrate labeled continue statement
        demonstrateLabeledContinue();
        
        // Demonstrate nested loops with control statements
        demonstrateNestedLoops();
        
        // Demonstrate practical example with data processing
        demonstratePracticalExample();
    }
    
    /**
     * Demonstrates the basic break statement
     * In Python: break (same functionality)
     */
    public static void demonstrateBreak() {
        System.out.println("1. Basic Break Statement:");
        System.out.println("   Finding first number divisible by 7:");
        
        for (int i = 1; i <= 20; i++) {
            if (i % 7 == 0) {
                System.out.println("   Found: " + i + " (breaking loop)");
                break; // Exit the loop immediately
            }
            System.out.println("   Checking: " + i);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates the basic continue statement
     * In Python: continue (same functionality)
     */
    public static void demonstrateContinue() {
        System.out.println("2. Basic Continue Statement:");
        System.out.println("   Printing even numbers only:");
        
        for (int i = 1; i <= 10; i++) {
            if (i % 2 != 0) {
                continue; // Skip odd numbers, go to next iteration
            }
            System.out.println("   Even number: " + i);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates labeled break statement
     * Python equivalent: No direct equivalent - would need flag variables
     */
    public static void demonstrateLabeledBreak() {
        System.out.println("3. Labeled Break Statement:");
        System.out.println("   Breaking from nested loop:");
        
        // Label for the outer loop
        outerLoop: for (int i = 1; i <= 3; i++) {
            System.out.println("   Outer loop iteration: " + i);
            
            for (int j = 1; j <= 5; j++) {
                if (i == 2 && j == 3) {
                    System.out.println("   Breaking from inner loop when i=2, j=3");
                    break outerLoop; // Break from the labeled outer loop
                }
                System.out.println("     Inner loop: i=" + i + ", j=" + j);
            }
        }
        System.out.println();
    }
    
    /**
     * Demonstrates labeled continue statement
     * Python equivalent: No direct equivalent - would need complex logic
     */
    public static void demonstrateLabeledContinue() {
        System.out.println("4. Labeled Continue Statement:");
        System.out.println("   Continuing outer loop from inner loop:");
        
        // Label for the outer loop
        outerLoop: for (int i = 1; i <= 3; i++) {
            System.out.println("   Outer loop iteration: " + i);
            
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("   Continuing outer loop when i=2, j=2");
                    continue outerLoop; // Continue the labeled outer loop
                }
                System.out.println("     Inner loop: i=" + i + ", j=" + j);
            }
            System.out.println("   End of outer loop iteration: " + i);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates nested loops with various control statements
     * Shows practical use of break and continue in complex scenarios
     */
    public static void demonstrateNestedLoops() {
        System.out.println("5. Nested Loops with Control Statements:");
        System.out.println("   Processing a 2D array with conditions:");
        
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        
        // Find first negative number or break if not found
        searchLoop: for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < 0) {
                    System.out.println("   Found negative number: " + matrix[i][j] + " at [" + i + "][" + j + "]");
                    break searchLoop;
                }
                if (matrix[i][j] % 2 == 0) {
                    System.out.println("   Skipping even number: " + matrix[i][j]);
                    continue; // Skip even numbers in inner loop
                }
                System.out.println("   Processing odd number: " + matrix[i][j]);
            }
        }
        System.out.println("   No negative numbers found in matrix");
        System.out.println();
    }
    
    /**
     * Demonstrates a practical example using loop control
     * Simulates processing a list of grades with various conditions
     */
    public static void demonstratePracticalExample() {
        System.out.println("6. Practical Example - Grade Processing:");
        
        int[] grades = {85, 92, 78, 95, 88, 100, 76, 89, 94, 87};
        int sum = 0;
        int count = 0;
        boolean foundPerfect = false;
        
        System.out.println("   Processing grades: " + java.util.Arrays.toString(grades));
        
        // Process grades with various conditions
        for (int grade : grades) {
            // Skip invalid grades (less than 0 or greater than 100)
            if (grade < 0 || grade > 100) {
                System.out.println("   Skipping invalid grade: " + grade);
                continue;
            }
            
            // Check for perfect score
            if (grade == 100) {
                System.out.println("   Found perfect score: " + grade);
                foundPerfect = true;
                break; // Exit loop when perfect score is found
            }
            
            // Add valid grades to sum
            sum += grade;
            count++;
            System.out.println("   Added grade: " + grade + " (running sum: " + sum + ")");
        }
        
        if (foundPerfect) {
            System.out.println("   Stopped processing due to perfect score found");
        } else {
            double average = (double) sum / count;
            System.out.println("   Final average: " + String.format("%.2f", average));
        }
        System.out.println();
    }
} 