package com.acu.datastructure.bubblesort;

/**
 * Bubble Sort implementation following Test-Driven Development principles.
 * 
 * This class should be implemented AFTER writing the test cases.
 * Follow the TDD cycle: Red → Green → Refactor
 */
public class BubbleSort {
    
    /**
     * Sorts an array of integers using the bubble sort algorithm in ascending order.
     * 
     * @param arr the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException if the input array is null
     */
    public int[] sort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        
        if (arr.length <= 1) {
            return arr.clone(); // Return a copy to avoid modifying original
        }
        
        int[] result = arr.clone(); // Work on a copy
        int n = result.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (result[j] > result[j + 1]) {
                    // Swap elements
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // Early termination if no swaps occurred (array is already sorted)
            if (!swapped) {
                break;
            }
        }
        
        return result;
    }
    
    /**
     * Sorts an array of integers using the bubble sort algorithm in descending order.
     * 
     * @param arr the array to be sorted
     * @return the sorted array in descending order
     * @throws IllegalArgumentException if the input array is null
     */
    public int[] sortDescending(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        
        if (arr.length <= 1) {
            return arr.clone();
        }
        
        int[] result = arr.clone();
        int n = result.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (result[j] < result[j + 1]) {
                    // Swap elements
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // Early termination if no swaps occurred
            if (!swapped) {
                break;
            }
        }
        
        return result;
    }
    
    /**
     * Checks if an array is sorted in ascending order.
     * 
     * @param arr the array to check
     * @return true if the array is sorted in ascending order, false otherwise
     * @throws IllegalArgumentException if the input array is null
     */
    public boolean isSorted(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        
        if (arr.length <= 1) {
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
     * Prints an array to the console in a readable format.
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
}
