package com.test.structureOfData.lafore.code.recursion;

class BinarySearch<E extends Comparable<E>> {
    private E[] array;

    public BinarySearch(final E[] array) {
        this.array = array;
    }

    public Integer binarySearch(final E key, final E[] source) {
        int inx, lowerBound, upperBound;
        lowerBound = 0;
        upperBound = source.length - 1;
        while (true) {
            inx = (lowerBound + upperBound) / 2;
            if (source[inx].equals(key)) {
                return inx; // Элемент найден
            } else if (lowerBound > upperBound) {
                return null; // Элемент не найден
            } else { // Деление диапазона
                if (source[inx].compareTo(key) < 0) { // В верхней половине
                    lowerBound = inx + 1;
                } else { // В нижней половине
                    upperBound = inx - 1;
                }
            }
        }
    }

    public Integer binarySearchRecursion(final E key, final E[] array) {
        return binarySearchRecursion(key, 0, array.length - 1,array);
    }

    private Integer binarySearchRecursion(final E key, final int lowerBound, final int upperBound, final E[]source) {
        int inx = (lowerBound + upperBound) / 2;
        if (source[inx].equals(key)) {
            return inx; // Элемент найден
        } else if (lowerBound > upperBound) {
            return null; // Элемент не найден
        } else {  // Деление диапазона
            if (source[inx].compareTo(key)<0) // В верхней половине
                return binarySearchRecursion(key, inx + 1, upperBound,source);
            else { // В нижней половине
                return binarySearchRecursion(key, lowerBound, inx - 1,source);
            }
        }
    }
}
