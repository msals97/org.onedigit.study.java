package org.onedigit.study.ikm;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

class Constants
{
    private static int count;
    static {
        System.out.println("in block 1");
        count = 10;
    }
    
    private int[] data;
    
    {
        System.out.println("in block 2");
        data = new int[count];
        for (int i = 0; i < count; i++) {
            data[i] = 1;
        }
    }
    
    public static void add(ArrayList<?> collection, Object element)
    {
        // collection.add(element.toString());
    }
    
    public static void main(String... args)
    {
        /*
        System.out.println("Count = " + count);
        System.out.println("before 1st call to new");
        Constants c1 = new Constants();
        System.out.println("before 2nd call to new");
        Constants c2 = new Constants();
        */
        
        byte c1[] = {10, 20, 30, 40, 50};
        byte c2[] = {60, 70, 80, 90};
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        ByteArrayOutputStream b2 = new ByteArrayOutputStream();
        b2.write(100);
        System.out.println("Out 1: " + b2.size());
        
        b2.write(c1, 0, c2.length);
        System.out.println("Out 2: " + b2.size());
        byte[] bytes = b2.toByteArray();
        System.out.println(Arrays.toString(bytes));
    }
}
