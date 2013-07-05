package org.onedigit.study.java.concurrent;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDown
{
    public void testWaitNotify()
    {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread() {
            public void run()
            {
                System.out.println("Going to count down...");
                latch.countDown();
            }
        };
        
        t.start();
        
        System.out.println("Going to await...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done waiting");
    }
    
    // A more complicated example
    class AsynchProcessor implements Callable<Object>
    {
        private Observer listener;
        private AsynchProcessor(Observer listener)
        {
            this.listener = listener;
        }
        
        public Object call() throws Exception
        {
            int sleepTime = new Random().nextInt(2000);
            System.out.println("Sleeping for " + sleepTime + "ms");
            Thread.sleep(sleepTime);
            listener.update(null, null);
            return null;
        }
    }
    
    public void testSomeProcessing() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        AsynchProcessor processor = new AsynchProcessor(new Observer() {
            @Override
            public void update(Observable o, Object arg)
            {
                System.out.println("Counting down...");
                latch.countDown();
            }
        });
        
        // submit two tasks
        executorService.submit(processor);
        executorService.submit(processor);
        
        System.out.println("Submitted tasks. Time to wait...");
        long time = System.currentTimeMillis();
        latch.await(5000, TimeUnit.MILLISECONDS);
        long totalTime = System.currentTimeMillis() - time;
        System.out.println("I awaited for " + totalTime + "ms. Did latch count down? " + (latch.getCount() == 0));
        executorService.shutdown();
    }
    
    public static void main(String[] args)
    {
        try {
            // new CountDown().testWaitNotify();            
            new CountDown().testSomeProcessing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
