package org.onedigit.study.java.generics;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Compare
{
	public static <E extends Comparable<E>> E max(Collection<E> collection)
	{
		Iterator<E> it = collection.iterator();
		E maxItem = null;
		if (it.hasNext()) {
			maxItem = it.next();
			while (it.hasNext()) {
				E next = it.next();
				if (next.compareTo(maxItem) > 0) {
					maxItem = next; 
				}
			}
		}
		
		return maxItem;
	}
	
	public <E extends Comparable<E>> int compare(E first, E second)
	{
		return (first.compareTo(second));
	}
	
	class A implements Comparable<A>
	{
		protected final int item;
		public A(int item)
		{
			this.item = item;
		}
		@Override
		public int compareTo(A other)
        {
	        return this.item - other.item;
        }
	}
	
	class B implements Comparable<A>
	{
		@Override
        public int compareTo(A o)
        {
	        // TODO Auto-generated method stub
	        return 0;
        }
	}
	
	public static void main(String[] args)
	{
		List<Integer> list = Arrays.asList(20, 5, 10, 90, 30, 7, 50);
		Integer maxItem = max(list);
		System.out.println(Collections.max(list));
		System.out.println(maxItem);
	}
}
