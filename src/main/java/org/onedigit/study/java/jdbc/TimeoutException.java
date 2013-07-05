package org.onedigit.study.java.jdbc;

public class TimeoutException extends RuntimeException 
{
    private static final long serialVersionUID = 1;
    
    public TimeoutException () 
    {
        super("Timeout while waiting for a free database connection."); 
    }
    
    public TimeoutException (String msg) 
    {
        super(msg); 
    }
}

