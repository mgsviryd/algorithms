package com.sviryd.algorithms.lafore.code.linkedList;

import com.sviryd.algorithms.lafore.exercise.chapter5.IosifSlaviiGoal;
import org.junit.Test;

public class IosifSlaviiGoalTest {
    @Test
    public void chainOfIndexes() {
        int count = 20;
        int step = 3;
        IosifSlaviiGoal goal = new IosifSlaviiGoal(count, step);
        int[] inxs = goal.chainLeftIndexes();
        for (int inx : inxs) {
            System.out.print(inx + " - ");
        }
    }
}
