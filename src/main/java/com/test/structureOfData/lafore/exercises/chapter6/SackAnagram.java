package com.test.structureOfData.lafore.exercises.chapter6;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Exercise 6.4
 * Using anagrams.
 * @param <E>
 */
public class SackAnagram<E extends Double> {
    public static final int MIN_COUNT = 2;
    private int nWeights;
    private E[] weights;
    private int capacity;
    private LinkedList<Double[]> combinations;

    public LinkedList<Double[]> getCombinations() {
        return combinations;
    }

    public SackAnagram(final E[] weights, final int capacity) {
        this.nWeights = weights.length;
        this.capacity = capacity;
        this.weights = weights;
        this.combinations = new LinkedList<>();
        doAnagram(nWeights);
    }


    private void doAnagram(int newSize) {
        if (newSize == 1) {
            return;
        }
        for (int j = 0; j < newSize; j++) {
            doAnagram(newSize - 1);
            if (newSize == MIN_COUNT) {
                int countItemIfFull = getCountItemIfFull();
                if (countItemIfFull > 0) {
                    addCombination(countItemIfFull);
                }
            }
            rotate(newSize);
        }
    }

    private void addCombination(final int countItem) {
        Double[] combination = new Double[countItem];
        for (int i = 0; i < countItem; i++) {
            combination[i] = weights[i];
        }
        Arrays.sort(combination);
        if (isCombinationNotExist(combination)) {
            combinations.add(combination);
        }
    }

    private boolean isCombinationNotExist(final Double[] checking) {
        for (Double[] exists : combinations) {
            if (Arrays.equals(exists, checking)) {
                return false;
            }
        }
        return true;
    }

    private void rotate(final int newSize) {
        int j;
        int position = nWeights - newSize;
        E temp = weights[position];
        for (j = position + 1; j < nWeights; j++) {
            weights[j - 1] = weights[j];
        }
        weights[j - 1] = temp;
    }

    private int getCountItemIfFull() {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum = Double.sum(sum, weights[i]);
            if (Double.compare(sum, capacity) > 0) {
                break;
            } else if (Double.compare(sum, capacity) == 0) {
                return i + 1;
            }
        }
        return -1;
    }
}
