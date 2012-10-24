package org.onedigit.study.java.generics;

import java.util.HashSet;
import java.util.Set;

public class WildCardCapture
{
	public static <T> void readOnly(Set<T> set, T t)
	{

	}
	
	public static void main(String[] args)
	{
		Set<?> anon = new HashSet<String>();
		// readOnly(anon, "abc");
	}
}
