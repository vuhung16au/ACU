/**
 * ArrayAlgorithms.java
 * 
 * This program demonstrates various array algorithms in Java:
 * - Sorting algorithms (bubble, selection, insertion)
 * - Searching algorithms (linear, binary)
 * - Array manipulation techniques
 * - Performance analysis of algorithms
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.Arrays;
import java.util.Scanner;

public class ArrayAlgorithms {
    
    public static void main(String[] args) {
        System.out.println("=== Array Algorithms Demonstration ===\n");
        
        // Initialize sample data
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        int[] sortedNumbers = {11, 12, 22, 25, 34, 64, 90};
        
        System.out.println("Original array: " + Arrays.toString(numbers));
        System.out.println("Sorted array: " + Arrays.toString(sortedNumbers));
        
        // Demonstrate sorting algorithms
        demonstrateSortingAlgorithms(numbers.clone());
        
        // Demonstrate searching algorithms
        demonstrateSearchingAlgorithms(sortedNumbers);
        
        // Demonstrate array manipulation
        demonstrateArrayManipulation(numbers.clone());
        
        // Interactive demonstration
        interactiveDemo();
    }
    
    /**
     * Demonstrates various sorting algorithms
     * Python equivalent: list.sort() or sorted(list)
     */
    public static void demonstrateSortingAlgorithms(int[] arr) {
        System.out.println("\n=== Sorting Algorithms ===");
        
        // Bubble Sort
        int[] bubbleArray = arr.clone();
        System.out.println("Bubble Sort:");
        System.out.println("Before: " + Arrays.toString(bubbleArray));
        bubbleSort(bubbleArray);
        System.out.println("After:  " + Arrays.toString(bubbleArray));
        
        // Selection Sort
        int[] selectionArray = arr.clone();
        System.out.println("\nSelection Sort:");
        System.out.println("Before: " + Arrays.toString(selectionArray));
        selectionSort(selectionArray);
        System.out.println("After:  " + Arrays.toString(selectionArray));
        
        // Insertion Sort
        int[] insertionArray = arr.clone();
        System.out.println("\nInsertion Sort:");
        System.out.println("Before: " + Arrays.toString(insertionArray));
        insertionSort(insertionArray);
        System.out.println("After:  " + Arrays.toString(insertionArray));
        
        // Quick Sort
        int[] quickArray = arr.clone();
        System.out.println("\nQuick Sort:");
        System.out.println("Before: " + Arrays.toString(quickArray));
        quickSort(quickArray, 0, quickArray.length - 1);
        System.out.println("After:  " + Arrays.toString(quickArray));
    }
    
    /**
     * Demonstrates searching algorithms
     * Python equivalent: list.index() or 'in' operator
     */
    public static void demonstrateSearchingAlgorithms(int[] arr) {
        System.out.println("\n=== Searching Algorithms ===");
        
        int target = 25;
        
        // Linear Search
        System.out.println("Linear Search for " + target + ":");
        int linearResult = linearSearch(arr, target);
        if (linearResult != -1) {
            System.out.println("Found at index: " + linearResult);
        } else {
            System.out.println("Not found");
        }
        
        // Binary Search (requires sorted array)
        System.out.println("\nBinary Search for " + target + ":");
        int binaryResult = binarySearch(arr, target);
        if (binaryResult != -1) {
            System.out.println("Found at index: " + binaryResult);
        } else {
            System.out.println("Not found");
        }
        
        // Search for non-existent element
        System.out.println("\nSearching for 99 (not in array):");
        int notFound = binarySearch(arr, 99);
        if (notFound != -1) {
            System.out.println("Found at index: " + notFound);
        } else {
            System.out.println("Not found");
        }
    }
    
    /**
     * Demonstrates array manipulation techniques
     * Python equivalent: list operations like reverse(), slicing, etc.
     */
    public static void demonstrateArrayManipulation(int[] arr) {
        System.out.println("\n=== Array Manipulation ===");
        
        // Reverse array
        System.out.println("Original: " + Arrays.toString(arr));
        reverseArray(arr);
        System.out.println("Reversed: " + Arrays.toString(arr));
        
        // Find maximum and minimum
        int max = findMaximum(arr);
        int min = findMinimum(arr);
        System.out.println("Maximum: " + max + ", Minimum: " + min);
        
        // Calculate sum and average
        int sum = calculateSum(arr);
        double average = calculateAverage(arr);
        System.out.println("Sum: " + sum + ", Average: " + average);
        
        // Remove duplicates (create new array)
        int[] withDuplicates = {1, 2, 2, 3, 4, 4, 5};
        System.out.println("With duplicates: " + Arrays.toString(withDuplicates));
        int[] withoutDuplicates = removeDuplicates(withDuplicates);
        System.out.println("Without duplicates: " + Arrays.toString(withoutDuplicates));
    }
    
    /**
     * Interactive demonstration with user input
     */
    public static void interactiveDemo() {
        System.out.println("\n=== Interactive Demonstration ===");
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter numbers separated by spaces: ");
        String input = scanner.nextLine();
        
        // Parse input string to array
        String[] parts = input.trim().split("\\s+");
        int[] userArray = new int[parts.length];
        
        try {
            for (int i = 0; i < parts.length; i++) {
                userArray[i] = Integer.parseInt(parts[i]);
            }
            
            System.out.println("Your array: " + Arrays.toString(userArray));
            
            // Sort the array
            int[] sorted = userArray.clone();
            quickSort(sorted, 0, sorted.length - 1);
            System.out.println("Sorted: " + Arrays.toString(sorted));
            
            System.out.print("Enter a number to search for: ");
            int searchTarget = scanner.nextInt();
            
            int foundIndex = binarySearch(sorted, searchTarget);
            if (foundIndex != -1) {
                System.out.println("Found " + searchTarget + " at index " + foundIndex);
            } else {
                System.out.println(searchTarget + " not found in the array");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers only");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    // ========== SORTING ALGORITHMS ==========
    
    /**
     * Bubble Sort - O(n²) time complexity
     * Python equivalent: Not directly available, but similar to nested loops
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no swapping occurred, array is sorted
            if (!swapped) break;
        }
    }
    
    /**
     * Selection Sort - O(n²) time complexity
     * Python equivalent: Not directly available
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap found minimum with first element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    
    /**
     * Insertion Sort - O(n²) time complexity
     * Python equivalent: Not directly available
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Move elements greater than key one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Quick Sort - O(n log n) average time complexity
     * Python equivalent: list.sort() or sorted(list) use Timsort (hybrid)
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    /**
     * Partition function for Quick Sort
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    // ========== SEARCHING ALGORITHMS ==========
    
    /**
     * Linear Search - O(n) time complexity
     * Python equivalent: list.index() or 'in' operator
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    /**
     * Binary Search - O(log n) time complexity (requires sorted array)
     * Python equivalent: bisect module or list.index() on sorted list
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
        return -1; // Not found
    }
    
    // ========== ARRAY MANIPULATION ==========
    
    /**
     * Reverse array in-place
     * Python equivalent: list.reverse() or list[::-1]
     */
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Find maximum value in array
     * Python equivalent: max(list)
     */
    public static int findMaximum(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    /**
     * Find minimum value in array
     * Python equivalent: min(list)
     */
    public static int findMinimum(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    
    /**
     * Calculate sum of array elements
     * Python equivalent: sum(list)
     */
    public static int calculateSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
    
    /**
     * Calculate average of array elements
     * Python equivalent: sum(list) / len(list)
     */
    public static double calculateAverage(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        return (double) calculateSum(arr) / arr.length;
    }
    
    /**
     * Remove duplicates from array (returns new array)
     * Python equivalent: list(set(list)) or list(dict.fromkeys(list))
     */
    public static int[] removeDuplicates(int[] arr) {
        if (arr.length <= 1) {
            return arr.clone();
        }
        
        // Sort array first to group duplicates
        int[] sorted = arr.clone();
        quickSort(sorted, 0, sorted.length - 1);
        
        // Count unique elements
        int uniqueCount = 1;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] != sorted[i - 1]) {
                uniqueCount++;
            }
        }
        
        // Create result array
        int[] result = new int[uniqueCount];
        result[0] = sorted[0];
        int index = 1;
        
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] != sorted[i - 1]) {
                result[index++] = sorted[i];
            }
        }
        
        return result;
    }
} 