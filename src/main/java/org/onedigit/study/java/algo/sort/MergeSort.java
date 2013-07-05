package org.onedigit.study.java.algo.sort;

import java.util.Arrays;

/**
 * Implementation of merge sort.
 * Reference: Introduction to Algorithms,
 *  Cormen, Leiserson, Rivest, Stein, 2nd Edition
 *  
 * @author Ahmed Riza (dr dot riza at gmail.com)
 *
 * @param <T>
 */
public class MergeSort<T extends Comparable<T>>
{
    public void sort(T[] A, int p, int r)
    {
        if (p < r) {
            int q = (p + r) / 2;
            sort(A, p, q);
            sort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }
    
    public void merge(T[] A, int p, int q, int r)
    {
        int n1 = q - p + 1;
        int n2 = r - q;
        // create left and right subarrays
        T[] L = newArray(n1);
        T[] R = newArray(n2);
        for (int i = 0; i < n1; i++) {
            L[i] = A[p + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = A[q + j + 1];
        }
        int i = 0;
        int j = 0;
        for (int k = p; k <= r; k++) {
            if (i < L.length && j < R.length) { 
                if (L[i].compareTo(R[j]) <= 0) {
                    A[k] = L[i];
                    i++;
                } else {
                    A[k] = R[j];
                    j++;
                }
            }
        }
    }
    
    private static <E> E[] newArray(int length, E... array)
    {
        return Arrays.copyOf(array, length);
    }

    public void main(int number)
    {
        // int i = 100/0;
        Double f = 100.00/0.0;
        System.out.println(1 + 2 + "3");
        System.out.println("1" + 2 + 3);
        char ch = 'd';
        if (ch > 3.00) {
            System.out.println("ch > 3.00");
            System.out.println((int)ch);
        }
        byte b = 10;
        // bytes are promoted to int before being added.
        // since the JVM has no instructions for arithmetic on
        // char byte or short. 
        // b = (byte)(b + a);  
        b += 10;
        System.out.println("b = " + b);
        
        float ff = 7/2;
        System.out.println(ff);
        
        final int a = 10;
        final int x = a;
        byte bb = x;
        System.out.println("bb = " + bb);
    }

    public static void main(String... args)
    {
        Integer[] A = {5, 2, 4, 7, 1, 3, 2, 6};
        MergeSort<Integer> sorter = new MergeSort<>();
        sorter.sort(A, 0, A.length - 1);
        System.out.println(Arrays.toString(A));
        
        // sorter.main(10);
    }
    
}
