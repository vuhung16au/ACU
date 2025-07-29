/**
 * Example2.java
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

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Intermediate Array Algorithms Example ===\n");
        
        // Sample data
        int[] data = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("Original data: " + Arrays.toString(data));
        
        // Demonstrate different sorting approaches
        demonstrateSortingApproaches(data.clone());
        
        // Demonstrate searching techniques
        demonstrateSearchingTechniques(data.clone());
        
        // Demonstrate array transformations
        demonstrateArrayTransformations(data.clone());
        
        // Demonstrate performance comparison
        demonstratePerformanceComparison();
    }
    
    /**
     * Demonstrates different sorting approaches
     * Python equivalent: sorted(list) or list.sort()
     */
    public static void demonstrateSortingApproaches(int[] arr) {
        System.out.println("\n=== Sorting Approaches ===");
        
        // 1. Manual bubble sort
        int[] bubbleArray = arr.clone();
        System.out.println("Bubble Sort:");
        System.out.println("Before: " + Arrays.toString(bubbleArray));
        bubbleSortWithSteps(bubbleArray);
        System.out.println("After:  " + Arrays.toString(bubbleArray));
        
        // 2. Manual selection sort
        int[] selectionArray = arr.clone();
        System.out.println("\nSelection Sort:");
        System.out.println("Before: " + Arrays.toString(selectionArray));
        selectionSortWithSteps(selectionArray);
        System.out.println("After:  " + Arrays.toString(selectionArray));
        
        // 3. Using Java's built-in sort (like Python's sorted())
        int[] builtinArray = arr.clone();
        System.out.println("\nBuilt-in Sort (Arrays.sort):");
        System.out.println("Before: " + Arrays.toString(builtinArray));
        Arrays.sort(builtinArray);
        System.out.println("After:  " + Arrays.toString(builtinArray));
    }
    
    /**
     * Demonstrates searching techniques
     * Python equivalent: list.index(), 'in' operator, or bisect module
     */
    public static void demonstrateSearchingTechniques(int[] arr) {
        System.out.println("\n=== Searching Techniques ===");
        
        // Sort array first for binary search
        Arrays.sort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
        
        int target = 7;
        
        // 1. Linear search
        System.out.println("\nLinear Search for " + target + ":");
        int linearIndex = linearSearch(arr, target);
        if (linearIndex != -1) {
            System.out.println("Found at index: " + linearIndex);
        } else {
            System.out.println("Not found");
        }
        
        // 2. Binary search
        System.out.println("\nBinary Search for " + target + ":");
        int binaryIndex = binarySearch(arr, target);
        if (binaryIndex != -1) {
            System.out.println("Found at index: " + binaryIndex);
        } else {
            System.out.println("Not found");
        }
        
        // 3. Count occurrences
        int[] countArray = {1, 2, 2, 3, 2, 4, 5, 2};
        int searchValue = 2;
        int count = countOccurrences(countArray, searchValue);
        System.out.println("\nOccurrences of " + searchValue + " in " + 
                          Arrays.toString(countArray) + ": " + count);
    }
    
    /**
     * Demonstrates array transformations
     * Python equivalent: list comprehensions, map(), filter()
     */
    public static void demonstrateArrayTransformations(int[] arr) {
        System.out.println("\n=== Array Transformations ===");
        
        // 1. Filter even numbers
        System.out.println("Original: " + Arrays.toString(arr));
        int[] evens = filterEvenNumbers(arr);
        System.out.println("Even numbers: " + Arrays.toString(evens));
        
        // 2. Square all numbers
        int[] squared = squareNumbers(arr);
        System.out.println("Squared: " + Arrays.toString(squared));
        
        // 3. Find numbers greater than average
        double avg = calculateAverage(arr);
        int[] aboveAvg = filterAboveAverage(arr, avg);
        System.out.println("Above average (" + avg + "): " + Arrays.toString(aboveAvg));
        
        // 4. Rotate array
        int[] rotated = rotateArray(arr, 3);
        System.out.println("Rotated by 3: " + Arrays.toString(rotated));
    }
    
    /**
     * Demonstrates performance comparison between algorithms
     */
    public static void demonstratePerformanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Create large array for testing
        int size = 1000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = (int)(Math.random() * 1000);
        }
        
        System.out.println("Testing with array of size: " + size);
        
        // Test bubble sort performance
        long startTime = System.currentTimeMillis();
        int[] bubbleArray = largeArray.clone();
        bubbleSort(bubbleArray);
        long bubbleTime = System.currentTimeMillis() - startTime;
        
        // Test built-in sort performance
        startTime = System.currentTimeMillis();
        int[] builtinArray = largeArray.clone();
        Arrays.sort(builtinArray);
        long builtinTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Bubble Sort time: " + bubbleTime + "ms");
        System.out.println("Built-in Sort time: " + builtinTime + "ms");
        System.out.println("Built-in sort is " + (bubbleTime / (double)builtinTime) + "x faster");
    }
    
    // ========== HELPER METHODS ==========
    
    /**
     * Bubble sort with step-by-step output
     */
    public static void bubbleSortWithSteps(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
    
    /**
     * Selection sort with step-by-step output
     */
    public static void selectionSortWithSteps(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    
    /**
     * Linear search implementation
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Binary search implementation (requires sorted array)
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            }
            
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * Count occurrences of a value in array
     * Python equivalent: list.count(value)
     */
    public static int countOccurrences(int[] arr, int value) {
        int count = 0;
        for (int num : arr) {
            if (num == value) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Filter even numbers from array
     * Python equivalent: [x for x in list if x % 2 == 0]
     */
    public static int[] filterEvenNumbers(int[] arr) {
        int count = 0;
        for (int num : arr) {
            if (num % 2 == 0) {
                count++;
            }
        }
        
        int[] result = new int[count];
        int index = 0;
        for (int num : arr) {
            if (num % 2 == 0) {
                result[index++] = num;
            }
        }
        return result;
    }
    
    /**
     * Square all numbers in array
     * Python equivalent: [x**2 for x in list]
     */
    public static int[] squareNumbers(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] * arr[i];
        }
        return result;
    }
    
    /**
     * Calculate average of array
     * Python equivalent: sum(list) / len(list)
     */
    public static double calculateAverage(int[] arr) {
        if (arr.length == 0) return 0.0;
        
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return (double) sum / arr.length;
    }
    
    /**
     * Filter numbers above average
     * Python equivalent: [x for x in list if x > avg]
     */
    public static int[] filterAboveAverage(int[] arr, double average) {
        int count = 0;
        for (int num : arr) {
            if (num > average) {
                count++;
            }
        }
        
        int[] result = new int[count];
        int index = 0;
        for (int num : arr) {
            if (num > average) {
                result[index++] = num;
            }
        }
        return result;
    }
    
    /**
     * Rotate array by given number of positions
     * Python equivalent: list[shift:] + list[:shift]
     */
    public static int[] rotateArray(int[] arr, int shift) {
        int n = arr.length;
        shift = shift % n; // Handle shifts larger than array size
        
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int newIndex = (i + shift) % n;
            result[newIndex] = arr[i];
        }
        return result;
    }
    
    /**
     * Simple bubble sort for performance testing
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
} 