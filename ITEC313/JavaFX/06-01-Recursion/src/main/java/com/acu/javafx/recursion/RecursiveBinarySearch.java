package com.acu.javafx.recursion;

public class RecursiveBinarySearch {
  public static int binarySearch(int[] list, int key) {
    int low = 0;
    int high = list.length - 1;
    return binarySearch(list, key, low, high);
  }

  private static int binarySearch(int[] list, int key,
      int low, int high) {
    if (low > high)  // The list has been exhausted without a match
      return -low - 1;

    int mid = (low + high) / 2;
    if (key < list[mid])
      return binarySearch(list, key, low, mid - 1);
    else if (key == list[mid])
      return mid;
    else
      return binarySearch(list, key, mid + 1, high);
  }
  
  public static void main(String[] args) {
    int[] list = {3, 5, 7, 8, 12, 17, 24, 29};
    System.out.println(binarySearch(list, 7));
    System.out.println(binarySearch(list, 0));
  }
} 