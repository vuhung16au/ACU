package com.acu.javafx.binarysearch;

/**
 * Simple test class to verify binary search algorithm
 * This class can be run independently to test the Geeks.binarySearch method
 */
public class BinarySearchTest {
    
    public static void main(String[] args) {
        System.out.println("Testing Binary Search Algorithm");
        System.out.println("===============================");
        
        // Test case 1: Original example
        testCase1();
        
        // Test case 2: Element not found
        testCase2();
        
        // Test case 3: Element at boundaries
        testCase3();
        
        // Test case 4: Single element array
        testCase4();
        
        System.out.println("\nAll tests completed!");
    }
    
    private static void testCase1() {
        System.out.println("\nTest Case 1: Original Example");
        int[] arr = {2, 3, 4, 10, 40};
        int target = 10;
        int result = Geeks.binarySearch(arr, 0, arr.length - 1, target);
        
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("Searching for: " + target);
        System.out.println("Result: " + result);
        System.out.println("Expected: 3");
        System.out.println("Test " + (result == 3 ? "PASSED" : "FAILED"));
    }
    
    private static void testCase2() {
        System.out.println("\nTest Case 2: Element Not Found");
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15};
        int target = 6;
        int result = Geeks.binarySearch(arr, 0, arr.length - 1, target);
        
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("Searching for: " + target);
        System.out.println("Result: " + result);
        System.out.println("Expected: -1");
        System.out.println("Test " + (result == -1 ? "PASSED" : "FAILED"));
    }
    
    private static void testCase3() {
        System.out.println("\nTest Case 3: Boundary Elements");
        int[] arr = {1, 2, 3, 4, 5};
        
        // Test first element
        int target1 = 1;
        int result1 = Geeks.binarySearch(arr, 0, arr.length - 1, target1);
        System.out.println("Searching for first element (" + target1 + "): " + result1);
        
        // Test last element
        int target2 = 5;
        int result2 = Geeks.binarySearch(arr, 0, arr.length - 1, target2);
        System.out.println("Searching for last element (" + target2 + "): " + result2);
        
        System.out.println("Test " + (result1 == 0 && result2 == 4 ? "PASSED" : "FAILED"));
    }
    
    private static void testCase4() {
        System.out.println("\nTest Case 4: Single Element Array");
        int[] arr = {42};
        int target = 42;
        int result = Geeks.binarySearch(arr, 0, arr.length - 1, target);
        
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("Searching for: " + target);
        System.out.println("Result: " + result);
        System.out.println("Expected: 0");
        System.out.println("Test " + (result == 0 ? "PASSED" : "FAILED"));
    }
} 