package com.test.structureOfData.lafore.exercises.tree;

import com.test.structureOfData.lafore.exercises.util.MathExtra;
import java.util.Deque;
import java.util.LinkedList;

public abstract class AbstractTree {
    public static int getCapacity(int indexLevel, int order, int items){
        int capacity = 0;
        for (int i = 0; i <= indexLevel; i++) {
            capacity+=getCapacityOnLevel(i,order,items);
        }
        return capacity;
    }
    public static int getCapacityOnLevel(int indexLevel, int order, int items) {
        int capacity = items;
        if (indexLevel != 0){
            for (int i = 0; i < indexLevel; i++) {
                capacity*=order;
            }
        }
        return capacity;
    }
    public static int getIndexOfLevel(int size, int order){
        double log = MathExtra.logN(size,order);
        int index = (int) log;
        return index;
    }
    public static int getStartIndexInLevel(int indexLevel,int order, int items){
        int index = 0;
        if (indexLevel != index){
            for (int i = 0; i < indexLevel; i++) {
                index = order*index+items;
            }
        }
        return index;
    }

    public abstract Object[] getNodes();
    public abstract int getOrder();
    public abstract int getItems();
    public void display(int sizeOfCell, int sizeOfSplit) {
        int sizeOfBlock = sizeOfCell + sizeOfSplit;
        if (sizeOfBlock < 2) {
            throw new IllegalArgumentException("Argument sizeOfBlock in getStringForBlock method " +
                    "must have size more than 0.");
        }
        if (sizeOfSplit < 1) {
            throw new IllegalArgumentException("Argument sizeOfSplit in getStringForBlock method " +
                    "must have size more than 0.");
        }
        if (sizeOfSplit >= sizeOfBlock) {
            throw new IllegalArgumentException("Argument sizeOfSplit in getStringForBlock method " +
                    "must have size less than sizeOfBlock.");
        }
        int order = getOrder();
        int items = getItems();
        String emptyCell = " ";
        StringBuilder sbBlock = new StringBuilder();
        for (int i = 0; i < sizeOfBlock; i++) {
            sbBlock.append(emptyCell);
        }
        String emptyBlock = sbBlock.toString();
        StringBuilder print = new StringBuilder();
        String newLine = "\n";

        Object[] values = getNodes();
        int level = getIndexOfLevel(values.length,order);
        int nElementsOnBottomLevel = getCapacityOnLevel(level, order,items);
        int nPrintBlocks = nElementsOnBottomLevel +items;
        int iCentre = nPrintBlocks/2;
        Deque<Integer> iOnLevel = new LinkedList<>();
        for (int i = 0; i <= level; i++) {
            int nElements = getCapacityOnLevel(i,order,items)/items;
            int iElement = getStartIndexInLevel(i,order,items);
            int step = nPrintBlocks / nElements;
            if (i==0){
                int centreLeftBound = iCentre-items/2;
                int centreRightBound = centreLeftBound+items;
                for (int j = 0; j < nPrintBlocks; j++) {
                    if (j < centreLeftBound || j>=centreRightBound){
                        print.append(emptyBlock);
                    }
                    else {
                        Object element = values[iElement++];
                        String printE = getStringForBlock(element, sizeOfBlock, sizeOfSplit);
                        print.append(printE);
                    }
                }
            }
            else {
                getStackIndexesOnLevel(iOnLevel,iCentre,step,nElements);
                for (int j = 0; j < nPrintBlocks; j++) {

                    if (iOnLevel.peekFirst()==null || j!=iOnLevel.peekFirst()) {
                        print.append(emptyBlock);
                    }
                    else {
                        iOnLevel.removeFirst();
                            Object element = values[iElement++];
                            String printE = getStringForBlock(element, sizeOfBlock, sizeOfSplit);
                            print.append(printE);
                    }
                }

            }
            print.append(newLine);
        }
        System.out.println(print);
    }

    private void getStackIndexesOnLevel(Deque<Integer> iOnLevel, int iCentre, int step, int count) {
        int iLeftSide = iCentre - (step+1)/2;
        int iRightSide = iCentre + (step+1)/2;
        int half = count/2;
        for (int i = 0; i < half; i++) {
            // addFirst because follow to left
            iOnLevel.addFirst(iLeftSide);
            iLeftSide-=step;
            // addLast because follow to right
            iOnLevel.addLast(iRightSide);
            iRightSide+=step;
        }
    }

    private String getStringForBlock(Object element, int sizeOfBlock, int sizeOfSplit) {
        String emptyCell = " ";
        String extension = "..";
        int longExtension = 2;
        if (element==null){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sizeOfBlock; i++) {
                sb.append(emptyCell);
            }
            return sb.toString();
        }
        int sizeOfSplitBefore = sizeOfSplit / 2;
        int sizeOfSplitAfter = sizeOfSplit - sizeOfSplitBefore;
        int insideBlock = sizeOfBlock - sizeOfSplit;
        String string = element.toString();
        int length = string.length();
        if (length > insideBlock) {
            if (insideBlock>=longExtension){
                string = string.substring(0,insideBlock-longExtension);
                string = string.concat(extension);
            }
            string = string.substring(0, insideBlock);
        } else {
            int extra = insideBlock - length;
            int extraBefore = extra/2;
            int extraAfter = extra-extraBefore;
            sizeOfSplitBefore+=extraBefore;
            sizeOfSplitAfter+=extraAfter;
        }
        StringBuilder sb = new StringBuilder(sizeOfBlock);
        for (int i = 0; i < sizeOfSplitBefore; i++) {
            sb.append(emptyCell);
        }
        sb.append(string);
        for (int i = 0; i < sizeOfSplitAfter; i++) {
            sb.append(emptyCell);
        }
        String result = sb.toString();
        return result;
    }
}
