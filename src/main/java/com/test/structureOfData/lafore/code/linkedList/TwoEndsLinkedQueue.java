package com.test.structureOfData.lafore.code.linkedList;

import java.util.NoSuchElementException;

public class TwoEndsLinkedQueue<E> {
    private Link<E> first;
    private Link<E> last;

    public boolean isEmpty() {
        return first == null;
    }

    public void insertLast(final E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
        }
        last = newLink;
    }

    public E deleteFirst() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        E temp = first.e;
        if (first.next == null) {
            last = null;
        }
        first = first.next;
        return temp;
    }

    public E getFirst() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        return first.e;
    }

    public void displayList() {
        Link<E> current = first;
        while (current != null) {
            current.display();
            current = current.next;
        }
        System.out.println("");
    }

    private static class Link<E> {
        private E e;
        private Link<E> next;

        private Link(E e) {
            this.e = e;
        }

        private void display() {
            System.out.print(e + " ");
        }
    }
}
