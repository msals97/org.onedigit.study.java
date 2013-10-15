package org.onedigit.study.java.algo.trading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Measure the rate of messages coming from StockTicker
 * 
 * @author ahmed
 *
 */
public class StockTickerPerformance implements StockListener
{
    private AtomicInteger counter = new AtomicInteger(0);
    private ScheduledExecutorService executor;
    
    @Override
    public void priceTick(StockPrice event)
    {
        counter.incrementAndGet();
    }
    
    public StockTickerPerformance()
    {
        executor = Executors.newSingleThreadScheduledExecutor();    
    }
    
    public void start()
    {
        executor.scheduleAtFixedRate(new Runnable() {
            public void run()
            {
                int count = counter.get();
                System.out.println("Count = " + count);
                counter.set(0);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    
    public void stop()
    {
        executor.shutdown();
    }
    
    public static void main(String[] args)
    {
        StockTickerPerformance stp = new StockTickerPerformance();
        stp.start();
        StockTicker st = new StockTicker(30, 0.01, 20, 0.01, 0.01);
        st.addStockListener(stp);
        Thread t = new Thread(st);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
