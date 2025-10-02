package com.acu.javafx.genericsort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Educational implementations of generic Bubble, Merge, and Quick sort.
 * Each algorithm has two overloads: Comparable-based and Comparator-based.
 * The implementations are intentionally simple and commented for teaching.
 */
public final class GenericSorts {

    private GenericSorts() {}

    // ----------------------- Bubble Sort -----------------------

    public static <E extends Comparable<E>> void bubbleSort(E[] list) {
        bubbleSort(list, Comparator.naturalOrder());
    }

    public static <E> void bubbleSort(E[] list, Comparator<? super E> comparator) {
        boolean swapped;
        for (int pass = 0; pass < list.length - 1; pass++) {
            swapped = false;
            for (int i = 0; i < list.length - 1 - pass; i++) {
                if (comparator.compare(list[i], list[i + 1]) > 0) {
                    E tmp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) {
                return; // already sorted
            }
        }
    }

    // ----------------------- Merge Sort -----------------------

    public static <E extends Comparable<E>> void mergeSort(E[] list) {
        mergeSort(list, Comparator.naturalOrder());
    }

    public static <E> void mergeSort(E[] list, Comparator<? super E> comparator) {
        if (list.length < 2) return;
        @SuppressWarnings("unchecked")
        E[] aux = (E[]) new Object[list.length];
        mergeSortRecursive(list, aux, 0, list.length - 1, comparator);
    }

    private static <E> void mergeSortRecursive(E[] a, E[] aux, int lo, int hi, Comparator<? super E> c) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSortRecursive(a, aux, lo, mid, c);
        mergeSortRecursive(a, aux, mid + 1, hi, c);
        if (c.compare(a[mid], a[mid + 1]) <= 0) return; // already ordered
        merge(a, aux, lo, mid, hi, c);
    }

    private static <E> void merge(E[] a, E[] aux, int lo, int mid, int hi, Comparator<? super E> c) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (c.compare(aux[i], aux[j]) <= 0) {
                a[k++] = aux[i++];
            } else {
                a[k++] = aux[j++];
            }
        }
        while (i <= mid) a[k++] = aux[i++];
        while (j <= hi) a[k++] = aux[j++];
    }

    // ----------------------- Quick Sort -----------------------

    public static <E extends Comparable<E>> void quickSort(E[] list) {
        quickSort(list, Comparator.naturalOrder());
    }

    public static <E> void quickSort(E[] list, Comparator<? super E> comparator) {
        if (list.length < 2) return;
        quickSortRecursive(list, 0, list.length - 1, comparator);
    }

    private static <E> void quickSortRecursive(E[] a, int lo, int hi, Comparator<? super E> c) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi, c);
        quickSortRecursive(a, lo, p - 1, c);
        quickSortRecursive(a, p + 1, hi, c);
    }

    private static <E> int partition(E[] a, int lo, int hi, Comparator<? super E> c) {
        E pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (c.compare(a[j], pivot) <= 0) {
                E tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                i++;
            }
        }
        E tmp = a[i]; a[i] = a[hi]; a[hi] = tmp;
        return i;
    }

    // Utility to verify sorted order in tests
    public static <E> boolean isSorted(E[] a, Comparator<? super E> c) {
        for (int i = 1; i < a.length; i++) {
            if (c.compare(a[i - 1], a[i]) > 0) return false;
        }
        return true;
    }
}


