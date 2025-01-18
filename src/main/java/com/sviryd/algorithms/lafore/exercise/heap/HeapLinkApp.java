package com.sviryd.algorithms.lafore.exercise.heap;

import java.util.Comparator;
import java.util.Random;

public class HeapLinkApp {
    public static void main(String[] args) {
        int size = 31;
        HeapLink heap = new HeapLink();
        for (int i = 0; i < size; i++) {
            heap.insert(new Random().nextInt(size));
        }
        heap.displayHeap();
        Comparator<Integer> comparator = (Integer x, Integer y) -> y - x;
        heap.sort(comparator);
        heap.displayHeap();

        Integer[] intArray = new Integer[size];
        for (int i = 0; i < 4; i++) {
            intArray[i] = 50 - i;
            System.out.print(intArray[i] + " ");
        }
        System.out.println();

        Heaps.sortBasedLink(intArray, false);
        for (int i = 0; i < size; i++) {
            System.out.print(intArray[i] + " ");
        }
        System.out.println();

        heap = new HeapLink(false);
        for (int i = 0; i < size; i++) {
            heap.insert(50-i);
        }
        heap.displayHeap();
    }
}
