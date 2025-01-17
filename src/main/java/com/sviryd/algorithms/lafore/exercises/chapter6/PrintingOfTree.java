package com.sviryd.algorithms.lafore.exercises.chapter6;

/**
 * Exercise 6.2
 */
//TODO (Take solution from Chapters about TREES
public class PrintingOfTree {
    private String[] array;

    public void fillingOfArray(int length) {
        for (int j = 0; j < length; j++) {
            array[j] = "-";
        }
    }

    public void printOfTree(int startCount) {
        if (startCount > array.length)
            return;
        int lowerIndex = 0;
        int upperIndex = array.length - 1;
        int step = (array.length) / (startCount + 1);
        int localCount = 0;
        while (true) {
            if (localCount != startCount) {
                array[lowerIndex += step] = "X";
                ++lowerIndex;
                ++localCount;
            } else break;
            if (localCount != startCount) {
                array[upperIndex -= step] = "X";
                --upperIndex;
                ++localCount;
            } else break;
        }
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j]);
        }
        System.out.println();
        fillingOfArray(array.length);
        printOfTree(startCount * 2);
    }

    public void print(int lengthPrint) {
        array = new String[lengthPrint];
        fillingOfArray(lengthPrint);
        printOfTree(1);
    }

    public static void main(String[] args) {
        new PrintingOfTree().print(13);
    }
}
