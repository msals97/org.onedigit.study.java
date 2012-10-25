package org.onedigit.study.java.collection.concurrent.cas;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample
{
	private ConcurrentSkipListMap<Integer, Integer> map;
	
	public ConcurrentSkipListMapExample()
	{
		map = new ConcurrentSkipListMap<>();
	}
}
