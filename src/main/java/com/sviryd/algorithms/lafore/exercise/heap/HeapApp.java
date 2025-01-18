package com.sviryd.algorithms.lafore.exercise.heap;

import java.util.Comparator;
import java.util.Random;

public class HeapApp {
    public static void main(String[] args) {
        int size = 31;
        Heap heap = new Heap(size);
        for (int i = 0; i < size; i++) {
            heap.insert(new Random().nextInt(size));
        }
        heap.displayHeap();
        Comparator<Integer> comparator = (Integer x, Integer y) -> y - x;
        heap.sort(comparator);
        heap.displayHeap();

//
        Integer[] intArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            intArray[i] = 50 - i;
            System.out.print(intArray[i] + " ");
        }
        System.out.println();
        Heaps.sortBasedArray(intArray, true);
        for (int i = 0; i < size; i++) {
            System.out.print(intArray[i] + " ");
        }
        System.out.println();

        heap = new Heap(size, true);
        heap.offBigDataMode();
        for (int i = 0; i < size; i++) {
            heap.insert(new Random().nextInt(size));
        }
        heap.displayHeap();
    }
}
