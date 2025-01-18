package com.sviryd.algorithms.lafore.exercise.util;

public class Swap {
    public static <E>void swap(final int iA, final int iB, final E[] array){
        E temp = array[iA];
        array[iA] = array[iB];
        array[iB] = temp;
    }
    public static void swap(final int iA, final int iB, final int[] array){
        int temp = array[iA];
        array[iA] = array[iB];
        array[iB] = temp;
    }
}
