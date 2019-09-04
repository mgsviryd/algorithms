import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] elements;
    private int capacity;
    private int n;

    public RandomizedQueue() {
        capacity = 1;
        elements = (Item[]) new Object[capacity];

    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private boolean isFull() {
        return capacity == n;
    }

    private boolean isQuarterFull() {
        return n > 0 && capacity / 4 == n;
    }

    private void upCapacity() {
        resize(capacity * 2);
    }

    private void downCapacity() {
        resize(capacity / 2);

    }

    private void resize(final int newCapacity) {
        Item[] copy = (Item[]) new Object[newCapacity];
        for (int i = 0; i < n; i++) {
            copy[i] = elements[i];
        }
        elements = copy;
        capacity = newCapacity;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Parameter in enqueue method is null."
            );
        }
        if (isFull()) {
            upCapacity();
        }
        elements[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Array is empty. Cannot derive element."
            );
        }
        int randomIndex = StdRandom.uniform(n);
        Item current = elements[randomIndex];
        if (randomIndex == n - 1) {
            elements[randomIndex] = null;
            n--;
        } else {
            elements[randomIndex] = elements[--n];
            elements[n] = null;
        }
        if (isQuarterFull()) {
            downCapacity();
        }
        return current;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Array is empty. Cannot derive element."
            );
        }
        int randomIndex = StdRandom.uniform(n);
        return elements[randomIndex];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private final int[] ixs;
        private int cursor;

        private RandomizedIterator() {
            ixs = new int[size()];
            for (int i = 0; i < ixs.length; i++) {
                ixs[i] = i;
            }
            StdRandom.shuffle(ixs);
        }

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "Iterator go out of the elements."
                );
            }
            return elements[ixs[cursor++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Method removeLast don't supported for Iterator."
            );
        }
    }
}
