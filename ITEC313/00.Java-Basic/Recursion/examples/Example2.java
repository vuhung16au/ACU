package Recursion.examples;
// Example2.java
// Recursion with arrays: sum of array elements
// Python equivalent: def sum_array(arr): return 0 if not arr else arr[0] + sum_array(arr[1:])

public class Example2 {
    /**
     * Recursively sums elements of an array.
     * @param arr Array of integers
     * @param index Current index
     * @return Sum of elements from index to end
     */
    public static int sumArray(int[] arr, int index) {
        if (index >= arr.length) return 0; // base case
        return arr[index] + sumArray(arr, index + 1); // recursive case
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        System.out.println("Sum of array = " + sumArray(nums, 0)); // 15
    }
} 