package com.test.structureOfData.lafore.exercise.chapter6;

import com.test.structureOfData.lafore.exercises.chapter6.Involution;
import org.junit.Assert;
import org.junit.Test;

public class InvolutionTest {
    @Test
    public void involution(){
        int i = Involution.involution(3, 3);
        Assert.assertEquals(27,i);
    }
}
