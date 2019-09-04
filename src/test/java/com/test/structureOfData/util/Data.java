package com.test.structureOfData.util;

public class Data {
    public static final int COUNT = 1000;

    public static int [] getIntNaturalArray(int size){
        int[] array = new int[size];
        int element = 0;
        for (int i = 0; i < size; i++) {
            array[i] = element;
            element++;
        }
        return array;
    }
    public static int [] getIntReverseArray(int size){
        int[] array = new int[size];
        int element = size-1;
        for (int i = 0; i < size; i++) {
            array[i] = element;
            element--;
        }
        return array;
    }
    public static int [] getIntDuplicateArray(int size){
        int [] array = new int [size];
        int multiply = 10;
        int element = 0;
        for (int i = 0; i < size; i++) {
            array [i] = element%multiply;
            element++;
        }
        return array;
    }
    public static Integer [] getIntegerReverseArray(int size){
        Integer[] array = new Integer[size];
        int element = size-1;
        for (int i = 0; i < size; i++) {
            array[i] = element;
            element--;
        }
        return array;
    }
    public static Integer [] getIntegerNaturalArray(int size){
        Integer[] array = new Integer[size];
        int element = 0;
        for (int i = 0; i < size; i++) {
            array[i] = element;
            element++;
        }
        return array;
    }
    public static Double [] getDoubleNaturalArray(int size){
        Double[] array = new Double[size];
        Double element = 0.0;
        for (int i = 0; i < size; i++) {
            array[i] = element;
            element++;
        }
        return array;
    }
}
