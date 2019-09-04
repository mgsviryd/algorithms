import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int n;

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void addFirst(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Argument in addFirst is null."
            );
        }
        Node<Item> newNode = new Node<>(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            first.setPrevious(newNode);
            newNode.setNext(first);
            first = newNode;
        }
        n++;
    }

    public void addLast(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Argument in addFirst is null."
            );
        }
        Node<Item> newNode = new Node<>(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrevious(last);
            last = newNode;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Queue is empty."
            );
        }
        Node<Item> current = first;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.getNext();
            first.setPrevious(null);
        }
        n--;
        return current.getItem();
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Queue is empty."
            );
        }
        Node<Item> current = last;
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.getPrevious();
            last.setNext(null);
        }
        n--;
        return current.getItem();
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Method removeLast don't supported for Iterator."
            );
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "Iterator go out of the elements."
                );
            }
            Item item = current.item;
            current = current.getNext();
            return item;
        }
    }

    private class Node<Item> {
        private final Item item;
        private Node<Item> previous, next;

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

        private Node<Item> getNext() {
            return next;
        }

        private void setNext(Node<Item> next) {
            this.next = next;
        }
    }
}
