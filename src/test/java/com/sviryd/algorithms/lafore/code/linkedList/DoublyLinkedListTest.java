package com.sviryd.algorithms.lafore.code.linkedList;

import org.junit.Assert;
import org.junit.Test;

public class DoublyLinkedListTest {
    @Test
    public void chainOfOperation() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.insertFirst(22); // 77
        list.insertFirst(44);
        list.insertFirst(66); //-
        list.insertLast(11);//-
        list.insertLast(33); // 88
        list.insertLast(55);//-
        list.displayForward();
        list.displayBackward();

        list.deleteFirst();
        list.deleteLast();
        list.deleteData(11);
        list.insertAfter(22, 77);
        list.insertAfter(33, 88);

        int[] result = {44, 22, 77, 33, 88};
        for (int item : result) {
            Object actual = list.deleteFirst();
            Assert.assertEquals(item, actual);
        }
        Assert.assertTrue(list.isEmpty());
    }
}
