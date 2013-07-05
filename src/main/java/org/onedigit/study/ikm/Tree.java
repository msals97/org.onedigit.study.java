package org.onedigit.study.ikm;

import java.util.LinkedList;

/**
 * Binary tree implementation.
 * @author ahmed
 *
 * @param <T>
 */
public class Tree<T extends Comparable<T>>
{
    class Node
    {
        T value;
        Node left;
        Node right;
        
        Node(T value)
        {
            this.value = value;
            left = null;
            right = null;
        }
        
        public String toString()
        {
            return value.toString();
        }
    }
    
    private Node root = null;
    
    public void insert(T value)
    {
        if (root == null) {
            root = new Node(value);
        } else {
            // look for the appropriate place in the tree to insert
            Node current = root;
            while (true) {
                Node parent = current;
                if (value.compareTo(current.value) < 0) { // search left subtree
                    current = current.left;
                    if (current == null) {
                        parent.left = new Node(value);
                        return;
                    }
                } else if (value.compareTo(current.value) > 0) { // search right subtree
                    current = current.right;
                    if (current == null) {
                        parent.right = new Node(value);
                        return;
                    }
                }
            }
        }
    }
    
    public void inOrder(Node node)
    {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.value + " ");
            inOrder(node.right);
        }
    }
    
    public void preOrder(Node node) 
    {
        if (node != null){
            System.out.print(node.value + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    
    public void postOrder(Node node)
    {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.value + " ");
        }
    }
    
    /**
     * Breadth first traversal
     * @param node
     */
    public void levelOrder(Node node)
    {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node p = queue.removeFirst();
            System.out.print(p + " ");
            if (p.left != null) {
                queue.add(p.left);
            }
            if (p.right != null) {
                queue.add(p.right);
            }
        }
    }
    
    /**
     * Find the minimum node in the tree.
     * This will be the left most leaf.
     * @return
     */
    Node findMinimum()
    {
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    /**
     * Find the lowest common ancestor of nodes with
     * values of v1 and v2.
     * 
     * @param v1
     * @param v2
     * @return
     */
    Node findLCA(Node node, T v1, T v2)
    {
        if (node == null) {
            return null;
        } 
        if (node.value.compareTo(v1) == 0 ||
                node.value.compareTo(v2) == 0) {
            return node;
        }
        // else search in left and right subtrees
        Node left = findLCA(node.left, v1, v2);
        Node right = findLCA(node.right, v1, v2);
        System.out.println("left = " + left + ", right = " + right);
        if (left != null && right != null) {
            return node;
        } else if (left != null) {
            return left;
        } else {
            return right;
        }
    }
    
    public int height(Node node)
    {
        if (node == null) {
            return 0;
        } else {
            return 1 + Math.max(
                    height(node.left), 
                    height(node.right)
                    );
        }
    }
    
    public void testTraversal()
    {
        System.out.println("-----IN ORDER------");
        inOrder(root);
        System.out.println();
        
        System.out.println("-----PRE ORDER------");
        preOrder(root);
        System.out.println();
        
        System.out.println("-----POST ORDER------");
        postOrder(root);     
        System.out.println();
        
        System.out.println("-----LEVEL ORDER------");
        levelOrder(root);
        System.out.println();
    }
    
    public static void testOne()
    {
        Tree<Integer> tree = new Tree<>();
        tree.insert(90);
        tree.insert(50);
        tree.insert(100);
        tree.insert(40);
        tree.insert(60);
        tree.insert(30);
        tree.testTraversal();
        System.out.println("Minimum node: " + tree.findMinimum());
    }
    
    public static void testThree()
    {
        Tree<Integer> tree = new Tree<>();
        tree.insert(20);
        tree.insert(8);
        tree.insert(22);
        tree.insert(4);
        tree.insert(12);
        tree.insert(10);
        tree.insert(14);
        int v1 = 4, v2 = 14;
        Tree<Integer>.Node lca = tree.findLCA(tree.root, v1, v2);
        System.out.println("LCA of " + v1 + ", " + v2 + " = " + lca);
    }
    
    public static void testTwo()
    {
        Tree<String> tree = new Tree<>();
        tree.insert("F");
        tree.insert("B");
        tree.insert("G");
        tree.insert("A");
        tree.insert("D");
        tree.insert("I");
        tree.insert("C");
        tree.insert("E");
        tree.insert("H");
        tree.insert("J");
        tree.testTraversal();
        System.out.println("height = " + tree.height(tree.root));
    }
    
    public static void main(String[] args)
    {
        // Tree.testOne();
        Tree.testTwo();
        // Tree.testThree();
    }
}
