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
	
	public BlockingQueueExample()
	{
		aQ = new ArrayBlockingQueue<>(10);
	}
}
