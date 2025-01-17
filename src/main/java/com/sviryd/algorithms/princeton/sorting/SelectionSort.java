package com.sviryd.algorithms.princeton.sorting;

/**
 * - not stable
 */
public class SelectionSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a, j, min)) {
                    min = j;
                }
            }
            exch(a, i, min);
        }
    }

    private static boolean less(Comparable[] a, int j, int i) {
        return a[j].compareTo(a[i]) == -1;
    }

    private static void exch(Comparable[] a, int i, int min) {
        Comparable temp = a[i];
        a[i] = a[min];
        a[min] = temp;
    }
}
