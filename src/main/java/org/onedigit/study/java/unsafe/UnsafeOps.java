package org.onedigit.study.java.unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import sun.misc.Unsafe;

import com.sun.corba.se.impl.ior.ByteBuffer;

public class UnsafeOps extends ByteBuffer
{
	private static Unsafe unsafe = getUnsafe();
	private static final long parkBlockerOffset;
	private static final int NR_BITS = Integer.valueOf(System.getProperty("sun.arch.data.model"));
	private static final int BYTE = 8;
	private static final int WORD = NR_BITS/BYTE;
	private static final int MIN_SIZE = 16; 

	static {
		try {
			parkBlockerOffset = unsafe.objectFieldOffset(
					java.lang.Thread.class.getDeclaredField("parkBlocker"));
		} catch (Exception ex) {
			throw new Error(ex);
		}
	}

	private static Unsafe getUnsafe()
	{
		String fieldName = "theUnsafe";
		Unsafe unsafe = null;
		try {
			Field field = Unsafe.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (NoSuchFieldException | SecurityException
		        | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return unsafe;
	}
	
	public static int sizeOf(Class<?> src)
	{
		//
		// Get the instance fields of src class
		//
		List<Field> instanceFields = new LinkedList<Field>();
		do {
			if (src == Object.class)
				return MIN_SIZE;
			for (Field f : src.getDeclaredFields()) {
				if ((f.getModifiers() & Modifier.STATIC) == 0) {
					instanceFields.add(f);
				}
			}
			src = src.getSuperclass();
		} while (instanceFields.isEmpty());
		//
		// Get the field with the maximum offset
		//
		long maxOffset = 0;
		for (Field f : instanceFields) {
			long offset = unsafe.objectFieldOffset(f);
			if (offset > maxOffset)
				maxOffset = offset;
		}
		return (((int) maxOffset / WORD) + 1) * WORD;
	}

	private static void setBlocker(Thread t, Object arg)
	{
		// Even though volatile, hotspot doesn't need a write barrier here.
		unsafe.putObject(t, parkBlockerOffset, arg);
	}

	public static void park(Object blocker)
	{
		Thread t = Thread.currentThread();
		setBlocker(t, blocker);
		unsafe.park(false, 0L);
		setBlocker(t, null);
	}
	
	private void sleep(int n)
	{
		try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }		
	}

	public void testThreadPark()
	{
		Thread thr = new Thread(new Runnable() {
			public void run()
			{
				System.out.println("Run...");
				park(this);
			}
		});

		thr.start();
		sleep(5_000);
		System.out.println("Unparking thread...");
		unsafe.unpark(thr);
	}

	public UnsafeOps()
	{
		int addressSize = unsafe.addressSize();
		System.out.println("Address size = " + addressSize);
		System.out.println("Page size = " + unsafe.pageSize());
		int nelems = 3;
		double[] loadavg = new double[nelems];
		System.out.println("Load avg = "
		        + unsafe.getLoadAverage(loadavg, nelems));
	}

	public static void main(String... args)
	{
		UnsafeOps ops = new UnsafeOps();
		// ops.testThreadPark();
		System.out.println(UnsafeOps.sizeOf(java.util.concurrent.ConcurrentHashMap.class));
		System.out.println(UnsafeOps.sizeOf(java.util.HashMap.class));
	}
}
