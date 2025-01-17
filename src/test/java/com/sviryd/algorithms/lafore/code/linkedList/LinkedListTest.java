package com.sviryd.algorithms.lafore.code.linkedList;

import com.sviryd.algorithms.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.sviryd.algorithms.util.Data.COUNT;

public class LinkedListTest {
    @Test
    public void chainOfOperation() {
        int[] testedArray = Data.getIntReverseArray(COUNT);
        int[] resultArray = Data.getIntNaturalArray(COUNT);
        LinkedList list = new LinkedList();
        for (int item : testedArray) {
            list.insertFirst(item, item);
        }
        for (int item : resultArray) {
            Assert.assertEquals(item, list.deleteFirst());
        }
        Assert.assertTrue(list.isEmpty());
    }
}
