package org.onedigit.study.java.collection;

import java.util.WeakHashMap;

public class WeakHashMapTest
{
	private WeakHashMap<String, String> map = new WeakHashMap<>();
	
	public WeakHashMapTest()
    {
		map.put("A", "AB");
		map.size();
    }
	
	public static void main(String... args)
	{
		new WeakHashMapTest();
	}
}
