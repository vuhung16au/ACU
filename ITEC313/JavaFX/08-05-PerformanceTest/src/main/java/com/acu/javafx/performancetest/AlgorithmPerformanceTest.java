package com.acu.javafx.performancetest;

import java.util.Arrays;
import java.util.Random;

/**
 * Enhanced performance test class that compares different algorithms.
 * This class provides methods to test various algorithms and measure their performance.
 */
public class AlgorithmPerformanceTest {
    
    private static final Random random = new Random();
    
    /**
     * Tests linear search algorithm performance.
     * 
     * @param arraySize the size of the array to search in
     * @return execution time in milliseconds
     */
    public static long testLinearSearch(int arraySize) {
        int[] array = generateRandomArray(arraySize);
        int target = random.nextInt(arraySize);
        
        long startTime = System.nanoTime();
        linearSearch(array, target);
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000; // Convert to milliseconds
    }
    
    /**
     * Tests binary search algorithm performance.
     * 
     * @param arraySize the size of the array to search in
     * @return execution time in milliseconds
     */
    public static long testBinarySearch(int arraySize) {
        int[] array = generateRandomArray(arraySize);
        Arrays.sort(array); // Binary search requires sorted array
        int target = array[random.nextInt(arraySize)];
        
        long startTime = System.nanoTime();
        binarySearch(array, target);
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000; // Convert to milliseconds
    }
    
    /**
     * Tests bubble sort algorithm performance.
     * 
     * @param arraySize the size of the array to sort
     * @return execution time in milliseconds
     */
    public static long testBubbleSort(int arraySize) {
        int[] array = generateRandomArray(arraySize);
        
        long startTime = System.nanoTime();
        bubbleSort(array.clone());
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000; // Convert to milliseconds
    }
    
    /**
     * Tests quick sort algorithm performance.
     * 
     * @param arraySize the size of the array to sort
     * @return execution time in milliseconds
     */
    public static long testQuickSort(int arraySize) {
        int[] array = generateRandomArray(arraySize);
        
        long startTime = System.nanoTime();
        quickSort(array.clone(), 0, array.length - 1);
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000; // Convert to milliseconds
    }
    
    /**
     * Tests simple loop performance (original test).
     * 
     * @param iterations the number of iterations
     * @return execution time in milliseconds
     */
    public static long testSimpleLoop(long iterations) {
        long startTime = System.currentTimeMillis();
        long k = 0;
        for (long i = 1; i <= iterations; i++) {
            k = k + 5;
        }
        long endTime = System.currentTimeMillis();
        
        return endTime - startTime;
    }
    
    /**
     * Linear search implementation.
     * 
     * @param array the array to search in
     * @param target the value to search for
     * @return index of target if found, -1 otherwise
     */
    private static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Binary search implementation.
     * 
     * @param array the sorted array to search in
     * @param target the value to search for
     * @return index of target if found, -1 otherwise
     */
    private static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (array[mid] == target) {
                return mid;
            }
            
            if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Bubble sort implementation.
     * 
     * @param array the array to sort
     */
    private static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap elements
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    
    /**
     * Quick sort implementation.
     * 
     * @param array the array to sort
     * @param low the low index
     * @param high the high index
     */
    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }
    
    /**
     * Partition method for quick sort.
     * 
     * @param array the array to partition
     * @param low the low index
     * @param high the high index
     * @return the pivot index
     */
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        
        return i + 1;
    }
    
    /**
     * Generates a random array of specified size.
     * 
     * @param size the size of the array
     * @return a random array
     */
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }
} 