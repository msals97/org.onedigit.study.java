package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LatchTest
{
    int nThreads = 5;
    private CountDownLatch startGate = new CountDownLatch(1);
    private CountDownLatch endGate = new CountDownLatch(nThreads);
    
    class Worker implements Runnable
    {
        public void run()
        {
            try {
                System.out.println("Waiting at start gate: startGate count = " + startGate.getCount());
                startGate.await();
                System.out.println("Starting run");
                Thread.sleep(3000);
                endGate.countDown();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void process()
    {
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(new Worker());
            t.start();
        }
        
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Starting Threads");
        long start = System.nanoTime();
        startGate.countDown();
        long end = 0;
        System.out.println("Waiting for threads to finish");
        try {
            endGate.await();
            end = System.nanoTime();
            System.out.println("All threads have finished");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Elapsed = " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS));
    }
    
    public static void main(String[] args)
    {
        LatchTest test = new LatchTest();
        test.process();
        test.process();
    }
}
