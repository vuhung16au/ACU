/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
package Recursion.examples;
// Advanced.java
// Advanced recursion: generate all subsets (power set) of a set
// Python equivalent: def subsets(arr): ... (see itertools or recursive solution)

public class Advanced {
    /**
     * Recursively prints all subsets of the given array.
     * @param arr Input array
     * @param index Current index
     * @param current Current subset (as String)
     */
    public static void printSubsets(int[] arr, int index, String current) {
        if (index == arr.length) {
            System.out.println(current);
            return;
        }
        // Include arr[index]
        printSubsets(arr, index + 1, current + arr[index] + " ");
        // Exclude arr[index]
        printSubsets(arr, index + 1, current);
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println("All subsets:");
        printSubsets(nums, 0, "");
    }
} 