package com.test.structureOfData.lafore.exercises.hashTable.based;

import java.util.Map;

public interface IHash<K, V> {
    V put(K key, V value);

    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V remove(Object key);

    void putAll(Map<? extends K, ? extends V> m);

    void clear();

    V get(Object key);
}
