package org.onedigit.study.java.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class LinkedHashSetTest
{
    public static void testSet(Set<Character> set)
    {
        set.add('a');
        set.add('z');        
        set.add('b');
        set.add('q');
        set.add('j');
        
        for (Iterator<Character> it = set.iterator(); it.hasNext(); ) {
            System.out.print(it.next() + " ");
        }        
    }
    
    public static void main(String... args)
    {
        // Iteration order is the order in which the elements
        // were inserted into the set (in contrast to HashSet)
        
        Set<Character> set = new LinkedHashSet<>();
        testSet(set);
        System.out.println();
        
        // Now compare with a HashSet
        Set<Character> hashSet = new HashSet<>();
        testSet(hashSet);
        System.out.println();
        
        // Now compare with a TreeSet
        Set<Character> treeSet = new TreeSet<>();
        testSet(treeSet);
        System.out.println();
    }
}
