package org.onedigit.study.java.collection;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Random;

public class WeakList<E> implements Iterable<E>
{
	private ReferenceQueue<E> queue = new ReferenceQueue<>();
	private Random rand = new Random();
	
	private class Node extends PhantomReference<E>
	{
		private E item;
		private Node next;
		private final int id;
		
		public Node(E item)
		{
			super(item, queue);
			this.item = item;
			this.id = rand.nextInt();
		}
		
		public void clear()
		{
			this.item = null;
			this.next = null;
		}
		
		@Override
		public String toString() 
		{
			StringBuilder sb = new StringBuilder();
			sb.append(id);
			return sb.toString();
		}
	}
	
	private Node head = null;
	private Node tail = null;
	private int count = 0;
	
	public void add(E item)
	{
		if (head == null) {
			head = tail = new Node(item);
		} else {
			tail = tail.next = new Node(item);
		}
		count++;
	}
	
	public void remove(E item)
	{
		// checkExpired();
		if (head.item.equals(item)) {
			System.out.println("Removing from head...");
			Node tmp = head.next;
			head.clear();
			head = null;
			head = tmp;
			count--;
		} else {
			System.out.println("Removing from middle...");
			Node current = head;
			Node next = head.next;
			while (next != null) {
				if (next.item.equals(item)) {
					current.next = next.next;
					next.clear();
					next = null;
					count--;
					break;
				}
				current = next;
				next = next.next;
			}
		}
	}
	
	public int size()
	{
		return count;
	}
	
	private void checkExpired()
	{
		Object ref = null;
		while ( (ref = queue.poll()) != null) {
			System.out.println("Reference Queue entries:");
			System.out.println("\t" + ref);
		}
	}
		
	public Iterator<E> iterator()
	{
		return new WeakListIterator();
	}
	
	private class WeakListIterator implements Iterator<E>
	{
		Node current = head;
		Node prev = head;
		
		@Override
        public boolean hasNext()
        {
			return current != null;
        }

		@Override
        public E next()
        {
	        E item = current.item;
	        prev = current;
	        current = current.next;
	        return item;
        }

		@Override
        public void remove()
        {
			WeakList.this.remove(prev.item);
        }
	}
	
	private static <E> void iterate(WeakList<E> list)
	{
		for (E item : list) {
			System.out.print(item + " ");
		}
		System.out.println();
	}
	
	private static <E> void remove(WeakList<E> list, E item)
	{
		for (Iterator<E> it = list.iterator(); it.hasNext(); ) {
			if (it.next().equals(item)) {
				it.remove();
			}
		}		
	}
	
	public static <E> void add(WeakList<E> list, E... items)
	{
		for (E item : items) {
			list.add(item);
		}
	}
	
	public static void main(String... args)
	{
		WeakList<Integer> list = new WeakList<>();

		Integer[] ints = {new Integer(1), new Integer(2), new Integer(3)};
		add(list, ints);
		iterate(list);
		remove(list, 3);
		remove(list, 2);
		// remove(list, 1);

        // Create some garbage
        for (int i = 0; i < 1_000_000; i++) {
        	String s = new String(i + "");
        }
        
        list.checkExpired();
        System.out.println("Count = " + list.size());
	}
}
