package com.test.structureOfData.lafore.code.sort;

/**
 * Stable
 * Best     O(n*log n)
 * Worst    O(n*log n)
 * Memory   O(n)
 */
public class MergeSort {
    public static <E extends Comparable<? super E>> void sort(final E[] array) {
        Comparable[] workSpace = new Comparable[array.length];
        recSort(workSpace, array, 0, array.length);
    }

    private static <E extends Comparable<? super E>> void recSort(final Comparable[] workSpace, final E[] array, final int lower, final int upper) {
        if (lower == upper) {
            return;
        }
        int middle = (upper - lower) / 2;
        recSort(workSpace, array, lower, middle);
        recSort(workSpace, array, middle + 1, upper);
        merge(workSpace, array, lower, upper);
    }

    private static <E extends Comparable<? super E>> void merge(final Comparable[] workSpace, final E[] array, int lower, int upper) {
        int iForCopy = lower;
        int iWork = lower;
        int n = upper - lower + 1;
        int toMiddle = (upper - lower) / 2;
        int fromMiddle = toMiddle + 1;
        while (lower <= toMiddle && fromMiddle <= upper) {
            if (array[lower].compareTo(array[fromMiddle]) < 0) {
                workSpace[iWork++] = array[lower++];
            } else {
                workSpace[iWork++] = array[fromMiddle++];
            }
        }
        while (lower <= toMiddle) {
            workSpace[iWork++] = array[lower++];
        }
        while (fromMiddle <= upper) {
            workSpace[iWork++] = array[fromMiddle++];
        }
        for (int i = iForCopy; i < n; i++) {
            array[i] = (E) workSpace[i];
        }
    }
}
