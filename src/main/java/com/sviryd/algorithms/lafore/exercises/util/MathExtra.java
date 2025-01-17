package com.sviryd.algorithms.lafore.exercises.util;

public class MathExtra {
    public static double log2(final int number) {
        if (number == 0) {
            return 0;
        }
        return 31 - Integer.numberOfLeadingZeros(number);
    }

    public static double logN(int size, int order) {
        return log2(size) / log2(order);
    }
}
