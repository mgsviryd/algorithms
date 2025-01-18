package com.sviryd.algorithms.lafore.exercise.tree.treeBinary;

import java.io.IOException;

import static com.sviryd.algorithms.lafore.exercise.tree.treeBinary.TraversalType.*;

public class TreeBinaryApp {
    public static void main(String[] args) throws IOException {
        int value;
        TreeBinary theTree = new TreeBinary();
        theTree.put(50, 1.5);
        theTree.put(25, 1.2);
        theTree.put(75, 1.7);
        theTree.put(12, 1.5);
        theTree.put(37, 1.2);
        theTree.put(43, 1.7);
        theTree.put(30, 1.5);
        theTree.put(33, 1.2);
        theTree.put(87, 1.7);
        theTree.put(93, 1.5);
        theTree.put(97, 1.5);
        theTree.displayTraversal(PREFIX);
        System.out.println("------------------");
        theTree.displayTraversal(INFIX);
        System.out.println("------------------");
        theTree.displayTraversal(POSTFIX);
        System.out.println("------------------");
        theTree.display(3,1);
    }
}
