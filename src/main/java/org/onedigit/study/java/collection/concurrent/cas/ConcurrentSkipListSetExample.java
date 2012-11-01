package org.onedigit.study.java.collection.concurrent.cas;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.onedigit.study.java.TimerUtility;

public class ConcurrentSkipListSetExample
{
    private static final int MAX = 1_000_000;
    TimerUtility timer = new TimerUtility();
    
    private void skipListSetTest()
    {
        timer.start();
        ConcurrentSkipListSet<Integer> set =  new ConcurrentSkipListSet<>();
        for (int i = 0; i < MAX; i++) {
            set.add(i);
        }
        double elapsed = timer.end();
        System.out.println("SkipListSet, elapsed = " + elapsed);
    }

    private void treeSetTest()
    {
        timer.start();
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < MAX; i++) {
            set.add(i);
        }        
        double elapsed = timer.end();
        System.out.println("TreeSet, elapsed = " + elapsed);
    }
	
	public ConcurrentSkipListSetExample()
	{
	    skipListSetTest();
	    treeSetTest();
	}
	
	public static void main(String... args)
	{
	    new ConcurrentSkipListSetExample();
	}
}
