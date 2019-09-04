package com.test.structureOfData.lafore.exercise.chapter5;

import com.test.structureOfData.lafore.exercises.chapter5.LinkedPriorityQueue;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.test.structureOfData.util.Data.COUNT;

public class LinkedPriorityQueueTest {
    @Test
    public void chainOfOperation() {
        int[] testedArray = Data.getIntReverseArray(COUNT);
        int[] resultArray = Data.getIntNaturalArray(COUNT);
        LinkedPriorityQueue queue = new LinkedPriorityQueue();
        for (int item : testedArray) {
            queue.add(item);
        }
        for (int item : resultArray) {
            Assert.assertEquals(item, queue.remove());
        }
        Assert.assertTrue(queue.isEmpty());
    }
}
