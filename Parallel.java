import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CompletionService;

public class Parallel
{
    private ExecutorService executor;
    private ConcurrentMap<Integer, QueueingFuture<Integer>> cache = 
        new ConcurrentHashMap<Integer, QueueingFuture<Integer>>();

    private BlockingQueue<QueueingFuture> completionQueue = 
        new LinkedBlockingQueue<QueueingFuture>();

    public Parallel()
    {
        // executor = Executors.newFixedThreadPool(5);
        executor = Executors.newCachedThreadPool();
    }

    class QueueingFuture<Integer> extends FutureTask<Integer>
    {
        public QueueingFuture(Callable<Integer> callable)
        {
            super(callable);
        }

        public QueueingFuture(Runnable runnable, Integer result)
        {
            super(runnable, result);
        }

        protected void done()
        {
            System.out.println("Putting on queue...");
            completionQueue.add(this);
        }
    }

    class FactorialTask implements Callable<Integer>
    {
        int n;
        public FactorialTask(int n)
        {
            this.n = n;
        }

        public Integer call() throws Exception
        {
            System.out.println("Starting task for " + n + ", in thread: " + Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            // factorial of n
            int result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            System.out.println("Finished task in thread: " + Thread.currentThread() + " result = " + result);
            return result;
        }
    }


    public int factorial(int n) throws InterruptedException, ExecutionException
    {
        Future<Integer> cachedFuture = cache.get(n);
        if (cachedFuture == null) {
            QueueingFuture<Integer> futureTask = new QueueingFuture<Integer>(new FactorialTask(n));
            cachedFuture = cache.putIfAbsent(n, futureTask);
            if (cachedFuture == null) {
                cachedFuture = futureTask;
                futureTask.run();
            }
        }

        // executor.submit(new Task(n));
        return cachedFuture.get();
    }

    public Future<Integer> process(final int n) throws InterruptedException, ExecutionException
    {
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception
            {
                return factorial(n);
            }
        });
        return future;
    }

    public void shutdown()
    {
        executor.shutdown();
    }

    public static void main(String[] args)
    {
        Parallel p = new Parallel();
        try {
            Future<Integer> result1 = p.process(10);
            Future<Integer> result2 = p.process(10);
            Future<Integer> result3 = p.process(12);
            System.out.println("Submitted");
            System.out.println("result1 = " + result1.get());
            System.out.println("result2 = " + result2.get());
            System.out.println("result3 = " + result3.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.shutdown();
    }
}
