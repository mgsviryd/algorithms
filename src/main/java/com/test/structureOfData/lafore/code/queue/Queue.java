package com.test.structureOfData.lafore.code.queue;

public class Queue<E> {
    private int maxSize;
    private int nElems;
    private int front;
    private int rear;
    private E[] array;

    public Queue(final int maxSize) {
        this.maxSize = maxSize;
        array = (E[]) new Object[maxSize];
        nElems = 0;
        front = 0;
        rear = -1;
    }

    public boolean insertFirst(final E key) {
        if (isFull()) {
            return false;
        }
        else if (rear == maxSize - 1) {
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

    public E removeLast() {
        E temp = array[front++];
        if (front == maxSize) {
            front = 0;
        }
        nElems--;
        return temp;
    }

    public E peek() {
        return array[front];
    }

    public boolean isEmpty() {
        return nElems == 0;
    }

    public boolean isFull() {
        return nElems == maxSize;
    }

    public int size() {
        return nElems;
    }
}
