package com.test.structureOfData.lafore.code.sort;

import com.test.structureOfData.util.Data;
import org.junit.Assert;
import org.junit.Test;

public class QuickSortTest {
    @Test
    public void sort(){
        Integer [] actual = Data.getIntegerReverseArray(9);
        Integer [] expected = Data.getIntegerNaturalArray(9);
        QuickSort.sort(actual);
        Assert.assertArrayEquals(expected,actual);
    }
}
