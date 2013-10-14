import java.util.concurrent.atomic.AtomicReference;

/**
  * Concurrent Stack using AtomicReference.
  *
  * @author: Ahmed Riza
  */

public class ConcurrentStack<E>
{
    private int size;
    private AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

    public ConcurrentStack()
    {
    }

    public void push(E item)
    {
        Node<E> node = new Node<E>(item);
        Node<E> prev;
        do {
            prev = head.get();
            node.next = prev;
        } while (!head.compareAndSet(prev, node));
        ++size;
    }

    public E pop()
    {
        if (size == 0) {
            throw new IllegalStateException("Empty Stack");
        }

        Node<E> newHead;
        Node<E> oldHead;
        do {
            oldHead = head.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!head.compareAndSet(oldHead, newHead));
        --size;
        return oldHead.item;
    }

    public int size() { return size; }

    private static class Node<E>
    {
        final E item;
        Node<E> next;

        public Node(E item)
        {
            this.item = item;
        }
    }

    public static void main(String[] args)
    {
        ConcurrentStack<Integer> stack = new ConcurrentStack<Integer>();
        stack.push(10);
        System.out.println("size = " + stack.size());
        stack.push(20);
        System.out.println("size = " + stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println("size = " + stack.size());
        // System.out.println(stack.pop());
    }
}
