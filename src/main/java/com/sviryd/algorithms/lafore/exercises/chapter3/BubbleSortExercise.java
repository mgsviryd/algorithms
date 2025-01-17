package com.sviryd.algorithms.lafore.exercises.chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.sviryd.algorithms.lafore.exercises.util.Swap.swap;

public class BubbleSortExercise {

    /**
     * Exercise 3.1
     * O(N^2), stable
     *
     * @param a - array
     */
    public static void sort(final int[] a) {
        int size = a.length;
        int inxStraight = 0;
        int inxBack = size - 1;

        while (inxStraight < inxBack) {
            for (int to = inxStraight; to < inxBack; to++) {
                if (a[to] > a[to + 1])
                    swap(to, to + 1, a);
            }
            --inxBack;
            for (int end = inxBack; end > inxStraight; end--) {
                if (a[end] < a[end - 1])
                    swap(end, end - 1, a);
            }
            ++inxStraight;
        }
    }


    /**
     * Exercise 3.4
     * Concurrency improves execution but in the same time it's slower that other sorts.
     * @param a - array
     */
    public static void oddEvenSort(final int[] a) {
        int lastOddInx;
        int lastEvenInx;
        int size = a.length;
        if ((size - 1) % 2 == 0) {
            lastOddInx = size - 1;
            lastEvenInx = size - 2;
        } else {
            lastOddInx = size - 2;
            lastEvenInx = size - 1;

        }

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Future<?> result = service.submit(() -> {
                for (int out = lastOddInx; out > 0; out -= 2) {
                    for (int in = 0; in < size - 2; in += 2) {
                        if (a[in] > a[in + 2]) {
                            swap(in, in + 2, a);
                        }
                    }
                }
            });

            Future<?> result1 = service.submit(() -> {
                for (int out = lastEvenInx; out > 0; out -= 2) {
                    for (int in = 1; in < size - 2; in += 2) {
                        if (a[in] > a[in + 2]) {
                            swap(in, in + 2, a);
                        }
                    }
                }
            });
            result.get(size, TimeUnit.MILLISECONDS);
            result1.get(size, TimeUnit.MICROSECONDS);
            service.submit(() -> sort(a));
        } catch (Exception e) {
            System.out.println("Not reached in time");
        } finally {
            if (service != null) service.shutdown();
        }
    }
}