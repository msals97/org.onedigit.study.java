package org.onedigit.study.ikm;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// Three threads perform 2 tasks.  Before task 2 can
// start, all three threads must finish task 1. 
// At the end of task 2 by the three threads,
// a single thread consolidates the final result.

public class CyclicBarrierExample
{
    class Worker implements Runnable  
    {
        private CyclicBarrier barrier;
        
        public Worker(CyclicBarrier barrier)
        {
            this.barrier = barrier;
        }
        
        public void run()
        {
            try {
                doTaskA();
                barrier.await();
                doTaskB();
                barrier.await();
                System.out.println(Thread.currentThread().getName() + ": Tasks A and B done");
            } catch (BrokenBarrierException e) {
                System.err.println(e.getMessage());
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

        }
    }
    
    private static int NUM_THREADS = 3;
    private static int NUM_TASKS = 2;
    private static int taskCount = 0;
    private CyclicBarrier barrier;
    
    public void doTaskA()
    {
        System.out.println(Thread.currentThread().getName() + ": Doing Task-A");
    }
    
    public void doTaskB()
    {
        System.out.println(Thread.currentThread().getName() + ": Doing Task-B");
    }
    
    public void process()
    {
        barrier = new CyclicBarrier(NUM_THREADS, new Runnable() {
            public void run()
            {
                System.out.println("All " + NUM_THREADS + " have reached barrier point");
                taskCount++;
                
                if (taskCount == NUM_TASKS) {
                    System.out.println(Thread.currentThread().getName() + " Consolidating results");
                }
            }
        });
        
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread t = new Thread(new Worker(barrier));
            t.start();
        }
    }

    public static void main(String[] args)
    {
        CyclicBarrierExample example = new CyclicBarrierExample();
        example.process();
    }
}
