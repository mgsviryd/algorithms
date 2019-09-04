package com.test.structureOfData.lafore.exercises.heap;

import java.util.Comparator;

public interface IHeap<V extends Comparable<? super V>> {

    boolean isEmpty();

    void insert(V value);

    V remove();

    boolean change(int index, V newValue);

    void sort(Comparator<V> comparator);

    void displayHeap();

    int getIndex(Comparator<V> comparator, V value);

    V getValue(int index);

    int getSize();
}
