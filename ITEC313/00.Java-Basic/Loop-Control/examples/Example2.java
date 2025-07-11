/**
 * Example 2: Labeled Break and Continue Statements
 * 
 * This example demonstrates the use of labeled break and continue
 * statements in nested loop scenarios.
 * 
 * Python comparison:
 * - No direct equivalent in Python
 * - Python would require flag variables or complex logic
 */
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Labeled Break and Continue ===\n");
        
        demonstrateLabeledBreak();
        demonstrateLabeledContinue();
    }
    
    /**
     * Demonstrates labeled break statement
     * Breaks from outer loop when a specific condition is met in inner loop
     */
    public static void demonstrateLabeledBreak() {
        System.out.println("Labeled Break Example:");
        System.out.println("Breaking from outer loop when inner loop finds target:");
        
        // Label for the outer loop
        outerLoop: for (int row = 1; row <= 4; row++) {
            System.out.println("Row " + row + ":");
            
            for (int col = 1; col <= 4; col++) {
                if (row == 3 && col == 2) {
                    System.out.println("  Found target at row " + row + ", col " + col);
                    System.out.println("  Breaking from outer loop!");
                    break outerLoop; // Break from the labeled outer loop
                }
                System.out.print("  " + col);
            }
            System.out.println();
        }
        System.out.println("Outer loop terminated\n");
    }
    
    /**
     * Demonstrates labeled continue statement
     * Continues outer loop when a specific condition is met in inner loop
     */
    public static void demonstrateLabeledContinue() {
        System.out.println("Labeled Continue Example:");
        System.out.println("Continuing outer loop when inner loop finds skip condition:");
        
        // Label for the outer loop
        outerLoop: for (int i = 1; i <= 5; i++) {
            System.out.println("Outer iteration " + i + ":");
            
            for (int j = 1; j <= 3; j++) {
                if (i == 3 && j == 2) {
                    System.out.println("  Skipping rest of inner loop for iteration " + i);
                    continue outerLoop; // Continue the labeled outer loop
                }
                System.out.println("    Inner: " + j);
            }
            System.out.println("  Completed inner loop for iteration " + i);
        }
        System.out.println();
    }
} 