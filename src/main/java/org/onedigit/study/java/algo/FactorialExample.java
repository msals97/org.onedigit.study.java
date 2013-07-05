package org.onedigit.study.java.algo;

public class FactorialExample
{
    private Integer current = new Integer(1);
    private static Integer lastValue = new Integer(1);
    
    public void calc()
    {
        synchronized (current) {
            System.out.println(Thread.currentThread() + ": Enter calc, current = " + current);
            current = current + 1;
            lastValue = current;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                
            }
            System.out.println(Thread.currentThread() + ": Leave calc, current = " + current);
        }
    }
    
    class Worker implements Runnable
    {
        public void run()
        {
            calc();
        }
    }
    
    public void start()
    {
        Thread t1 = new Thread(new Worker());
        Thread t2 = new Thread(new Worker());
        Thread t3 = new Thread(new Worker());
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException ex) {
            
        }
    }
    
    public Integer getResult()
    {
        return lastValue;
    }
    
    public static void main(String[] args)
    {
        FactorialExample ex = new FactorialExample();
        ex.start();
        System.out.println(ex.getResult());
    }
}
