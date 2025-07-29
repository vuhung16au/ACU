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
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Complex Loop Control Scenarios ===\n");
        
        demonstrateMatrixSearch();
        demonstrateDataValidation();
        demonstrateAlgorithmOptimization();
    }
    
    /**
     * Demonstrates matrix search with multiple exit conditions
     * Shows how labeled break can simplify complex nested loop logic
     */
    public static void demonstrateMatrixSearch() {
        System.out.println("1. Matrix Search with Multiple Conditions:");
        
        int[][] matrix = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20}
        };
        
        int target = 13;
        boolean found = false;
        
        // Search for target value with early exit
        searchLoop: for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    System.out.println("   Found " + target + " at position [" + i + "][" + j + "]");
                    found = true;
                    break searchLoop; // Exit both loops immediately
                }
                if (matrix[i][j] > target) {
                    System.out.println("   Value " + matrix[i][j] + " exceeds target, moving to next row");
                    continue searchLoop; // Skip rest of current row
                }
            }
        }
        
        if (!found) {
            System.out.println("   Target " + target + " not found in matrix");
        }
        System.out.println();
    }
    
    /**
     * Demonstrates data validation with continue statements
     * Shows how to process data while skipping invalid entries
     */
    public static void demonstrateDataValidation() {
        System.out.println("2. Data Validation with Continue:");
        
        String[] data = {"valid1", "invalid", "valid2", "", "valid3", null, "valid4"};
        int validCount = 0;
        int invalidCount = 0;
        
        System.out.println("   Processing data array: " + java.util.Arrays.toString(data));
        
        for (String item : data) {
            // Skip null or empty strings
            if (item == null || item.trim().isEmpty()) {
                System.out.println("   Skipping invalid item: '" + item + "'");
                invalidCount++;
                continue;
            }
            
            // Skip items that don't start with "valid"
            if (!item.startsWith("valid")) {
                System.out.println("   Skipping non-valid item: '" + item + "'");
                invalidCount++;
                continue;
            }
            
            // Process valid items
            System.out.println("   Processing valid item: '" + item + "'");
            validCount++;
        }
        
        System.out.println("   Summary: " + validCount + " valid items, " + invalidCount + " invalid items");
        System.out.println();
    }
    
    /**
     * Demonstrates algorithm optimization using break and continue
     * Shows how loop control can improve algorithm efficiency
     */
    public static void demonstrateAlgorithmOptimization() {
        System.out.println("3. Algorithm Optimization:");
        
        int[] numbers = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        int searchValue = 15;
        
        System.out.println("   Searching for " + searchValue + " in sorted array");
        System.out.println("   Array: " + java.util.Arrays.toString(numbers));
        
        // Optimized search: break early when value exceeds target
        boolean found = false;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == searchValue) {
                System.out.println("   Found " + searchValue + " at index " + i);
                found = true;
                break; // Exit early when found
            }
            if (numbers[i] > searchValue) {
                System.out.println("   Value " + numbers[i] + " exceeds target, stopping search");
                break; // Exit early since array is sorted
            }
        }
        
        if (!found) {
            System.out.println("   Value " + searchValue + " not found");
        }
        
        // Demonstrate continue with complex condition
        System.out.println("\n   Processing numbers with complex conditions:");
        for (int num : numbers) {
            // Skip numbers that are both even and divisible by 4
            if (num % 2 == 0 && num % 4 == 0) {
                System.out.println("   Skipping " + num + " (even and divisible by 4)");
                continue;
            }
            
            // Skip numbers greater than 15
            if (num > 15) {
                System.out.println("   Skipping " + num + " (greater than 15)");
                continue;
            }
            
            System.out.println("   Processing " + num);
        }
        System.out.println();
    }
} 