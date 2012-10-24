package org.onedigit.study.java.collection.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Merge
{
	public static <E extends Comparable<? super E>> List<E> merge(
			Collection<E> first, Collection<E> second)
	{
		List<E> merged = new ArrayList<>();
		
		Iterator<E> firstIt = first.iterator();
		Iterator<E> secondIt = second.iterator();
		
		E firstItem = getNextElement(firstIt);
		E secondItem = getNextElement(secondIt);
		while (firstItem != null && secondItem != null) {
			if (firstItem.compareTo(secondItem) < 0) {
				merged.add(firstItem);
				firstItem = getNextElement(firstIt);
			} else if (firstItem.compareTo(secondItem) > 0) {
				merged.add(secondItem);
				secondItem = getNextElement(secondIt);
			}
		}
		return merged;
	}
	
	public static <E> E getNextElement(Iterator<E> it)
	{
		E element = null;
		if (it.hasNext()) {
			element = it.next();
		}
		return element;
	}
	
	public static void main(String[] args)
	{
		List<Integer> first = Arrays.asList(2, 10, 15, 30);
		List<Integer> second = Arrays.asList(20, 25, 50);
		List<Integer> merged = merge(first, second);
		System.out.println(merged);
	}
}
