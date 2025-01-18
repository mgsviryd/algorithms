package com.sviryd.algorithms.lafore.exercise.tree.tree234;

import java.util.Random;

public class Tree234App {
    public static void main(String[] args) {
        int size = 15;
        Tree234 tree234 = new Tree234();
        for (int i = 0; i < size; i++) {
            int random = new Random().nextInt(size*10);
            tree234.put(random,random);
        }
    }
}
