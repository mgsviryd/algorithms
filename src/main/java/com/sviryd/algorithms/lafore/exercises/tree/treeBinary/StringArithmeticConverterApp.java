package com.sviryd.algorithms.lafore.exercises.tree.treeBinary;

public class StringArithmeticConverterApp {
    public static void main(String[] args) {
        double result = StringArithmeticConverter.computeArithmeticExpressionInInfixForm("(5+6*3)/23");
        System.out.println(result);
    }
}
