package org.onedigit.study.java.file;

import java.io.FileInputStream;
import java.io.IOException;

public class ByteStreams
{
    public static void test()
    {
        // use try-with-resource
        try (FileInputStream in = new FileInputStream("numbers.txt")) {
            int c;
            while ( (c = in.read()) != -1) {
                System.out.print(c + " ");
            }            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    
    public static void main(String[] args)
    {
        test();
    }
}
