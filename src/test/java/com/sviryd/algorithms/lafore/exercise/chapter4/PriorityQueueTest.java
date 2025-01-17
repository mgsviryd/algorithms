package com.sviryd.algorithms.lafore.exercise.chapter4;

import com.sviryd.algorithms.lafore.exercises.chapter4.PriorityQueue;
import com.sviryd.algorithms.util.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

import static com.sviryd.algorithms.util.Data.COUNT;

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
