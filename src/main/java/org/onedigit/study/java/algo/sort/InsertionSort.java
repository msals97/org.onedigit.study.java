package org.onedigit.study.java.algo.sort;

import java.util.Arrays;

/**
 * Insertion Sort
 * Reference: Introduction to Algorithms,
 * Cormen, Leiserson, Rivest, Stein, 2nd Edition.
 * 
 * @author Ahmed Riza (dr dot riza at gmail.com)
 *
 * @param <T>
 */
public class InsertionSort<T extends Comparable<T>>
{
    public void sort(T[] A)
    {
        for (int j = 1; j < A.length; j++) {
            T key = A[j];
            int i = j - 1;
            while (i >= 0 && A[i].compareTo(key) > 0) {
                A[i+1] = A[i];
                i--;
            }
            A[i + 1] = key;
        }
    }
    
    public static void main(String[] args)
    {
        int len = 20;
        Integer[] A = new Integer[len];
        for (int i = 0; i < len; i++) {
            // Generate random numbers between 1 and 10
            A[i] = (int) (Math.random()*10 + 1);
        }
        System.out.println(Arrays.toString(A));
        
        InsertionSort<Integer> sorter = new InsertionSort<>();
        sorter.sort(A);
        System.out.println(Arrays.toString(A));
    }
}
