package com.test.structureOfData.lafore.exercise.chapter7;

import com.test.structureOfData.lafore.exercises.chapter7.QuickSortReport;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.test.structureOfData.util.Data.COUNT;

public class QuickSortReportTest {
    @Test
    public void report(){
        Integer [] actual = Data.getIntegerReverseArray(COUNT*COUNT);
        Integer [] expected = Data.getIntegerNaturalArray(COUNT*COUNT);
        QuickSortReport<Integer> quickSortReport = new QuickSortReport<>(actual);
        quickSortReport.sort();
        quickSortReport.showSortReport();
        Assert.assertArrayEquals(expected,actual);
    }
}
