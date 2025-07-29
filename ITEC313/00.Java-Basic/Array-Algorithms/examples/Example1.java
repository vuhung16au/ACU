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
import java.util.Arrays;

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Basic Array Operations Example ===\n");
        
        // Creating arrays (fixed size in Java)
        // Python equivalent: list = [1, 2, 3, 4, 5]
        int[] numbers = {1, 2, 3, 4, 5};
        int[] scores = new int[5]; // Initialize with zeros
        
        System.out.println("Original array: " + Arrays.toString(numbers));
        
        // Accessing elements (0-indexed like Python)
        System.out.println("First element: " + numbers[0]);
        System.out.println("Last element: " + numbers[numbers.length - 1]);
        
        // Modifying elements
        numbers[2] = 10;
        System.out.println("After modification: " + Arrays.toString(numbers));
        
        // Array length (Python equivalent: len(list))
        System.out.println("Array length: " + numbers.length);
        
        // Iterating through array
        System.out.println("\nIterating through array:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Index " + i + ": " + numbers[i]);
        }
        
        // Enhanced for loop (Python equivalent: for item in list:)
        System.out.println("\nEnhanced for loop:");
        for (int num : numbers) {
            System.out.println("Value: " + num);
        }
        
        // Copying arrays
        int[] copy = numbers.clone(); // Shallow copy
        System.out.println("\nCopied array: " + Arrays.toString(copy));
        
        // Comparing arrays
        boolean areEqual = Arrays.equals(numbers, copy);
        System.out.println("Arrays are equal: " + areEqual);
        
        // Filling arrays
        Arrays.fill(scores, 100);
        System.out.println("Filled array: " + Arrays.toString(scores));
        
        // Demonstrating array bounds
        demonstrateArrayBounds();
    }
    
    /**
     * Demonstrates array bounds and error handling
     * Python equivalent: IndexError when accessing out of bounds
     */
    public static void demonstrateArrayBounds() {
        System.out.println("\n=== Array Bounds Demonstration ===");
        
        int[] arr = {10, 20, 30, 40, 50};
        
        try {
            // Valid access
            System.out.println("Valid access: " + arr[2]);
            
            // Invalid access - will throw ArrayIndexOutOfBoundsException
            System.out.println("Invalid access: " + arr[10]);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Array index out of bounds!");
            System.out.println("Array length: " + arr.length);
            System.out.println("Valid indices: 0 to " + (arr.length - 1));
        }
        
        // Safe access method
        System.out.println("\nSafe access demonstration:");
        System.out.println("Safe access at index 2: " + safeGet(arr, 2));
        System.out.println("Safe access at index 10: " + safeGet(arr, 10));
    }
    
    /**
     * Safe array access method
     * Python equivalent: list[index] if 0 <= index < len(list) else None
     */
    public static Integer safeGet(int[] arr, int index) {
        if (index >= 0 && index < arr.length) {
            return arr[index];
        } else {
            return null; // Return null for invalid index
        }
    }
} 