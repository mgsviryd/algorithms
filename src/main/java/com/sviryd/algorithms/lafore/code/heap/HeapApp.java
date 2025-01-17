package com.sviryd.algorithms.lafore.code.heap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class HeapApp
{
    public static void main(String[] args) throws IOException
    {
        int value, value2;
        Heap theHeap = new Heap(31); // Создание пирамиды с максимальным
        boolean success; // размером 31
        theHeap.insert(70); // Вставка 10 items
        theHeap.insert(40);
        theHeap.insert(50);
        theHeap.insert(20);
        theHeap.insert(60);
        theHeap.insert(100);
        theHeap.insert(80);
        theHeap.insert(30);
        theHeap.insert(10);
        theHeap.insert(90);
        while(true) // Пока пользователь не нажмет Ctrl+C
        {
            System.out.print("Enter first letter of ");
            System.out.print("show, add, removeLast, change: ");
            int choice = getChar();
            switch(choice)
            {
                case 's': // Вывод
                        theHeap.displayHeap();
                    break;
                case 'i': // Вставка
                    System.out.print("Enter value to add: ");
                    value = getInt();
                    success = theHeap.insert(value);
                    if( !success )
                        System.out.println("Can’t add; heap full");
                    break;
                case 'r': // Удаление
                    if( !theHeap.isEmpty() )
                        theHeap.remove();
                    else
                        System.out.println("Can’t removeLast; heap empty");
                    break;
                case 'c': // Изменение приоритета
                    System.out.print("Enter current index of item: ");
                    value = getInt();
                    System.out.print("Enter new key: ");
                    value2 = getInt();
                    success = theHeap.change(value, value2);
                    if( !success )
                        System.out.println("Invalid index");
                    break;
                default:
                    System.out.println("Invalid entry\n");
            }
        }
    }
    //-------------------------------------------------------------
    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    //-------------------------------------------------------------
    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }
    //-------------------------------------------------------------
    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }
//-------------------------------------------------------------
} // Конец класса HeapApp
