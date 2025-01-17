package com.sviryd.algorithms.lafore.exercises.heap;

import java.util.Comparator;

public class Heaps {
    public static <V extends Comparable<? super V>> void sortBasedArray(V[] array, boolean isAscendingOrder) {
        IHeap<V> heap = new Heap(isAscendingOrder);
        sort(array,heap);
    }
    public static <V extends Comparable<? super V>> void sortBasedArray(V[] array, Comparator<V> comparator) {
        IHeap<V> heap = new Heap(comparator);
        sort(array,heap);
    }
    public static <V extends Comparable<? super V>> void sortBasedLink(V[] array, boolean isAscendingOrder) {
        IHeap<V> heap = new HeapLink(isAscendingOrder);
        sort(array,heap);
    }
    public static <V extends Comparable<? super V>> void sortBasedLink(V[] array, Comparator<V> comparator) {
        IHeap<V> heap = new HeapLink(comparator);
        sort(array,heap);
    }
    private static <V extends Comparable<? super V>> void sort(V[]array, IHeap<V> heap){
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                continue;
            }
            heap.insert(array[i]);
            array[i] = null;
            ++count;
        }
        for (int i = 0; i < count; i++) {
            array[i] = heap.remove();
        }
    }
}
