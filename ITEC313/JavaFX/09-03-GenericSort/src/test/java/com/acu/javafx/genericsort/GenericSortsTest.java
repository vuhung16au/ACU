package com.acu.javafx.genericsort;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GenericSortsTest {

    private Integer[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(1000);
        return a;
    }

    @Test void testBubbleComparable() {
        Integer[] a = randomArray(100, 1);
        GenericSorts.bubbleSort(a);
        assertTrue(GenericSorts.isSorted(a, Comparator.naturalOrder()));
    }

    @Test void testBubbleComparatorDescending() {
        Integer[] a = randomArray(100, 2);
        GenericSorts.bubbleSort(a, Comparator.reverseOrder());
        assertTrue(GenericSorts.isSorted(a, Comparator.reverseOrder()));
    }

    @Test void testMergeComparable() {
        Integer[] a = randomArray(200, 3);
        GenericSorts.mergeSort(a);
        assertTrue(GenericSorts.isSorted(a, Comparator.naturalOrder()));
    }

    @Test void testMergeComparatorDescending() {
        Integer[] a = randomArray(200, 4);
        GenericSorts.mergeSort(a, Comparator.reverseOrder());
        assertTrue(GenericSorts.isSorted(a, Comparator.reverseOrder()));
    }

    @Test void testQuickComparable() {
        Integer[] a = randomArray(200, 5);
        GenericSorts.quickSort(a);
        assertTrue(GenericSorts.isSorted(a, Comparator.naturalOrder()));
    }

    @Test void testQuickComparatorDescending() {
        Integer[] a = randomArray(200, 6);
        GenericSorts.quickSort(a, Comparator.reverseOrder());
        assertTrue(GenericSorts.isSorted(a, Comparator.reverseOrder()));
    }
}


