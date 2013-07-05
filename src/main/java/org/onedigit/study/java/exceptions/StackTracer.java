package org.onedigit.study.java.exceptions;

public class StackTracer
{
    public void trace()
    {
        for (StackTraceElement ste : new Exception().getStackTrace()) {
            System.out.println(ste);
        }        
    }
    
    public void f()
    {
        g();
    }
    
    public void g()
    {
        h();
    }
    
    public void h()
    {
        trace();
    }
    
    public static void main(String... args)
    {
        new StackTracer().f();
    }
}
