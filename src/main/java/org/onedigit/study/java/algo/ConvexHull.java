package org.onedigit.study.java.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Calculate the convex hull of a set of points in the plane.
 * 
 * @author ahmed
 *
 */
public class ConvexHull
{
    /**
     * A comparator used to sort points according to their Y coordinate
     */
    private static class YComparator implements Comparator<Point>
    {
        @Override
        public int compare(Point p1, Point p2)
        {
            int result = 0;
            double diff = p1.getY() - p2.getY();
            if (diff > 0) {
                result = 1;
            } else if (diff < 0) {
                result = -1;
            }
            return result;
        }        
    }
    
    /**
     * Graham Scan algorithm.
     * Time complexity: O(n log n)
     * @param points
     * @return
     */
    public Stack<Point> grahamScan(List<Point> points)
    {
        // Find the point with the minimum y-coordinate.
        Collections.sort(points, new YComparator());
        // get the point with minimum Y coordiante
        Point p0 = points.get(0); 
        // sort the remaining points by polar angle using p0 as reference.
        List<Point> rest = points.subList(1, points.size());      
        ComputationalGeometry.sortByPolarAngle(rest, p0);
        
        // Push points on to stack
        Stack<Point> stack = new Stack<>();
        stack.push(p0);
        stack.push(rest.get(0));
        stack.push(rest.get(1));
        
        for (int i = 2; i < rest.size(); i++) {
            Point p = rest.get(i);
            ComputationalGeometry.Direction direction = getDirection(stack, p);
            while (ComputationalGeometry.Direction.LEFT != direction) {
                stack.pop();
                direction = getDirection(stack, p);
            }
            stack.push(p);
        }
        return stack;
    }
    
    /**
     * Determine the direction of the turn at the point top, on the
     * line segment nextToTop-top-p
     * @param stack
     * @param p
     * @return
     */
    private ComputationalGeometry.Direction getDirection(Stack<Point> stack, Point p)
    {
        int topIndex = stack.size() - 1;
        Point top = stack.get(topIndex);
        Point nextToTop = stack.get(topIndex - 1);
        // Determine the angle made by the line segment, nextToTop-top-p
        ComputationalGeometry.Direction direction = 
                ComputationalGeometry.direction(nextToTop, top, p);
        return direction;
    }
    
    // ------------------------------------------------------------------------
    
    private void printPoints(List<Point> points)
    {
        for (Point p : points) {
            System.out.println(p);
        }
    }

    public List<Point> testPoints()
    {
        /*
        0: 37.437324,-122.137892
        1: 37.431451,-122.173139
        2: 37.45268,-122.127186
        3: 37.437223,-122.127406
        4: 37.442207,-122.109955
        5: 37.455329,-122.169286
        6: 37.422156,-122.138933
        7: 37.431383,-122.147224
        8: 37.458405,-122.104107
        9: 37.454891,-122.106883
        */
        // Convex Hull
        /*
        37.431451,-122.173139
        37.422156,-122.138933
        37.442207,-122.109955
        37.458405,-122.104107
        37.455329,-122.169286
        37.431451,-122.173139
        */
        
        List<Point> points = new ArrayList<>();
        Point p0 = new Point("p0", 37.437324,-122.137892);
        Point p1 = new Point("p1", 37.431451,-122.173139);
        Point p2 = new Point("p2", 37.45268,-122.127186); 
        Point p3 = new Point("p3", 37.437223,-122.127406);
        Point p4 = new Point("p4", 37.442207,-122.109955);
        Point p5 = new Point("p5", 37.455329,-122.169286);
        Point p6 = new Point("p6", 37.422156,-122.138933);
        Point p7 = new Point("p7", 37.431383,-122.147224);
        Point p8 = new Point("p8", 37.458405,-122.104107);
        Point p9 = new Point("p9", 37.454891,-122.106883);
        points.add(p0);
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        points.add(p9);
        return points;
    }
    
    public static void main(String[] args)
    {
        ConvexHull ch = new ConvexHull();
        List<Point> points = ch.testPoints();
        Stack<Point> hull = ch.grahamScan(points);
        System.out.println("Convex hull:");
        for (Point p : hull) {
            System.out.println(p);
        }
    }
}
