package org.onedigit.study.java.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

class UniqueThreadIdGenerator
{
	private static final AtomicInteger uniqueId = new AtomicInteger(0);
	
	private static final ThreadLocal<Integer> uniqueNum = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue()
		{
			return uniqueId.getAndIncrement();
		}
	};

	public static int getCurrentThreadId()
	{
		return uniqueNum.get();
	}
} 

public class ThreadLocalTest
{
	public static void main(String... args)
	{
		int id = UniqueThreadIdGenerator.getCurrentThreadId();
		System.out.println("Id = " + id);
		id = UniqueThreadIdGenerator.getCurrentThreadId();
		System.out.println("Id = " + id);
		
		
	}
}
