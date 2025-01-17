package com.sviryd.algorithms.lafore.code.recursion;

/**
 * Goal:
 * Shift each item from source to destination using buffer <>inter</>.
 * Prerequisite:
 * you cannot put source[2] on source [1] - only natural order for all 3(source, destination, buffer).
 */
public class Tower {
    public static void doTower(int topN, char from, char inter, char to) {
        if (topN == 1) {
            System.out.println("Disc 1 from " + from + " to " + to);
        } else {
            doTower(topN - 1, from, to, inter);
            System.out.println("Disc " + topN + " from " + from + " to " + to);
            doTower(topN - 1, inter, from, to);
        }
    }
}
