package com.sviryd.algorithms.lafore.exercises.chapter4;

public class QueueExercise<T> {
    private int maxSize;
    private int nElems;
    private int front;
    private int rear;
    private T[] array;

    public QueueExercise(final int maxSize) {
        this.maxSize = maxSize;
        array = (T[]) new Object[maxSize];
        nElems = 0;
        front = 0;
        rear = -1;
    }

    public boolean insert(final T key) {
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

    public T remove() {
        T temp = array[front++];
        if (front == maxSize) {
            front = 0;
        }
        nElems--;
        return temp;
    }

    public T peek() {
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

    /**
     * Exercise 4.1
     */
    public void display() {
        if (front <= rear) {
            for (int j = front; j <= rear; j++) {
                System.out.print(array[j] + " ");
            }
            System.out.println();
        } else {
            for (int j = front; j <= maxSize - 1; j++) {
                System.out.print(array[j] + " ");
            }
            for (int j = 0; j <= rear; j++) {
                System.out.print(array[j] + " ");
            }
            System.out.println();
        }
    }
}