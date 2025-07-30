package com.acu.javafx.sortstring;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Demonstrates sorting strings by length.
 * Original source: https://liveexample.pearsoncmg.com/html/SortStringByLength.html
 */
public class SortStringByLength {
    public static void main(String[] args) {
        String[] cities = {"Sydney", "Melbourne", "Brisbane", "Perth"};
        Arrays.sort(cities, new MyComparator());

        for (String s : cities) {
            System.out.print(s + " ");
        }
    }

    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }
} 