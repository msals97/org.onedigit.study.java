import java.util.concurrent.atomic.AtomicReference;

public class LinkedQueue<E>
{
    private static class Node<E>
    {
        E item;
        AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next)
        {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }

        @Override
        public String toString()
        {
            return item == null ? null : item.toString();
        }
    }

    private final Node<E> dummy = new Node<E>(null, null);
    private AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
    private AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(dummy);

    public boolean put(E item)
    {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) { // Queue is in inconsistent state
                    // CAS the tail
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // CAS new node as tail
                    if (curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

    public E take()
    {
        while (true) {
            Node<E> curHead = head.get();
            Node<E> curTail = tail.get();
            Node<E> nextNode = curHead.next.get();
            if (curHead == head.get()) { // head is still consistent ?
                if (curHead == curTail) { // head and tail are the same?
                    if (nextNode == null) {
                        return null;
                    }
                    // CAS(tail, curTail, nextNode);
                    // tail.compareAndSet(curTail, nextNode);
                } else {
                    E result = nextNode.item;
                    if (head.compareAndSet(curHead, nextNode)) {
                        return result;
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        LinkedQueue<Integer> lq = new LinkedQueue<Integer>();
        lq.put(10);
        lq.put(20);
        lq.put(30);
        System.out.println("head = " + lq.head.get() + ", head.next = " + lq.head.get().next.get());
        System.out.println(lq.take());
        System.out.println(lq.take());
        System.out.println(lq.take());
        System.out.println(lq.take());
        System.out.println(lq.take());
        lq.put(40);
        System.out.println(lq.take());
    }
}
