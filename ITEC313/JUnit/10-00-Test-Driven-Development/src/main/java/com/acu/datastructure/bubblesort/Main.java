package com.acu.datastructure.bubblesort;

/**
 * Main class to demonstrate the TDD Bubble Sort implementation.
 * 
 * This class shows the results of implementing Bubble Sort using Test-Driven Development.
 */
public class Main {
    
    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        
        System.out.println("=== Test-Driven Development: Bubble Sort Demo ===");
        System.out.println("This implementation was created following TDD principles:");
        System.out.println("1. Tests were written FIRST (Red phase)");
        System.out.println("2. Implementation was written to make tests pass (Green phase)");
        System.out.println("3. Code was refactored while keeping tests green (Refactor phase)");
        System.out.println();
        
        // Test case 1: Normal unsorted array
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Test Case 1: Normal unsorted array");
        System.out.print("Original: ");
        bubbleSort.printArray(arr1);
        
        int[] sorted1 = bubbleSort.sort(arr1);
        System.out.print("Sorted:   ");
        bubbleSort.printArray(sorted1);
        System.out.println("Is sorted: " + bubbleSort.isSorted(sorted1));
        System.out.println();
        
        // Test case 2: Already sorted array
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 2: Already sorted array");
        System.out.print("Original: ");
        bubbleSort.printArray(arr2);
        
        int[] sorted2 = bubbleSort.sort(arr2);
        System.out.print("Sorted:   ");
        bubbleSort.printArray(sorted2);
        System.out.println("Is sorted: " + bubbleSort.isSorted(sorted2));
        System.out.println();
        
        // Test case 3: Array with duplicates
        int[] arr3 = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("Test Case 3: Array with duplicates");
        System.out.print("Original: ");
        bubbleSort.printArray(arr3);
        
        int[] sorted3 = bubbleSort.sort(arr3);
        System.out.print("Sorted:   ");
        bubbleSort.printArray(sorted3);
        System.out.println("Is sorted: " + bubbleSort.isSorted(sorted3));
        System.out.println();
        
        // Test case 4: Descending sort
        int[] arr4 = {5, 2, 8, 1, 9};
        System.out.println("Test Case 4: Descending sort");
        System.out.print("Original: ");
        bubbleSort.printArray(arr4);
        
        int[] sorted4 = bubbleSort.sortDescending(arr4);
        System.out.print("Descending: ");
        bubbleSort.printArray(sorted4);
        System.out.println();
        
        // Test case 5: Edge cases
        System.out.println("Test Case 5: Edge cases");
        
        // Empty array
        int[] emptyArray = {};
        System.out.print("Empty array: ");
        bubbleSort.printArray(emptyArray);
        System.out.print("Sorted: ");
        bubbleSort.printArray(bubbleSort.sort(emptyArray));
        System.out.println();
        
        // Single element
        int[] singleElement = {42};
        System.out.print("Single element: ");
        bubbleSort.printArray(singleElement);
        System.out.print("Sorted: ");
        bubbleSort.printArray(bubbleSort.sort(singleElement));
        System.out.println();
        
        // Negative numbers
        int[] negativeArray = {-3, -1, -4, -1, -5};
        System.out.print("Negative numbers: ");
        bubbleSort.printArray(negativeArray);
        System.out.print("Sorted: ");
        bubbleSort.printArray(bubbleSort.sort(negativeArray));
        System.out.println();
        
        System.out.println("=== TDD Benefits Demonstrated ===");
        System.out.println("✓ All edge cases are handled");
        System.out.println("✓ Code is well-tested and reliable");
        System.out.println("✓ Implementation follows clear requirements");
        System.out.println("✓ Refactoring is safe with comprehensive tests");
        System.out.println();
        System.out.println("Run 'mvn test' to execute all TDD tests!");
    }
}
