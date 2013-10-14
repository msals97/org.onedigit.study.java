import java.util.concurrent.locks.ReentrantLock;

public class LockInterrupt
{
    ReentrantLock lock = new ReentrantLock();

    class Task implements Runnable
    {
        public void run()
        {
            try {
                lock.lockInterruptibly();
                // Enter the next try block, if the lock was acquired.
                try {
                    System.out.println(Thread.currentThread() + ", starting task...");
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread() + ", end of task...");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread() + ", " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread() + ", " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    class Interrupt implements Runnable
    {
        Thread t;

        public Interrupt(Thread t)
        {
            this.t = t;
        }
        public void run()
        {
            System.out.println(Thread.currentThread() + ", going to interrupt " + t);
            t.interrupt();
        }
    }

    public void runTasks()
    {
        Thread t1 = new Thread(new Task());
        t1.start();

        Thread t2 = new Thread(new Task());
        t2.start();

        Thread interrupter = new Thread(new Interrupt(t2));
        interrupter.start();

        try {
            t1.join();
            interrupter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        LockInterrupt li = new LockInterrupt();
        li.runTasks();
    }
}
