package com.test.structureOfData.lafore.code.recursion;

import com.test.structureOfData.util.Data;
import org.junit.Test;

import java.util.List;

public class AnagramBuilderTest {
    @Test
    public void getAnagrams(){
        Integer [] array = Data.getIntegerNaturalArray(4);
        AnagramBuilder builder = new AnagramBuilder(array, 3);
        List<List> anagrams = builder.getAnagrams();
        for (List anagram: anagrams ) {
            for (Object o: anagram) {
                System.out.print(o + " ");
            }
            System.out.println();
        }
    }
}
