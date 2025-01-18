package com.sviryd.algorithms.lafore.exercise.chapter7;

public class QuickAdjustSortReport<E extends Comparable<? super E>> {
    private static final int THRESHOLD = 10;
    private final E[] array;
    private Report sortReport;

    public QuickAdjustSortReport(final E[] array) {
        this.array = array;
    }

    public void showSortReport() {
        if (sortReport == null) {
            throw new IllegalStateException("Before need invoke sort().");
        }
        System.out.println(sortReport);
    }

    public void sort() {
        sortReport = new Report();
        int first = 0;
        int last = array.length - 1;
        recQuickSort(first, last, array);
    }

    private void recQuickSort(final int left, final int right, final E[] array) {
        int size = right - left + 1;
        if (size < THRESHOLD) { // Если размер < n елеменотов,
            insertionSort(left, right, array);
            // сортируем InsertSort
        } else {   // Для размера 2 и более
            E pivot = median(left, right, array); // Медиана из значений индексов(левый, средний, правый)
            // Разбиение диапазона
            int partition = partitionIt(left, right, pivot, array);
            recQuickSort(left, partition - 1, array); // Сортировка левой части
            recQuickSort(partition + 1, right, array); // Сортировка правой части
        }
    }

    private void insertionSort(final int left, final int right, final E[] array) {
        int in, out;
        for (out = left + 1; out <= right; out++) {
            E temp = array[out];
            in = out;
            sortReport.increaseCompare(2);
            while (in > left && array[in - 1].compareTo(temp) > 0) {
                sortReport.increaseCompare(2);
                array[in] = array[in - 1];
                --in;
            }
            sortReport.increaseCopy(1);
            array[in] = temp;
        }
    }

    private E median(final int left, final int right, final E[] array) {
        int centre = (left + right) / 2;
        sortReport.increaseCompare(3);
        if (array[left].compareTo(array[centre]) > 0) {
            swap(left, centre, array);
        }
        if (array[left].compareTo(array[right]) > 0) {
            swap(left, right, array);
        }
        if (array[centre].compareTo(array[right]) > 0) {
            swap(centre, right, array);
        }
        swap(centre, right - 1, array);
        return array[right - 1];
    }

    private int partitionIt(
            final int left, final int right, final E pivot, final E[] array) {
        int leftPtr = left; // Левая граница (после ++)
        int rightPtr = right - 1; // Правая граница-1 (after --)
        while (true) { // Поиск большего элемента
            sortReport.increaseCompare(1);
            while (array[++leftPtr].compareTo(pivot) < 0) {
                sortReport.increaseCompare(1); // (nop)
            }
            // Поиск меньшего элемента
            sortReport.increaseCompare(1);
            while (rightPtr > 0 && array[--rightPtr].compareTo(pivot) > 0) {
                sortReport.increaseCompare(1); // (nop)
            }
            sortReport.increaseCompare(1);
            if (leftPtr >= rightPtr) { // Если указатели сошлись,
                break;
            } // разбиение закончено.
            else { // В противном случае
                swap(leftPtr, rightPtr, array);
            } // поменять элементы местами.
        }
        swap(leftPtr, right - 1, array); // Перестановка опорного элемента
        return leftPtr; // Возврат позиции опорного элемента
    }

    private void swap(final int dex1, final int dex2, final E[] array) { // Перестановка двух элементов
        E temp;
        temp = array[dex1]; // A копируется в temp
        array[dex1] = array[dex2]; // B копируется в A
        array[dex2] = temp; // temp копируется в B
        sortReport.increaseCopy(3);
    }

    private static class Report {
        private int countCompare;
        private int countCopy;

        private void increaseCompare(int size) {
            countCompare += size;
        }

        private void increaseCopy(int size) {
            countCopy += size;
        }

        public int getCountCompare() {
            return countCompare;
        }

        public int getCountCopy() {
            return countCopy;
        }

        public String toString() {
            return "Count of comparison operation: "
                    + countCompare
                    + "\n"
                    + "Count of copying operation: "
                    + countCopy
                    + "\n";
        }
    }
}
