package com.sviryd.algorithms.lafore.code.graph;

import javafx.util.Pair;

import java.util.LinkedList;

public interface IGraph<V> {
    void addVertex(V value);

    void addEdge(int indexFrom, int indexTo);

    void displayDepthFirstTraversalForIndirectGraph();

    LinkedList<Pair<Integer, Integer>> displayWidthFirstTraversalForIndirectGraph();

    Object[] getTopologicalSortedArray();

    void applyTransitiveClosure();

    int [] getTransitiveIndexesFor(int indexFrom);

    boolean isAllVisited();

    int size();

    V getValue(int index);
}
