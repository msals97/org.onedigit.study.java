package org.onedigit.study.java.collection.sync;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SyncWrapper
{
	private Set<Integer> set;
	
	public SyncWrapper()
	{
		set = Collections.synchronizedSet(new HashSet<Integer>());
		
		// Collections.synchronizedCollection(c);
		// Collections.synchronizedList(list);
		// Collections.synchronizedMap(m);
		// Collections.synchronizedSet(s);
		// Collections.synchronizedSortedMap(m);
		// Collections.synchronizedSortedSet(s);
		
		set.add(1);
	}
	
	public static void main(String... args)
	{
		new SyncWrapper();
	}
}
