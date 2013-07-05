package org.onedigit.study.java.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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

	public static int get()
	{
		return uniqueNum.get();
	}
} 

class LocalDateFormatter extends ThreadLocal<SimpleDateFormat>
{
    @Override
    protected SimpleDateFormat initialValue()
    {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}

public class ThreadLocalTest
{
	public static void main(String... args)
	{
		int id = UniqueThreadIdGenerator.get();
		System.out.println("Id = " + id);
		id = UniqueThreadIdGenerator.get();
		System.out.println("Id = " + id);

		Date now = new Date();
		System.out.println(new LocalDateFormatter().get().format(now));
		
		try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
