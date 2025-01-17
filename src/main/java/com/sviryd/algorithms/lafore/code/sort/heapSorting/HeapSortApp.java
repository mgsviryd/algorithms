package com.sviryd.algorithms.lafore.code.sort.heapSorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HeapSortApp{
        public static void main(String[] args) throws IOException
        {
            int size, j;
            System.out.print("Enter number of items: ");
            size = getInt();
            Heap theHeap = new Heap(size);
            for(j=0; j<size; j++) // Заполнение массива
            { // случайными данными
                int random = (int)(java.lang.Math.random()*100);
                Node newNode = new Node(random);
                theHeap.insertAt(j, newNode);
                theHeap.incrementSize();
            }
            System.out.print("Random: ");
            theHeap.displayArray(); // Вывод случайного массива
            for(j=size/2-1; j>=0; j--) // Преобразование массива в пирамиду
                theHeap.trickleDown(j);
            System.out.print("Heap: ");
            theHeap.displayArray(); // Вывод массива
            theHeap.displayHeap(); // Вывод пирамиды
            for(j=size-1; j>=0; j--) // Извлечение из пирамиды
            { // с сохранением в конце массива
                Node biggestNode = theHeap.remove();
                theHeap.insertAt(j, biggestNode);
            }
            System.out.print("Sorted: ");
            theHeap.displayArray(); // Вывод отсортированного массива
        }
        // -------------------------------------------------------------
        public static String getString() throws IOException
        {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String s = br.readLine();
            return s;
        }
        //-------------------------------------------------------------
        public static int getInt() throws IOException
        {
            String s = getString();
            return Integer.parseInt(s);
        }
// -------------------------------------------------------------
} // Конец класса HeapSortApp
