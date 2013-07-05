package org.onedigit.study.java.algo;

/**
 * Given a string in the form of a linked list, check whether the string is a palindrome
 * or not.  Don't use extra memory.  Give the time complexity.
 * 
 * @author ahmed
 *
 */
public class Palindrome
{
    class Node
    {
        char data;
        Node next;
        
        public Node(char data, Node next)
        {
            this.data = data;
            this.next = next;
        }
    }

    Node head = null;
    
    public Palindrome()
    {
    }
    
    public boolean isPalindrome()
    {
        boolean result = true;
        Node p = head, q = head;
        // Find the length of the string.
        int len = 0;
        while (q != null) {
            q = q.next;
            len++;
        }
        // Start comparing p and q nodes. We start with q placed at the end 
        // of the string. At each iteration, we move q one step backwards.
        // We do that by resetting q to the head of the list, and advancing it
        // as in the following loop (because we have a singly linked list).
        for (int i = len - 1; i >= len/2; i--) {
            q = head;
            for (int j = 0; j < i; j++) {
                q = q.next;
            }
            // compare the current p and q node values
            if (p.data != q.data) {
                result = false;
                break;
            }
            p = p.next;
        }
        return result;
    }
    
    public void add(String s)
    {
        Node n = new Node(s.charAt(0), head);
        head = n;
        int len = s.length();
        for (int i = 1; i < len; i++) {
            n = new Node(s.charAt(i), n);
            head = n;
        }
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node p = head;
        while (p != null) {
            sb.append(p.data);
            p = p.next;
        }
        return sb.toString();
    }
    
    public static void main(String... args)
    {
        String s = "racecar";
        s = "evacanistabbatsinacave";
        Palindrome p = new Palindrome();
        p.add(s);
        System.out.println(p);
        boolean result = p.isPalindrome();
        System.out.println("is palindrome: " + result);
    }
}
