import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Tree
{
    private Node root;
    private int size;

    public void insert(int value)
    {
        root = insert(root, value);
    }

    private Node insert(Node node, int value)
    {
        if (node == null) {
            ++size;
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insert(node.left, value);
            node.left.parent = node;
        } else if (value > node.value) {
            node.right = insert(node.right, value);
            node.right.parent = node;
        } /* else if (value ==  node.value) { // replace
            node.value = value;
        } */
        return node;
    }

    public int size() { return size; }

    public void print()
    {
        print(root);
    }

    private void print(Node node)
    {
        if (node == null) {
            return;
        }
        print(node.left);
        System.out.print(node.value + " ");
        print(node.right);
    }

    public void printLevelOrder()
    {
        Queue<Node> queue = new ArrayBlockingQueue<Node>(size);
        queue.offer(root);
        printLevelOrder(queue);
    }

    private void printLevelOrder(Queue<Node> queue)
    {
        Queue<Node> q = new ArrayBlockingQueue<Node>(size);
        while (!queue.isEmpty()) {
            Node p = queue.poll();
            System.out.print(p.value + " ");
            if (p.left != null) {
                q.offer(p.left);
            }
            if (p.right != null) {
                q.offer(p.right);
            }
        }
        System.out.println();
        if (!q.isEmpty()) {
            printLevelOrder(q);
        }
    }

    /**
      * Find right (left) level-order neighbour of node.
      */
    Node neighbour(Node node)
    {
        Node neighbour = null; 
        Node parent = node.parent;
        // If the node is a left child
        if (parent.left == node) {
            System.out.println("Given node is a left child");
            if (parent.right != null) { // if there is a brother to our right
                neighbour = parent.right;
            } else { 
                int level = 1;
                while (parent.parent != null) {
                    parent = parent.parent;
                    // Move up to grand-parent
                    // Traverse it depth first upto 'level' levels.
                    neighbour = kdepth(parent.right, level, Orientation.LEFT);
                    level++;
                    if (neighbour != null) {
                        break;
                    }
                }
            }
        } else if (parent.right == node) {  // node is a right child
            System.out.println("Given node is a right child");
            if (parent.left != null) { // if there is a brother to our left
                neighbour = parent.left;
            } else {
                int level = 1;
                while (parent.parent != null) {
                    parent = parent.parent;
                    neighbour = kdepth(parent.left, level, Orientation.RIGHT);
                    level++;
                    if (neighbour != null) {
                        break;
                    }
                }
            }
        }
        return neighbour;
    }

    public Node kdepth(Node node, int depth, Orientation orientation)
    {
        System.out.println("kdepth left for node " + node + ", depth = " + depth);
        int currentDepth = 0;
        Node result = null;
        Deque<Node> dq = new ArrayDeque<Node>();
        dq.offerFirst(node);
        while (!dq.isEmpty()) {
            Node n = dq.pollFirst();
            System.out.println("\tn = " + n + ", currentDepth = " + currentDepth);
            if (currentDepth == depth) {
                result = n;
                break;
            }
            if (orientation == Orientation.LEFT) { // traverse left subtree first
                if (n.right != null) {
                    dq.offerFirst(n.right);
                }
                if (n.left != null) {
                    dq.offerFirst(n.left);
                }
            } else { // traverse right subtree first
                if (n.left != null) {
                    dq.offerFirst(n.left);
                }
                if (n.right != null) {
                    dq.offerFirst(n.right);
                }
            }
            if (n.right != null || n.left != null) {
                currentDepth++;
            }
        }
        return result;
    }

    /**
      * Find the node with the given value. 
      * Return null if not found
      */
    Node find(int value)
    {
        return find(root, value);
    }

    Node find(Node node, int value)
    {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            return find(node.left, value);
        } else if (value > node.value) {
            return find(node.right, value);
        } else if (value == node.value) {
            return node;
        }

        return null;
    }

    Node findLowestCommonAncestor(Node x, Node y)
    {
        Map<Integer, Node> xMap = new HashMap<Integer, Node>();
        Node p = root;
        while (p != null) {
            xMap.put(p.value, p);
            if (x.value < p.value) {
                p = p.left;
            } else if (x.value > p.value) {
                p = p.right;
            } else if (x.value == p.value) {
                break;
            }
        }
        // System.out.println("\txMap = " + xMap);

        List<Node> yList = new ArrayList<Node>(); 
        getPath(y, yList);
        // System.out.println("\tyList = " + yList);

        // Now search from the end of the yList;
        // The last element of yList is Node y, so we skip it.
        int len = yList.size();
        for (int i = len - 1; i >= 0; --i) {
            Node node = yList.get(i);
            System.out.println("Looking for " + node.value);
            if (xMap.containsKey(node.value)) {
                System.out.println("lowest common ancestor of " + x + " and " + y + " = " + node);
                break;
            }
        }

        return null;
    }

    private void getPath(Node node, List<Node> path)
    {
        Node p = root;
        while (p != null) {
            path.add(p);
            if (node.value < p.value) {
                p = p.left;
            } else if (node.value > p.value) {
                p = p.right;
            } else if (node.value == p.value) {
                break;
            }
        }
    }

    public void depthFirstSerial()
    {
        depthFirst(root); 
    }

    private void depthFirst(Node node)
    {
        if (node == null) {
            return;
        }
        depthFirst(node.left);
        depthFirst(node.right);
        System.out.println("node " + node + " fib = " + node.fib());
    }

    enum Orientation { LEFT, RIGHT };

    class Node
    {
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return "(" + value + ")";
        }

        /**
         * N^th fibonacci number
         */
        public int fib()
        {
            if (value == 0) {
                return 0;
            }
            if (value == 1) {
                return 1;
            }
            int a = 0;
            int b = 1;
            int result = 0;
            for (int i = 2; i <= value; i++) {
                result = a + b;
                a = b;
                b = result;
            }
            return result;
        }
    }


    public static void main(String[] args)
    {
        Tree tree = new Tree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(5);
        tree.insert(12);
        tree.insert(25);
        tree.insert(40);

        tree.insert(3);

        // tree.insert(11); // extra
        // tree.insert(13); // extra

        tree.insert(24);
        tree.insert(26);
        tree.insert(39);
        tree.insert(41);
        tree.print();
        System.out.println();
        tree.printLevelOrder();

        Node node = tree.find(3);
        Node neighbour = tree.neighbour(node);
        System.out.println("Neighbour of " + node + " = " + neighbour);

        node = tree.find(41);
        neighbour = tree.neighbour(node);
        System.out.println("Neighbour of " + node + " = " + neighbour);
        System.out.println("fib of " + node + " = " + node.fib());

        // System.out.println();
        // node = tree.find(12);
        // Node n = tree.kdepth(node, 1);
        // System.out.println("n = " + n);
        tree.findLowestCommonAncestor(tree.find(20), tree.find(41));
        System.out.println();

        tree.depthFirstSerial();
    }
}
