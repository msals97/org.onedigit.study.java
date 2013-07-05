package org.onedigit.study.java.concurrent;


public class WaitNotify
{
    public void testWaitNotify()
    {
        final Object mutex = new Object();
        Thread thread = new Thread() {
            public void run()
            {
                synchronized (mutex) {
                    System.out.println("Going to wait (lock held by " +
                            Thread.currentThread().getName() + ")");
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Done waiting (lock held by " +
                            Thread.currentThread().getName() + ")");
                }
            }
        };
        
        thread.start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        synchronized(mutex) {
            System.out.println("Going to notify (lock held by " +
                    Thread.currentThread().getName() + ")");
            // mutex.notify();
            mutex.notifyAll();
            System.out.println("Done notify (lock held by " +
                    Thread.currentThread().getName() + ")");
        }
    }
    
    public static void main(String[] args)
    {
        new WaitNotify().testWaitNotify();
    }
}
