import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Random;

class ReaderWriter
{
    private ReadWriteLock rwLock;
    private Lock readLock;
    private Lock writeLock;
    private Deque<Integer> deque;
    private Random rand;
    private Condition notEmpty;

    public ReaderWriter()
    {
        rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
        deque = new ArrayDeque<Integer>();
        rand = new Random();
        notEmpty = writeLock.newCondition();
    }

    class Writer implements Runnable
    {
        public void run()
        {
            while (true) {
                writeLock.lock();
                try {
                    deque.addLast(rand.nextInt());
                    notEmpty.signal();
                } finally {
                    writeLock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Reader implements Runnable
    {
        public void run()
        {
            while (true) {
                readLock.lock();
                try {
                    while (!deque.isEmpty()) {
                        int value = deque.pollFirst();
                        System.out.println("Dequeued value: " + value);
                    }
                } finally {
                    readLock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start()
    {
        Thread writerThread = new Thread(new Writer());
        Thread readerThread = new Thread(new Reader());
        writerThread.start();
        readerThread.start();
    }

    public static void main(String[] args)
    {
        ReaderWriter rw = new ReaderWriter();
        rw.start();
    }
}
