package org.onedigit.study.ikm;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class IKMDistributed
{
    static class SuperClass
    {
        int index = 1;
        public void print() { System.out.println("Superclass"); }
    }
    static class SubClass extends SuperClass
    {
        int index = 2;
        public void print() { System.out.println("Sublass"); }
    }
    
    public boolean equals(Object obj)
    {
        if (obj instanceof IKMDistributed) {
            return this == obj;
        } else {
            return false;
        }
    }
    
    // upto 3 out of 5 correct answers
    private int parent;
    
    interface PatientRecord
    {
        String getRegionalKey();
    }
    
    DayRecord dr = new DayRecord();
    public void f()
    {
        int t = dr.patient;
    }
    
    class DayRecord implements PatientRecord
    {
        private int patient;
        
        @Override
        public String getRegionalKey()
        {
            parent = 1;
            patient = 2;
            return null;
        }

        private String getRegionalKey(String test)
        {
            return null;
        }
    }
    
    static class Helper 
    {
        public Helper()
        {
            
        }
        
        public Helper(int d)
        {
            this();
        }
        
        private int data = 5;
        public void bump(int inc) {
            inc++;
            data = data + inc;
        }
    }
    
    static class Interest
    {
        int calc(BigDecimal amount, double rate, long months)
        {
            // return (int) (amount * rate * months / 100);
            return 0;
        }
    }
    
    static class Invoice
    {
        public static String format(String old)
        {
            return old + "_Invoice";
        }
    }
    
    static class SalesInvoice extends Invoice
    {
        public static String format(String old)
        {
            return old + "_SalesInvoice";
        }        
    }
    
    public static void print(String line)
    {
        System.out.println(line);
    }
    
    public static void main(String[] args)
    {
        System.out.println(String.format("%tH:%tM:%tS", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance()));
        System.out.println(String.format("%tT", Calendar.getInstance()));
        
        Invoice invoice = new Invoice();
        // System.out.println((SalesInvoice)Invoice.format("1234"));
        
        Interest interest = new Interest();
        BigDecimal amount = new BigDecimal(100.00);
        double rate = 1.6f;
        double totalInterest = interest.calc(amount, rate, 12);
        System.out.println(totalInterest);
        
        double d = -27.2345;
        System.out.println(Math.ceil(d));
        System.out.println(Math.round(d));
        System.out.println(Math.abs(d));
        System.out.println(Math.floor(d));
        
        Helper h = new Helper();
        int data = 2;
        h.bump(data);
        System.out.println(h.data + " " + data);
        
        System.out.println("--------");
        
        int x = 5, y = 0;
        try {
            try {
                System.out.println(x);
                System.out.println(x/y);
                System.out.println(y);
            } catch (ArithmeticException e) {
                System.out.println("Inner catch 1");
                throw e;
            } catch (RuntimeException ex) {
                System.out.println("Inner catch 2");
            } finally {
                System.out.println("Inner finally");
            }
        } catch (Exception ex) {
            System.out.println("Outer");
        }
        
        System.out.println("----------");
        int t = -1;
        t = t >>> 1;
        System.out.println(t);
        
        int[] a = {1, 2, 3};
        int[] b = a.clone();
        b[0] = 2;
        System.out.println(Arrays.toString(a));
        
        SuperClass superclass = new SubClass();
        superclass.print();
        System.out.println(superclass.index);
        
        Date aDate = null;
        try {
            aDate = new SimpleDateFormat("yyyy-mm-dd").parse("2012-01-15");
            Calendar cal = Calendar.getInstance();
            cal.setTime(aDate);
            System.out.println(cal.get(Calendar.MONTH));
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        StringBuilder sb = new StringBuilder("buffering");
        sb.replace(2, 7, "BUFFER");
        System.out.println(sb);
        sb.delete(2, 4);
        System.out.println(sb);
        String s = sb.substring(1, 5);
        System.out.println(s);
    }
}
