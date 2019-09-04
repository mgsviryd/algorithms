package com.test.structureOfData.lafore.exercise.chapter4;

import com.test.structureOfData.lafore.exercises.chapter4.PriorityQueue;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

import static com.test.structureOfData.util.Data.COUNT;

public class PriorityQueueTest {
    @Test
    public void checkOrder() {
        int[] arrayNat = Data.getIntNaturalArray(COUNT);
        int[] arrayRev = Data.getIntReverseArray(COUNT);
        Comparator comparator = Comparator.naturalOrder();
        PriorityQueue queue = new PriorityQueue(COUNT, comparator);
        for (int item : arrayNat) {
            queue.insert(item);
        }
        for (int item : arrayRev) {
            Assert.assertEquals(item, queue.remove());
        }
    }
}
