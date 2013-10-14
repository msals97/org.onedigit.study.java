package org.onedigit.study.java.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Computation
{
    private ConcurrentMap<Integer, FutureTask<List<Integer>>> cache;
    private ExecutorService executorService;
        
    public Computation()
    {
        cache = new ConcurrentHashMap<>();
        executorService = Executors.newFixedThreadPool(5);
    }
    
    public Future<List<Integer>> calculatePrimeFactors(final int n) throws InterruptedException, ExecutionException
    {
        Future<List<Integer>> result = executorService.submit(new PrimeFactors(n));
        System.out.println("Waiting for a prime factors calcuation for " + n);
        return result;
    }
    
    public void shutdown()
    {
        executorService.shutdown();
    }
    
    class PrimeFactors implements Callable<List<Integer>>
    {
        int n;
        
        public PrimeFactors(int n)
        {
            this.n = n;
        }
        
        @Override
        public List<Integer> call() throws Exception
        {
            FutureTask<List<Integer>> result = cache.get(n);
            if (result == null) {
                Callable<List<Integer>> task = new Callable<List<Integer>>() {
                    @Override
                    public List<Integer> call() throws Exception
                    {
                        System.out.println(Thread.currentThread() + " calculating prime factors of " + n);
                        Thread.sleep(5000);
                        return Arrays.asList(2, 3, 3);
                    }
                };

                FutureTask<List<Integer>> futureTask = new FutureTask<>(task);
                result = cache.putIfAbsent(n, futureTask);
                if (result == null) {
                    System.out.println("Starting a prime factors calculation for " + n);
                    result = futureTask;
                    futureTask.run();
                }                    
            }
            
            return result.get();
        }
    }
    
    public static void main(String[] args)
    {
        Computation computation = new Computation();
        try {
            Future <List<Integer>> factors = computation.calculatePrimeFactors(18);
            System.out.println("Obtained prime factors: " + factors);
            
            factors = computation.calculatePrimeFactors(20);
            System.out.println("Obtained prime factors: " + factors);

            factors = computation.calculatePrimeFactors(25);
            System.out.println("Obtained prime factors: " + factors);

            System.out.println("Shutting down computation service");
            computation.shutdown();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
