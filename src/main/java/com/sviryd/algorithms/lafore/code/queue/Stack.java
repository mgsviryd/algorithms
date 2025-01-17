package com.sviryd.algorithms.lafore.code.queue;

import java.util.NoSuchElementException;

public class Stack<E> {
    private E[] array;
    private int maxSize;
    private int nElems;

    public Stack(final int maxSize) {
        this.maxSize = maxSize;
        array = (E[]) new Object[maxSize];
        nElems = 0;
    }

    public void push(final E key) {
        array[nElems++] = key;
    }

    public E pop() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        E temp = array[nElems - 1];
        nElems--;
        return temp;
    }

    public E peek() {
        return array[nElems - 1];
    }

    public boolean isEmpty() {
        return nElems == 0;
    }

    public boolean isFull() {
        return nElems == maxSize;
    }
}
