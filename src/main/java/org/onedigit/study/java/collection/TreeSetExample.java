package org.onedigit.study.java.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetExample
{
    public TreeSetExample()
    {
        NavigableSet<String> base = new TreeSet<>(
                Collections.reverseOrder());
        Collections.addAll(base, "a", "d", "c");
        System.out.println(base);
        
        // Overload resolution takes place at compile time
        // and this will call f taking a Collection argument. 
        f((Set<String>)base);
        
        NavigableSet<String> sortedSet1 = new TreeSet<String>((Set<String>)base);
        NavigableSet<String> sortedSet2 = new TreeSet<String>(base);
        
        System.out.println(sortedSet1);
        System.out.println(sortedSet2);
    }
    
    public <T> void f(Collection<? extends T> c)
    {
        System.out.println("Collection");
        if (c instanceof SortedSet) {
            System.out.println("a sorted set");
        }
    }

    public <T> void f(SortedSet<? extends T> set)
    {
        System.out.println("SortedSet");
    }

    public static void main(String... args)
    {
        new TreeSetExample();
    }
}
