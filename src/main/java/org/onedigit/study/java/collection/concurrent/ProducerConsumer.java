package org.onedigit.study.java.collection.concurrent;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer
{
    private static int MAX_ENTRIES = 2;
    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(MAX_ENTRIES);
    private Lock lock = new ReentrantLock();
    private Condition producerCond;
    private Condition consumerCond;
    private Random random = new Random();
    
    private LinkedList<Integer> list = new LinkedList<>();
    
    public ProducerConsumer()
    {
        producerCond = lock.newCondition();
        consumerCond = lock.newCondition();
    }
    
    class Producer implements Runnable
    {
        public void run()
        {
            while (true) {
                try {
                    Integer i = random.nextInt();
                    // queue.put(i);
                    lock.lock();
                    try {
                        while (list.size() > MAX_ENTRIES) {
                            consumerCond.await();
                        }
                        list.add(i);
                        System.out.println(Thread.currentThread().getName() + ": Produced " + i);
                        producerCond.signalAll();
                    } finally {
                        lock.unlock();
                    }
                } catch (/* InterruptedException */ Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    class Consumer implements Runnable
    {
        public void run()
        {
            while (true) {
                try {
                    // Integer i = queue.take();
                    lock.lock();
                    try {
                        while (list.isEmpty()) {
                            producerCond.await();
                        }
                        Integer i = list.removeFirst();
                        System.out.println("\t" + Thread.currentThread().getName() + ": Consumed " + i);
                        TimeUnit.SECONDS.sleep(2);
                        consumerCond.signalAll();
                    } finally {
                        lock.unlock();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    public void start()
    {
        ExecutorService producerPool = Executors.newFixedThreadPool(1);
        producerPool.execute(new Producer());
        
        ExecutorService consumerPool = Executors.newFixedThreadPool(1);
        consumerPool.execute(new Consumer());
    }
    
    public static void main(String[] args)
    {
        ProducerConsumer pc = new ProducerConsumer();
        pc.start();
    }
}
