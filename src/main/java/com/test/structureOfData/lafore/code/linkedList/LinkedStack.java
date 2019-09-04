package com.test.structureOfData.lafore.code.linkedList;

public class LinkedStack<E> {
    private DoublyLinkedList<E> list;

    public LinkedStack() {
        list = new DoublyLinkedList();
    }

    public void push(E e) {
        list.insertFirst(e);
    }

    public E pop() {
        return list.deleteFirst();
    }

    public E peek() {
        return list.getFirst();
    }

    public boolean isEmpty() {
        return (list.isEmpty());
    }

    public void displayStack() {
        System.out.print("Stack (top-->bottom): ");
        list.displayForward();
    }
}
