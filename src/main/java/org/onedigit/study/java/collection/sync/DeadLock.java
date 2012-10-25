package org.onedigit.study.java.collection.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock
{
	Lock lockOne = new ReentrantLock();
	Lock lockTwo = new ReentrantLock();
	
	public void f() 
	{
		lockOne.lock();
		sleep(5000);
		g();
	}
	
	public void g() 
	{
		lockTwo.lock();
		sleep(1000);
		f();
	}
	
	private void sleep(int n)
	{
		try {
	        Thread.sleep(n);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
	}
	
	public void deadlock()
	{
		Thread t1 = new Thread(new Runnable() {
			public void run()
			{
				f();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run()
			{
				g();
			}
		});

		t1.start();
		t2.start();
	}
	
	public static void main(String... args)
	{
		DeadLock dl = new DeadLock();
		dl.deadlock();
	}
}
