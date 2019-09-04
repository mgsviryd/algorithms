package com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight;

import com.test.structureOfData.lafore.code.graph.basedLinkedList.Graph;
import com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight.location.Location;
import com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight.location.Shifting;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Exercise 13.5
 */
public class MoveOfKnightSimulator {
    private static final int MAX_SIZE_SIDE_BOARD = 7;
    private static final List<Shifting> SHIFTING_LIST = new ArrayList<>();

    static {
        for (MoveOfKnight moveOfKnight : MoveOfKnight.values()) {
            SHIFTING_LIST.add(moveOfKnight.getShifting());
        }
    }

    private int sizeSideOfBoard;
    private int sizeCellOfBoard;
    private Graph<Knight> graph;
    private Deque<Integer> marchKnightAsIndexesInGraph;

    public MoveOfKnightSimulator(final int sizeSideOfBoard) {
        this.sizeSideOfBoard = sizeSideOfBoard;
        this.sizeCellOfBoard = sizeSideOfBoard * sizeSideOfBoard;
        graph = new Graph(true);
        generateKnightAsVertexOfGraph();
        generateAllOfMovingAsEdgeOfGraph();
        buildOrderIndexMoveOfKnight();
    }

    private void generateKnightAsVertexOfGraph() {
        for (int i = 0; i < sizeSideOfBoard; i++) {
            for (int j = 0; j < sizeSideOfBoard; j++) {
                Location location = new Location(j, i);
                Knight knight = new Knight(location);
                graph.addVertex(knight);
            }
        }
    }

    // this calculation needs because all Vertex generate in one-dimensional array
    private int getIndexInVertexArrayUsingLocation(final Location location) {
        return location.getOrd() * sizeSideOfBoard + location.getAbs();
    }

    private void generateAllOfMovingAsEdgeOfGraph() {
        for (int i = 0; i < sizeCellOfBoard; i++) {
            Knight knight = graph.getValue(i);
            Location fromKnight = knight.getLocation();
            for (Shifting shifting : SHIFTING_LIST) {
                Location toKnight = fromKnight.getShiftingLocation(shifting);
                generateMovingAsEdgeOfGraph(toKnight, i);
            }
        }
    }

    private void generateMovingAsEdgeOfGraph(final Location toKnight,final int indexFromKnight) {
        if (isLocationNotOutOfBound(toKnight)) {
            int indexToKnight = getIndexInVertexArrayUsingLocation(toKnight);
            graph.addEdge(indexFromKnight, indexToKnight);
        }
    }

    private boolean isLocationNotOutOfBound(Location location) {
        int x = location.getAbs();
        int y = location.getOrd();
        if (x < 0 || x >= sizeSideOfBoard) {
            return false;
        }
        if (y < 0 || y >= sizeSideOfBoard) {
            return false;
        }
        return true;
    }

    private void buildOrderIndexMoveOfKnight() {
        if (sizeSideOfBoard > MAX_SIZE_SIDE_BOARD) {
            throw new IllegalArgumentException("The time of execution go to infinity.");
        }
        Deque<Integer> stack = new LinkedList<>(); // each shifting save in stack

        for (int i = 0; i < sizeCellOfBoard; i++) {  // we observe each cells as a position for start
            graph.setVisited(i);
            stack.push(i);
            Integer indexNotSuccess = null; // next success vertex need to go after this
            while (stack.size() != 0) {
                Integer indexNextNotVisited = graph.getIndexAdjacencyForFrom(stack.peek(), indexNotSuccess);
                if (indexNextNotVisited != null) {
                    graph.setVisited(indexNextNotVisited);
                    stack.push(indexNextNotVisited);
                    indexNotSuccess = null;
                } else {
                    indexNotSuccess = stack.pop();
                    graph.setNotVisited(indexNotSuccess);
                }
                if (stack.size() == sizeCellOfBoard) {
                    marchKnightAsIndexesInGraph = stack;
                    return;
                }
            } // stack always empty and all vertex are not visited
        }
        marchKnightAsIndexesInGraph = stack;
    }

    public void displaySequenceOfKnights() {
        int count = 0;
        int maxSequenceNumberForOrder = sizeCellOfBoard;
        int maxCountSymbol = getCountSymbolForPositive(maxSequenceNumberForOrder);
        int displayArray[][] = new int[sizeSideOfBoard][sizeSideOfBoard];
        for (Integer index : marchKnightAsIndexesInGraph) {
            Knight knight = graph.getValue(index);
            Location location = knight.getLocation();
            int x = location.getAbs();
            int y = location.getOrd();
            displayArray[x][y] = ++count;
        }
        for (int i = sizeSideOfBoard - 1; i >= 0; i--) {
            for (int j = 0; j < sizeSideOfBoard; j++) {
                int sequenceNumber = displayArray[i][j];
                int countSymbolOfSequenceNumber = getCountSymbolForPositive(sequenceNumber);
                int numberSplitSymbol = maxCountSymbol - countSymbolOfSequenceNumber;
                String split = "";
                for (int k = 0; k < numberSplitSymbol; k++) {
                    split += " ";
                }
                System.out.print(sequenceNumber + split + " ");
            }
            System.out.println();
        }
    }

    private int getCountSymbolForPositive(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("The argument of getCountSymbolForPositive is negative.");
        }
        int maxCountOfSymbols = 1;
        while (true) {
            if (number < 10) {
                return maxCountOfSymbols;
            }
            number %= 10;
            maxCountOfSymbols += 1;
        }
    }
}
