package com.sviryd.algorithms.lafore.code.sort;

import com.sviryd.algorithms.util.Data;
import org.junit.Assert;
import org.junit.Test;

public class ShellSortTest {
    @Test
    public void sort(){
        Integer [] actual = Data.getIntegerReverseArray(9);
        Integer [] expected = Data.getIntegerNaturalArray(9);
        ShellSort.sort(actual);
        Assert.assertArrayEquals(expected,actual);
    }
}
