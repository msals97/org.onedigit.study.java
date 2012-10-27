package org.onedigit.study.java.collection;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetTest
{
    public static void main(String... args)
    {
        // Iteration order is the order in which the elements
        // were inserted into the set (in contrast to HashSet)
        
        Set<Character> set = new LinkedHashSet<>();
        
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
