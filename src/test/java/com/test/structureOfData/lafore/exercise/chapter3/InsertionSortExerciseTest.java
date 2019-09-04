package com.test.structureOfData.lafore.exercise.chapter3;

import com.test.structureOfData.util.Data;
import com.test.structureOfData.lafore.exercises.chapter3.EfficiencyReport;
import com.test.structureOfData.lafore.exercises.chapter3.InsertionSortExercise;
import org.junit.Assert;
import org.junit.Test;

import static com.test.structureOfData.util.Data.COUNT;

public class InsertionSortExerciseTest {
    @Test
    public void getMedian() {
        int[] array = Data.getIntReverseArray(COUNT);
        int median = InsertionSortExercise.getMedian(array);
        Assert.assertEquals(COUNT / 2 - 1, median);
    }

    @Test
    public void deleteDuplicate() {
        int[] array = Data.getIntDuplicateArray(COUNT);
        InsertionSortExercise.sortAndDeleteDuplicate(array);
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] != 0 && array[i + 1] != 0) {
                Assert.assertNotEquals(array[i], array[i + 1]);
            }
        }
    }

    @Test
    public void efficiencyReport() {
        int[] array = Data.getIntReverseArray(COUNT);
        InsertionSortExercise ise = new InsertionSortExercise(array);
        EfficiencyReport sort = ise.sort();
        int compare = sort.getCompare();
        int copy = sort.getCopy();
        boolean efficientCompare = compare < COUNT * COUNT;
        boolean efficientCopy = copy < COUNT * COUNT;
        System.out.println("compare operation InsertionSort: " + compare);
        System.out.println("copy operation InsertionSort: " + copy);
        Assert.assertEquals(true, efficientCompare);
        Assert.assertEquals(true, efficientCopy);
    }
}
