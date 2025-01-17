package com.sviryd.algorithms.lafore.code.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Not stable
 * Best     O(n^(3/2))
 * Worst    O(n^(7/6))
 * Memory   -
 * Lafore version is better than Princeton because part of code with insertion sort is better.
 */
public class ShellSort {
    public static <E extends Comparable<? super E>> void sort(final E[] a) {
        int size = a.length;
        int in, out;
        int h = 1;
        while (h < size / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (out = h; out < size; out++) {
                E temp = a[out];
                in = out;
                while (in > h - 1 && temp.compareTo(a[in - h]) < 0) { // insertion sort
                    a[in] = a[in - h];
                    in -= h;
                }
                a[in] = temp; // 1 time swap
            }
            h = h / 3;
        }
    }

    public static void main(String[] args) {
        int size = 10;
        Integer[] a = new Integer[size];
        a = Stream.generate(() -> ThreadLocalRandom.current().nextInt(size)).limit(size).collect(Collectors.toList()).toArray(a);
        sort(a);
        System.out.println(Arrays.asList(a));
    }
}
