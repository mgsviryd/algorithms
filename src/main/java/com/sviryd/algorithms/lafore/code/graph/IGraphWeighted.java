package com.sviryd.algorithms.lafore.code.graph;

public interface IGraphWeighted<V> {
    void addVertex(V value);

    void addEdge(int indexFrom, int indexTo, int weight);

    void displayMinSpanningTreeForIndirectGraph();

    void buildMinPathMatrix();

    Integer[] getMinPathAsIndexes(int indexFrom, int indexTo);

    int getMinWeight(int indexFrom, int indexTo);

    int[][] getMinWeightIndexesMatrix();

    void applyMinPathMatrix();

    Integer[] getMinPathToEveryPossibleFrom(int indexFrom);

    Integer[] getClosedMinPathToEveryPossibleFrom(int indexFrom);

    int size();

    V getValue(int index);

    void displayVertex(int index);
}
