package com.sviryd.algorithms.lafore.code.sort;

import static com.sviryd.algorithms.lafore.exercises.util.Swap.swap;

/**
 * Stable
 * Best     O(n^2)
 * Worst    O(n^2)
 * Memory   -
 */
public class SelectionSort {
    public static void sort(final int[] a) {
        int size = a.length;
        int in, out, min;
        for (out = 0; out < size; out++) { // N^2/2
            min = out;
            for (in = out + 1; in < size; in++) {
                if (a[in] < a[min]) { // N^2/2
                    min = in;
                }
            }
            swap(out, min, a); // N
        }
    }
}

