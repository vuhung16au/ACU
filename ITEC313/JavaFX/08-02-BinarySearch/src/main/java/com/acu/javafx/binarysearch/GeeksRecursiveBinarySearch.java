package com.acu.javafx.binarysearch;

// Java implementation of
// recursive Binary Search
public class GeeksRecursiveBinarySearch
{
    static int binarySearch(int a[], int l, int r, int x)
    {

      	if (r >= l) {
            int m = l + (r - l) / 2;

            // Returned Index of the Element
            if (a[m] == x)
                return m;

            // If element is smaller than mid, then
            // it can only be present in left subarray
            if (a[m] > x)
                return binarySearch(a, l, m - 1, x);

            // Else the element can only be present
            // in right subarray
            return binarySearch(a, m + 1, r, x);
        }

        // No Element Found
        return -1;
    }

    // main function
    public static void main(String args[])
    {
        int a[] = { 2, 3, 4, 10, 40 };
        int n = a.length;
        int x = 10;
      
        int res = binarySearch(a, 0, n - 1, x);

       System.out.println("Element to be searched is : "+ x); 

        if (res == -1)
            System.out.println(
                "Element is not present in array");
        else
            System.out.println("Element is present at index: " + res);
    }
}