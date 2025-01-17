package com.sviryd.algorithms.lafore.code.sort;

import com.sviryd.algorithms.util.Data;
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
