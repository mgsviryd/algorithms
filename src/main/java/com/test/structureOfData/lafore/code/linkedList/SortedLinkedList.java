package com.test.structureOfData.lafore.code.linkedList;

import java.util.NoSuchElementException;

public class SortedLinkedList<E extends Comparable<E>> {
    private Link<E> first;

    /*
   _____________
     p    c
       ^
       |
       n
   _____________
    */
    public void add(final E element) {
        Link<E> newLink = new Link(element);
        Link<E> previous = null;
        Link<E> current = first;
        while (current != null && element.compareTo(current.e) > 0) {
            previous = current;
            current = current.next;
        }
        if (previous == null) {
            first = newLink;
        } else {
            previous.next = newLink;
        }
        newLink.next = current;
    }

    public E remove() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        Link<E> temp = first;
        first = first.next;
        return temp.e;
    }

    public void displayList() {
        System.out.print("List (first-->last): ");
        Link current = first;
        while (current != null) {
            current.display();
            current = current.next;
        }
        System.out.println("");
    }

    public boolean isEmpty() {
        return first == null;
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