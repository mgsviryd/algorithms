package com.sviryd.algorithms.lafore.code.linkedList;

import com.sviryd.algorithms.lafore.exercise.chapter5.ClosureLinkedList;
import com.sviryd.algorithms.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.sviryd.algorithms.util.Data.COUNT;

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
