package com.test.structureOfData.lafore.code.graph.basedArray;

import com.test.structureOfData.lafore.code.graph.IGraphWeighted;
import com.test.structureOfData.lafore.exercises.heap.Heap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Exercise 14.2 skipped because solution will be too confused.
 *
 * @param <V>
 */
public class GraphWeighted<V> implements IGraphWeighted<V> {
    private static final int DEFAULT_MAX_VERTEX = 20;
    private static final boolean VISITED = true;
    private static final boolean NO_VISITED = false;

    private int maxVertex;
    private Vertex<V>[] vertexArray;
    private Edge<V>[][] adjacencyMatrix;
    private int nVertex;
    private boolean isDirectGraph;

    private Path[][] minPathMatrix;

    private Integer[] minPEP; // (minPEP) - min path to every possible vertex starting from one of few
    private Integer[] workMinPEP; // work space for minPEP
    private int countPEP; // count vertex on minPEP;
    private int weightPEP; // weight of minPEP;

    private Integer[] minCPEP; // (minCPEP) - min closed path to every possible vertex starting from one of few
    private Integer[] workMinCPEP; // work space for minCPEP
    private int countCPEP; // count vertex on minCPEP;
    private int weightCPEP; // weight of minCPEP;

    private GraphWeighted() {
    }

    private GraphWeighted(final int maxVertex) {
        this.maxVertex = maxVertex;
        vertexArray = new Vertex[maxVertex];
        adjacencyMatrix = new Edge[maxVertex][maxVertex];
    }

    public GraphWeighted(final boolean isDirectGraph) {
        this(DEFAULT_MAX_VERTEX);
        this.isDirectGraph = isDirectGraph;
    }

    public GraphWeighted(final int maxVertex, final boolean isDirectGraph) {
        this(maxVertex);
        this.isDirectGraph = isDirectGraph;
    }

    @Override
    public void addVertex(final V value) {
        if (nVertex == maxVertex) {
            resizeDouble();
        }
        if (nVertex == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("The index of vertexArray overflow Integer.MAX_VALUE.");
        }
        vertexArray[nVertex++] = new Vertex(value);
    }

    @Override
    public void addEdge(final int indexFrom, final int indexTo, final int weight) {
        if (indexFrom < 0 || indexFrom >= nVertex || indexTo < 0 || indexTo >= nVertex) {
            throw new IllegalArgumentException("The indexFrom and indexTo not be index of existed vertexArray.");
        }
        Vertex<V> fromVertex = vertexArray[indexFrom];
        Vertex<V> toVertex = vertexArray[indexTo];
        Edge<V> edge = new Edge(fromVertex, toVertex, weight);
        adjacencyMatrix[indexFrom][indexTo] = edge;
        if (!isDirectGraph) {
            edge = new Edge(toVertex, fromVertex, weight);
            adjacencyMatrix[indexTo][indexFrom] = edge;
        }
    }

