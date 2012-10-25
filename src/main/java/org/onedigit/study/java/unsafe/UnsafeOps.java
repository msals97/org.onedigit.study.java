package org.onedigit.study.java.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

import com.sun.corba.se.impl.ior.ByteBuffer;

public class UnsafeOps extends ByteBuffer
{
	public UnsafeOps()
	{
		Unsafe unsafe = getUnsafe();
		int addressSize = unsafe.addressSize();
		System.out.println("Address size = " + addressSize);
		System.out.println("Page size = " + unsafe.pageSize());
		int nelems = 3;
		double[] loadavg = new double[nelems];
		System.out.println("Load avg = " + unsafe.getLoadAverage(loadavg, nelems));
	}

	private Unsafe getUnsafe()
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

	public static void main(String... args)
	{
		new UnsafeOps();
	}
}
