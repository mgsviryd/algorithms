package com.test.structureOfData.lafore.exercise.chapter4;

import com.test.structureOfData.lafore.exercises.chapter4.DequeCyclicCarry;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

import static com.test.structureOfData.util.Data.COUNT;

public class DequeCyclicCarryTest {
    @Test
    public void cyclicCarry(){
        int[] array = Data.getIntNaturalArray(COUNT);
        DequeCyclicCarry deque = new DequeCyclicCarry(COUNT);
        for (int item: array) {
            deque.insertLast(item);
        }
        int exhaust = COUNT;
        deque.removeFirst();
        deque.insertLast(exhaust);
        Assert.assertNotEquals(exhaust, deque.removeFirst());
        Assert.assertEquals(false,deque.isFull());
    }
    @Test(expected = NoSuchElementException.class)
    public void noControlSize(){
        int[] array = Data.getIntNaturalArray(COUNT);
        DequeCyclicCarry deque = new DequeCyclicCarry(COUNT);
        for (int item: array) {
            deque.insertLast(item);
        }
        int moreThanSize = COUNT+1;
        for (int i = 0; i < moreThanSize; i++) {
            deque.removeFirst();
        }
    }

}
