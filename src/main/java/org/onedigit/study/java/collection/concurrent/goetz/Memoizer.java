package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<A, V> implements Computable<A, V>
{
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;
    
    public Memoizer(Computable<A, V> c)
    {
        this.c = c;
    }
    
    @Override
    public V compute(final A arg) throws InterruptedException
    {
        // The while loop ensures that we retry if the future
        // gets cancelled.
        while (true) { 
            Future<V> f = cache.get(arg);
            if (f == null) { // computation has not been started
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException
                    {
                        return c.compute(arg);
                    }
                };

                FutureTask<V> futureTask = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, futureTask);
                if (f == null) {
                    f = futureTask;
                    futureTask.run();
                }
            }

            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f); // remove from the cache.
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }
    
    static class ExpensiveComputation implements Computable<String, Integer>
    {
        @Override
        public Integer compute(String arg) throws InterruptedException
        {
            System.out.println("Starting expensive computation");
            Thread.sleep(5000);
            System.out.println("Finished expensive computation");
            return 100;
        }
    }
    
    public static void main(String[] args)
    {
        ExpensiveComputation computation = new ExpensiveComputation();
        Memoizer<String, Integer> memoizer = new Memoizer<>(computation);
        try {
            Integer result = memoizer.compute("Test");
            System.out.println("Result = " + result);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}

