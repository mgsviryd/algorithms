package com.test.structureOfData.lafore.exercises.chapter3;

import java.util.Arrays;

/**
 * Exercise 3.6 skiped because not efficient in comparison with Exercise 3.3
 */
public class InsertionSortExercise {
    private int[] a;

    public static void sort(final int[] a) {
        int size = a.length;
        int in, out;
        for (out = 1; out < size; out++) {
            int temp = a[out];
            in = out;
            while (in > 0 && a[in - 1] > temp) {
                a[in] = a[in - 1];
                in--;
            }
            a[in] = temp;
        }
    }

    public InsertionSortExercise(final int[] a) {
        this.a = a;
    }

    /**
     * Exercise 3.5
     * @return report about size of copying and comparing operations when this method run
     */
    public EfficiencyReport sort() {
        int size = a.length;
        EfficiencyReport report = new EfficiencyReport();
        int in, out;
        for (out = 1; out < size; out++) {
            int temp = a[out];
            in = out;
            while (true) {
                report.increaseCompare();
                if (in > 0 && a[in - 1] > temp) {
                    a[in] = a[in - 1];
                    in--;
                    report.increaseCopy();
                } else break;
            }
            a[in] = temp;
        }
        return report;
    }

    /**
     * Exercise 3.2
     * Algorithm: 1.sort 2. get median-index element
     */
    public static int getMedian(final int[] a) {
        int[] clone = Arrays.copyOf(a, a.length);
        InsertionSortExercise in = new InsertionSortExercise(clone);
        in.sort();
        int inxMeridian = in.getMedianIndex(clone);
        return clone[inxMeridian];
    }

    public static int getMedianIndex(final int[] a) {
        return a.length / 2 - 1;
    }

    /**
     * Exercise 3.3
     */
    public static void sortAndDeleteDuplicate(final int[] a) {
        sort(a);
        int defaultForTypeInt = 0;
        int prev = a[0];
        int inx = 1;
        for (int i = inx; i < a.length; i++) {
            if (a[i] != prev) {
                prev = a[i];
                a[inx++] = prev;
            }
        }
        for (int i = inx; i < a.length; i++) {
            a[i] = defaultForTypeInt;
        }
    }
}
