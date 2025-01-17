package com.sviryd.algorithms.lafore.exercises.hashTable.advanced;

import java.util.*;

/**
 * Exercise 11.4 from Robert Lafore Structures of data and algorithms
 */
public class HashTableChain<K, V>
        implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private transient Entry<?, ?>[] table;
    private transient int count;
    private int threshold;
    private float loadFactor;
    private transient int modCount = 0;

    public HashTableChain(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal Load: " + loadFactor);
        }
        if (initialCapacity == 0) {
            initialCapacity = 1;
        }
        this.loadFactor = loadFactor;
        table = new Entry[initialCapacity];
        threshold = (int) Math.min(initialCapacity * loadFactor, MAX_ARRAY_SIZE + 1);
    }

    public HashTableChain(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableChain() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableChain(Map<? extends K, ? extends V> t) {
        this(Math.max(t.size() * 2, DEFAULT_INITIAL_CAPACITY));
        putAll(t);
    }

    public synchronized void putAll(Map<? extends K, ? extends V> t) {
        for (Map.Entry<? extends K, ? extends V> e : t.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;

    public synchronized V put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        Entry<?, ?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        Entry<K, V> entry = (Entry<K, V>) tab[index];
        for (; entry != null; entry = entry.next) {
            if ((entry.hash == hash) && entry.key.equals(key)) {
                V old = entry.value;
                entry.value = value;
                return old;
            }
        }
        addEntry(hash, key, value, index);
        return null;
    }

    public synchronized void clear() {
        Entry<?, ?> tab[] = table;
        modCount++;
        for (int index = tab.length; --index >= 0; )
            tab[index] = null;
        count = 0;
    }

    private void addEntry(int hash, K key, V value, int index) {
        modCount++;
        Entry<?, ?> tab[] = table;
        if (count >= threshold) {
            rehash();
            tab = table;
            hash = key.hashCode();
            index = (hash & 0x7FFFFFFF) % tab.length;
        }
        Entry<K, V> e = (Entry<K, V>) tab[index];
        tab[index] = new Entry<>(hash, key, value, e);
        count++;
    }
    protected void rehash() {
        int oldCapacity = table.length;
        Entry<?, ?>[] oldMap = table;
        int newCapacity = (oldCapacity << 1) + 1;
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            if (newCapacity == MAX_ARRAY_SIZE) {
                return;
            }
            newCapacity = MAX_ARRAY_SIZE;
        }
        Entry<?, ?>[] newMap = new Entry<?, ?>[newCapacity];
        modCount++;
        threshold = (int) Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE);
        table = newMap;
        for (int i = oldCapacity; i-- > 0; ) {
            for (Entry<K, V> old = (Entry<K, V>) oldMap[i]; old != null; ) {
                Entry<K, V> e = old;
                old = old.next;

                int index = (e.hash & 0x7FFFFFFF) % newCapacity;
                e.next = (Entry<K, V>) newMap[index];
                newMap[index] = e;
            }
        }
    }

    public synchronized int size() {
        return count;
    }

    public synchronized boolean isEmpty() {
        return count == 0;
    }

    public synchronized Enumeration<K> keys() {
        return this.<K>getEnumeration(KEYS);
    }

    public synchronized Enumeration<V> elements() {
        return this.<V>getEnumeration(VALUES);
    }

    public synchronized boolean containsValue(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        Entry<?, ?> tab[] = table;
        for (int i = tab.length; i-- > 0; ) {
            for (Entry<?, ?> e = tab[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean containsKey(Object key) {
        Entry<?, ?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<?, ?> e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public synchronized V remove(Object key) {
        Entry<?, ?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        Entry<K, V> e = (Entry<K, V>) tab[index];
        for (Entry<K, V> prev = null; e != null; prev = e, e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                modCount++;
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                count--;
                return e.value;
            }
        }
        return null;
    }

    public synchronized V get(Object key) {
        Entry<?, ?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<?, ?> e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                return (V) e.value;
            }
        }
        return null;
    }

    private class Enumerator<T> implements Enumeration<T>, Iterator<T> {
        Entry<?, ?>[] table = HashTableChain.this.table;
        int index = table.length;
        Entry<?, ?> entry;
        Entry<?, ?> lastReturned;
        int type;
        boolean iterator;
        protected int expectedModeCount = modCount;

        Enumerator(int type, boolean iterator) {
            this.type = type;
            this.iterator = iterator;
        }

        @Override
        public boolean hasMoreElements() {
            Entry<?, ?> e = entry;
            int i = index;
            Entry<?, ?>[] t = table;
            while (e == null && i > 0) {
                e = t[--i];
            }
            entry = e;
            index = i;
            return e != null;
        }

        @Override
        public T nextElement() {
            Entry<?, ?> et = entry;
            int i = index;
            Entry<?, ?>[] t = table;
            while (et == null && i > 0) {
                et = t[--i];
            }
            entry = et;
            index = i;
            if (et != null) {
                Entry<?, ?> e = lastReturned = entry;
                entry = e.next;
                return type == KEYS ? (T) e.key : (type == VALUES ? (T) e.value : (T) e);
            }
            throw new NoSuchElementException("HashTable Enumerator");
        }

        @Override
        public boolean hasNext() {
            return hasMoreElements();
        }

        @Override
        public T next() {
            if (modCount != expectedModeCount) {
                throw new ConcurrentModificationException();
            }
            return nextElement();
        }
    }

    private <T> Iterator<T> getIterator(int type) {
        if (count == 0) {
            return Collections.emptyIterator();
        } else {
            return new Enumerator<>(type, true);
        }
    }

    private <T> Enumeration<T> getEnumeration(int type) {
        if (count == 0) {
            return Collections.emptyEnumeration();
        } else {
            return new Enumerator<>(type, false);
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        protected Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            if (value == null) {
                throw new NullPointerException();
            }
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof Map.Entry)) return false;

            Entry<?, ?> entry = (Entry<?, ?>) o;

            if (key != null ? !key.equals(entry.key) : entry.key != null) return false;
            if (value != null ? !value.equals(entry.value) : entry.value != null) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return hash ^ Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
