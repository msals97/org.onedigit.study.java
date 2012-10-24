package org.onedigit.study.java.generics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WildCard
{
	public static void add(List<? super Integer> list)
	{
		Integer i = 10;
		list.add(i);
		Number n = 20;
		// This will  not work.
		// We don't know the actual type of list, it could be a list
		// of Integer, Number of Object, so we can only add Integer
		// 
		// E.g. if it's a list of Numbers, then it will be a mistake
		// to add Objects to it, since Object is not a sub-type of Number.
		// list.add(n); 
		
		List<Number> nList = new ArrayList<>();
		//  Now we can add all subtypes of Number to nList
		nList.add(i);
		double d = 3.1415;
		nList.add(d);
	}
	
	public static <T> void add(List<? super T> list, T... a)
	{
		for (T t : a) {
			list.add(t);
		}
		System.out.println(list);
	}
	
	public static <T> void addToSet(Set<T> s, T t)
	{
	}
	
	public static <T> void readOnlySet(Set<T> s)
	{
		for (T t : s) {
			System.out.println(t);
		}
	}
	
	public static void main(String... args)
	{
		List<Number> numbers = new ArrayList<>();
		Double[] a = {1.0, 2.0, 3.0};
		add(numbers, a);
		
		Set<?> unknownSet = new HashSet<String>();
		
		// This won't work
		// addToSet(unknownSet, "abc");
		
		// This will work
		readOnlySet(unknownSet);
	}
}
