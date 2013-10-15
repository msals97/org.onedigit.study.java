package org.onedigit.study.java.algo.trading;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TimeWindow implements Iterable<AlgoEvent>
{
    private final ArrayDeque<AlgoEvent> buffer;
    private final int length; // length in milliseconds of the window.
    private final ScheduledExecutorService executor;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rLock = rwLock.readLock();
    private final Lock wLock = rwLock.writeLock();

    public TimeWindow(int length)
    {
        buffer = new ArrayDeque<>();
        this.length = length;
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start()
    {
        executor.scheduleAtFixedRate(new ExpireEvents(), 0, length,
                TimeUnit.MILLISECONDS);
    }
    
    /**
     * Add new event and return expired events.
     * @param event
     * @return expired events.
     */
    public void handleEvent(StockPrice event)
    {
        rLock.lock();
        try {
            // System.out.println("Adding stock price " + event.getPrice());
            buffer.add(new AlgoEvent(new Date().getTime(), event));
        } finally {
            rLock.unlock();
        }
    }    

    private class ExpireEvents implements Runnable
    {
        public void run()
        {
            long currentTime = System.currentTimeMillis();
            long expireBefore = currentTime - length + 1;
            
            wLock.lock();
            try {
                if (!buffer.isEmpty()) {
                    AlgoEvent event = buffer.getFirst();
                    // System.out.println("Running ExpireEvents, top event = " + event);
                    // expire anything that has a timestamp less than expireBefore.
                    while (event.getTimestamp() < expireBefore) {
                        AlgoEvent removed = buffer.removeFirst();
                        // System.out.println("Removed event from buffer: " + removed);
                        if (buffer.isEmpty()) {
                            break;
                        }
                        event = buffer.getFirst();
                    }
                }
                System.out.println(new Date() + ", current buffer size = " + buffer.size());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                wLock.unlock();
            }
        }
    }
    
    public static void main(String[] args)
    {
        final TimeWindow tw = new TimeWindow(10000);
        tw.start();
        
        StockTicker st = new StockTicker(30, 0.01, 20, 0.01, 0.01);
        st.addStockListener(new StockListener() {
            @Override
            public void priceTick(StockPrice event)
            {
                tw.handleEvent(event);
            }
        });
        Thread t = new Thread(st);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<AlgoEvent> iterator()
    {
        return buffer.iterator();
    }
}
