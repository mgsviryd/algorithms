package com.sviryd.algorithms.lafore.exercise.chapter5;

import com.sviryd.algorithms.lafore.exercises.chapter5.LinkedPriorityQueue;
import com.sviryd.algorithms.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.sviryd.algorithms.util.Data.COUNT;

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
