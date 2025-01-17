package com.sviryd.algorithms.util;

public class Print {
    public static void printArray(Object [] array){
        for (int i = 0; i < array.length; i++) {
            if (array[i]!=null){
                System.out.print(array[i] + " ");
            }
        }
        System.out.println();
    }
}
