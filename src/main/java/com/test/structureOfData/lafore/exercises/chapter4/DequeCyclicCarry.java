package com.test.structureOfData.lafore.exercises.chapter4;

import java.util.NoSuchElementException;

/**
 * Exercise 4.2
 *
 * @param <T>
 */
public class DequeCyclicCarry<T> {
    private T[] array;
    private int maxSize;
    private int nElems;
    private int front;
    private int rear;

    public DequeCyclicCarry(final int maxSize) {
        this.maxSize = maxSize;
        array = (T[]) new Object[maxSize];
        nElems = 0;
        front = 0;
        rear = -1;
    }

    public boolean insertLast(final T key) {
        if (isFull()) {
            return false;
        } else if (rear == maxSize - 1) {
            rear = 0;
            array[rear] = key;
            nElems++;
            return true;
        } else {
            array[++rear] = key;
            nElems++;
            return true;
        }
    }

    public boolean insertFirst(final T key) {
        if (isFull()) {
            return false;
        } else if (front > 0) {
            array[--front] = key;
            nElems++;
            return true;
        } else {
            front = maxSize - 1;
            array[front] = key;
            nElems++;
            return true;
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T temp = array[front++];
        if (front == maxSize) {
            front = 0;
        }
        nElems--;
        return temp;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T temp = array[rear--];
        if (rear < 0)
            rear = maxSize - 1;
        nElems--;
        return temp;
    }

    public boolean isFull() {
        return nElems == maxSize;
    }

    public boolean isEmpty() {
        return nElems == 0;
    }

    public int size() {
        return nElems;
    }
}