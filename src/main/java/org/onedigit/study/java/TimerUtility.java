package org.onedigit.study.java;

public class TimerUtility
{
    static long start;
    static long end;
    
    public static void start()
    {
        start = System.nanoTime();
    }
    
    public static double end()
    {
        end = System.nanoTime();
        double elapsed = (end - start) * 1.0 / 1000.0 / 1000.0;
        return elapsed;
    }
}
