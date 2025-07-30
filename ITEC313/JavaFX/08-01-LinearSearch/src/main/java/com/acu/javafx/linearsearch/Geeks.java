package com.acu.javafx.linearsearch;

import java.util.Arrays;

/**
 * Geeks class containing the linear search implementation
 * Based on the code from: https://www.geeksforgeeks.org/dsa/java-program-for-linear-search/
 */
public class Geeks {
    
    /**
     * Linear search implementation to find x in arr[]
     * If x is present then return its location, otherwise return -1
     * 
     * @param a the array to search in
     * @param n the length of the array
     * @param x the element to search for
     * @return index of the element if found, -1 otherwise
     */
    public static int search(int a[], int n, int x) {
        for (int i = 0; i < n; i++) {
            if (a[i] == x)
                return i;
        }
        
        // return -1 if the element is not found
        return -1;
    }
    
    /**
     * Main method for standalone testing
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[] a = { 3, 4, 1, 7, 5 };
        int n = a.length;
        
        int x = 7;
        
        int index = search(a, n, x);

        // print the array
        System.out.println("Array: " + Arrays.toString(a));
        
        if (index == -1)
            System.out.println("Element is not present in the array");
        else
            System.out.println("Element found at index: " + index);
    }
} 