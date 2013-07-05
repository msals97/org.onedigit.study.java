package org.onedigit.study.java.algo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Fibonacci
{   
    public int fib(int n)
    {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int a = 0;
        int b = 1;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }
    
    class Worker implements Runnable
    {
        public void run()
        {
            
        }
    }
    
    public static void main(String[] args)
    {
        Fibonacci f = new Fibonacci();
        int n = f.fib(2);
        System.out.println(n);
        
        double d = -7.5;
        byte b = (byte)d;
        System.out.println(Byte.toString(b));
    }
}
