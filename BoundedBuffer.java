import java.util.concurrent.Semaphore;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

class BoundedBuffer<E>
{
    private int capacity;
    private final E[] items;
    private int size;
    private int head, tail;
    private Semaphore availableItems, availableSpaces;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity)
    {
        this.capacity = capacity;
        availableSpaces = new Semaphore(capacity);
        availableItems = new Semaphore(0);
        items = (E[]) new Object[capacity];
    }

    public void put(E item) throws InterruptedException
    {
        availableSpaces.acquire();
        doInsert(item);
        availableItems.release();
    }

    public E take() throws InterruptedException
    {
        availableItems.acquire();
        E item = doTake();
        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E item)
    {
        int i = tail;
        items[i] = item;
        tail = (++i == items.length) ? 0 : i;
        ++size;
    }

    private synchronized E doTake()
    {
        int i = head;
        E item = items[i];
        items[i] = null;
        head = (++i == items.length) ? 0 : i;
        --size;
        return item;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    //------------------------------------------------------------------

    private static int LOCKUP_DETECT_TIMEOUT = 1000;

    public static void fail()
    {
        System.out.println("test failed");
    }

    public static void success()
    {
        System.out.println("test successful");
    }

    public static void testTakeBlocksWhenEmpty()
    {
        final BoundedBuffer<Integer> buf = new BoundedBuffer<Integer>(2);
        Thread taker = new Thread() {
            public void run()
            {
                try {
                    int unused = buf.take(); // should block;
                    fail();
                } catch (InterruptedException e) { 
                    System.out.println("Blocking thread interrupted");
                }
            }
        };

        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            System.out.println("Is taker sill alive? " + taker.isAlive());
        } catch (Exception e) {
            fail();
        }
    }
    
    //------------------------------------------------------------------

    public void simpleTest() throws InterruptedException
    {
        BoundedBuffer<Integer> buf = new BoundedBuffer<Integer>(2);
        buf.put(10);
        buf.put(20);
        System.out.println(buf.take());
        System.out.println(buf.take());
    }

    public static void main(String[] args) throws InterruptedException
    {
        // simpleTest();
        // testTakeBlocksWhenEmpty();
        PutTakeTest pt = new PutTakeTest(10, 10, 100000); // capacity, npairs, ntrials
        pt.test();
    }
}
