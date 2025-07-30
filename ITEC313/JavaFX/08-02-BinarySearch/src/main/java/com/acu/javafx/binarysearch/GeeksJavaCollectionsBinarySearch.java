package com.acu.javafx.binarysearch;

// Java Program to Demonstrate Working of binarySearch()
// method of Collections class
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeeksJavaCollectionsBinarySearch
{
    public static void main(String[] args)
    {

      	List<Integer> a = new ArrayList<Integer>();

        // Populating the Arraylist
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(10);
        a.add(20);

        int x = 10;
        int res = Collections.binarySearch(a, x);
        
        System.out.println("Element to be searched is : "+ x);
        
        if (res >= 0)
            System.out.println(x + " found at index = " + res);
        else
            System.out.println(x + " Not found");

        x = 15;
        res = Collections.binarySearch(a, x);

        if (res >= 0)
            System.out.println(x + " found at index = " + res);
        else
            System.out.println(x + " Not found");
    }
}