package org.onedigit.study.java.collection.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueExample
{
	class DelayItem implements Delayed
	{
		@Override
        public int compareTo(Delayed o)
        {
	        return 0;
        }

		@Override
        public long getDelay(TimeUnit unit)
        {
	        return 0;
        }
	}
	
	ArrayBlockingQueue<Integer> aQ;
	DelayQueue<DelayItem> dQ;
	LinkedBlockingQueue<Integer> lQ;
	PriorityBlockingQueue<Integer> pQ;
	SynchronousQueue<Integer> sQ;
	LinkedBlockingDeque<Integer> ldQ;
	LinkedTransferQueue<Integer> ltQ;
	
	public void arrayBlockingQueue() throws InterruptedException
	{
		aQ = new ArrayBlockingQueue<>(10);
		aQ.put(1);
		aQ.put(2);
		aQ.put(3);
		aQ.put(4);
		Integer i = aQ.poll();
		System.out.println(i);
		i = aQ.poll();
		System.out.println(i);		
	}
	
	public void delayQueue()
	{
		dQ = new DelayQueue<DelayItem>();
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		try {
			lock.lock();
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public BlockingQueueExample() 
	{

	}
	
	public static void main(String... args) throws InterruptedException
	{
		BlockingQueueExample example = new BlockingQueueExample();
		example.delayQueue();
	}
}
