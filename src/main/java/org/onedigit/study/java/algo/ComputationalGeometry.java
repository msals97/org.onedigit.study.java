package org.onedigit.study.java.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Solution to Problem 33.1-3 of Introduction to Algorithms, 
 * Cormen, Leiserson, Rivest and Stein; 2nd Edition.
 * 
 * @author Ahmed Riza (dr dot riza at gmail.com).
 *
 */
public class ComputationalGeometry
{
    public enum Direction {
        RIGHT, LEFT, COLLINEAR
    }
    
    /**
     * Comparator for sorting according to polar angle.
     */
    static class PointComparator implements Comparator<Point>
    {
        Point reference;
        
        public PointComparator(Point reference)
        {
            this.reference = reference;
        }
        @Override
        public int compare(Point p1, Point p2)
        {
            int result = 0;
            double cp = crossProduct(p1, p2, reference);
            // If the cross product is > 0, then the point p2 is 
            // anti-clockwise from the reference point p1.
            if (cp > 0) {
                result = -1;
            } else if (cp < 0) {
                result = 1;
            } 
            return result;
        }
    }
    
    public static double crossProduct(Point a, Point b, Point reference)
    {
        // Subtract reference point from from a and b.
        Point aa = a.subtract(reference);
        Point bb = b.subtract(reference);
        // Calculate the determinant 
        double cp = aa.getX() * bb.getY() - bb.getX() * aa.getY();
        return cp;
    }
   
    /**
     * Determines whether the line segments p1-p2-p3 makes
     * a left or right turn at their common point p2.
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return Direction.LEFT or Direction.RIGHT 
     */
    public static Direction direction(Point p1, Point p2, Point p3)
    {
        Direction result = Direction.COLLINEAR;
        double cp = crossProduct(p2, p3, p1);
        if (cp < 0.0) {
            result = Direction.RIGHT;
        } else if (cp > 0.0) {
            result = Direction.LEFT;
        }
        return result;
    }
    
    public static void sortByPolarAngle(List<Point> points, Point reference)
    {
        Collections.sort(points, new PointComparator(reference));
    }
    
    // --------------  TEST methods -----------------------------------------
    
    public static void testCrossProduct()
    {
        Point p1 = new Point("p1", 1.0, 1.0);
        Point p2 = new Point("p2", 0.0, 1.0);
        Point origin = new Point("origin", 0.0, 0.0);
        double cp = crossProduct(p1, p2, origin);
        // Positive cross-product means p2 is anticlockwise from p1
        // with respect to the origin point.
        System.out.println("cp = " + cp);
    }
    
    public static List<Point> generateTestPoints()
    {
        // Generate points on a circle of radius r, centered at the origin.
        // x^2 + y^2 = r. x = r*cos(t), y = r*sin(t).
        List<Point> points = new ArrayList<>();
        double r = 1.0;
        int k = 0; // for naming the points.
        for (int i = 0; i <= 360; i += 10) {
            double t = (i)* Math.PI / 180.0;
            double x = r * Math.cos(t);
            double y = r * Math.sin(t);
            String name = "Point-" + k;
            Point p = new Point(name, x, y);
            points.add(p);
            k++;
        }
        return points;
    }
    

    
    public static void main(String... args)
    {
        ComputationalGeometry.testCrossProduct();
        List<Point> points = ComputationalGeometry.generateTestPoints();
        Point reference = new Point("origin", 0.0, 0.0);
        ComputationalGeometry.sortByPolarAngle(points, reference);
        for (Point p : points) {
            System.out.println("p: " + p); 
        }
    }
}
