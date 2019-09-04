package com.test.structureOfData.lafore.exercises.chapter6;

/**
 * Exercise 6.5
 */
public class Combination {
    private int sizeOfCom;
    private int[] com;
    private int inxCom;
    private int maxResult;
    private int[][] allCom;
    private int countCom;

    public Combination(final int countItem, final int sizeOfCombination, final int maxResult) {
        this.sizeOfCom = sizeOfCombination;
        this.maxResult = maxResult;
        com = new int[sizeOfCom];
        allCom = new int[maxResult][sizeOfCom];
        buildCombination(countItem-1, sizeOfCom);
    }

    private void buildCombination(final int node, final int countInCombination) {
        if (node == 0 || countInCombination == 0) {
            return;
        }
        addNodeInCombination(node);
        buildCombination(node - 1, countInCombination - 1);
        buildCombination(node - 1, countInCombination);
        if (isAllCombinationBuild()) {
            return;
        }
        if (isCombinationFull()) {
            copyCombinationInAllCombination(node);
        }
        if (com[sizeOfCom - 1] == node) {
            inxCom -= 2;
        }
    }

    private void copyCombinationInAllCombination(final int lastInCom) {
        for (int i = 0; i < sizeOfCom; i++) {
            allCom[countCom][i] = com[i];
        }
        allCom[countCom][this.sizeOfCom - 1] = lastInCom;
        countCom++;
    }

    private boolean isAllCombinationBuild() {
        return countCom == maxResult;
    }

    private void addNodeInCombination(final int node) {
        if (inxCom < sizeOfCom) {
            com[inxCom++] = node;
        }
    }

    private boolean isCombinationFull() {
        return inxCom == sizeOfCom;
    }

    public int[][] getAllCombination() {
        return allCom;
    }
}
