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
		
		System.out.println(Long.toBinaryString(1L << 63));
		System.out.println(Long.toBinaryString( (1L << 63) - 1));
		long l1 = 0b10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000L;
		long l2 = 0b01111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111L;
		System.out.println(l1);
		System.out.println(l2);
		
		int aNumber = 3;
        if (aNumber >= 0)
            if (aNumber == 0)
                System.out.println("first string");
            else
                System.out.println("second string");
        System.out.println("third string");
        
        int i = 10;
        int n = ++i%5;
        System.out.println("i = " + i + ", n = " + n);
	}
}
