package com.sviryd.algorithms.lafore.code.graph.basedLinkedList;

import com.sviryd.algorithms.util.Print;
import org.junit.Assert;
import org.junit.Test;

public class GraphTest {
    @Test
    public void topologicalSort() {
        Object[] expected = {'B', 'A', 'E', 'D', 'G', 'C', 'F', 'H'};
        Graph graph = new Graph(true);
        graph.addVertex('A'); // 0
        graph.addVertex('B'); // 1
        graph.addVertex('C'); // 2
        graph.addVertex('D'); // 3
        graph.addVertex('E'); // 4
        graph.addVertex('F'); // 5
        graph.addVertex('G'); // 6
        graph.addVertex('H'); // 7
        graph.addEdge(0, 3); // AD
        graph.addEdge(0, 4); // AE
        graph.addEdge(1, 4); // BE
        graph.addEdge(2, 5); // CF
        graph.addEdge(3, 6); // DG
        graph.addEdge(4, 6); // EG
        graph.addEdge(5, 7); // FH
        graph.addEdge(6, 7); // GH
        Object[] actual = graph.getTopologicalSortedArray();
        Print.printArray(actual);
        graph.displayWidthFirstTraversalForIndirectGraph();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void displayDepthFirstTraversalForIndirectGraph() {
        Object[] expected = {'A', 'B', 'C', 'D', 'E'};
        Graph graph = new Graph(false);
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.displayDepthFirstTraversalForIndirectGraph();
    }

    @Test
    public void displayWidthFirstTraversalForIndirectGraph() {
        Object[] expected = {'A', 'B', 'C', 'D', 'E'};
        Graph graph = new Graph(false);
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.displayWidthFirstTraversalForIndirectGraph();
    }
}
