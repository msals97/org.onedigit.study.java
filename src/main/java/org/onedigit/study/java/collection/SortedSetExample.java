package org.onedigit.study.java.collection;

import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetExample
{
    SortedSet<Integer> set = new TreeSet<>();
    
    public SortedSetExample()
    {
        set.add(10);
        set.add(20);
        set.add(30);
        set.add(40);
        set.add(2);
        
        for (Integer s : set) {
            System.out.println(s);
        }
        
        System.out.println(set.first()); // 2
        System.out.println(set.last()); // 40
        
        System.out.println(set.headSet(20)); // 2, 10 = interval (2, 20)
        System.out.println(set.tailSet(20)); // 20, 30, 40  = interval [20, 40]
        
        System.out.println(set.subSet(2, 40)); // interval [2, 40)
    }
    
    public static void main(String... args)
    {
        new SortedSetExample();
    }
}
