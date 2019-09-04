package com.test.structureOfData.lafore.exercise.chapter4;

import com.test.structureOfData.lafore.exercises.chapter4.QueueExercise;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.test.structureOfData.util.Data.COUNT;

public class QueueExerciseTest {
    @Test
    public void display(){
        int [] array = Data.getIntNaturalArray(COUNT);
        QueueExercise queue = new QueueExercise(COUNT);
        for (int item: array    ) {
            queue.insert(item);
        }
        for (int item: array) {
            Assert.assertEquals(item,queue.remove());
        }
        queue.display();
    }

    @Test
    public void displayOne(){
        int sizeOne = 1;
        QueueExercise queueOne = new QueueExercise(sizeOne);
        queueOne.insert(sizeOne);
        queueOne.display();
    }

    @Test
    public void displayZero(){
        int sizeZero = 0;
        QueueExercise queueOne = new QueueExercise(sizeZero);
        queueOne.insert(sizeZero);
        queueOne.display();
    }
}
