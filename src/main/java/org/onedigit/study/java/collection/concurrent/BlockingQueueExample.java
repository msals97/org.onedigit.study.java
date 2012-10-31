package org.onedigit.study.java.collection.concurrent;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
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

import sun.nio.cs.ArrayDecoder;

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
	
	PriorityQueue<Integer> pQ; // implements Queue
	
	ArrayBlockingQueue<Integer> aBQ; // implements BlockingQueue implements Queue 
	DelayQueue<WorkItem> dQ; // implements BlockingQueue implements Queue 
	LinkedBlockingQueue<Integer> lQ; // implements BlockingQueue implements Queue 
	PriorityBlockingQueue<Integer> pBQ; // implements BlockingQueue implements Queue 
	SynchronousQueue<Integer> sQ; // implements BlockingQueue implements Queue 
	
	// implements BlockingQueue implements Queue
	// implements Deque
	LinkedBlockingDeque<Integer> lBQ;
	
	// implements TransferQueue implements BlockingQueue implements Queue
	LinkedTransferQueue<Integer> lTQ;
	
	ArrayDeque<Integer> aD; // implements Deque
	LinkedList<Integer> linkedList; // implements Deque
	
	public void arrayBlockingQueue() throws InterruptedException
	{
		System.out.println("--ArrayBlockingQueue--");
		aBQ = new ArrayBlockingQueue<>(5);
		// inserts at tail, waiting if necessary for space
		aBQ.put(1);
		aBQ.put(2);
		aBQ.put(3);
		aBQ.put(4);
		
		// same as offer, but will throw exceptions. Note that
		// ArrayBlockingQueue allows duplicates.
		aBQ.add(4); 
		
		// This should fail, as we will exceed Queue length
		boolean success = aBQ.offer(6); 
		assert(false == success);
		
		System.out.println(aBQ);
		
		// retrieve and remove, wait if necessary
		aBQ.take();
		
		Integer i = aBQ.poll();
		System.out.println(i);
		i = aBQ.poll();
		System.out.println(i);	
		
		aBQ.peek();
		// same as peek except that this will throw an exception
		// if Queue is empty
		aBQ.element(); 
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
		System.out.println("--DelayQueue--");
		WorkItem workItem_1 = new WorkItem(5000);
		WorkItem workItem_2 = new WorkItem(1000);
		WorkItem workItem_3 = new WorkItem(7000);
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
		example.arrayBlockingQueue();
		// example.delayQueue();
	}
}
