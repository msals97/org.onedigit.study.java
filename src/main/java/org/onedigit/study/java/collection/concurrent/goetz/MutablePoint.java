package org.onedigit.study.java.collection.concurrent.goetz;

public class MutablePoint
{
    public int x, y;
    
    public MutablePoint()
    {
        x = y = 0;
    }
    
    public MutablePoint(MutablePoint p)
    {
        this.x = p.x;
        this.y = p.y;
    }
}
