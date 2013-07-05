package org.onedigit.study.ikm;

import java.util.Arrays;

public class AddingService
{
    interface AdderListener
    {
        public void resultReady(int result);
    }
    
    static class Adder
    {
        public static void add(final int a, final int b, final AdderListener listener)
        {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run()
                {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    int result = a + b;
                    System.out.println("Sending result...");
                    listener.resultReady(result);                    
                }
            });
            t.start();
        }
    }
    
    class Listener implements AdderListener
    {
        public void resultReady(int result)
        {
            handleResult(result);
        }
    }
    
    private int result;
    private boolean resultReady = false;
    
    public void handleResult(int result)
    {
        synchronized (this) {
            this.result = result;
            resultReady = true;
            notifyAll();
        }
    }
    
    public int add(int a, int b) 
    {
        Adder.add(a, b, new Listener());
        synchronized (this) {
            while (!resultReady) {
                System.out.println("Waiting for result");
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return result;
        }
    }
    
    public static void main(String[] args)
    {
        AddingService service = new AddingService();
        int result = service.add(10, 20);
        System.out.println("Result = " + result);
    }
}
