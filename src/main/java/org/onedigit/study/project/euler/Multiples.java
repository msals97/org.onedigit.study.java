package org.onedigit.study.project.euler;

public class Multiples
{
    /**
     * Find the sum of the natural numbers below n that are multiples of 3 
     * and 5.
     * 
     * @param n
     */
    private static int multiplesOfThreeFive(int n)
    {
       int sum = 0;
       for (int i = 1; i < n; i++) {
           if (i % 3 == 0 || i % 5 == 0) {
               sum += i;
           }
       }
       return sum;
    }
    
    public static void main(String[] args)
    {
        System.out.println(multiplesOfThreeFive(10));
        System.out.println(multiplesOfThreeFive(1000));
    }
}
