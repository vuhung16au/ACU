/**
 * Arrays Basic Demo
 * 
 * This program demonstrates fundamental array concepts in Java:
 * - Array declaration and initialization
 * - Array access and modification
 * - Common array operations
 * - Array traversal techniques
 * - Array utility methods
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 * Date: July 11, 2025
 */

import java.util.Arrays;
import java.util.Scanner;

public class ArraysBasic {
    
    public static void main(String[] args) {
        System.out.println("=== Arrays Basic Demo ===\n");
        
        // Demonstrate array declaration and initialization
        demonstrateArrayDeclaration();
        
        // Demonstrate array access and modification
        demonstrateArrayAccess();
        
        // Demonstrate array traversal
        demonstrateArrayTraversal();
        
        // Demonstrate common array operations
        demonstrateArrayOperations();
        
        // Interactive array example
        Scanner scanner = new Scanner(System.in);
        interactiveArrayExample(scanner);
        scanner.close();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    public static void demonstrateArrayDeclaration() {
        System.out.println("1. ARRAY DECLARATION AND INITIALIZATION");
        System.out.println("=======================================");
        
        // Different ways to declare arrays
        int[] numbers1 = new int[5];                    // Declaration with size
        int[] numbers2 = {10, 20, 30, 40, 50};         // Declaration with values
        int[] numbers3 = new int[]{1, 2, 3, 4, 5};     // Alternative syntax
        
        // Array of strings
        String[] names = {"Alice", "Bob", "Carol", "David"};
        
        // Array of doubles
        double[] prices = new double[3];
        prices[0] = 12.99;
        prices[1] = 25.50;
        prices[2] = 8.75;
        
        // Display array information
        System.out.printf("numbers1 length: %d%n", numbers1.length);
        System.out.printf("numbers2: %s%n", Arrays.toString(numbers2));
        System.out.printf("numbers3: %s%n", Arrays.toString(numbers3));
        System.out.printf("names: %s%n", Arrays.toString(names));
        System.out.printf("prices: %s%n", Arrays.toString(prices));
        
        // Array of characters
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        System.out.printf("vowels: %s%n", Arrays.toString(vowels));
        
        // Boolean array
        boolean[] flags = new boolean[4]; // Default: all false
        flags[1] = true;
        flags[3] = true;
        System.out.printf("flags: %s%n", Arrays.toString(flags));
        
        System.out.println();
    }
    
    public static void demonstrateArrayAccess() {
        System.out.println("2. ARRAY ACCESS AND MODIFICATION");
        System.out.println("================================");
        
        int[] scores = {85, 92, 78, 96, 88};
        System.out.printf("Original scores: %s%n", Arrays.toString(scores));
        
        // Access individual elements
        System.out.printf("First score: %d%n", scores[0]);
        System.out.printf("Last score: %d%n", scores[scores.length - 1]);
        System.out.printf("Middle score: %d%n", scores[scores.length / 2]);
        
        // Modify elements
        scores[2] = 82; // Change third element
        System.out.printf("After modifying third score: %s%n", Arrays.toString(scores));
        
        // Bounds checking demonstration
        System.out.println("\nArray bounds information:");
        System.out.printf("Valid indices: 0 to %d%n", scores.length - 1);
        System.out.printf("Array length: %d%n", scores.length);
        
        // Safe array access method
        int index = 10;
        int value = getArrayElementSafely(scores, index);
        if (value != -1) {
            System.out.printf("scores[%d] = %d%n", index, value);
        } else {
            System.out.printf("Index %d is out of bounds%n", index);
        }
        
        System.out.println();
    }
    
    public static void demonstrateArrayTraversal() {
        System.out.println("3. ARRAY TRAVERSAL TECHNIQUES");
        System.out.println("=============================");
        
        String[] subjects = {"Math", "Science", "English", "History", "Art"};
        
        // Traditional for loop
        System.out.println("Using traditional for loop:");
        for (int i = 0; i < subjects.length; i++) {
            System.out.printf("  %d: %s%n", i + 1, subjects[i]);
        }
        
        // Enhanced for loop (for-each)
        System.out.println("\nUsing enhanced for loop:");
        for (String subject : subjects) {
            System.out.println("  Subject: " + subject);
        }
        
        // Reverse traversal
        System.out.println("\nReverse order:");
        for (int i = subjects.length - 1; i >= 0; i--) {
            System.out.printf("  %s", subjects[i]);
            if (i > 0) System.out.print(" -> ");
        }
        System.out.println();
        
        // While loop traversal
        System.out.println("\nUsing while loop:");
        int index = 0;
        while (index < subjects.length) {
            System.out.printf("  Position %d: %s%n", index, subjects[index]);
            index++;
        }
        
        System.out.println();
    }
    
    public static void demonstrateArrayOperations() {
        System.out.println("4. COMMON ARRAY OPERATIONS");
        System.out.println("==========================");
        
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        System.out.printf("Original array: %s%n", Arrays.toString(numbers));
        
        // Find maximum and minimum
        int max = findMaximum(numbers);
        int min = findMinimum(numbers);
        System.out.printf("Maximum: %d%n", max);
        System.out.printf("Minimum: %d%n", min);
        
        // Calculate sum and average
        int sum = calculateSum(numbers);
        double average = calculateAverage(numbers);
        System.out.printf("Sum: %d%n", sum);
        System.out.printf("Average: %.2f%n", average);
        
        // Search for an element
        int searchValue = 25;
        int position = linearSearch(numbers, searchValue);
        if (position != -1) {
            System.out.printf("Found %d at index %d%n", searchValue, position);
        } else {
            System.out.printf("%d not found in array%n", searchValue);
        }
        
        // Count occurrences
        int[] duplicates = {1, 2, 3, 2, 4, 2, 5};
        int countValue = 2;
        int count = countOccurrences(duplicates, countValue);
        System.out.printf("Number %d appears %d times in %s%n", 
                         countValue, count, Arrays.toString(duplicates));
        
        // Copy array
        int[] copy = Arrays.copyOf(numbers, numbers.length);
        System.out.printf("Copied array: %s%n", Arrays.toString(copy));
        
        // Sort array (creates new sorted copy)
        int[] sorted = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(sorted);
        System.out.printf("Sorted array: %s%n", Arrays.toString(sorted));
        System.out.printf("Original unchanged: %s%n", Arrays.toString(numbers));
        
        System.out.println();
    }
    
    public static void interactiveArrayExample(Scanner scanner) {
        System.out.println("5. INTERACTIVE ARRAY EXAMPLE");
        System.out.println("============================");
        
        System.out.print("Enter the size of the array: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int size = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (size <= 0 || size > 20) {
            System.out.println("Array size must be between 1 and 20.");
            return;
        }
        
        int[] userArray = new int[size];
        
        // Input array elements
        System.out.printf("Enter %d integers:%n", size);
        for (int i = 0; i < size; i++) {
            System.out.printf("Element %d: ", i + 1);
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid integer: ");
                scanner.next();
            }
            userArray[i] = scanner.nextInt();
        }
        scanner.nextLine(); // Consume newline
        
        // Display array
        System.out.printf("Your array: %s%n", Arrays.toString(userArray));
        
        // Perform operations
        System.out.printf("Sum: %d%n", calculateSum(userArray));
        System.out.printf("Average: %.2f%n", calculateAverage(userArray));
        System.out.printf("Maximum: %d%n", findMaximum(userArray));
        System.out.printf("Minimum: %d%n", findMinimum(userArray));
        
        // Create sorted version
        int[] sortedArray = Arrays.copyOf(userArray, userArray.length);
        Arrays.sort(sortedArray);
        System.out.printf("Sorted: %s%n", Arrays.toString(sortedArray));
        
        System.out.println();
    }
    
    // Utility methods
    
    public static int getArrayElementSafely(int[] array, int index) {
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return -1; // Error indicator
    }
    
    public static int findMaximum(int[] array) {
        if (array.length == 0) return Integer.MIN_VALUE;
        
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    public static int findMinimum(int[] array) {
        if (array.length == 0) return Integer.MAX_VALUE;
        
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
    
    public static int calculateSum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }
    
    public static double calculateAverage(int[] array) {
        if (array.length == 0) return 0.0;
        return (double) calculateSum(array) / array.length;
    }
    
    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    public static int countOccurrences(int[] array, int target) {
        int count = 0;
        for (int value : array) {
            if (value == target) {
                count++;
            }
        }
        return count;
    }
}
