package com.test.structureOfData.lafore.exercises.chapter5;

/**
 * Exercise 5.5.
 * Goal Iosif Slavii:
 * 1. have closure element
 * 2. element leaves circle in order of step
 */
public class IosifSlaviiGoal {
    private int count;
    private int step;

    public IosifSlaviiGoal(final int count, final int step) {
        this.count = count;
        this.step = step;
    }

    public int[] chainLeftIndexes() {
        int[] indexes = new int[count];
        ClosureLinkedList list = new ClosureLinkedList();
        for (int i = 0; i < count; i++) {
            list.push(i);
        }
        list.step(1);
        int i = 0;
        while (!list.isEmpty()) {
            indexes[i++] = (int) list.pop();
            list.step(step);
        }
        return indexes;
    }
}
