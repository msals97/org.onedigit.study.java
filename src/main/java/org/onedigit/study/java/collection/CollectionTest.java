package org.onedigit.study.java.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionTest
{
    public CollectionTest()
    {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        
        Integer[] a = new Integer[0];
        a = list.toArray(a);
        System.out.println(Arrays.toString(a));
        classInfo(a);
        classInfo(list);
    }
    
    public <T> void classInfo(T t)
    {
        Class<?> clazz = t.getClass();
        System.out.println(clazz);
    }
    
    public static void main(String... args)
    {
        new CollectionTest();
    }
}
