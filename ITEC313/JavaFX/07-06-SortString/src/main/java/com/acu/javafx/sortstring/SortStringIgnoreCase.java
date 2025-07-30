package com.acu.javafx.sortstring;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates case-insensitive string sorting.
 * Original source: https://liveexample.pearsoncmg.com/html/SortStringIgnoreCase.html
 */
public class SortStringIgnoreCase {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("Sydney", "Melbourne", "brisbane", "P erth");
        cities.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

        for (String s: cities) {
            System.out.print(s + " ");
        }
    }
} 