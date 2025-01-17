package com.sviryd.algorithms.lafore.exercise.chapter7;

import com.sviryd.algorithms.lafore.exercises.chapter7.QuickAdjustSortReport;
import com.sviryd.algorithms.util.Data;
import org.junit.Assert;
import org.junit.Test;

import static com.sviryd.algorithms.util.Data.COUNT;

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
