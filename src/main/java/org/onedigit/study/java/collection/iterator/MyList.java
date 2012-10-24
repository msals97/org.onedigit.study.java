package org.onedigit.study.java.collection.iterator;

import java.util.Iterator;

public class MyList<E> implements Iterable<E>
{
    class Node
    {
        E item;
        Node next;
        
        public Node(E item)
        {
            this.item = item;
            next = null;
        }
        
        @Override
        public String toString() { return item.toString(); }
    }
    
    private Node head = null;
    private Node tail = null;

    /**
     * Append to the tail of the list
     * @param item
     */
    public void add(E item)
    {
        if (head == null) {
            head = tail = new Node(item);
        } else {
            tail = tail.next = new Node(item);
        }
    }
    
    /**
     * Remove the first node containing item.
     * @param item
     */
    public void remove(E item)
    {
        if (head != null && head.item.equals(item)) {
            Node tmp = head;
            head = head.next;
            tmp = null;
        } else if (head != null) {
            Node current = head;
            Node next = head.next;
            while (next != null) {
                if (next.item.equals(item)) {
                    current.next = next.next;
                    next = null;
                    break;
                } 
                current = next;
                next = next.next;
            }
        }
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node node = head;
        while (node != null) {
            sb.append(node);
            sb.append(" ");
            node = node.next;
        }
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new MyListIterator();
    }    
    
    private class MyListIterator implements Iterator<E>
    {
        private Node current = head;
        
        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public E next()
        {
            E item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    
    public static <E> void iterate(MyList<E> list)
    {
        for (E i : list) {
            System.out.print(i + " ");
        }
        System.out.println();        
    }
    
    public static void main(String... args)
    {
        MyList<Integer> list = new MyList<>();
        list.add(10);
        list.add(50);
        list.add(30);
        
        for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
            System.out.print(it.next() + " ");
        }
        System.out.println();        
        
        iterate(list);

        list.remove(50);
        iterate(list);
        
        list.remove(10);
        iterate(list);
        
        list.remove(30);
        iterate(list);
    }
}
