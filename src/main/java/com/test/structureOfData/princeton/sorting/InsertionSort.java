package com.test.structureOfData.princeton.sorting;

/**
 * Lafore version is better than Princeton because there is not more swap operations.
 * - stable
 */
public class InsertionSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j++) {
                exch(a, j, j - 1); // more swap operations
            }
        }
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) == -1;
    }

    private static void exch(Comparable[] a, int j, int i) {
        Comparable temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
}
