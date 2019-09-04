package com.test.structureOfData.lafore.code.linkedList;

import com.test.structureOfData.lafore.exercises.chapter5.ClosureLinkedList;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.test.structureOfData.util.Data.COUNT;

public class ClosureLinkedListTest {
    @Test
    public void chainOfOperation(){
        int[] array = Data.getIntNaturalArray(COUNT);
        int steps = COUNT/2;
        ClosureLinkedList list = new ClosureLinkedList();
        for (int item: array             ) {
            list.push(item);
        }
        list.step(steps);
        list.display();
        int expected = (int) list.peek();
        int actual = steps-1;
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void find(){
        int[] array = Data.getIntNaturalArray(COUNT);
        int steps = COUNT/2;
        ClosureLinkedList list = new ClosureLinkedList();
        for (int item: array) {
            list.push(item);
        }
        list.step(steps);
        int expected = COUNT-1;
        int actual = (int) list.find(COUNT-1);
        Assert.assertEquals(expected,actual);
    }
}
