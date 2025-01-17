package com.sviryd.algorithms.lafore.exercises.chapter5;

import java.util.NoSuchElementException;

/**
 * Exercise 5.2
 */
public class LinkedDeque<E> implements IDeque<E> {
    private Link<E> first; // Ссылка на первый элемент списка
    private Link<E> last; // Ссылка на последний элемент списка

    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void addFirst(E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            last = newLink;
        } else {
            first.previous = newLink;
            newLink.next = first;
        }
        first = newLink;
    }

    @Override
    public void addLast(E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
    }

    @Override
    public boolean offerFirst(E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            last = newLink;
        } else {
            first.previous = newLink;
            newLink.next = first;
        }
        first = newLink;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link<E> temp = first;
        if (first.next == null) {
            last = null;
        } else {
            first.next.previous = null;
        }
        first = first.next;
        return temp.e;
    }

    @Override
    public E removeLast() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        Link<E> temp = last;
        if (first.next == null) {
            first = null;
        } else {
            last.previous.next = null;
        }
        last = last.previous;
        return temp.e;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        Link<E> temp = first;
        if (first.next == null) {
            last = null;
        } else {
            first.next.previous = null;
        }
        first = first.next;
        return temp.e;
    }

    @Override
    public E pollLast() {
        if (isEmpty()){
            return null;
        }
        Link<E> temp = last;
        if (first.next == null) {
            first = null;
        } else {
            last.previous.next = null;
        }
        last = last.previous;
        return temp.e;
    }

    public E getFirst() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        return first.e;
    }

    @Override
    public E getLast() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        return last.e;
    }

    @Override
    public E peekFirst() {
        return first.e;
    }

    @Override
    public E peekLast() {
        return last.e;
    }

    @Override
    public boolean add(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public E remove() {
        return removeLast();
    }

    @Override
    public E poll() {
        return pollLast();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        add(e);
    }

    @Override
    public E pop() {
        return remove();
    }

    private static class Link<E> {
        private E e; // Данные
        private Link<E> next; // Следующий элемент в списке
        private Link<E> previous; // Предыдущий элемент в списке

        private Link(E d) {
            e = d;
        }

        private void displayLink() {
            System.out.print(e + " ");
        }
    }
}
