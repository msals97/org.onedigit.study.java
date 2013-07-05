package org.onedigit.study.java.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter
{
    static class A implements Comparable<A>
    {
        private int a;
        public A(int a)
        {
            this.a = a;
        }
        public int compareTo(A other)
        {
            return this.a - other.a;
        }
        public String toString()
        {
            return String.valueOf(a);
        }
    }
    
    public static void main(String... args)
    {
        List<Integer> list = Arrays.asList(20, 10, 50, 100, 1, 3, 90);
        System.out.println(list);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2 - o1;
            }
        });
        System.out.println(list);
        
        List<A> list2 = new ArrayList<>();
        list2.add(new A(20));
        list2.add(new A(10));
        list2.add(new A(50));
        list2.add(new A(100));
        list2.add(new A(1));
        list2.add(new A(3));
        list2.add(new A(90));
        Collections.sort(list2);
        System.out.println(list2);
    }
}
