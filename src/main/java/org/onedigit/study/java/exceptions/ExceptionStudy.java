package org.onedigit.study.java.exceptions;

import java.io.IOException;

public class ExceptionStudy
{
    public static void demoRetrhow() throws IOException 
    {
        try {
            throw new IOException("Error");
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static void main(String[] args)
    {
        try {
            demoRetrhow();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
