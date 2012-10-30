package org.onedigit.study.java;

public class JavaPrimitives
{
	public static void main(String... args)
	{
		byte byteMin = -128;
		byte byteMax = 127;
		assert(byteMin == Byte.MIN_VALUE);
		assert(byteMax == Byte.MAX_VALUE);
		
		short shortMax = 32767;
		short shortMin = -32768;
		assert(shortMax == Short.MAX_VALUE);
		assert(shortMin == Short.MIN_VALUE);
		
		int intMax = (1 << 31) - 1;
		int intMin = (1 << 31);
		int intSize = Integer.SIZE;
		System.out.println("intSize = " + intSize + ", intMax = " + 
				intMax + " intMin = " + intMin);
		assert(intMax == Integer.MAX_VALUE);
		assert(intMin == Integer.MIN_VALUE);
		
		long longMax = (1L << (63)) - 1;
		long longMin = -(1L << (63));
		int longSize = Long.SIZE;
		System.out.println("longSize = " + longSize + ", longMax = " + 
				longMax + " longMin = " + longMin);
	}
}