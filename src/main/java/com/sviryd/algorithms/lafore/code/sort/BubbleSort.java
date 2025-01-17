package com.sviryd.algorithms.lafore.code.sort;

import static com.sviryd.algorithms.lafore.exercises.util.Swap.swap;

/**
 * Stable
 * Best     O(n^2/4)
 * Worst    0(n^2/2)
 * Memory   -
 */
public class BubbleSort {
    public static void sort(final int[] a) {
        int size = a.length;
        int in, out;
        for (out = size - 1; out > 0; out--) {
            for (in = 0; in < out; in++) {
                if (a[in] > a[in + 1]) {
                    swap(in, in + 1, a);
                }
            }
        }
    }
}
