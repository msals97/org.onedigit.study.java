package org.onedigit.study.java.collection.cow;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWrite
{
	private CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>();
	private CopyOnWriteArraySet<Integer> cowSet = new CopyOnWriteArraySet<>();
	
	public CopyOnWrite()
	{
		Integer[] a = {10, 50, 20, 30, 70};
		cowList.addAll(Arrays.asList(a));
		cowSet.addAll(Arrays.asList(a));
		print(cowList);
		print(cowSet);
	}
	
	private <E> void print(Collection<E> collection)
	{
		for (E i : collection) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public static void main(String... args)
	{
		new CopyOnWrite();
	}
}
