package org.onedigit.study.java.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetTest
{
    static Character[] table = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    };
    
    public static void calculateHash()
    {
        int buckets = 16; // default capacity of HashSet
        
        for (int i = 0; i < table.length; i++) {
            System.out.println(table[i] + ": " + (table[i].hashCode() & (buckets - 1)));
        }
    }
    
    public static void main(String... args)
    {
        calculateHash();
        
        Set<Character> set = new HashSet<>();
        // 'a' and 'q' will hash to the same bucket when the default
        // capacity is 16. 'z' and 'j' will also hash to the same bucket.
        set.add('a');
        set.add('b');
        set.add('q');
        set.add('j');
        set.add('z');
        
        for (Iterator<Character> it = set.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }
    }
}
