package com.test.structureOfData.lafore.code.graph.basedArray;

import com.test.structureOfData.util.Print;
import org.junit.Assert;
import org.junit.Test;

public class GraphWeightedTest {
    @Test
    public void minPathMatrix(){
        Integer [] expected = {0,3,2,4};
        GraphWeighted graph = new GraphWeighted(true);
        graph.addVertex('A'); // 0 (исходная вершина)
        graph.addVertex('B'); // 1
        graph.addVertex('C'); // 2
        graph.addVertex('D'); // 3
        graph.addVertex('E'); // 4
        graph.addEdge(0, 1, 50); // AB 50
        graph.addEdge(0, 3, 80); // AD 80
        graph.addEdge(1, 2, 60); // BC 60
        graph.addEdge(1, 3, 90); // BD 90
        graph.addEdge(2, 4, 40); // CE 40
        graph.addEdge(3, 2, 20); // DC 20
        graph.addEdge(3, 4, 70); // DE 70
        graph.addEdge(4, 1, 50); // EB 50
        graph.applyMinPathMatrix();
        Integer actual [] = graph.getMinPathAsIndexes(0,4);
        Print.printArray(actual);
        Assert.assertArrayEquals(expected,actual);
    }

    @Test
    public void minWeight(){
        int expected = 100;
        GraphWeighted graph = new GraphWeighted(true);
        graph.addVertex('A'); // 0 (исходная вершина)
        graph.addVertex('B'); // 1
        graph.addVertex('C'); // 2
        graph.addVertex('D'); // 3
        graph.addVertex('E'); // 4
        graph.addEdge(0, 1, 50); // AB 50
        graph.addEdge(0, 3, 80); // AD 80
        graph.addEdge(1, 2, 60); // BC 60
        graph.addEdge(1, 3, 90); // BD 90
        graph.addEdge(2, 4, 40); // CE 40
        graph.addEdge(3, 2, 20); // DC 20
        graph.addEdge(3, 4, 70); // DE 70
        graph.addEdge(4, 1, 50); // EB 50
        graph.applyMinPathMatrix();
        int actual = graph.getMinWeight(0, 2);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void minSpanningTree(){
        GraphWeighted graph = new GraphWeighted(false);
        graph.addVertex('A'); // 0 (исходная вершина)
        graph.addVertex('B'); // 1
        graph.addVertex('C'); // 2
        graph.addVertex('D'); // 3
        graph.addVertex('E'); // 4
        graph.addVertex('F'); // 5
        graph.addEdge(0, 1, 6); // AB 6
        graph.addEdge(0, 3, 4); // AD 4
        graph.addEdge(1, 2, 10); // BC 10
        graph.addEdge(1, 3, 7); // BD 7
        graph.addEdge(1, 4, 7); // BE 7
        graph.addEdge(2, 3, 8); // CD 8
        graph.addEdge(2, 4, 5); // CE 5
        graph.addEdge(2, 5, 6); // CF 6
        graph.addEdge(3, 4, 12); // DE 12
        graph.addEdge(4, 5, 40); // EF 7
        graph.displayMinSpanningTreeForIndirectGraph();
    }

    @Test
    public void minPathToEveryPossible(){
        GraphWeighted graph = new GraphWeighted(true);
        graph.addVertex('A'); // 0 (исходная вершина)
        graph.addVertex('B'); // 1
        graph.addVertex('C'); // 2
        graph.addVertex('D'); // 3
        graph.addVertex('E'); // 4
        graph.addEdge(0, 1, 50); // AB 50
        graph.addEdge(0, 3, 80); // AD 80
        graph.addEdge(1, 2, 60); // BC 60
        graph.addEdge(1, 3, 90); // BD 90
        graph.addEdge(2, 4, 40); // CE 40
        graph.addEdge(3, 2, 20); // DC 20
        graph.addEdge(3, 4, 70); // DE 70
        graph.addEdge(4, 1, 50); // EB 50
        Integer[] path = graph.getMinPathToEveryPossibleFrom(4);
        Print.printArray(path);
        System.out.println();
    }

    @Test
    public void minClosedPathToEveryPossible(){
        GraphWeighted graph = new GraphWeighted(true);
        graph.addVertex('A'); // 0 (исходная вершина)
        graph.addVertex('B'); // 1
        graph.addVertex('C'); // 2
        graph.addVertex('D'); // 3
        graph.addVertex('E'); // 4
        graph.addEdge(0, 1, 50); // AB 50
        graph.addEdge(0, 3, 80); // AD 80
        graph.addEdge(1, 2, 60); // BC 60
        graph.addEdge(1, 3, 90); // BD 90
        graph.addEdge(2, 4, 40); // CE 40
        graph.addEdge(3, 2, 20); // DC 20
        graph.addEdge(3, 4, 70); // DE 70
        graph.addEdge(4, 1, 50); // EB 50

        Integer[] path = graph.getClosedMinPathToEveryPossibleFrom(3);
        Print.printArray(path);
    }

}
