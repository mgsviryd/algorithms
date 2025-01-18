package com.sviryd.algorithms.lafore.exercise.hashTable;

public class PrimeGenerator {
    public static final int MAX_INTEGER_PRIME = Integer.MAX_VALUE-19;
    private static final int START = 2;
    private static boolean[] crossOut;
    private static int[] result;

    /**
     * @param startValue    first integer in range
     * @return  first appeared prime integer after {@code START} first integer in range
     */
    public static int getNextPrime(int startValue) {
        for (int j = startValue + 1; true; j++) {
            if (isPrime(j))
                return j;
        }
    }

    private static boolean isPrime(int n) {
        for (int j = START; (j * j <= n); j++)
            if (n % j == 0)
                return false;
        return true;
    }

    /**
     * This method generates prime integers until {@code maxValue} maximum integer in accordance with algorithm
     * "Recheta Eratosphena".
     * Take array of integers starting with {@code START} 2 and cross out all multiple integers. Take next not
     * crossed out integer and cross out all integers which is multiple for him. Repeat until there isn't crossed
     * out all multiple integers.
     *
     * @param maxValue  less integer in range
     * @return  array of generated prime integers
     */
    public static int[] generatePrimes(int maxValue) {
        if (maxValue<START){
            return new int[0];
        }
        else {
            initializeCrossOut(maxValue);
            crossOutMultiples();
            putUncrossedIntegerIntoResult();
            return result;
        }
    }

    private static void putUncrossedIntegerIntoResult() {
        result = new int[numberOfUncrossedIntegers()];
        for (int j=0, i = START; i < crossOut.length; i++) {
            if (notCrossed(i)){
                result[j++]=i;
            }
        }
    }

    private static int numberOfUncrossedIntegers() {
        int count = 0;
        for (int i = START; i < crossOut.length; i++) {
            if (notCrossed(i)){
                count++;
            }
        }
        return count;
    }

    private static void crossOutMultiples() {
        int limit = determineIterationLimit();
        for (int i = START; i <=limit; i++) {
            if (notCrossed(i)){
                crossOutMultiplesOf(i);
            }
        }
    }

    private static void crossOutMultiplesOf(int i) {
        for (int j = i * START; j < crossOut.length; j+=i) {
            crossOut[j] = true;
        }
    }

    private static boolean notCrossed(int i) {
        return !crossOut[i];
    }

    private static int determineIterationLimit() {
        double iterationLimit = Math.sqrt(crossOut.length);
        return (int) iterationLimit;
    }

    private static void initializeCrossOut(int maxValue){
        crossOut = new boolean[maxValue+1];
    }
}
