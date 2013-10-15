package org.onedigit.study.project.euler;

import java.util.ArrayList;
import java.util.List;

/**
 * The prime factors of 13195 are 5, 7, 13, and 29.
 * What is the largest prime factor of the number 600851475143
 * 
 * @author ahmed
 *
 */
public class LargestPrimeFactor
{
    public static List<Long> factors(long n)
    {
        List<Long> list = new ArrayList<>();
        long d = 2;
        while (n > 1) {
            while ((n % d) == 0) {
                list.add(d);
                n /= d;
            }
            d++;
            if (d * d > n && n > 1) {
                list.add(n);
                break;
            }
        }
        return list;
    }
    
    public static List<Long> primeFactors(long n)
    {
        List<Long> list = new ArrayList<>();
        long d = 2;
        while (d*d < n) {
            while (n % d == 0) {
                list.add(d);
                n /= d;
            }
            d++;
        }
        list.add(n);
        return list;
    }
    
    public static void main(String[] args)
    {
        List<Long> list = primeFactors(600851475143L);
        // List<Long> list = factors(13195);
        
        long max = 0;
        for (Long l : list) {
            if (l > max) {
                max = l;
            }
        }
        System.out.println(list);
        System.out.println("max factor = " + max);
    }
}
