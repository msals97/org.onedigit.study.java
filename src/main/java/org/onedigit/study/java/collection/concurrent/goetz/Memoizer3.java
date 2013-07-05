package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer3<A, V> implements Computable<A, V>
{
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;
    
    public Memoizer3(Computable<A, V> c)
    {
        this.c = c;
    }
    
    @Override
    public V compute(final A arg) throws InterruptedException
    {
        Future<V> f = cache.get(arg);
        if (f == null) { // computation has not been started
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            
            FutureTask<V> futureTask = new FutureTask<V>(eval);
            f = futureTask;
            cache.put(arg, futureTask);
            futureTask.run();
        }
        
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

}

