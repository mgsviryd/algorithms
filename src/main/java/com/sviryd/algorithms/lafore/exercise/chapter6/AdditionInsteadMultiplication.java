package com.sviryd.algorithms.lafore.exercise.chapter6;

/**
 *  Exercise 6.1
 */
public class AdditionInsteadMultiplication {
    public static int mult(int number, int countOfInvocation){
        if (countOfInvocation==0)
            return 0;
        return number+mult(number, --countOfInvocation);

    }
}
