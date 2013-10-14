import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class PutTakeTest
{
    private ExecutorService pool = Executors.newCachedThreadPool();
    private AtomicInteger putSum = new AtomicInteger(0);
    private AtomicInteger takeSum = new AtomicInteger(0);
    private CyclicBarrier barrier;
    // private BoundedBuffer<Integer> bb;
    private LinkedQueue<Integer> bb;
    // private ConcurrentLinkedQueue<Integer> bb;
    // private LinkedBlockingQueue<Integer> bb;
    // private ArrayBlockingQueue<Integer> bb;
    private int capacity, npairs, ntrials;

    public PutTakeTest(int capacity, int npairs, int ntrials)
    {
        this.capacity = capacity;
        this.npairs = npairs;
        this.ntrials = ntrials;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
        // this.bb = new BoundedBuffer<Integer>(capacity);
        this.bb = new LinkedQueue<Integer>();
        // this.bb = new ConcurrentLinkedQueue<Integer>();
        // this.bb = new LinkedBlockingQueue<Integer>(capacity);
        // this.bb = new ArrayBlockingQueue<Integer>(capacity);
    }

    int xorShift(int y)
    {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    class Producer implements Runnable
    {
        public void run()
        {
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;
                barrier.await();        
                for (int i = ntrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();        
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable
    {
        public void run()
        {
            try {
                barrier.await();        
                int sum = 0;
                for (int i = ntrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();        
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void test()
    {
        try {
            for (int i = 0; i < npairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }   
            barrier.await(); // wait for threads to be ready
            barrier.await(); // wait for threads to finish
            int diff = takeSum.get() - putSum.get();
            System.out.println("takSum - putSum = " + diff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    public static void main(String[] args) throws InterruptedException
    {
        PutTakeTest pt = new PutTakeTest(10, 10, 100000); // capacity, npairs, ntrials
        pt.test();
    }
}
