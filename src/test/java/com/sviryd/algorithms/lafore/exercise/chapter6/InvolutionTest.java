package com.sviryd.algorithms.lafore.exercise.chapter6;

import com.sviryd.algorithms.lafore.exercises.chapter6.Involution;
import org.junit.Assert;
import org.junit.Test;

public class InvolutionTest {
    @Test
    public void involution(){
        int i = Involution.involution(3, 3);
        Assert.assertEquals(27,i);
    }
}