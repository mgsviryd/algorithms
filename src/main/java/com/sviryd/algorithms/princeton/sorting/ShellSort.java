package com.sviryd.algorithms.princeton.sorting;

/**
 * Lafore version is better than Princeton because there are more swap operations.
 * - not stable
 */
public class ShellSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j > h && less(a, j, j - h); j -= h) {
                    exch(a, j, j - h); // more swap operations
                }
            }
            h = h / 3;
        }
    }

    private static boolean less(Comparable[] a, int j, int i) {
        return a[j].compareTo(a[i]) == -1;
    }

    private static void exch(Comparable[] a, int j, int i) {
        Comparable temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
}
