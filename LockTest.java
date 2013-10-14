import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

/**
  * Test the performance of locks vs atomic variables
  */

public class LockTest
{
    static class PseudoRandom
    {
        public int calculateNext(int prev)
        {
            prev ^= prev << 6;
            prev ^= prev >>> 21;
            prev ^= (prev << 7);
            return prev;
        }
    }

    static class ReentrantLockPseudoRandom extends PseudoRandom
    {
        private final Lock lock = new ReentrantLock(false);
        private int seed;

        public ReentrantLockPseudoRandom(int seed) { this.seed = seed; }
        
        public int nextInt(int n)
        {
            lock.lock();
            try {
                int s = seed;
                seed = calculateNext(s);
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            } finally {
                lock.unlock();
            }
        }
    }

    static class AtomicPseudoRandom extends PseudoRandom
    {
        private AtomicInteger seed;

        public AtomicPseudoRandom(int seed) { this.seed = new AtomicInteger(seed); }

        public int nextInt(int n)
        {
            while (true) {
                int s = seed.get();
                int nextSeed = calculateNext(s);
                if (seed.compareAndSet(s, nextSeed)) {
                    int remainder = s % n;
                    return remainder > 0 ? remainder : remainder + n;
                }
            }
        }
    }

    static ReentrantLockPseudoRandom myRLPR = new ReentrantLockPseudoRandom(10);
    static AtomicPseudoRandom myAPR = new AtomicPseudoRandom(10);

    static final int HOW_LONG_TO_RUN = 20000;
    static final int NUMBER_OF_THREADS = 4;

    static class RLPR implements Runnable
    {
        public int counter = 0;

        public void run()
        {
            while (true) {
                myRLPR.nextInt(100);
                counter++;
            }
        }
    }

    static class APR implements Runnable
    {
        public int counter = 0;

        public void run()
        {
            while (true) {
                myAPR.nextInt(100);
                counter++;
            }
        }
    }

    public static int testRLPR() throws InterruptedException
    {
        long startTime, endTime;

        RLPR[] rlprs = new RLPR[NUMBER_OF_THREADS];
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        int numberGenerated = 0;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            rlprs[i] = new RLPR();
            threads[i] = new Thread(rlprs[i]);
        }

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].start();
        }

        Thread.sleep(HOW_LONG_TO_RUN);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].stop(); // NOTE: Deprecated but used for timing tests.
            numberGenerated += rlprs[i].counter;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Locking version generated " + numberGenerated + 
                " random numbers in " + (endTime - startTime) + " ms");
        return numberGenerated;
    }

    public static int testAPR() throws InterruptedException
    {
        long startTime, endTime;

        APR[] aprs = new APR[NUMBER_OF_THREADS];
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        int numberGenerated = 0;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            aprs[i] = new APR();
            threads[i] = new Thread(aprs[i]);
        }

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].start();
        }

        Thread.sleep(HOW_LONG_TO_RUN);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].stop(); // NOTE: Deprecated but used for timing tests.
            numberGenerated += aprs[i].counter;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Non-locking version generated " + numberGenerated + 
                " random numbers in " + (endTime - startTime) + " ms");
        return numberGenerated;
    }

    public static void main(String[] args) throws InterruptedException
    {
        int n1 = testRLPR();
        int n2 = testAPR();
        System.out.println("Atomic/Lock ratio = " + (1.0 * n2)/n1);
    }
}
