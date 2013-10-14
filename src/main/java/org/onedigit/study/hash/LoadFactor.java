package org.onedigit.study.hash;

import java.util.HashMap;

public class LoadFactor
{
    public static void main(String[] args)
    {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i,  i);
        }
        int size = map.size();
        map.put(1000, 1000);
        System.out.println("size = " + size);
    }
}
