package org.onedigit.study.util;

import java.util.concurrent.TimeUnit;

public class Clock
{
    private long start;
    
    public Clock()
    {
        start = System.currentTimeMillis();
    }
    
    public static long getElapsed(long start, long end, TimeUnit sourceUnit)
    {
        return TimeUnit.MILLISECONDS.convert(end - start, sourceUnit);
    }
    
    public long getElapsed()
    {
        long end = System.currentTimeMillis();
        return (end - start);
    }
}
