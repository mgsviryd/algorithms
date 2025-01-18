package com.sviryd.algorithms.lafore.exercise.hashTable.based;

import com.sviryd.algorithms.lafore.exercise.hashTable.advanced.HashTableChain;
import java.util.Hashtable;

import static com.sviryd.algorithms.lafore.exercise.hashTable.based.OpenAddressing.*;

public class HashTableOpenAddressingApp {
    public static void main(String[] args) {
        long time = System.nanoTime();
        HashTableOpenAddressing table = new HashTableOpenAddressing(2000, SQUARE);
        int size = 2000;
        for (int i = 0; i < size; i++) {
            String string = getRandomString(size);
            table.put(string, i);
            table.put(45f,"sdfsdf;");
        }
        System.out.println(System.nanoTime()-time);
        System.out.println(table.size());

        time = System.nanoTime();
        table = new HashTableOpenAddressing(2000, DOUBLE);
        size = 2000;
        for (int i = 0; i < size; i++) {
            String string = getRandomString(size);
            table.put(string, i);
        }
        System.out.println(System.nanoTime()-time);
        System.out.println(table.size());

        time = System.nanoTime();
        table = new HashTableOpenAddressing(2000, LINEAR);
        size = 2000;
        for (int i = 0; i < size; i++) {
            String string = getRandomString(size);
            table.put(string, i);
        }
        System.out.println(System.nanoTime()-time);
        System.out.println(table.size());

        time = System.nanoTime();
        Hashtable table1 = new Hashtable(2000);
        size = 2000;
        for (int i = 0; i < size; i++) {
            String string = getRandomString(size);
            table1.put(string, i);
        }
        System.out.println(System.nanoTime()-time);
        System.out.println(table.size());

        time = System.nanoTime();
        HashTableChain table2 = new HashTableChain(2000);
        size = 2000;
        for (int i = 0; i < size; i++) {
            String string = getRandomString(size);
            table2.put(string, i);
        }
        System.out.println(System.nanoTime()-time);
        System.out.println(table.size());
    }

    public static String getRandomString(int size) {
        StringBuilder sb = new StringBuilder(size);
        sb.append("sdfsd");
//        for (int i = 0; i < size; i++) {
//            char letter = (char) new Random().nextInt(10);
//            sb.append(letter);
//        }
        return sb.toString();
    }
}
