package org.onedigit.study.java.concurrent;

public class BoundedBuffer<V>
{
    private final V[] buf;
    private int tail;
    private int head;
    private int count;
    
    public static void main(String[] args) 
    {
        final BoundedBuffer<Integer> buf = new BoundedBuffer<>(5);
        
        Thread t = new Thread() {
            public void run() 
            {
                try {
                    Thread.sleep(2000);
                    buf.put(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        
        try {
            System.out.println("Going to take an item...");
            int item = buf.take();
            System.out.println("Got the item: " + item);                        
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity)
    {
        this.buf = (V[]) new Object[capacity];
    }
    
    public synchronized void put(V v) throws InterruptedException
    {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }
    
    public synchronized V take() throws InterruptedException
    {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
    
    private synchronized final void doPut(V v)
    {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }
    
    private synchronized final V doTake()
    {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }
    
    public synchronized final boolean isFull()
    {
        return count == buf.length;
    }
    
    public synchronized final boolean isEmpty()
    {
        return count == 0;
    }

}
