package com.test.structureOfData.lafore.exercises.chapter6;

/**
 * Exercise 6.3
 */
public class Involution {
    public static int involution(final int number, final int degree) {
        if (degree == 1) {
            return number;
        }
        int result = involutionRaw(number, degree);
        if (degree % 2 == 1) {
            result *= number;
        }
        return result;
    }

    private static int involutionRaw(int number, final int degree) {
        if (degree == 1) {
            return number;
        }
        int narrowDegree = degree / 2;
        if (narrowDegree == 1) {
            return number * number;
        }
        number *= number;
        return number * involutionRaw(number, narrowDegree);
    }
}
