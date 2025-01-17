package com.sviryd.algorithms.lafore.exercises.chapter5;

import java.util.NoSuchElementException;

/**
 * Exercise 5.3
 * Exercise 5.4 skipped because it's alike this class.
 * There is closure linked list.
 * Last link has next link that is a first link from time of start operating.
 * All link is in round starting from first pushed.
 * @param <E>
 */
public class ClosureLinkedList<E> {
    private Link<E> last;

    public boolean isEmpty() {
        return (last == null);
    }

    public void push(final E e) {
        Link<E> link = new Link(e);
        if (isEmpty()) {
            link.next = link;
            link.previous = link;
            last = link;
        } else {
            Link<E> temp = last.next;
            last.next = link;
            link.previous = last;
            link.next = temp;
            temp.previous = link;
            last = link;
        }
    }

    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link<E> temp = last;
        if (isOnlyOne()) {
            last = null;
        } else {
            last.previous.next = last.next;
            last.next.previous = last.previous;
            last = last.next;
        }
        return temp.e;
    }

    public E peek() {
        if (last == null) {
            return null;
        }
        return last.e;
    }

    private boolean isOnlyOne() {
        return last == last.previous && last == last.next;
    }

    public E find(final E e) {
        if (isEmpty()) {
            return null;
        }
        Link<E> start = last;
        if (start.e.equals(e)) {
            return start.e;
        } else {
            start = start.next;
        }
        if (start == last) {
            return null;
        }
        Link<E> curr = start;
        while (!curr.e.equals(e) && curr != last) {
            curr = curr.next;
        }
        return curr.e;
    }

    public void step(final int count) {
        if (last == null){
            return;
        }
        for (int i = 0; i < count; i++) {
            last = last.next;
        }
    }

    public void display() {
        System.out.print("List (last-->last): ");
        last.displayLink();
        if (isOnlyOne()) {
            System.out.println("");
            return;
        }
        Link<E> curr = last.next;
        while (curr != last) {
            curr.displayLink();
            curr = curr.next;
        }
        System.out.println("");
    }

    private static class Link<E> {
        private E e;
        private Link<E> next;
        private Link<E> previous;

        private Link(final E e) {
            this.e = e;
        }

        private void displayLink() {
            System.out.print("{" + e + "} ");
        }
    }
}
