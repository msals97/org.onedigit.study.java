package org.onedigit.study.java.collection.sync;

import java.util.concurrent.TimeUnit;

public class BrokenSynchronization
{
    private static volatile boolean stopRequested;
    
    private static synchronized void requestStop()
    {
        stopRequested = true;
    }
    
    private static synchronized boolean stopRequested()
    {
        return stopRequested;
    }
    
    public static void main(String... args) throws InterruptedException
    {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                int i = 0;
                while (!stopRequested) {
                    i++;
                }
            }
        });
        backgroundThread.start();
        
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
