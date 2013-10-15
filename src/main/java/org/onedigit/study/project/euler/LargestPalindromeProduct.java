package org.onedigit.study.project.euler;

import org.onedigit.study.util.Clock;

/**
 * A palindromic number reads the same both ways. The largest palindrome made
 * from the product of two 2-digit numbers is 9009 = 91 x 99.
 *
 * Find the largest palindrome made from the product of two 3-digit numbers.
 * 
 * @author ahmed
 *
 */
public class LargestPalindromeProduct
{
    /**
     * For products of 3 digits, answer = 906609.
     * @return
     */
    public static int highestProductThreePalindrome()
    {
        int result = Integer.MIN_VALUE;
        for (int i = 1; i < 1000; i++) {
            for (int j = i + 1; j < 1000; j++) {
                int prod = i * j;
                if (isPalindrome(prod)) {
                    if (prod > result) {
                        result = prod;
                    }
                }
            }
        }
        return result;
    }
        
    public static int reverse(int n)
    {
        int result = 0;
        while (n != 0) {
            result = 10 * result + n % 10;
            n /= 10;
        }
        return result;
    }
    
    public static boolean isPalindrome(int n)
    {
        int reverse = reverse(n);
        return n == reverse;
    }
    
    /**
     * For products of 2 digits, answer = 9009.
     * @return
     */
    public static int highestProductTwoPalindrome()
    {
        int result = 0;
        for (int i = 1; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                int prod = i * j;
                if (isPalindrome(prod)) {
                    result = prod;
                }
            }
        }
        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(isPalindrome(9119));
        System.out.println(highestProductTwoPalindrome());
        int ans = 0;
        Clock clock = new Clock();
        for (int i = 0; i < 100; i++) {
            ans = highestProductThreePalindrome();
        }
        System.out.println(clock.getElapsed() / 100.0 + " ms");
        System.out.println(ans);
    }
}
