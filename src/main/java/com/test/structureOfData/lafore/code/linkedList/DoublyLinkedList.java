package com.test.structureOfData.lafore.code.linkedList;

import java.util.NoSuchElementException;

public class DoublyLinkedList<E> {
    private Link<E> first; // Ссылка на первый элемент списка
    private Link<E> last; // Ссылка на последний элемент списка

    public boolean isEmpty() {
        return first == null;
    }

    /*
    _____________
         f   l
      ^
      |
      n
    _____________
     */
    public void insertFirst(final E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            last = newLink;
        } else {
            first.previous = newLink;
            newLink.next = first;
        }
        first = newLink;
    }

    /*
    _____________
      f   l
            ^
            |
            n
    _____________
     */

    public void insertLast(final E e) {
        Link<E> newLink = new Link(e);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
    }

    /*
    _____________
    
      f   l
      ^
      |
    delete
    _____________
     */
    public E deleteFirst() {
        Link<E> temp = first;
        if (first.next == null) {
            last = null;
        } else {
            first.next.previous = null;
        }
        first = first.next;
        return temp.e;
    }

    /*
    _____________
          
      f   l
          ^
          |
        delete
    _____________
     */
    public E deleteLast() {
        Link<E> temp = last;
        if (first.next == null) {
            first = null;
        } else {
            last.previous.next = null;
        }
        last = last.previous;
        return temp.e;
    }

    /*
    _____________
      f    l
        ^    ^
        |    |
        n    n
    _____________
     */

    public boolean insertAfter(final E key, final E e) {
        Link<E> newLink = new Link(e);
        Link<E> current = first;
        while (current.e != key) {
            current = current.next;
            if (current == null) {
                return false;
            }
        }
        if (current == last) {
            newLink.next = null;
            last = newLink;
        } else {
            newLink.next = current.next;
            current.next.previous = newLink;
        }
        newLink.previous = current;
        current.next = newLink;
        return true;
    }

    /*
    _____________
      delete
        ^
        |
      f    l
    _____________
     */
    public E deleteData(final E e) {
        Link<E> current = first;
        while (current.e != e) {
            current = current.next;
            if (current == null) {
                return null;
            }
        }
        if (current == first) {
            first = current.next;
        } else {
            current.previous.next = current.next;
        }
        if (current == last) {
            last = current.previous;
        } else {
            current.next.previous = current.previous;
        }
        return current.e;
    }

    public void displayForward() {
        System.out.print("List (first-->last): ");
        Link current = first;
        while (current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println("");
    }

    public void displayBackward() {
        System.out.print("List (last-->first): ");
        Link current = last;
        while (current != null) {
            current.displayLink();
            current = current.previous;
        }
        System.out.println("");
    }

    public E getFirst() {
        if (isEmpty()){
            throw  new NoSuchElementException();
        }
        return first.e;
    }

    private static class Link<E> {
        private E e; // Данные
        private Link<E> next; // Следующий элемент в списке
        private Link<E> previous; // Предыдущий элемент в списке

        private Link(E e) {
            this.e = e;
        }

        private void displayLink() {
            System.out.print(e + " ");
        }
    }
}