package com.test.structureOfData.lafore.code.graph.basedLinkedList;

import com.test.structureOfData.lafore.code.graph.IGraph;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Exercise 13.2
 *
 * @param <V>
 */
public class Graph<V> implements IGraph<V> {
    private static final boolean VISITED = true;
    private static final boolean NO_VISITED = false;

    private boolean isDirectGraph;
    private LinkedList<Vertex<V>> vertexList;
    private LinkedList<LinkedList<Vertex<V>>> adjacencyList;
    private int nVertex;
    private boolean isTransitiveClosureApply;

    public Graph(final boolean isDirectGraph) {
        this.isDirectGraph = isDirectGraph;
        this.vertexList = new LinkedList<>();
        this.adjacencyList = new LinkedList<>();
    }

    @Override
    public void addVertex(final V value) {
        if (value == null) {
            throw new IllegalArgumentException("The argument in addVertex method is null.");
        }
        if (isValueExists(value)) {
            throw new IllegalArgumentException("The vertex already exists with value " + value);
        }
        vertexList.addLast(new Vertex(value));
        adjacencyList.addLast(new LinkedList<Vertex<V>>());
        ++nVertex;
    }

    private boolean isValueExists(final V value) {
        for (Vertex<V> v : vertexList) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addEdge(final int indexFrom, final int indexTo) {
        if (indexFrom < 0 || indexFrom >= nVertex || indexTo < 0 || indexTo >= nVertex) {
            throw new IllegalArgumentException("The indexFrom or(and) indexTo is out of vertexList range.");
        }
        Vertex<V> toVertex = vertexList.get(indexTo);
        LinkedList<Vertex<V>> listFrom = adjacencyList.get(indexFrom);
        listFrom.addLast(toVertex);
        if (!isDirectGraph) {
            Vertex<V> fromVertex = vertexList.get(indexFrom);
            listFrom = adjacencyList.get(indexTo);
            listFrom.addLast(fromVertex);
        }
    }

    private void flushVisited() {
        LinkedList<Vertex<V>> listFrom;
        for (int i = 0; i < nVertex; i++) {
            listFrom = adjacencyList.get(i);
            for (Vertex<V> vertex : listFrom) {
                vertex.setVisited(NO_VISITED);
            }
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
     * Algorithm traverse from start vertex to next which have adjacency with started in a cycle until vertex have
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
        Stack<Integer> stack = new Stack<>();
        Vertex<V> firstVertexInList = vertexList.get(indexFromFirst);
        firstVertexInList.setVisited(VISITED);
        stack.push(indexFromFirst);

        while (!stack.isEmpty()) {
            int indexFrom = stack.peek();
            int indexTo = getIndexToUnvisitedVertex(indexFrom);
            if (indexTo == -1) {
                stack.pop();
            } else {
                Vertex<V> toVertex = vertexList.get(indexTo);
                toVertex.setVisited(VISITED);
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
     * Build all pairs of index vertex using indirect graph in width first traversal order.
     * Order:
     * Algorithm traverse from started vertex to all which have adjacency with started and not visited. In the next step
     * algorithm passes to next already visited vertex and repeats above behaviour.
     * Algorithm finishes its work when all vertex is visited.
     */

    private LinkedList<Pair<Integer, Integer>> buildWidthFirstTraversalForIndirectGraph() {
        if (isDirectGraph) {
            throw new IllegalArgumentException("buildWidthFirstTraversalForIndirectGraph method " +
                    "not supported for the direct graph.");
        }
        LinkedList<Pair<Integer, Integer>> pairs = new LinkedList<>();

        int indexFromFirst = 0;
        Queue<Integer> queue = new LinkedList<>();
        Vertex<V> firstVertex = vertexList.get(indexFromFirst);
        firstVertex.setVisited(VISITED);
        queue.add(indexFromFirst);

        int indexFrom;
        int indexTo;
        while (!queue.isEmpty()) {
            indexFrom = queue.remove();
            while ((indexTo = getIndexToUnvisitedVertex(indexFrom)) != -1) {
                Vertex<V> toVertex = vertexList.get(indexTo);
                toVertex.setVisited(VISITED);
                Pair<Integer, Integer> pair = new Pair(indexFrom, indexTo);
                pairs.add(pair);
                queue.add(indexTo);
            }
        }
        flushVisited();
        return pairs;
    }

    private int getIndexToUnvisitedVertex(final int indexFrom) {
        LinkedList<Vertex<V>> listFrom = adjacencyList.get(indexFrom);
        for (Vertex<V> vertex : listFrom) {
            if (!vertex.isVisited()) {
                return getIndex(vertex);
            }
        }
        return -1;
    }

    /**
     * Return array builds by in series excluding from graph vertex which not have any adjacency.
     * V of each excluding vertex insertFirst in array from the end. This order of building names topological.
     * <p>
     * Returning array may to have imaginable representation as a special tree.
     * All edges direct from bottom to root of tree.
     * All levels of tree is a group of vertex which not have adjacency between each other. In contrary case
     * the tree has a cycle and there is no way for topological sort.
     *
     * @return array of V in topological order
     */
    @Override
    public Object[] getTopologicalSortedArray() {
        int countDown = nVertex;
        Object[] array = new Object[countDown];
        Graph<V> copyGraph = clone();

        while (countDown > 0) {
            int indexNoHavingEdges = copyGraph.getIndexNoHavingEdges();
            if (indexNoHavingEdges == -1) {
                throw new IllegalArgumentException("The graph has a cycle.");
            }
            array[--countDown] = copyGraph.vertexList.get(indexNoHavingEdges).getValue();
            copyGraph.deleteVertexWithAdjacency(indexNoHavingEdges);
        }
        return array;
    }
    @Override
    public Graph<V> clone() {
        Graph<V> copyGraph = new Graph(isDirectGraph);
        copyGraph.nVertex = nVertex;
        copyGraph.adjacencyList = (LinkedList<LinkedList<Vertex<V>>>) adjacencyList.clone();
        copyGraph.vertexList = (LinkedList<Vertex<V>>) vertexList.clone();
        copyGraph.isTransitiveClosureApply = isTransitiveClosureApply;
        return copyGraph;
    }

    private int getIndexNoHavingEdges() {
        for (int i = 0; i < nVertex; i++) {
            LinkedList<Vertex<V>> listFrom = adjacencyList.get(i);
            if (listFrom.isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    private void deleteVertexWithAdjacency(final int index) {
        Vertex<V> deleteVertex = vertexList.get(index);
        deleteVertex(index);
        moveRowUp(index);
        moveFromAnotherRow(deleteVertex);
    }

    private int getIndex(final Vertex<V> vertex) {
        return vertexList.indexOf(vertex);
    }

    private void deleteVertex(final int index) {
        vertexList.remove(index);
        nVertex--;
    }

    private void moveRowUp(final int index) {
        adjacencyList.remove(index);
    }

    private void moveFromAnotherRow(final Vertex<V> deleteVertex) {
        for (LinkedList<Vertex<V>> listFrom : adjacencyList) {
            listFrom.remove(deleteVertex);
        }
    }

    /**
     * Builds another adjacencyList by adding to adjacencyList new edges in order:
     * A -> B and B -> C so A -> C
     */
    public LinkedList<LinkedList<Vertex<V>>> getTransitiveMatrix() {
        LinkedList<LinkedList<Vertex<V>>> transitiveMatrix = (LinkedList<LinkedList<Vertex<V>>>) adjacencyList.clone();
        for (int i = 0; i < nVertex; i++) {
            LinkedList<Vertex<V>> listTo = transitiveMatrix.get(i);
            Vertex<V> vertexFrom = getNotVisitedVertex(listTo);

            while (vertexFrom != null) {
                int indexFrom = getIndex(vertexFrom);
                LinkedList<Vertex<V>> listFrom = transitiveMatrix.get(indexFrom);
                copyVertexIfNotExistsFromTo(listFrom, listTo);
                if (!isDirectGraph) {
                    copyVertexIfNotExistsFromTo(listTo, listFrom);
                }
                vertexFrom.setVisited(VISITED);
                vertexFrom = getNotVisitedVertex(listTo);
            }
            flushVisited();
        }
        return transitiveMatrix;
    }

    @Override
    public void applyTransitiveClosure() {
        adjacencyList = getTransitiveMatrix();
        isTransitiveClosureApply = true;
    }

    private Vertex<V> getNotVisitedVertex(final LinkedList<Vertex<V>> list) {
        for (Vertex<V> currentVertex : list) {
            if (!currentVertex.isVisited()) {
                return currentVertex;
            }
        }
        return null;
    }

    private void copyVertexIfNotExistsFromTo(final LinkedList<Vertex<V>> src, final LinkedList<Vertex<V>> dest) {
        for (Vertex<V> vertex : src) {
            if (dest.indexOf(vertex) == -1) {
                dest.addLast(vertex);
            }
        }
    }

    @Override
    public int[] getTransitiveIndexesFor(final int indexFrom) {
        if (!isTransitiveClosureApply) {
            throw new IllegalArgumentException("getTransitiveListFor() allow to use after " +
                    "calling of applyTransitiveClosure()");
        }
        LinkedList<Vertex<V>> listTo = adjacencyList.get(indexFrom);
        int size = listTo.size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            Vertex<V> vertex = listTo.get(i);
            int index = getIndex(vertex);
            array[i] = index;
        }
        return array;
    }

    /**
     * Return index of not visited and adjacency with indexFrom.
     * If indexNotSuccess is not null, return index after indexNotSuccess.
     *
     * @param indexFrom       index of started Vertex in vertexList (accordingly in adjacencyList)
     * @param indexNotSuccess index of not successful Vertex in vertexList
     * @return index of adjacency Vertex in vertexList (accordingly in adjacencyList)
     */
    public Integer getIndexAdjacencyForFrom(final int indexFrom, final Integer indexNotSuccess) {
        if (isIndexOverBoundOfVertexList(indexFrom)) {
            throw new IllegalArgumentException(
                    "The first argument of getIndexNextVisitedVertexFrom is over bound of vertexArray.");
        }
        Integer inxAdj = null;
        LinkedList<Vertex<V>> listTo = adjacencyList.get(indexFrom);
        if (indexNotSuccess == null) {
            for (Vertex<V> to : listTo) {
                if (!to.isVisited()) {
                    inxAdj = getIndex(to);
                    break;
                }
            }
        } else {
            inxAdj = getIndexAdjacencyAfterNotSuccess(listTo, indexNotSuccess);
        }
        return inxAdj;
    }

    private Integer getIndexAdjacencyAfterNotSuccess(LinkedList<Vertex<V>> listTo, Integer indexNotSuccess) {
        boolean isSkipIndexNotSuccess = false;
        Vertex<V> vertexNotSuccess = vertexList.get(indexNotSuccess);
        for (int i = 0; i < listTo.size(); i++) {
            Vertex<V> temp = listTo.get(i);
            if (temp.equals(vertexNotSuccess)) {
                isSkipIndexNotSuccess = true;
                continue;
            }
            if (isSkipIndexNotSuccess && !temp.isVisited()) { // get index after vertexNotSuccess
                return getIndex(temp);
            }
        }
        return null;
    }

    private boolean isIndexOverBoundOfVertexList(final int index) {
        return index < 0 || index >= nVertex;
    }

    public void setVisited(final int index) {
        if (isIndexOverBoundOfVertexList(index)) {
            throw new IllegalArgumentException("The argument in setVisited method is over a bound of vertexList.");
        }
        Vertex<V> indexVertex = vertexList.get(index);
        indexVertex.setVisited(VISITED);
    }

    public void setNotVisited(final int index) {
        if (isIndexOverBoundOfVertexList(index)) {
            throw new IllegalArgumentException("The argument in setVisited method is over a bound of vertexList.");
        }
        Vertex<V> indexVertex = vertexList.get(index);
        indexVertex.setVisited(NO_VISITED);
    }

    @Override
    public boolean isAllVisited() {
        for (int i = 0; i < nVertex; i++) {
            if (!vertexList.get(i).isVisited()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return nVertex;
    }

    @Override
    public V getValue(final int index) {
        return vertexList.get(index).getValue();
    }

    private void displayVertex(final int index) {
        System.out.print(vertexList.get(index));
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

        public void setValue(final V value) {
            this.value = value;
        }

        private boolean isVisited() {
            return visited;
        }

        private void setVisited(final boolean visited) {
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
