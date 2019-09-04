package com.test.structureOfData.lafore.exercises.chapter7;

/**
 * For escaping of StackOverFlow use QuickAdjustSort.
 * Exercise 7.1 skipped because it's easy.
 * Exercise 7.2
 * Exercise 7.3 skipped because i really don't understand how we now about real amount of median.
 * Exercise 7.4 skipped because index of pivot change in each index range.
 * Exercise 7.5 skipped because i don't know how organize that.
 * Pivot is not constant, it's relative.
 * Index range uses left or right or another relative index.
 *
 *
 */
public class QuickSortReport<E extends Comparable<? super E>> {
    private final E[] array;
    private Report sortReport;

    public QuickSortReport(final E[] array) {
        this.array = array;
    }
    public void showSortReport(){
        if (sortReport == null){
            throw new IllegalStateException("Before need invoke sort().");
        }
        System.out.println(sortReport);
    }

    public void sort() {
        sortReport = new Report();
        int first = 0;
        int last = array.length - 1;
        recQuickSort(first, last);
    }

    private void recQuickSort(final int left, final int right) {
        if (right - left <= 0) { // Если размер <= 0,
            return; // массив отсортирован
        } else {   // Для размера 2 и более
            E pivot = array[right]; // Крайний правый элемент
            // Разбиение диапазона
            int partition = partitionIt(left, right, pivot);
            recQuickSort(left, partition - 1); // Сортировка левой части
            recQuickSort(partition + 1, right); // Сортировка правой части
        }
    }

    private int partitionIt(
            final int left, final int right, final E pivot) {
        int leftPtr = left - 1; // Левая граница (после ++)
        int rightPtr = right; // Правая граница-1 (after --)
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
                swap(leftPtr, rightPtr);
            } // поменять элементы местами.
        }
        swap(leftPtr, right); // Перестановка опорного элемента
        return leftPtr; // Возврат позиции опорного элемента
    }

    private void swap(final int dex1, final int dex2) { // Перестановка двух элементов
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
            countCompare+=size;
        }

        private void increaseCopy(int size) {
            countCopy+=size;
        }

        public int getCountCompare() {
            return countCompare;
        }

        public int getCountCopy() {
            return countCopy;
        }
        public String toString(){
            return "Count of comparison operation: "
                    + countCompare
                    +"\n"
                    +"Count of copying operation: "
                    +countCopy
                    +"\n";
        }
    }
} 