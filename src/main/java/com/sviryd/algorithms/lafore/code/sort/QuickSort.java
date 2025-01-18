package com.sviryd.algorithms.lafore.code.sort;

import static com.sviryd.algorithms.lafore.exercise.util.Swap.swap;

/**
 * Stable
 * Best     O(n*log n)
 * Worst    O(n^2) - if version without determination pivot as median
 * Memory   -
 * For escaping of StackOverFlow error (the cause is a recursion in method) use QuickAdjustSort.
 */
class QuickSort {
    public static <E extends Comparable<? super E>> void sort(final E[] array) {
        int first = 0;
        int last = array.length - 1;
        recQuickSort(first, last, array);
    }

    private static <E extends Comparable<? super E>> void recQuickSort( final int lower, final int upper, final E[] array) {
        if (upper <= lower) {
            return;
        } else {
            E pivot = array[upper];
            int partition = partition(lower, upper, array, pivot);
            recQuickSort(lower, partition - 1, array);
            recQuickSort(partition + 1, upper, array);
        }
    }

    private static <E extends Comparable<? super E>> int partition(int lower, int upper, E[] a, E pivot) {
        int low = lower - 1; // because in while we will use ++ pre
        int up = upper; // because we need exclude pivot (in while -- pre)
        while (true) {
            // Поиск большего элемента
            while (a[++low].compareTo(pivot) < 0) {
                //non
            }
            // Поиск меньшего элемента
            while (up > 0 && a[--up].compareTo(pivot) > 0) {
                //non
            }
            if (low >= up) { // Если указатели сошлись,
                break; // разбиение закончено.
            } else {
                swap(low, up, a); // поменять элементы местами.
            }
        }
        swap(low, upper, a); // Перестановка опорного элемента
        return low; // Возврат позиции опорного элемента
    }
} 