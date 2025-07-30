package com.acu.javafx.binarysearch;

// Java Program to demonstrate working of binarySearch()
// Method of Arrays class In a sorted array
import java.util.Arrays;

public class GeeksInBuildBinarySearch
{

    public static void main(String[] args)
    {
      	int a[] = { 10, 20, 15, 22, 35 };

        // Sorting the above array
        // using sort() method of Arrays class
        Arrays.sort(a);

        int x = 22;
        
      	int res = Arrays.binarySearch(a, x);
        
         System.out.println("Element to be searched is : "+ x);
         
        if (res >= 0)
            System.out.println(x + " found at index = " + res);
        else
            System.out.println(x + " Not found");

        x = 40;
        res = Arrays.binarySearch(a, x);
        
      	System.out.println("Element to be searched is : "+ x);
        if (res >= 0)
            System.out.println(x + " found at index = " + res);
        else
            System.out.println(x + " Not found");
    }
}