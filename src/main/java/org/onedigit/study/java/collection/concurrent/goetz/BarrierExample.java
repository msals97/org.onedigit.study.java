package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierExample
{
    int nthreads = 5;
    CyclicBarrier barrier;
    
    public BarrierExample()
    {
        barrier = new CyclicBarrier(nthreads, new Runnable() {
           public void run()
           {
               System.out.println("End of the barrier sync");
           }
        });
    }
    
    class Worker implements Runnable
    {
        public void run()
        {
            System.out.println(Thread.currentThread().getId() + ": starting work");
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getId() + ": waiting at barrier");
                barrier.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void process()
    {
        for (int i = 0; i < nthreads; i++) {
            Thread t = new Thread(new Worker());
            t.start();
        }
    }
    
    public static void main(String... args)
    {
        BarrierExample example = new BarrierExample();
        example.process();
    }
}
