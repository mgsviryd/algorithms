package com.test.structureOfData.lafore.exercises.hashTable;

/**
 * Exercise 11.2 from Robert Lafore Structures of data and algorithms
 */
public class CalculateHashOfString {
    public static final int PRIME_FOR_JAVA = 31;
    /**
     * It's the same method as hashCode() from String class.
     * Important: return type int and it's NEVER OVERFLOW (when int over MAX then return MIN, and the reversed is right.
     * Returns a hash code for string. The hash code for a {@code String} object is computed as
     * <blockquote><pre>
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * </pre></blockquote>
     *
     * @return  hash code value for string
     */
    public static int hash (String value){
        int h = value.charAt(0);
        for (int i = 1; i < value.length(); i++) {
            h=PRIME_FOR_JAVA * h + value.charAt(i);
        }
        return h;
    }
}
