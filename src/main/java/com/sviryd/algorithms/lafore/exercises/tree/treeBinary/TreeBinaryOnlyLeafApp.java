package com.sviryd.algorithms.lafore.exercises.tree.treeBinary;

public class TreeBinaryOnlyLeafApp {
    public static void main(String[] args) {
        TreeBinaryOnlyLeaf tree = new TreeBinaryOnlyLeaf("+");
        int size = 10;
        for (int i = 0; i < size; i++) {
            tree.put(i);
        }
        tree.display(1, 1);
    }
}
