package org.onedigit.study.java.algo;

import java.util.Arrays;

public class ClosestPair2D
{
    private static class Point
    {
        int x, y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public String toString()
        {
            return "(" + x + "," + y + ")";
        }        
    }
    
    public int square(int x)
    {
        return x * x;
    }
    
    public double distance(Point p1, Point p2)
    {
        return Math.sqrt(square(p1.x - p2.x) + square(p1.y - p2.y));
    }
    
    public double bruteForce(Point[] points)
    {
        int len = points.length;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                min = Math.min(min, distance(points[i], points[j]));
            }
        }
        return min;
    }
    
    public static void main(String[] args)
    {
        Point[] points = new Point[] {
                new Point(2, 3), new Point(12, 30), new Point(40, 50),
                new Point(5, 1), new Point(12, 10),  new Point(3, 4)
        };
        System.out.println(Arrays.toString(points));
        
        ClosestPair2D cp = new ClosestPair2D();
        double d1 = cp.bruteForce(points);
        System.out.println("d1 = " + d1);
    }
}
