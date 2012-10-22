package org.onedigit.study.java.collection;

import java.util.NavigableSet;
import java.util.TreeSet;

public class NavigableSetExample
{
    NavigableSet<Integer> ns = new TreeSet<>();
    
    public NavigableSetExample()
    {
        ns.add(10);
        ns.add(70);
        ns.add(30);
        ns.add(2);
        ns.add(5);
        
        for (Integer i : ns) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        System.out.println("Ceiling 10 = " + ns.ceiling(10));
        System.out.println("Smallest Higher than 10 = " + ns.higher(10));
        
        System.out.println("Floor 70 = " + ns.floor(70));
        System.out.println("Greatest Less than 70 = " + ns.lower(70));
    }
    
    public static void main(String... args)
    {
        new NavigableSetExample();
    }
}
