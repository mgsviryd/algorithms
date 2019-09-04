package com.test.structureOfData.princeton.util;

import java.util.NoSuchElementException;

public class StackWithMaxMethod<Item extends Comparable<Item>> {
    private Node<Item> tail;

    public boolean isEmpty() {
        return tail == null;
    }

    public void push(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Argument in push is null."
            );
        }
        Node<Item> node = new Node<>(item);
        if (isEmpty()) {
            tail = node;
        } else {
            node.setPrevious(tail);
            tail = node;
        }
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Stack is empty."
            );
        }
        Item item = tail.getItem();
        tail = tail.getPrevious();
        return item;
    }

    public Item max() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Stack is empty."
            );
        }
        Node<Item> current = tail;
        Node<Item> previous = tail.getPrevious();
        Item item = current.getItem();
        while (previous != null) {
            int compare = current.compareTo(previous);
            if (compare == -1) {
                item = previous.getItem();
            }
            current = previous;
            previous = current.getPrevious();
        }
        return item;
    }

    private class Node<Item extends Comparable<Item>> implements Comparable<Node<Item>> {
        private final Item item;
        private Node<Item> previous;

        private Node(final Item item) {
            this.item = item;
        }

        private Item getItem() {
            return item;
        }

        private Node<Item> getPrevious() {
            return previous;
        }

        private void setPrevious(Node<Item> previous) {
            this.previous = previous;
        }

        @Override
        public int compareTo(Node<Item> o) {
            return item.compareTo(o.getItem());
        }
    }
}
