package org.onedigit.study.java.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyList<T>
{
    class Node
    {
        T value;
        Node next;
        
        Node(T value)
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
    
    private Node head;
    private Node tail;
    
    /**
     * Add to the end of the list.
     * @param value
     */
    public void add(T value)
    {
        if (head == null) {
            head = tail = new Node(value);
        } else {
            tail = tail.next = new Node(value);
        }
    }
    
    /**
     * Return the m-th to last node.
     * If m = 0, return the last node.
     * @param m
     * @return
     */
    public Node mthToLastSlow(int m)
    {
        if (m == 0) {
            return tail;
        } else {
            Node p = head;
            while (p != null) {
                Node q = p;
                for (int k = 0; k <= m; k++) {
                    if (q != null) {
                        if (q == tail && k == m) {
                            return p;
                        } else {
                            q = q.next;
                        }
                    }
                }
                p = p.next;
            }
        }
        return null;
    }
    
    public Node mthToLast(int m)
    {
        Node result = null;
        if (m == 0) {
            return tail;
        } else {
            Node p = head;
            // advance p, m positions
            for (int k = 0; k < m; k++) {
                if (p.next != null) {
                    p = p.next;
                } else {
                    return null;
                }
            }
            Node q = head;
            while (p.next != null) {
                p = p.next;
                q = q.next;
            }
            result = q;
        }
        return result;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node p = head;
        while (p != null) {
            sb.append(p).append(" ");
            p = p.next;
        }
        return sb.toString();
    }
    
    public static void main(String[] args)
    {
        MyList<Integer> list = new MyList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        System.out.println(list);
        int n = 2;
        System.out.println(list.mthToLastSlow(n));
        System.out.println(list.mthToLast(n));
    }
}
