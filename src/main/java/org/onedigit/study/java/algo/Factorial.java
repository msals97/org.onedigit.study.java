package org.onedigit.study.java.algo;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Factorial 
{
    private int numThreads = Runtime.getRuntime().availableProcessors();
    
    private static class FactorialWorker implements Runnable
    {
        final long start;
        final long end;
        BigDecimal result = new BigDecimal(1);
        
        public FactorialWorker(long start, long end)
        {
            this.start = start;
            this.end = end;
        }
        
        public void run()
        {
            for (long i = start; i <= end; i++) {
                result = result.multiply(BigDecimal.valueOf(i));
            }
        }
        
        public BigDecimal getResult()
        {
            return result;
        }
    }
    
    public Factorial()
    {
        System.out.println("Number of processors = " + numThreads);
    }
    
    /**
     * Multi-threaded factorial calculation.
     * 
     * @param N
     * @return
     */
    public BigDecimal factorial_mt(long N)
    {
        Thread[] threads = new Thread[numThreads];
        FactorialWorker[] workers = new FactorialWorker[numThreads];
        
        long blocks = N / numThreads;
        for (int i = 1; i <= numThreads; i++) {
            long start = (i == 1) ? 1 : blocks * (i - 1) + 1;
            long end = (i == numThreads) ? N : blocks * i;
            FactorialWorker worker = new FactorialWorker(start, end);
            threads[i - 1] = new Thread(worker);
            workers[i - 1] = worker;
        }
        
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
        
        // Wait for threads to finish;
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        BigDecimal result = new BigDecimal(1);
        // Calculate the final results;
        for (int i = 0; i < numThreads; i++) {
            result = result.multiply(workers[i].getResult());
        }
        return result;
    }
    
    /**
     * single threaded, N factorial, i.e. N!
     * @param N
     * @return
     */
    BigDecimal factorial(long N)
    {
        BigDecimal result = new BigDecimal(1);
        for (long i = 1; i <= N; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }
    
    public static void main(String[] args)
    {
        Factorial f = new Factorial();
        int N = 203457;
        
        long start = System.nanoTime();
        BigDecimal r1 = f.factorial(N);
        long end = System.nanoTime();
        long elapsed = TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS);
        System.out.println("1 CPU, elapsed = " + elapsed);
        // System.out.println(r1);
        
        start = System.nanoTime();
        BigDecimal r2 = f.factorial_mt(N);
        end = System.nanoTime();
        elapsed = TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS);
        System.out.println(Runtime.getRuntime().availableProcessors() + " CPUs, elapsed = " + elapsed);
        
        BigDecimal diff = r2.subtract(r1);
        System.out.println("Diff = " + diff);
    }
}
