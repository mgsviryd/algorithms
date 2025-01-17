package com.sviryd.algorithms.lafore.exercises.heap;

import java.util.Comparator;

/**
 * Exercise 12.1-5 from Robert Lafore Structures of data and algorithms
 */

public class Heap<V extends Comparable<? super V>> implements IHeap<V> {
    private static final int RESIZE_FACTOR = 2;
    private static final int DEFAULT_SIZE = 16;
    private Node<V>[] heapArray;
    private Comparator<V> comparator;
    private int size;
    private int count;
    private boolean isBigDataMode;
    private int countForControlBigDataMode;
    private int negative, zero, positive;

    public Heap() {
        this(DEFAULT_SIZE);
    }
    public Heap(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("argument in constructor is less zero");
        }
        this.size = size;
        heapArray = new Node[size];
        setDescendingOrder();
    }

    public Heap(boolean isAscendingOrder){
        this();
        if (isAscendingOrder){
            setAscendingOrder();
        }
    }
    public Heap (int size, boolean isAscendingOrder){
        this(size);
        if (isAscendingOrder){
            setAscendingOrder();
        }
    }

    public Heap(Comparator<V> comparator) {
        this(DEFAULT_SIZE);
        this.comparator = comparator;
    }

    public Heap(int size, Comparator<V> comparator) {
        this(size);
        this.comparator = comparator;
    }

    private void setDescendingOrder(){
        negative =-1;
        zero = 0;
        positive = 1;
    }
    private void setAscendingOrder(){
        negative = 1;
        zero = 0;
        positive = -1;
    }

    private int negativeZeroPositive(int n){
        return (n < 0) ? -1 : ((n == 0) ? 0 : 1);
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public void onBigDataMode(){
        isBigDataMode = true;
    }

    public void offBigDataMode(){
        isBigDataMode = false;
    }
    @Override
    public void insert(V value) {
        if (count == size) {
            resize();
        }
        Node newNode = new Node(value);
        heapArray[count] = newNode;
        if (!isBigDataMode){
            trickleUp(count);
        }
        count++;
    }

    private void resize() {
        int newSize;
        if (size * RESIZE_FACTOR > Integer.MAX_VALUE) {
            newSize = Integer.MAX_VALUE;
        } else {
            newSize = size * RESIZE_FACTOR;
        }
        Node[] largeArray = new Node[newSize];
        System.arraycopy(heapArray, 0, largeArray, 0, count);
        heapArray = largeArray;
    }

    private void trickleUp(int index) {
        int parent = (index - 1) / 2;
        Node<V> bottom = heapArray[index];
        int comparison;
        while (index > 0) {
            if (comparator != null) {
                comparison = negativeZeroPositive(comparator.compare(heapArray[parent].value, bottom.value));
                if (comparison == zero || comparison==positive) {
                    break;
                }
            } else {
                comparison = negativeZeroPositive(heapArray[parent].value.compareTo(bottom.value));
                if ( comparison == zero || comparison == positive) {
                    break;
                }
            }
            heapArray[index] = heapArray[parent];
            index = parent;
            parent = (parent - 1) / 2;
        }
        heapArray[index] = bottom;
    }

    @Override
    public V remove() {
        if (isBigDataMode && count != countForControlBigDataMode){
            trickleDownForFirstHalfData();
            countForControlBigDataMode = count-1;
        }
        V root = heapArray[0].value;
        heapArray[0] = heapArray[--count];
        trickleDown(0);
        return root;
    }

    private void trickleDownForFirstHalfData(){
        for (int i = count/2-1; i>=0; i--) {
            trickleDown(i);
        }
    }
    private void trickleDown(int index) {
        int largeChild;
        Node<V> top = heapArray[index];
        while (index < count / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int comparison;
            if (comparator != null) {
                comparison = negativeZeroPositive(comparator.compare(heapArray[leftChild].value, heapArray[rightChild].value));
                if (rightChild < count &&  comparison == negative) {
                    largeChild = rightChild;
                } else {
                    largeChild = leftChild;
                }
                comparison = negativeZeroPositive(comparator.compare(top.value, heapArray[largeChild].value));
                if (comparison == positive) {
                    break;
                }
            } else {
                comparison = negativeZeroPositive(heapArray[leftChild].value.compareTo(heapArray[rightChild].value));
                if (rightChild < count &&  comparison == negative) {
                    largeChild = rightChild;
                } else {
                    largeChild = leftChild;
                }
                comparison = negativeZeroPositive(top.value.compareTo(heapArray[largeChild].value));
                if (comparison == positive) {
                    break;
                }
            }
            heapArray[index] = heapArray[largeChild];
            index = largeChild;
        }
        heapArray[index] = top;
    }
    @Override
    public boolean change(int index, V newValue) {
        if (index < 0 || index>count-1) {
            return false;
        }
        V oldValue = heapArray[index].value;
        heapArray[index].value = newValue;
        if (!isBigDataMode){
            int comparison = negativeZeroPositive(oldValue.compareTo(newValue));
            if (comparison == negative) {
                trickleUp(index);
            } else {
                trickleDown(index);
            }
        }
        return true;
    }

    @Override
    public void sort(Comparator<V> comparator) {
        this.comparator = comparator;
        Integer number = count;
        int d = count;
        count = 0;
        for (int i = 0; i < number; i++) {
            insert(heapArray[i].value);
        }
    }

    @Override
    public int getIndex(Comparator<V> comparator, V value) {
        if (value==null){
            return -1;
        }
        for (int i = 0; i < count; i++) {
            if (comparator.compare(heapArray[i].value, value) == 0){
                return i;
            }
        }
        return -1;
    }

    @Override
    public V getValue(int index){
        if (index<0 || index>=count){
            return null;
        }
        return heapArray[index].value;
    }
    @Override
    public int getSize(){
        return count;
    }
    @Override
    public void displayHeap() {
        if (isBigDataMode){
            trickleDownForFirstHalfData();
        }
        // format of array
        System.out.print("heapArray: ");
        for (int m = 0; m < count; m++)
            if (heapArray[m] != null) {
                System.out.print(heapArray[m].value + " ");
            } else {
                System.out.print("-- ");
            }
        System.out.println();
        // format of heap
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;
        String dots = "...............................";
        System.out.println(dots + dots);
        while (count > 0) {
            if (column == 0) {
                for (int k = 0; k < nBlanks; k++) {
                    System.out.print(' ');
                }
            }

            System.out.print(heapArray[j].value);
            if (++j == count) {
                break;
            }
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else {
                for (int k = 0; k < nBlanks * 2 - 2; k++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println("\n" + dots + dots);
    }

    private static class Node<V extends Comparable<? super V>> {
        private V value;

        public Node(V value) {
            this.value = value;
        }
        public V getValue() {
            return value;
        }
    }
}
