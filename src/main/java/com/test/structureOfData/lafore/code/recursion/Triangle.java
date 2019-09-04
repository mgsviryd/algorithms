package com.test.structureOfData.lafore.code.recursion;

public class Triangle {
    public static int triangle(final int n) {
        if (n == 0) {
            return 1;
        } else {
            return n + triangle(n - 1);
        }
    }
}
