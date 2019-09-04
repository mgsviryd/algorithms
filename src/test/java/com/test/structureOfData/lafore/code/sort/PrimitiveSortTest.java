package com.test.structureOfData.lafore.code.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.test.structureOfData.util.Data.COUNT;
import static com.test.structureOfData.util.Data.getIntNaturalArray;
import static com.test.structureOfData.util.Data.getIntReverseArray;

public class PrimitiveSortTest {

    @Test
    public void bubbleSort() {
        int [] unSorted = getIntReverseArray(COUNT);
        int [] sorted = getIntNaturalArray(COUNT);
        long start = System.nanoTime();
        BubbleSort.sort(unSorted);
        long end = System.nanoTime();
        long elapsedTime = TimeUnit.MILLISECONDS.convert(end-start,TimeUnit.NANOSECONDS);
        System.out.println("bubbleSort: " + elapsedTime + " milliseconds");
        Assert.assertArrayEquals(sorted, unSorted);
    }

    @Test
    public void insertionSort(){
        int [] unSorted = getIntReverseArray(COUNT);
        int [] sorted = getIntNaturalArray(COUNT);
        long start = System.nanoTime();
        InsertionSort.sort(unSorted);
        long end = System.nanoTime();
        long elapsedTime = TimeUnit.MILLISECONDS.convert(end-start,TimeUnit.NANOSECONDS);
        System.out.println("insertionSort: " + elapsedTime + " milliseconds");
        Assert.assertArrayEquals(sorted, unSorted);
    }

    @Test
    public void selectionSort(){
        int [] unSorted = getIntReverseArray(COUNT);
        int [] sorted = getIntNaturalArray(COUNT);
        long start = System.nanoTime();
        SelectionSort.sort(unSorted);
        long end = System.nanoTime();
        long elapsedTime = TimeUnit.MILLISECONDS.convert(end-start,TimeUnit.NANOSECONDS);
        System.out.println("selectionSort: " + elapsedTime + " milliseconds");
        Assert.assertArrayEquals(sorted, unSorted);
    }
}