    private void resizeDouble() {
        maxVertex = maxVertex * 2 >= Integer.MAX_VALUE ? Integer.MAX_VALUE : maxVertex * 2;
        Vertex<V>[] newVertexArray = new Vertex[maxVertex];
        System.arraycopy(vertexArray, 0, newVertexArray, 0, nVertex);
        vertexArray = newVertexArray;
        Edge<V>[][] newAdjacencyMatrix = new Edge[maxVertex][maxVertex];
        for (int i = 0; i < nVertex; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newAdjacencyMatrix[i], 0, nVertex);
        }
        adjacencyMatrix = newAdjacencyMatrix;
    }

    private void flushVisited() {
        for (int i = 0; i < nVertex; i++) {
            vertexArray[i].setVisited(NO_VISITED);
        }
    }

    private boolean isIndexOverBoundOfVertexArray(final int index) {
        return index < 0 || index >= nVertex;
    }

    @Override
    public void displayMinSpanningTreeForIndirectGraph() {
        System.out.print("Minimum spanning tree: ");
        LinkedList<Edge> edges = getMinSpanningTreeForIndirectGraph();
        for (Edge edge : edges) {
            System.out.print(edge + " ");
        }
        System.out.println();
    }

    /**
     * READ!!!!!!!!!!!!!!!!!
     * Prim's algorithm(eager version)
     * Display all vertex from indirect graph in special order.
     * Order:
     * Algorithm adds in heap all edge of starting vertex. If heap already consists the edge with the same
     * destination vertex, in heap stay only vertex with min weight. Then from heap extracted edge with min weight
     * and its destination vertex become a started vertex. Process proceeds while the heap is not empty.
     */
    private LinkedList<Edge> getMinSpanningTreeForIndirectGraph() {
        if (isDirectGraph) {
            throw new IllegalArgumentException("displayMinSpanningTreeForIndirectGraph method " +
                    "not supported for direct graph.");
        }
        LinkedList<Edge> list = new LinkedList<>();

        Heap heap = new Heap(nVertex, true);
        // comparator needs because: Edge consists 'toVertex' field which needs to compare
        // between each other so that only light in weight edge add in heap.
        Comparator<Edge> comparator = ((x, y) -> x.toVertex.equals(y.toVertex) ? 0 : -1);
        int from = 0;

        while (true) {
            vertexArray[from].setVisited(VISITED);

            for (int to = 0; to < nVertex; to++) {
                if (to == from) {
                    continue;
                }
                if (vertexArray[to].isVisited()) {
                    continue;
                }
                Edge edgeCurrent = adjacencyMatrix[from][to];
                if (edgeCurrent == null) {
                    continue;
                }
                int toHeap = heap.getIndex(comparator, edgeCurrent); // currentEdje.indexTo == (heapEdges).indexTo
                if (toHeap != -1) { // exists
                    Edge edgeHeap = (Edge) heap.getValue(toHeap);
                    Integer weightHeap = edgeHeap.weight;
                    Integer weightCurrent = edgeCurrent.weight;
                    if (weightCurrent.compareTo(weightHeap) < 0) {
                        heap.change(toHeap, edgeCurrent);
                    }
                } else { // not exists
                    heap.insert(edgeCurrent);
                }
            }
            if (heap.isEmpty()) {
                flushVisited();
                return list;
            }
            Edge minEdge = (Edge) heap.remove();
            list.add(minEdge);
            from = getIndexVertexInVertexArray(minEdge.toVertex);
        }
    }

    private int getIndexVertexInVertexArray(final Vertex<V> vertex) {
        for (int i = 0; i < nVertex; i++) {
            if (vertexArray[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Builds the matrix where each cells is a path represented by min sum of weight needed to pass from starting vertex
     * to end vertex.
     * Start vertex satisfies vertex which index in vertexArray satisfies first index of matrix.
     * End vertex satisfies vertex which index in vertexArray satisfies second index of matrix.
     */
    @Override
    /**
     * Exercise 14.3
     */
    public void buildMinPathMatrix() {
        minPathMatrix = new Path[nVertex][nVertex];
        for (int indexFrom = 0; indexFrom < nVertex; indexFrom++) {
            vertexArray[indexFrom].setVisited(VISITED);
            buildStartedPathFrom(indexFrom);

            while (true) {
                Integer indexMin = getIndexVertexWithMinWeightOfPath(indexFrom);
                if (indexMin == null) {
                    break;
                }
                vertexArray[indexMin].setVisited(VISITED);
                adjustPath(indexFrom, indexMin);
            }
            flushVisited();
        }
    }

    private void buildStartedPathFrom(final int indexFrom) {
        for (int indexTo = 0; indexTo < nVertex; indexTo++) {
            if (adjacencyMatrix[indexFrom][indexTo] != null) {
                Integer weight = adjacencyMatrix[indexFrom][indexTo].weight;
                minPathMatrix[indexFrom][indexTo] = new Path(indexFrom, weight);
            }
        }
    }

    private Integer getIndexVertexWithMinWeightOfPath(final int indexFrom) {
        Integer indexMin = null;
        Integer weightMin = null;
        for (int indexTo = 0; indexTo < nVertex; indexTo++) {
            Integer weight = minPathMatrix[indexFrom][indexTo] != null ? minPathMatrix[indexFrom][indexTo].weight : null;
            if (!vertexArray[indexTo].isVisited()) {
                if (weight != null) {
                    if (weightMin != null && weight.compareTo(weightMin) < 0) {
                        weightMin = weight;
                        indexMin = indexTo;
                    } else if (weightMin == null) {
                        weightMin = weight;
                        indexMin = indexTo;
                    }
                }
            }
        }
        return indexMin;
    }

    /**
     * Compares old path to end vertex existed in matrix with new path and persist only min path. New path is sum
     * of intermediate path and weight of edge where the start vertex is the end vertex of intermediate path.
     *
     * @param indexFrom index of started vertex
     * @param indexMin  index of intermediate vertex
     */
    private void adjustPath(final int indexFrom, final int indexMin) {
        for (int i = 0; i < nVertex; i++) {
            if (!vertexArray[i].isVisited()) {
                Integer fromStartToMin = minPathMatrix[indexFrom][indexMin].weight;
                Integer fromMinToNotVisited = adjacencyMatrix[indexMin][i] != null ? adjacencyMatrix[indexMin][i].weight : null;
                if (fromMinToNotVisited == null) {
                    continue; // if not adjacency from indexMin - continue
                }
                Integer fromStartToNotVisited = fromStartToMin + fromMinToNotVisited;
                Integer fromStartToNotVisitedOld = minPathMatrix[indexFrom][i] != null ? minPathMatrix[indexFrom][i].weight : null;

                if (fromStartToNotVisitedOld != null && fromStartToNotVisited.compareTo(fromStartToNotVisitedOld) >= 0) {
                    continue; // if new path >= old path
                } else {
                    // id new path < old path
                    minPathMatrix[indexFrom][i] = new Path(indexMin, fromStartToNotVisited);
                }
            }
        }
    }

    @Override
    /**
     * Exercise 14.1
     */
    public Integer[] getMinPathAsIndexes(final int indexFrom, final int indexTo) {
        if (isIndexOverBoundOfVertexArray(indexFrom) || isIndexOverBoundOfVertexArray(indexTo)) {
            throw new IllegalArgumentException("" +
                    "Argument of getMinPathAsIndexes method is over index bound of vertexArray.");
        }
        if (minPathMatrix == null) {
            throw new IllegalArgumentException("getMinPathAsIndexes() may be invoked after " +
                    "calling of buildMinPathMatrix()");
        }
        int previous = indexTo;
        Stack<Integer> stack = new Stack<>();
        while (previous != indexFrom) {
            stack.push(previous);
            previous = minPathMatrix[indexFrom][previous].indexPreviousVisitedVertex;
        }
        stack.push(indexFrom);
        int countOfIndex = stack.size();
        Integer[] minPath = new Integer[countOfIndex];
        for (int i = 0; i < countOfIndex; i++) {
            minPath[i] = stack.pop();
        }
        return minPath;
    }

    @Override
    public int getMinWeight(final int indexFrom, final int indexTo) {
        if (isIndexOverBoundOfVertexArray(indexFrom) || isIndexOverBoundOfVertexArray(indexTo)) {
            throw new IllegalArgumentException("Argument of getMinWeight method is over index bound of vertexArray.");
        }
        if (minPathMatrix == null) {
            throw new IllegalArgumentException("getMinPathAsIndexes() may be invoked after " +
                    "calling of buildMinPathMatrix()");
        }
        return minPathMatrix[indexFrom][indexTo].weight;
    }

    @Override
    public int[][] getMinWeightIndexesMatrix() {
        if (minPathMatrix == null) {
            throw new IllegalArgumentException("getMinPathAsIndexes() may be invoked after " +
                    "calling of buildMinPathMatrix()");
        }
        int[][] minWeightToAll = new int[nVertex][nVertex];
        for (int i = 0; i < nVertex; i++) {
            for (int j = 0; j < nVertex; j++) {
                minWeightToAll[i][j] = minPathMatrix[i][j].weight;
            }
        }
        return minWeightToAll;
    }

    /**
     * Rebuilds AdjacencyArray so that all possible paths consists from light weight edges.
     */
    @Override
    public void applyMinPathMatrix() {
        if (minPathMatrix == null) {
            buildMinPathMatrix();
        }
        adjacencyMatrix = new Edge[nVertex][nVertex];
        for (int from = 0; from < nVertex; from++) {
            for (int to = 0; to < nVertex; to++) {
                if (from == to) {
                    continue;
                }
                addEdgesBySplitPaths(from, to);
            }
        }
    }

    private void addEdgesBySplitPaths(final int from, final int to) {
        if (minPathMatrix[from][to] == null) {
            return;
        }
        Integer split = minPathMatrix[from][to].indexPreviousVisitedVertex;
        if (split.equals(from)) {
            Vertex<V> fromVertex = vertexArray[from];
            Vertex<V> toVertex = vertexArray[to];
            Integer weight = minPathMatrix[from][to].weight;
            adjacencyMatrix[from][to] = new Edge(fromVertex, toVertex, weight);
        } else {
            addEdgesBySplitPaths(from, split);
            addEdgesBySplitPaths(split, to);
        }
    }

    @Override
    /**
     * Exercise 14.4. Need use
     */
    public Integer[] getMinPathToEveryPossibleFrom(final int indexFrom) {
        if (isIndexOverBoundOfVertexArray(indexFrom)) {
            throw new IllegalArgumentException("" +
                    "Argument in getMinPathToEveryPossibleFrom method is over index bound of vertexArray.");
        }
        if (minPEP == null) {
            initializeWorkDataForMinPath();
            doAnagramMinPath(nVertex, indexFrom);
        }
        return minPEP;
    }

    private void initializeWorkDataForMinPath() {
        workMinPEP = new Integer[nVertex];
        minPEP = new Integer[nVertex];
        for (int i = 0; i < nVertex; i++) {
            workMinPEP[i] = i;
        }
        countPEP = 0;
        weightPEP = 0;
    }

    private void doAnagramMinPath(final int newSize, final int startIndex) {
        if (newSize == 1) {
            return;
        }
        for (int j = 0; j < newSize; j++) {
            doAnagramMinPath(newSize - 1, startIndex);
            if (newSize == 2) {
                checkMinPathAndMaxVisitedVertex(startIndex);
            }
            rotate(newSize, workMinPEP);
        }
    }

    private void rotate(final int newSize, final Integer[] array) {
        int j;
        int position = nVertex - newSize;
        Integer temp = array[position];
        for (j = position + 1; j < nVertex; j++) {
            array[j - 1] = array[j];
        }
        array[j - 1] = temp;
    }

    private void checkMinPathAndMaxVisitedVertex(final int startIndex) {
        if (workMinPEP[0] != startIndex) {
            return;
        }
        int currentCountPEP = 0;
        int currentWeightPEP = 0;
        for (int i = 0; i < workMinPEP.length - 1; i++) {
            int indexFrom = workMinPEP[i];
            int indexTo = workMinPEP[i + 1];
            Integer weight = adjacencyMatrix[indexFrom][indexTo] != null ? adjacencyMatrix[indexFrom][indexTo].weight : null;
            if (weight != null) {
                weightPEP += weight;
                ++currentCountPEP;
            } else {
                return;
            }
            int length = i + 2;
            if (currentCountPEP > countPEP) {
                countPEP = currentCountPEP;
                weightPEP = currentWeightPEP;
                copyWorkMinPEPInMinPEP(length);
            } else if (currentCountPEP == countPEP && weightPEP < currentWeightPEP) {
                countPEP = currentCountPEP;
                weightPEP = currentWeightPEP;
                copyWorkMinPEPInMinPEP(length);
            }
        }
    }

    private void copyWorkMinPEPInMinPEP(final int length) {
        minPEP = new Integer[nVertex];
        System.arraycopy(workMinPEP, 0, minPEP, 0, length);
    }

    @Override
    /**
     * Exercise 14.5
     */
    public Integer[] getClosedMinPathToEveryPossibleFrom(final int indexFrom) {
        if (isIndexOverBoundOfVertexArray(indexFrom)) {
            throw new IllegalArgumentException("" +
                    "Argument in getClosedMinPathToEveryPossibleFrom method is over index bound of vertexArray.");
        }
        initializeWorkDataForClosedMinPath();
        doAnagramClosedMinPath(nVertex, indexFrom);
        return minCPEP;
    }

    private void initializeWorkDataForClosedMinPath() {
        workMinCPEP = new Integer[nVertex];
        minCPEP = new Integer[nVertex];
        for (int i = 0; i < nVertex; i++) {
            workMinCPEP[i] = i;
        }
        countCPEP = 0;
        weightCPEP = 0;
    }

    private void doAnagramClosedMinPath(final int newSize, final int startIndex) {
        if (newSize == 1) {
            return;
        }
        for (int j = 0; j < newSize; j++) {
            doAnagramClosedMinPath(newSize - 1, startIndex);
            if (newSize == 2) {
                checkClosedMinPathAndMaxVisitedVertex(startIndex);
            }
            rotate(newSize, workMinCPEP);
        }
    }

    private void checkClosedMinPathAndMaxVisitedVertex(final int startIndex) {
        if (workMinCPEP[0] != startIndex) {
            return;
        }
        int currentCountCPEP = 0;
        int currentWeightCPEP = 0;
        for (int i = 0; i < workMinCPEP.length - 1; i++) {

            int indexFrom = workMinCPEP[i];
            int indexTo = workMinCPEP[i + 1];
            Integer weight = adjacencyMatrix[indexFrom][indexTo] != null ? adjacencyMatrix[indexFrom][indexTo].weight : null;
            if (weight != null) {
                currentCountCPEP++;
                currentWeightCPEP += weight;
            } else {
                return;
            }
            if (adjacencyMatrix[indexTo][startIndex] == null) {
                continue; // if you find a closed path the last index <indexTo>  must be have edge with started index <pointOfIndex>
            }
            int length = i + 2;
            if (currentCountCPEP > countCPEP) {
                countCPEP = currentCountCPEP;
                weightCPEP = currentWeightCPEP;
                copyWorkMinCPEPInMinCPEP(length);
            } else if (currentCountCPEP == countCPEP && currentWeightCPEP < weightCPEP) {
                countCPEP = currentCountCPEP;
                weightCPEP = currentWeightCPEP;
                copyWorkMinCPEPInMinCPEP(length);
            }
        }
    }

    private void copyWorkMinCPEPInMinCPEP(final int length) {
        minCPEP = new Integer[nVertex];
        System.arraycopy(workMinCPEP, 0, minCPEP, 0, length);
    }

    @Override
    public int size() {
        return nVertex;
    }


    @Override
    public V getValue(final int index) {
        return vertexArray[index].getValue();
    }

    @Override
    public void displayVertex(final int index) {
        System.out.print(vertexArray[index]);
    }


    private static class Vertex<V> {
        private final V value;
        private boolean visited;

        private Vertex(final V value) {
            this.value = value;
        }

        private V getValue() {
            return value;
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

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private static class Edge<V> implements Comparable<Edge> {
        private final Vertex<V> fromVertex;
        private final Vertex<V> toVertex;
        private final Integer weight;

        private Edge(final Vertex<V> fromVertex, final Vertex<V> toVertex, final Integer weight) {
            this.fromVertex = fromVertex;
            this.toVertex = toVertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge e) {
            return weight.compareTo(e.weight);
        }

        @Override
        public String toString() {
            return "" + fromVertex + toVertex;
        }
    }

    private static class Path implements Comparable<Path> {
        private Integer indexPreviousVisitedVertex;
        private Integer weight;

        private Path(final Integer indexPreviousVisitedVertex, final Integer weight) {
            this.indexPreviousVisitedVertex = indexPreviousVisitedVertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Path d) {
            return weight.compareTo(d.weight);
        }
    }
}
