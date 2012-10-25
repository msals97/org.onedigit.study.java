package org.onedigit.study.java.collection;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Random;

public class WeakList<E> implements Iterable<E>
{
	private ReferenceQueue<E> queue = new ReferenceQueue<>();
	private Random rand = new Random();
	
	private class Node extends WeakReference<E>
	{
		private Node next = null;
		private final String id;
		
		public Node(E item)
		{
			super(item, queue);
			this.id = String.valueOf(item);
			this.next = null;
		}
		
		public void clear()
		{
		    // this.enqueue();
		    E item = get();
		    item = null;
			this.next = null;
		}
		
		public E getItem()
		{
		    return get();
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
	// private Node tail = null;
	private int count = 0;
	
	public void add(E item)
	{
		if (head == null) {
		    System.out.println("Adding to head");
		    // head = tail = new Node(item);
		    head = new Node(item);
		} else {
		    System.out.println("Appending to head");
			// tail = tail.next = new Node(item);
			Node node = new Node(item);
			node.next = head;
			head = node;
		}
		count++;
	}
	
	public void remove(E item)
	{
	    if (head.getItem().equals(item)) {
			System.out.println("Removing from head... " + head.getItem());
	        /*
			Node tmp = null;
			if (head.next != null) {
			    System.out.println("Head next is not null");
			    tmp = head.next;
			}
			head.clear();
			// head = null;
			// head = tmp;
	        */
			count--;
		} else {
			Node current = head;
			Node next = head.next;
			while (next != null) {
				if (next.getItem().equals(item)) {
		            System.out.println("Removing from middle... " + item);
		            // current.next = next.next;
		            /*
					next.clear();
					next = null;
					count--;
					break;
					*/
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
	        E item = current.getItem();
	        prev = current;
	        current = current.next;
	        return item;
        }

		@Override
        public void remove()
        {
			WeakList.this.remove(prev.getItem());
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
	
	private void checkExpired()
	{
	    Object ref = null;
	    while (true) {
	        createGarbage();
	        // System.out.println("Creating garbage...");
	        while ( (ref = queue.poll()) != null) {
	            System.out.println("Reference Queue entries:");
	            System.out.println("\t" + ref);
	        }
	        // System.out.println("Sleeping...");
	        try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
	    }
	}

	public static void createGarbage()
	{
        for (int i = 0; i < 1_000_000; i++) {
            String s = new String(i + "");
        }
        /*
        for (int i = 0; i < 100; i++) {
            System.gc();
        }
        */
	}
	
	public static void main(String... args)
	{
		WeakList<Integer> list = new WeakList<>();
		Integer one = new Integer(1);
		Integer two = new Integer(2);
        // Integer[] ints = {one};
        // add(list, ints);
		list.add(one);
		list.add(two);
		iterate(list);
		one = null;
		two = null;
		// list.remove(1);
		// list.remove(2);
		// list.remove(3);
        list.checkExpired();
        System.out.println("Count = " + list.size());
        iterate(list);
	}
}
