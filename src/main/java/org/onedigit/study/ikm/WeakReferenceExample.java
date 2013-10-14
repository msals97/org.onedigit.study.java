package org.onedigit.study.ikm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceExample
{
    public static void main(String[] args) throws InterruptedException
    {
        Integer referent = new Integer(10);
        
        ReferenceQueue<Integer> referenceQueue = new ReferenceQueue<>();
        WeakReference<Integer> wref = new WeakReference<Integer>(referent, referenceQueue);
        
        referent = null;
        System.gc();
        Reference<? extends Integer> oldReferent = referenceQueue.remove();
        System.out.println("old reference = " + oldReferent + ", value = " + oldReferent.get());
        
    }
}
