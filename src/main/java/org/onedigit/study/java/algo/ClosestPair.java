package org.onedigit.study.java.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClosestPair
{
    /**
     * Find the pair such that the Euclidean distance between
     * them is the minimum amongst all the points.
     * 
     * Time complexity: O(n^2).
     * 
     * @param points
     */
    int closestPairOne(int[] points, int p, int r)
    {
        int min = Integer.MAX_VALUE;
        int p1 = 0, p2 = 0;
        for (int i = p; i < r; i++) {
            for (int j = i + 1; j < r; j++) {
                int diff = Math.abs(points[i] - points[j]);
                if (diff < min) {
                    min = diff;
                    p1 = points[i];
                    p2 = points[j];
                }
            }
        }
        // System.out.println("Min distance = " + min + ", p1 = " + p1 + ", p2 = " + p2);
        return min;
    }
    
    /**
     * Recursive, divide and conquer
     * points are assumed to be sorted before entry.
     */
    int closestPairTwo(int[] points, int p, int r)
    {
        if (r - p > 3) {
            int q = (p + r) / 2;
            // System.out.println("p = " + p + ", r = " + r + ", q = " + q);
            // int[] a = Arrays.copyOfRange(points, p, r);
            // System.out.println(Arrays.toString(a));
            int d1 = closestPairTwo(points, p, q);
            int d2 = closestPairTwo(points, q, r);
            // System.out.println("d1 = " + d1 + ", d2 = " + d2 + ", q = " + q);
            // Find the smallest distance across the middle
            int p3 = points[q - 1];
            int p4 = points[q + 1];
            // System.out.println("p3 = " + p3 + ", p4 = " + p4);
            int[] strip = {p3, p4, points[q]};
            int d3 = closestPairOne(strip, 0, 3);
            // System.out.println("d3 = " + d3);
            int d = Math.min(d1, d2);
            return Math.min(d, d3);
        } else {
            // System.out.println("r - p < 1: p = " + p + ", r = " + r);
            int min = closestPairOne(points, p, r);
            // int[] a = Arrays.copyOfRange(points, p, r);
            // System.out.println("p = " + p + ", r = " + r + ": " + Arrays.toString(a));
            // int min = (r - p == 1) ? points[p] : Math.abs(points[p] - points[r - 1]);
            // System.out.println("min = " + min);
            return min;
        }
    }
    
    public int[] generateRandomPoints(int size)
    {
        List<Integer> sequence = new ArrayList<>();
        int N = 10000;
        for (int i = 1; i <= N; i++) {
            sequence.add(i);
        }
        Collections.shuffle(sequence);
        // Generate random points;
        int[] points = new int[size];
        for (int i = 0; i < size; i++) {
            points[i] = sequence.get(i);
        }
        return points;        
    }
    
    public int testBruteForce(int[] points)
    {
        int min1 = closestPairOne(points, 0, points.length);
        return min1;
    }
    
    public int testDivideConquer(int[] points)
    {
        // System.out.println(Arrays.toString(points));
        int min2 = closestPairTwo(points, 0, points.length);
        // System.out.println("global min2 = " + min2);    
        return min2;        
    }

    public static void main(String[] args)
    {
        ClosestPair cp = new ClosestPair();
        // int[] points = {4349, 9586, 6240, 7621, 6289, 5109, 7, 6163, 227, 260, 6137};
        // int[] points = {2, 10, 13, 5, 7, 15};      
        // int [] points = {2, 4, 6, 7, 9, 11};
        int[] points = cp.generateRandomPoints(31);
        int min1 = 0;
        int min2 = 0;
        long N = 1;

        long start = System.nanoTime(); 
        for (long i = 0; i < N; i++) {
            min1 = cp.testBruteForce(points);
        }
        long end = System.nanoTime();
        System.out.println("Elapsed = " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS));
        
        start = System.nanoTime(); 
        Arrays.sort(points);
        for (long i = 0; i < N; i++) {
            min2 = cp.testDivideConquer(points);
        }
        end = System.nanoTime();
        System.out.println("Elapsed = " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS));
        
        if (Math.abs(min1 - min2) > 0) {
            System.out.println("ERROR: min1 = " + min1 + ", min2 = " + min2);
        } else {
            System.out.println("min1 = " + min1 + ", min2 = " + min2);
        }
    }
}
