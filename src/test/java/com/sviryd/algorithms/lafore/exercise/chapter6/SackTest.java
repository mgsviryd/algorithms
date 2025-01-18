package com.sviryd.algorithms.lafore.exercise.chapter6;

import com.sviryd.algorithms.util.Data;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;

public class SackTest {
    @Test
    @Ignore("It's too mush time if you rise a count.")
    public void getCombinationIndex() {
        int count = 12;
        int capacity = 9;
        Double[] weight = Data.getDoubleNaturalArray(count);
        SackAnagram sackAnagram = new SackAnagram(weight, capacity);
        LinkedList<Double[]> combinations = sackAnagram.getCombinations();

        for (Double[] com : combinations) {
            for (int i = 0; i < com.length; i++) {
                System.out.print(com[i]);
                System.out.print(" + ");
            }
            System.out.println();
        }
    }
}
