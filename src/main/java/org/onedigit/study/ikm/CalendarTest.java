package org.onedigit.study.ikm;

import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

public class CalendarTest
{
    public static void main(String[] args)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.JANUARY, 30, 0, 0, 0);
        System.out.println(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cal.getTime());
        
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
    }
}
