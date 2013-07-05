package org.onedigit.study.java.algo;

import java.util.Calendar;
import java.util.Date;

public class BabyBoomer
{
    private Date dateOfBirth;
    private static final Date BOOM_START; 
    private static final Date BOOM_END;
    
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
        BOOM_START = calendar.getTime();
        calendar.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        BOOM_END = calendar.getTime();
    }
    
    public BabyBoomer(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }
    
    public boolean isBabyBoomer()
    {
        boolean result = false;
        result = (dateOfBirth.compareTo(BOOM_START) >= 0) &&
                (dateOfBirth.compareTo(BOOM_END) < 0);
        return result;
    }
    
    public static void main(String[] args)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(1950,  Calendar.MARCH, 31);
        BabyBoomer b1 = new BabyBoomer(cal.getTime());
        System.out.println("b1: " + b1.isBabyBoomer());
        
        cal.set(1964,  Calendar.DECEMBER, 31);
        BabyBoomer b2 = new BabyBoomer(cal.getTime());
        System.out.println("b2: " + b2.isBabyBoomer());
    }
}
