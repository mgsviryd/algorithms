package com.test.structureOfData.lafore.code.sort;

/**
 * Stable
 * Best     O(n^2)
 * Worst    O(n^2)
 * Memory   -
 * Best that Selection sort if an array is sorted partly.
 * Princeton version is worst than Lafore because there is more swap operations.
 */
public class InsertionSort {
    public static void sort(final int[] a) {
        int size = a.length;
        int in, out;
        for (out = 1; out < size; out++) {
            int temp = a[out];
            in = out;
            while (in > 0 && a[in - 1] > temp) { // N*(N-1)/2
                a[in] = a[in - 1]; // N*(N-1)/2
                in--;
            }
            a[in] = temp; // O(N)
        }
    }
}
