package com.sviryd.algorithms.lafore.code.recursion;

public class Factorial {
    public static int factorial(final int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * (factorial(n - 1));
        }
    }
}
