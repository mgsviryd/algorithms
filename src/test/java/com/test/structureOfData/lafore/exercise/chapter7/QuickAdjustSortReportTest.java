package com.test.structureOfData.lafore.exercise.chapter7;

import com.test.structureOfData.lafore.exercises.chapter7.QuickAdjustSortReport;
import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.test.structureOfData.util.Data.COUNT;

public class QuickAdjustSortReportTest {
    @Test
    public void sort(){
        Integer [] actual = Data.getIntegerReverseArray(COUNT);
        Integer [] expected = Data.getIntegerNaturalArray(COUNT);
        QuickAdjustSortReport quickAdjustSort = new QuickAdjustSortReport(actual);
        quickAdjustSort.sort();
        quickAdjustSort.showSortReport();
        Assert.assertArrayEquals(expected,actual);
    }
}
