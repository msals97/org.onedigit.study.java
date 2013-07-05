package org.onedigit.study.java.algo;

/**
 * 
 * @author Ahmed Riza (dr dot riza at gmail.com)
 *
 */
public class Point
{
    private final String name;
    private final double x;
    private final double y;
    
    Point(String name, double x, double y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", x=").append(x).append(", y=").append(y);
        return sb.toString();
    }
    
    public Point subtract(Point another)
    {
        return new Point(this.name, 
                this.x - another.x, this.y - another.y);
    }
    
    public double length(Point p)
    {
        return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
    
    
}
