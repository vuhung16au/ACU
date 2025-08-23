package com.acu.bubblesort;

/**
 * A simple implementation of the Bubble Sort algorithm.
 */
public class BubbleSort {
    
    /**
     * Sorts an array of integers using the bubble sort algorithm.
     * 
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public int[] sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }
        
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
            
            // If no swapping occurred, array is already sorted
            if (!swapped) {
                break;
            }
        }
        
        return arr;
    }
    
    /**
     * Sorts an array of integers in descending order using bubble sort.
     * 
     * @param arr the array to be sorted
     * @return the sorted array in descending order
     */
    public int[] sortDescending(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }
        
        int n = arr.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // If no swapping occurred, array is already sorted
            if (!swapped) {
                break;
            }
        }
        
        return arr;
    }
    
    /**
     * Checks if an array is sorted in ascending order.
     * 
     * @param arr the array to check
     * @return true if the array is sorted, false otherwise
     */
    public boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }
        
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Prints an array to the console.
     * 
     * @param arr the array to print
     */
    public void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("Array is null");
            return;
        }
        
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Main method to demonstrate the bubble sort functionality.
     */
    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        
        System.out.println("=== JUnit 5 Bubble Sort Demo ===");
        System.out.println();
        
        // Test case 1: Normal array
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array:");
        bubbleSort.printArray(arr1);
        
        bubbleSort.sort(arr1);
        System.out.println("Sorted array:");
        bubbleSort.printArray(arr1);
        System.out.println();
        
        // Test case 2: Already sorted array
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Already sorted array:");
        bubbleSort.printArray(arr2);
        
        bubbleSort.sort(arr2);
        System.out.println("After sorting:");
        bubbleSort.printArray(arr2);
        System.out.println();
        
        // Test case 3: Array with duplicates
        int[] arr3 = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("Array with duplicates:");
        bubbleSort.printArray(arr3);
        
        bubbleSort.sort(arr3);
        System.out.println("Sorted array:");
        bubbleSort.printArray(arr3);
        System.out.println();
        
        // Test case 4: Descending sort
        int[] arr4 = {5, 2, 8, 1, 9};
        System.out.println("Array for descending sort:");
        bubbleSort.printArray(arr4);
        
        bubbleSort.sortDescending(arr4);
        System.out.println("Descending sorted array:");
        bubbleSort.printArray(arr4);
        System.out.println();
        
        // Test case 5: Empty array
        int[] arr5 = {};
        System.out.println("Empty array:");
        bubbleSort.printArray(arr5);
        
        bubbleSort.sort(arr5);
        System.out.println("After sorting:");
        bubbleSort.printArray(arr5);
        System.out.println();
        
        System.out.println("Run 'mvn test' to execute the JUnit 5 tests!");
    }
}
