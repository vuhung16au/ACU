/**
 * Example2.java
 * 
 * Enhanced For Loop Examples
 * Demonstrates for-each loops and array iteration
 * 
 * Key concepts:
 * - Enhanced for loop syntax: for (Type item : collection)
 * - Array iteration patterns
 * - String array processing
 * - Numeric array operations
 * 
 * Python comparison:
 * - Python: for item in collection:
 * - Java: for (Type item : collection)
 */

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Enhanced For Loops ===\n");
        
        // Example 1: String array iteration
        System.out.println("1. Iterating over string array:");
        String[] colors = {"red", "green", "blue", "yellow", "purple"};
        for (String color : colors) {
            System.out.println("   Color: " + color);
        }
        
        // Example 2: Numeric array iteration
        System.out.println("\n2. Iterating over numeric array:");
        int[] numbers = {10, 25, 30, 45, 60};
        for (int number : numbers) {
            System.out.println("   Number: " + number);
        }
        
        // Example 3: Array processing with enhanced for loop
        System.out.println("\n3. Processing array elements:");
        double[] prices = {19.99, 29.99, 39.99, 49.99};
        double total = 0.0;
        for (double price : prices) {
            total += price;
            System.out.println("   Price: $" + price);
        }
        System.out.println("   Total: $" + total);
        
        // Example 4: Finding maximum value
        System.out.println("\n4. Finding maximum value:");
        int[] scores = {85, 92, 78, 96, 88, 91};
        int maxScore = scores[0]; // Start with first element
        for (int score : scores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }
        System.out.println("   Maximum score: " + maxScore);
        
        // Example 5: Counting occurrences
        System.out.println("\n5. Counting even numbers:");
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int evenCount = 0;
        for (int value : values) {
            if (value % 2 == 0) {
                evenCount++;
            }
        }
        System.out.println("   Even numbers count: " + evenCount);
        
        // Example 6: String processing
        System.out.println("\n6. String processing:");
        String[] words = {"hello", "world", "java", "programming"};
        for (String word : words) {
            System.out.println("   Word: " + word + " (length: " + word.length() + ")");
        }
        
        // Example 7: Conditional processing
        System.out.println("\n7. Conditional processing:");
        int[] grades = {65, 85, 95, 75, 90, 80};
        System.out.println("   Grades above 80:");
        for (int grade : grades) {
            if (grade > 80) {
                System.out.println("   High grade: " + grade);
            }
        }
        
        // Example 8: Array transformation
        System.out.println("\n8. Array transformation (doubling values):");
        int[] original = {1, 2, 3, 4, 5};
        System.out.println("   Original values:");
        for (int num : original) {
            System.out.println("   " + num);
        }
        System.out.println("   Doubled values:");
        for (int num : original) {
            System.out.println("   " + (num * 2));
        }
        
        System.out.println("\n=== Example 2 Complete ===");
    }
} 