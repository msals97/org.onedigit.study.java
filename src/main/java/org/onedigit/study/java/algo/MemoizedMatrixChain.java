package org.onedigit.study.java.algo;

/**
 * Introduction to Algorithms, CLRS
 * Dynamic Programming, Chapter 15
 * 
 * Time complexity: O(n^3)
 * Space complexity: O(n^2)
 * where n is the number of matrices in the chain.
 * 
 * @author ahmed
 *
 */
public class MemoizedMatrixChain
{
    int[][] m;
    
    public int matrixChain(int[] p)
    {
        int n = p.length - 1;
        m = new int[n][n];
        // Initialise the cost matrix
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                m[i][j] = Integer.MAX_VALUE;
            }
        }
        return lookupChain(p, 0, n-1);
    }
    
    public int lookupChain(int[] p, int i, int j)
    {
        if (m[i][j] < Integer.MAX_VALUE) {
            return m[i][j];
        }

        if (i == j) {
            m[i][j] = 0;
        } else {
            for (int k = i; k < j; k++) {
                int q = lookupChain(p, i, k) + 
                        lookupChain(p, k+1, j) + p[i]*p[k+1]*p[j+1];
                if (q < m[i][j]) {
                    m[i][j] = q;
                }
            }
        }
        return m[i][j];
    }
    
    public static void main(String[] args)
    {
        // Six matrices with size given by
        // A1 : 30x35, A2 : 35x15, A3 : 15x5
        // A4 : 5x10, A5 : 10x20, A6 : 20x25
        int[] p = {30, 35, 15, 5, 10, 20, 25};
        MemoizedMatrixChain mc = new MemoizedMatrixChain();
        int cost = mc.matrixChain(p);
        System.out.println("Optimal number of scalar multiplications = " + cost);
    }
}
