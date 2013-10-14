package org.onedigit.study.ikm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SizeofOperator
{
    public long sizeof()
    {
        long start, end;
        Runtime r = Runtime.getRuntime();
        for (int i = 0; i < 1000; i++) {
            r.gc();
        }
        
        Object o;
        start = r.freeMemory();
        o = new Object();
        end = r.freeMemory();
        return start - end;
    }
    
    public static void main(String[] args)
    {
        SizeofOperator SO = new SizeofOperator();
        long bytes = SO.sizeof();
        System.out.println("Size of object = " + bytes + " bytes");
    }
}
