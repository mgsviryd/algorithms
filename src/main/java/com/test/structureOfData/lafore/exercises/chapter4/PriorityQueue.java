package com.test.structureOfData.lafore.exercises.chapter4;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Exercise 4.4 not execute because there only need add key in array and then sort array before removing,
 * it's too expensive
 * Exercise 4.5 skipped because it's primitive.
 *
 * Comparable didn't used because we need another data structure instead array.
 * @param <T>
 */
public class PriorityQueue<T> {
    private T[] array;
    private int maxSize;
    private int nElems;
    private Comparator<T> comparator;


    public PriorityQueue(final int maxSize, final Comparator<T> comparator) {
        this.maxSize = maxSize;
        this.array = (T[]) new Object[maxSize];
        this.nElems = 0;
        this.comparator = comparator;
    }


    public boolean insert(final T key) {
        if (isFull()) {
            return false;
        }
        int in = nElems;
        while (true) {
            if (in > 0 && isGreat(array[in - 1], key)) {
                array[in] = array[in - 1];
                --in;
            } else break;
        }
        array[in] = key;
        nElems++;
        return true;
    }

    private boolean isGreat(final T t, final T key) {
        return comparator.compare(t, key) > 0;
    }

    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T temp = array[--nElems];
        return temp;
    }

    public T peek() {
        return array[nElems - 1];
    }

    public boolean isEmpty() {
        return nElems == 0;
    }

    public boolean isFull() {
        return nElems == maxSize;
    }
}
