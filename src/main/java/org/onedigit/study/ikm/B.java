package org.onedigit.study.ikm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

class A
{
    public void m1()
    {
        
    }
    
    protected void m2()
    {
        
    }
    
    private void m3()
    {
    }
    
    void m4()
    {
        
    }
}

public class B
{
    public static void main(String... args)
    {
        /*
        A a = new A();
        a.m1();
        a.m2();
        // a.m3(); error
        a.m4();
        */
        // throw new NoSuchElementException();
        
        int j = 0;
        int a[] = {2, 4};
        do for (int i : a) 
            System.out.print(i + " ");
        while (j++ < 1);
        
        System.out.println();
        
        Object o = null;
        if (o instanceof Object) {
            System.out.println("instance of object");
        }
        
        Map<String, Integer> names = Calendar.getInstance().getDisplayNames(Calendar.DAY_OF_WEEK,  Calendar.LONG, Locale.ENGLISH);
        System.out.println(names);
        try {
            FileOutputStream fos = new FileOutputStream("test.txt");
            Writer out;
            out = new OutputStreamWriter(fos, "UTF-32");
            out.write(names.toString());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        boolean aa = true, b = false, c = true, d = false;
        System.out.println("----");
        if (aa = c == b) {
            b = false;
        }
        System.out.println(aa);
        System.out.println("----");
        if (true == d != false) {
            System.out.println(" d = " + d);
            c = d;
        }
        System.out.println(aa);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        
        System.out.println("----");
        System.out.println("----");
        System.out.println(Math.round(123456789123456789.12f));
        System.out.println(Integer.MAX_VALUE);
    }
}
