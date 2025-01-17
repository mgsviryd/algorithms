package com.sviryd.algorithms.lafore.code.linkedList;

public class DoublyLinkedQueue<E> {
    private Link<E> first;
    private Link<E> last;

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
        Link<E> temp = first;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        return temp.e;
    }

    public boolean isEmpty() {
        return first == null;
    }

    private static class Link<E> {
        private E e; // Данные
        private Link<E> next; // Следующий элемент в списке

        private Link(E e) {
            this.e = e;
        }

        private void displayLink() {
            System.out.print(e + " ");
        }
    }
}
