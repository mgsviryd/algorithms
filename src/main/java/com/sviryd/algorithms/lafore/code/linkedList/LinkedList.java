package com.sviryd.algorithms.lafore.code.linkedList;

public class LinkedList<K, E> {
    private Link<K, E> first;

    public boolean isEmpty() {
        return (first == null);
    }

    public void insertFirst(final K key, final E e) {
        Link<K, E> newLink = new Link(key, e);
        newLink.next = first;
        first = newLink;
    }

    public E deleteFirst() {
        Link<K,E> temp = first;
        first = first.next;
        return temp.e;
    }

    public E find(final K key) {
        Link<K, E> current = first;
        while (current.key != key) {
            if (current.next == null) {
                return null;
            } else {
                current = current.next;
            }
        }
        return current.e;
    }

    public E delete(final E key) {
        Link<K, E> current = first;
        Link<K, E> previous = first;
        while (!current.key.equals(key)) {
            if (current.next == null) {
                return null;
            } else {
                previous = current;
            }
            current = current.next;
        }
        if (current == first) {
            first = first.next;
        } else {
            previous.next = current.next;
        }
        return current.e;
    }

    public void displayList() {
        System.out.print("List (first-->last): ");
        Link current = first;
        while (current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println("");
    }

    private static class Link<K, E> {
        private K key;
        private E e;
        private Link<K, E> next;

        private Link(final K key, final E e) {
            this.key = key;
            this.e = e;
        }

        private void displayLink() {
            System.out.print("{" + key + ", " + e + "} ");
        }
    }
}