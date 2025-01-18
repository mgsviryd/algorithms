package com.sviryd.algorithms.lafore.exercise.hashTable.based;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import static com.sviryd.algorithms.lafore.exercise.hashTable.PrimeGenerator.MAX_INTEGER_PRIME;
import static com.sviryd.algorithms.lafore.exercise.hashTable.PrimeGenerator.getNextPrime;
import static com.sviryd.algorithms.lafore.exercise.hashTable.based.HashTableOpenAddressing.PrivateOpenAddressing.DOUBLE;

public class HashTableOpenAddressing<K, V> implements Serializable, IHash<K, V> {
    private static final long serialVersionUID = 1;
    private static final PrivateOpenAddressing DEFAULT_OPEN_ADDRESSING = DOUBLE;
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int RESIZE_FACTOR = 2;
    private static final Entry DELETE_ENTRY = new Entry(0, null, null);
    private Entry<K, V>[] tab;
    private PrivateOpenAddressing addressing;
    private int size;
    private int count;

    public HashTableOpenAddressing(int size) {
        if (size < DEFAULT_INITIAL_CAPACITY) {
            this.size = DEFAULT_INITIAL_CAPACITY;
        } else if (size > MAX_INTEGER_PRIME) {
            this.size = MAX_INTEGER_PRIME;
        } else {
            this.size = getNextPrime(size);
        }
        tab = new Entry[this.size];
        addressing = DEFAULT_OPEN_ADDRESSING;
    }

    public HashTableOpenAddressing(int size, OpenAddressing typeAddressing) {
        this(size);
        if (typeAddressing == null) {
            throw new IllegalArgumentException("Open addressing type must be definite.");
        }
        addressing = getAddressing(typeAddressing);
    }

    private PrivateOpenAddressing getAddressing(OpenAddressing t) {
        for (PrivateOpenAddressing a : PrivateOpenAddressing.values()) {
            if (a.toString().equals(t.toString())) {
                return a;
            }
        }
        throw new IllegalArgumentException("Not compatible type of OpenAddressing and PrivateOpenAddressing.");
    }


    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        if (isThreshold()) {
            resize();
        }
        int hash = key.hashCode();
        int hashF1 = hashFunction1(hash);
        int hashAdjust = hashF1;
        if (hashAdjust<0){
            System.out.println("BITCH");
        }
        for (int i = 1; tab[hashAdjust] != null && tab[hashAdjust] != DELETE_ENTRY; i++) {
            hashAdjust = addressing.adjustHash(hashAdjust, hashF1, i, size);
            if (hashAdjust<0){
                System.out.println("FACK"+addressing);
            }
        }
        Entry<K, V> entry = new Entry(hashF1, key, value);
        tab[hashAdjust] = entry;
        ++count;
        return value;
    }

    private boolean isThreshold() {
        return ((count + 1) / size) > addressing.getLoadFactor();
    }

    private void resize() {
        if (size * RESIZE_FACTOR > MAX_INTEGER_PRIME) {
            size = MAX_INTEGER_PRIME;
        } else {
            size = getNextPrime(size * RESIZE_FACTOR);
        }
        Entry<K, V>[] oldTab = tab;
        tab = new Entry[size];
        count = 0;
        for (Entry<K, V> e : oldTab) {
            if (e != null && e != DELETE_ENTRY) {
                put(e.key, e.value);
            }
        }
    }

    private int hashFunction1(int key) {
        int hashF1 = Math.abs(key) % size;
        return hashF1;
    }


    @Override
    public V remove(Object key) {
        int hash = key.hashCode();
        int hashF1 = hashFunction1(hash);
        int hashAdjust = hashF1;
        for (int i = 0; tab[hashAdjust] != null; i++) {
            if (tab[hashAdjust].hash==hashF1 && tab[hashAdjust].getKey().equals(key)) {
                V temp = tab[hashAdjust].value;
                tab[hashAdjust] = DELETE_ENTRY;
                --count;
                return temp;
            } else {
                hashAdjust = addressing.adjustHash(hashAdjust, hashF1, i, size);
            }
        }
        return null;
    }

    @Override
    public V get(Object key) {
        int hash = key.hashCode();
        int hashF1 = hashFunction1(hash);
        int hashAdjust = hashF1;
        for (int i = 0; tab[hashAdjust] != null; i++) {
            if (tab[hashAdjust].hash==hashF1 && tab[hashAdjust].getKey().equals(key)) {
                V temp = tab[hashAdjust].value;
                return temp;
            } else {
                hashAdjust = addressing.adjustHash(hashAdjust, hashF1, i, size);
            }
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }


    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < count; i++) {
            if (tab[i].value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        for (int index = tab.length; --index >= 0; )
            tab[index] = null;
        count = 0;
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;

        private Entry(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
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

    enum PrivateOpenAddressing {
        LINEAR(0.50f) {
            int adjustHash(int hashAdjust, int hash, int orderCall, int sizeArray) {
                hashAdjust += 1;
                hashAdjust %= sizeArray;
                return Math.abs(hashAdjust);
            }
        },
        SQUARE(0.50f) {
            int adjustHash(int hashAdjust, int hash, int orderCall, int sizeArray) {
                int extra = orderCall * orderCall;
                hashAdjust += extra;
                hashAdjust %= sizeArray;
                return Math.abs(hashAdjust);
            }

        },
        DOUBLE(0.75f) {
            int adjustHash(int hashAdjust, int hash, int orderCall, int sizeArray) {
                int step = STEP_PRIME_DOUBLE - hash % STEP_PRIME_DOUBLE;
                hashAdjust += step;
                hashAdjust %= sizeArray;
                return Math.abs(hashAdjust);
            }
        };
        private static final int STEP_PRIME_DOUBLE = 5;
        private float loadFactor;
        abstract int adjustHash(int hashAdjust, int hash, int orderCall, int sizeArray);
        float getLoadFactor(){return loadFactor;}

        PrivateOpenAddressing(float loadFactor) {
            this.loadFactor = loadFactor;
        }
    }
}
