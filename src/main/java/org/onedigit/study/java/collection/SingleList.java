package org.onedigit.study.java.collection;

public class SingleList<E>
{
	private class Node
	{
		E value;
		Node next;
		
		public Node(E value)
		{
			this.value = value;
			this.next = null;
		}
		
		@Override
		public String toString()
		{
			return value.toString();
		}
	}

	private Node head = null;
	private Node tail = null;
	
	public void add(E value)
	{
		if (head == null) {
			head = tail = new Node(value);
		} else {
			tail = tail.next = new Node(value);
		}
	}
	
	public void reverse()
	{
		Node current = head;
		Node prev = null;
		while (current != null) {
			Node next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		Node tmp = head;
		head = tail;
		tail = tmp;
	}
	
	public boolean remove(E value)
	{
		boolean result = false;
		
		if (head == null) {
			return result;
		}
		Node prev = null;
		Node current = head;
		Node next = current.next;
		while (current != null) {
			if (current.value.equals(value)) {
				current.next = null;
				if (prev != null) {
					prev.next = next;
				}
				if (current == head) {
					head = next;
				}
				if (current == tail) {
					tail = next;
				}
				result = true;
				break;
			}
			prev = current;
			current = next;
			next = current.next;
		}
		return result;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		Node node = head;
		sb.append("[");
		while (node != null) {			
			sb.append(node.value);
			if (node.next != null) {
				sb.append(", ");
			}
			node = node.next;
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void main(String... args)
	{
		SingleList<Integer> sList = new SingleList<>();
		sList.add(20);
		sList.add(10);
		sList.add(30);
		sList.add(50);
		System.out.println(sList);
		sList.reverse();
		System.out.println(sList);
		boolean ret = sList.remove(20);
		assert(ret == true);
		ret = sList.remove(30);
		assert(ret == true);
		ret = sList.remove(10);
		assert(ret == true);
		ret = sList.remove(50);
		assert(ret == true);
		ret = sList.remove(10);
		assert(ret == false);
		System.out.println(sList);
		sList.reverse();
		System.out.println(sList);
	}
}
