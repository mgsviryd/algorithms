package com.sviryd.algorithms.lafore.code.graph.basedArray;

import com.sviryd.algorithms.lafore.code.graph.IGraph;
import com.sviryd.algorithms.princeton.util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph<V> implements IGraph<V>, Cloneable {
    private static final int DEFAULT_MAX_VERTEX = 20;
    private static final boolean NO_EDGE = false;
    private static final boolean EXIST_EDGE = true;
    private static final boolean VISITED = true;
    private static final boolean NO_VISITED = false;

    private int maxVertex;
    private Vertex<V>[] vertexArray;
    private boolean [][] adjacencyMatrix;
    private int nVertex;
    private boolean isDirectGraph;
    private boolean isTransitiveClosureApply;

    private Graph() {
    }

    private Graph(final int maxVertex) {
        this.maxVertex = maxVertex;
        vertexArray = new Vertex[maxVertex];
        adjacencyMatrix = new boolean[maxVertex][maxVertex];
        for (int i = 0; i < maxVertex; i++) {
            for (int j = 0; j < maxVertex; j++) {
                adjacencyMatrix[i][j] = NO_EDGE;
            }
        }
    }

    public Graph(final boolean isDirectGraph) {
        this(DEFAULT_MAX_VERTEX);
        this.isDirectGraph = isDirectGraph;
    }

    public Graph(final int maxVertex, final boolean isDirectGraph) {
        this(maxVertex);
        this.isDirectGraph = isDirectGraph;
    }

    public void addVertex(final V value) {
        if (nVertex == maxVertex) {
            throw new IllegalArgumentException("The array of Vertex is already full.");
        }
        vertexArray[nVertex++] = new Vertex(value);
    }

    public void addEdge(final int indexFrom, final int indexTo) {
        if (indexFrom < 0 || indexFrom >= nVertex || indexTo < 0 || indexTo >= nVertex) {
            throw new IllegalArgumentException("The indexFrom and indexTo not be index of existed vertexArray.");
        }
        adjacencyMatrix[indexFrom][indexTo] = EXIST_EDGE;
        if (!isDirectGraph) {
            adjacencyMatrix[indexTo][indexFrom] = EXIST_EDGE;
        }
    }

    private void flushVisited() {
        for (int i = 0; i < nVertex; i++) {
            vertexArray[i].setVisited(NO_VISITED);
        }
    }

    @Override
    public void displayDepthFirstTraversalForIndirectGraph() {
        LinkedList<Pair<Integer, Integer>> pairs = buildDepthFirstTraversalForIndirectGraph();
        for (Pair<Integer, Integer> pair : pairs) {
            displayVertex(pair.getKey());
            displayVertex(pair.getValue());
            System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * Build all pairs of index vertex using indirect graph in depth first traversal order.
     * Order:
     * Algorithm traverse from indexFrom vertex to next which have adjacency with indexFrom in a cycle until vertex have
     * adjacency. Otherwise it returns back at a previous vertex and executes above behaviour.
     * Algorithm finishes its work when all vertex is visited.
     */
    private LinkedList<Pair<Integer, Integer>> buildDepthFirstTraversalForIndirectGraph() {
        if (isDirectGraph) {
            throw new IllegalArgumentException("displayDepthFirstTraversalForIndirectGraph method " +
                    "not supported for the direct graph.");
        }
        LinkedList<Pair<Integer, Integer>> pairs = new LinkedList<>();

        int indexFromFirst = 0;
        Stack<Integer> stack = new Stack();
        vertexArray[indexFromFirst].setVisited(VISITED);
        stack.push(indexFromFirst);

        while (!stack.isEmpty()) {
            int indexFrom = stack.peek();
            int indexTo = getIndexToUnvisitedVertex(indexFrom);
            if (indexTo == -1) {
                stack.pop();
            } else {
                vertexArray[indexTo].setVisited(VISITED);
                stack.push(indexTo);
                Pair<Integer, Integer> pair = new Pair(indexFrom, indexTo);
                pairs.add(pair);
            }
        }
        flushVisited();
        return pairs;
    }

    @Override
    public LinkedList<Pair<Integer, Integer>> displayWidthFirstTraversalForIndirectGraph() {
        LinkedList<Pair<Integer, Integer>> pairs = buildWidthFirstTraversalForIndirectGraph();
        for (Pair<Integer, Integer> pair : pairs) {
            displayVertex(pair.getKey());
            displayVertex(pair.getValue());
            System.out.print(" ");
        }
        System.out.println();
        return pairs;
    }

    /**
     * Exercise 13.1
     * Build all pairs of index vertex using indirect graph in width first traversal order.
     * Order:
     * Algorithm traverse from indexFromed vertex to all which have adjacency with indexFromed and not visited. In the next step
     * algorithm passes to next already visited vertex and repeats above behaviour.
     * Algorithm finishes its work when all vertex is visited.
     */
    private LinkedList<Pair<Integer, Integer>> buildWidthFirstTraversalForIndirectGraph() {
        if (isDirectGraph) {
            throw new IllegalArgumentException("buildWidthFirstTraversalForIndirectGraph method " +
                    "not supported for the direct graph.");
        }
        LinkedList<Pair<Integer, Integer>> pairs = new LinkedList<>();

        Queue<Integer> queue = new LinkedList<>();
        vertexArray[0].setVisited(true);
        queue.add(0);

        int indexFrom;
        int indexTo;
        while (!queue.isEmpty()) {
            indexFrom = queue.remove();
            while ((indexTo = getIndexToUnvisitedVertex(indexFrom)) != -1) {
                vertexArray[indexTo].setVisited(VISITED);
                queue.add(indexTo);
                Pair<Integer, Integer> pair = new Pair(indexFrom, indexTo);
                pairs.add(pair);
            }
        }
        flushVisited();
        return pairs;
    }

    private int getIndexToUnvisitedVertex(final int indexFrom) {
        for (int indexTo = 0; indexTo < nVertex; indexTo++) {
            if (adjacencyMatrix[indexFrom][indexTo] == EXIST_EDGE && !vertexArray[indexTo].isVisited()) {
                return indexTo;
            }
        }
        return -1;
    }

    public Graph<V> clone() {
        Graph<V> copyGraph = new Graph<V>();
        copyGraph.maxVertex = maxVertex;
        copyGraph.isDirectGraph = isDirectGraph;
        copyGraph.isTransitiveClosureApply = isTransitiveClosureApply;
        for (int i = 0; i < vertexArray.length; i++) {
            copyGraph.vertexArray[i] = vertexArray[i];
        }
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                copyGraph.adjacencyMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }
        return copyGraph;
    }

    /**
     * Return array builds by in series excluding from graph vertex which not have any adjacency.
     * V of each excluding vertex add in array from the indexTo. This order of building names topological.
     * <p>
     * Returning array may to have imaginable representation as a special tree.
     * All edges direct from bottom to root of tree.
     * All levels of tree is a group of vertex which not have adjacency between each other. In contrary case
     * the tree has a cycle and there is no way for topological sort.
     *
     * @return array of V in topological order
     */
    public Object[] getTopologicalSortedArray() {
        Object[] array = new Object[nVertex];
        int countDown = array.length;
        Graph<V> copyGraph = clone();

        while (copyGraph.size() > 0) {
            int indexNoHavingEdges = copyGraph.getIndexNoHavingEdges();
            if (indexNoHavingEdges == -1) {
                throw new IllegalArgumentException("The graph has a cycle.");
            }
            array[--countDown] = copyGraph.vertexArray[indexNoHavingEdges].getValue();
            copyGraph.deleteVertexAndAdjacencyFor(indexNoHavingEdges);
        }
        return array;
    }

    private int getIndexNoHavingEdges() {
        boolean isEdge;
        for (int indexFrom = 0; indexFrom < nVertex; indexFrom++) {
            isEdge = false;
            for (int indexTo = 0; indexTo < nVertex; indexTo++) {
                if (adjacencyMatrix[indexFrom][indexTo] == EXIST_EDGE) {
                    isEdge = true;
                    break;
                }
            }
            if (!isEdge) {
                return indexFrom;
            }
        }
        return -1;
    }

    private void deleteVertexAndAdjacencyFor(final int index) {
        if (index != nVertex - 1) {
            deleteVertex(index);
            moveRowUp(index);
            moveColumnLeft(index);
        }
        nVertex--;
    }

    private void deleteVertex(final int index) {
        for (int i = index; i < nVertex - 1; i++) {
            vertexArray[i] = vertexArray[i + 1];
        }
    }

    private void moveRowUp(final int index) {
        for (int row = index; row < nVertex - 1; row++) {
            for (int column = 0; column < nVertex; column++) {
                adjacencyMatrix[row][column] = adjacencyMatrix[row + 1][column];
            }
        }
    }

    private void moveColumnLeft(final int index) {
        for (int column = index; column < nVertex - 1; column++) {
            for (int row = 0; row < nVertex - 1; row++) {
                adjacencyMatrix[row][column] = adjacencyMatrix[row][column + 1];
            }
        }
    }

    /**
     * Exercise 13.4
     * Builds another adjacencyMatrix by adding to adjacencyMatrix new edges in order:
     * A -> B and B -> C so A -> C
     */
    public boolean [][] getTransitiveMatrix() {
        boolean[][] transitiveMatrix = new boolean[nVertex][nVertex];
        for (int i = 0; i < nVertex; i++) {
            transitiveMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], nVertex);
        }
        for (int i = 0; i < nVertex; i++) {
            for (int j = 0; j < nVertex; j++) {
                if (transitiveMatrix[i][j] == EXIST_EDGE) {
                    for (int k = 0; k < nVertex; k++) {
                        if (transitiveMatrix[j][k] == EXIST_EDGE) {
                            transitiveMatrix[i][k] = EXIST_EDGE;
                            if (!isDirectGraph) {
                                transitiveMatrix[k][i] = EXIST_EDGE;
                            }
                        }
                    }
                }
            }
        }
        return transitiveMatrix;
    }

    @Override
    public void applyTransitiveClosure() {
        adjacencyMatrix = getTransitiveMatrix();
        isTransitiveClosureApply = true;
    }

    /**
     * Exercise 13.3
     */
    public void displayTransitiveMatrix() {
        boolean [][] transitiveMatrix;
        if (isTransitiveClosureApply) {
            transitiveMatrix = adjacencyMatrix;
        } else {
            transitiveMatrix = getTransitiveMatrix();
        }
        int countVertex = transitiveMatrix.length;
        System.out.print("  ");
        for (int i = 0; i < countVertex; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < countVertex; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < countVertex; j++) {
                System.out.print(transitiveMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int[] getTransitiveIndexesFor(final int indexFrom) {
        if (!isTransitiveClosureApply) {
            throw new IllegalArgumentException("getTransitiveIndexFor() allow to use after " +
                    "calling of applyTransitiveClosure().");
        }
        int size = getCountEdge(indexFrom);
        int array[] = new int[size];
        int count = 0;
        for (int i = 0; i < nVertex; i++) {
            if (adjacencyMatrix[indexFrom][i] == EXIST_EDGE) {
                array[count++] = i;
            }
        }
        return array;
    }

    private int getCountEdge(final int indexFrom) {
        int count = 0;
        for (boolean edge : adjacencyMatrix[indexFrom]) {
            if (edge == EXIST_EDGE) {
                ++count;
            }
        }
        return count;
    }

    public boolean isAllVisited() {
        for (int i = 0; i < nVertex; i++) {
            if (!vertexArray[i].isVisited()) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return nVertex;
    }

    public V getValue(final int index) {
        return vertexArray[index].getValue();
    }

    public void displayVertex(final int index) {
        System.out.print(vertexArray[index]);
    }

    private static class Vertex<V> {
        private V value;
        private boolean visited;

        private Vertex(final V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        boolean isVisited() {
            return visited;
        }

        void setVisited(final boolean visited) {
            this.visited = visited;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof Vertex)) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return visited == vertex.visited && value.equals(vertex.value);
        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + (visited ? 1 : 0);
            return result;
        }

        public String toString() {
            return value.toString();
        }
    }
}
