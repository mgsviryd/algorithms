package com.test.structureOfData.lafore.exercise.chapter3;

import com.test.structureOfData.lafore.exercises.chapter3.BubbleSortExercise;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.test.structureOfData.util.Data.COUNT;
import static com.test.structureOfData.util.Data.getIntNaturalArray;
import static com.test.structureOfData.util.Data.getIntReverseArray;

public class BubbleSortExerciseTest {
    @Test
    public void rotateSideSort() {
        int[] unSorted = getIntReverseArray(COUNT);
        int[] sorted = getIntNaturalArray(COUNT);
        long start = System.nanoTime();
        BubbleSortExercise.sort(unSorted);
        long end = System.nanoTime();
        long elapsedTime = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
        System.out.println("bubbleRotateSideSort: " + elapsedTime + " milliseconds");
        Assert.assertArrayEquals(sorted, unSorted);
    }

    @Test
    public void oddEvenSort(){
        int[] unSorted = getIntReverseArray(COUNT);
        int[] sorted = getIntNaturalArray(COUNT);
        long start = System.nanoTime();
        BubbleSortExercise.oddEvenSort(unSorted);
        long end = System.nanoTime();
        long elapsedTime = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
        System.out.println("bubbleOddEvenSort: " + elapsedTime + " milliseconds");
        Assert.assertArrayEquals(sorted, unSorted);
    }
}
