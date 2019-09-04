package com.test.structureOfData.lafore.code.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class AnagramPredicateBuilder<E> {
    private static final int MIN_COUNT = 2;
    private E[] source;
    private int count;
    private Predicate<E[]> rule;
    private LinkedList<List<E>> anagrams;

    public AnagramPredicateBuilder(final E[] source, final int count, final Predicate<E[]> rule) {
        if (source == null) {
            throw new IllegalArgumentException("Source is null");
        }
        if (source.length < MIN_COUNT || count < MIN_COUNT || count >= source.length) {
            throw new IllegalArgumentException("Count and length of array must be equal or more " +
                    "than 2 and less than size of array");
        }
        this.source = Arrays.copyOf(source, count);
        this.count = count;
        this.rule = rule;
        anagrams = new LinkedList<>();
        build();
    }

    private void build() {
        buildAnagram(count, count, rule);
    }

    private void buildAnagram(final int newSize, final int count, final Predicate<E[]> rule) {
        if (newSize == 1) {
            return;
        }
        for (int j = 0; j < newSize; j++) {
            buildAnagram(newSize - 1, count,rule);
            if (newSize == MIN_COUNT && rule.test(source)) {
                saveAnagram();
            }
            rotate(newSize, count);
        }
    }

    private void saveAnagram() {
        List<E> anagram = new ArrayList<E>(count);
        for (E item : source) {
            anagram.add(item);
        }
        anagrams.add(anagram);
    }

    private void rotate(final int newSize, final int count) {
        int j;
        int position = count - newSize;
        E temp = source[position]; // Сохранение первой буквы
        for (j = position + 1; j < count; j++) // Сдвиг остальных букв влево
            source[j - 1] = source[j];
        source[j - 1] = temp; // Перемещение первой буквы на правый край
    }

    public List<List<E>> getAnagrams() {
        return anagrams;
    }
}