package org.onedigit.study.java.concurrent;

public class BadThread
{
    private static Integer lock = new Integer(1);
    private int count;
    
    public static void main(String[] args)
    {
        new BadThread().calculate();
        new BadThread().calculate();
        new BadThread().calculate();
    }
    
    public void calculate()
    {
        Thread t = new Thread() {
            public void run() {
                synchronized (lock) {
                    long id = Thread.currentThread().getId();
                    System.out.println("Thread-" +  id + ", entering locked region");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    BadThread bt = new BadThread();
                    bt.count = 10;
                    lock = new Integer(lock.intValue() + 1);
                    System.out.println("Thread-" + id + ", lock = " + lock.intValue());
                    System.out.println("Thread-" + id + ", exit locked region");
                }
            }
        };
        
        t.start();
    }
}
