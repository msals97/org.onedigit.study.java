package org.onedigit.study.hash;

import java.util.concurrent.ConcurrentHashMap;

public class HashCode
{
    public static void main(String[] args)
    {
        String s = "polygenelubricants";
        int m = s.hashCode();
        System.out.println(m);
        System.out.println((-1) << 31);
        
        System.out.println(Math.abs(m)); // m is Int.MIN_VALUE, so abs(m) = Int.MIN_VALUE
        
        // BUG
        int hash = Math.abs(m) % 19; // returns a negative number!!!
        System.out.println("hash = " + hash);
        
        // FIX 
        // Make the number positive by m % 0x7fffffff
        // then take the modulus.
        hash = (m & 0x7fffffff) % 19;
        System.out.println("hash = " + hash);
        System.out.println("0x7fffffff = " + 0x7fffffff);
        System.out.println((1 << 31) - 1);
        System.out.println("0x7fffffff = " + Integer.toBinaryString(0x7fffffff));
        // 01111111 11111111 11111111 11111111
        
        System.out.println(Integer.MIN_VALUE & 0x7fffffff); // 2147483647 <- Int.MAX_VALUE
        
        // Negative modulus.
        int a = -10;
        int b = 3;
        int c = a % b;
        System.out.println(c);
        int d = a - a/b * b; // definition of modulus.
        System.out.println(d);
        
        int positiveModuls = (a % b + b) % b;
        System.out.println(positiveModuls);
        
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(10, 10);
        map.get(10);
    }
}
