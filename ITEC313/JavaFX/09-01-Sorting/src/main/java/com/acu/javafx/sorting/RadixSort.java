package com.acu.javafx.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Radix Sort implementation
 */
public class RadixSort {
  
  /** Radix sort method */
  public static void radixSort(int[] list) {
    if (list.length == 0) return;
    
    // Find the maximum number to know number of digits
    int max = list[0];
    for (int i = 1; i < list.length; i++) {
      if (list[i] > max) {
        max = list[i];
      }
    }
    
    // Do counting sort for every digit
    for (int exp = 1; max / exp > 0; exp *= 10) {
      countingSort(list, exp);
    }
  }
  
  /** Counting sort for a specific digit */
  private static void countingSort(int[] list, int exp) {
    int n = list.length;
    int[] output = new int[n];
    int[] count = new int[10];
    
    // Store count of occurrences in count[]
    for (int i = 0; i < n; i++) {
      count[(list[i] / exp) % 10]++;
    }
    
    // Change count[i] so that count[i] now contains
    // actual position of this digit in output[]
    for (int i = 1; i < 10; i++) {
      count[i] += count[i - 1];
    }
    
    // Build the output array
    for (int i = n - 1; i >= 0; i--) {
      int digit = (list[i] / exp) % 10;
      output[count[digit] - 1] = list[i];
      count[digit]--;
    }
    
    // Copy the output array to list[], so that list[] now
    // contains sorted numbers according to current digit
    for (int i = 0; i < n; i++) {
      list[i] = output[i];
    }
  }
  
  /** A test method */
  public static void main(String[] args) {
    int[] list = {170, 45, 75, 90, 802, 24, 2, 66};
    radixSort(list);
    for (int i = 0; i < list.length; i++)
      System.out.print(list[i] + " ");
  }
} 