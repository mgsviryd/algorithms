package com.test.structureOfData.lafore.exercises.chapter5;


import java.util.NoSuchElementException;

/**
 * Exercise 5.1
 */
public class LinkedPriorityQueue<E extends Comparable<E>> implements IQueue<E> {
    private Link<E> first;

    /**
     * Not throw exception as java.util.Queue because it's not restricted in capacity.
     * Queue uses a link - not an array.
     */
    @Override
    public boolean add(E e) {
        Link<E> newLink = new Link(e);
        Link<E> previous = null;
        Link<E> current = first;
        while (current != null && e.compareTo(current.e) > 0) {
            previous = current;
            current = current.next;
        }
        if (previous == null) {
            first = newLink;
        } else {
            previous.next = newLink;
        }
        newLink.next = current;
        return true;
    }

    @Override
    public boolean offer(E e) {
        Link<E> newLink = new Link(e);
        Link<E> previous = null;
        Link<E> current = first;
        while (current != null && e.compareTo(current.e) > 0) {
            previous = current;
            current = current.next;
        }
        if (previous == null) {
            first = newLink;
        } else {
            previous.next = newLink;
        }
        newLink.next = current;
        return true;
    }


    @Override
    public E remove() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Link<E> temp = first;
        first = first.next;
        return temp.e;
    }

    @Override
    public E poll() {
        if (first == null) {
            return null;
        }
        Link<E> temp = first;
        first = first.next;
        return temp.e;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.e;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return first.e;
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

        private void displayLink() {
            System.out.print("{" + e + "} ");
        }
    }
}
