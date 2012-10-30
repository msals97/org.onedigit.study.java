package org.onedigit.study.java.collection.concurrent;

import java.util.Date;
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
	class WorkItem implements Delayed
	{
		private TimeUnit expireUnit = TimeUnit.NANOSECONDS;
		private TimeUnit delayUnit = TimeUnit.MILLISECONDS;
		private long expire;
		private long delay;
		
		public WorkItem(long delay)
		{
			this.delay = delay;
			long delayNano = expireUnit.convert(delay, delayUnit);
			this.expire = System.nanoTime() + delayNano;
		}
		
		@Override
        public int compareTo(Delayed other)
        {
	        return Long.valueOf(this.expire).compareTo(((WorkItem)other).expire);
        }

		@Override
        public long getDelay(TimeUnit unit)
        {
			long current = System.nanoTime();
	        return unit.convert(expire - current, expireUnit);
        }
		
		@Override 
		public String toString() { return String.valueOf(delay); } 
	}
	
	ArrayBlockingQueue<Integer> aQ;
	DelayQueue<WorkItem> dQ;
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
	
	private void sleep(long n)
	{
		try {
	        Thread.sleep(n);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
	}
	
	public void delayQueue() 
	{
		WorkItem workItem_1 = new WorkItem(5000);
		WorkItem workItem_2 = new WorkItem(1000);
		WorkItem workItem_3 = new WorkItem(10000);
		WorkItem workItem_4 = new WorkItem(3000);
		WorkItem workItem_5 = new WorkItem(4000);

		dQ = new DelayQueue<WorkItem>();
		dQ.add(workItem_1);
		dQ.add(workItem_2);
		dQ.add(workItem_3);
		dQ.add(workItem_4);
		dQ.add(workItem_5);
		
		System.out.println(new Date(System.currentTimeMillis()));
		try {
			do {
				WorkItem item = dQ.take();
				System.out.println(new Date(System.currentTimeMillis()) + ": " + item);
			} while (dQ.peek() != null);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		
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
