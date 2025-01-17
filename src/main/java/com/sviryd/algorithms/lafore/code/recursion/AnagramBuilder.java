package com.sviryd.algorithms.lafore.code.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnagramBuilder<E> {
    private static final int MIN_COUNT = 2;
    private E[] source;
    private int count;
    private List<List<E>> anagrams;

    public AnagramBuilder(final E[] source, final int count) {
        if (source == null) {
            throw new IllegalArgumentException("Source is null");
        }
        if (source.length < MIN_COUNT || count < MIN_COUNT || count >= source.length) {
            throw new IllegalArgumentException("Count and length of array must be equal or more " +
                    "than 2 and less than size of array");
        }
        this.source = Arrays.copyOf(source, count);
        this.count = count;
        int countOfAnagrams = Factorial.factorial(count);
        anagrams = new ArrayList<>(countOfAnagrams);
        build();
    }

    private void build() {
        buildAnagram(count, count);
    }

    private void buildAnagram(final int newSize, final int count) {
        if (newSize == 1) {
            return;
        }
        for (int j = 0; j < newSize; j++) {
            buildAnagram(newSize - 1, count);
            if (newSize == MIN_COUNT) {
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
