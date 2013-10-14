package org.onedigit.study.ikm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceExample
{
    public static void main(String[] args) throws InterruptedException
    {
        Integer referent = new Integer(10);
        
        ReferenceQueue<Integer> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Integer> wref = new PhantomReference<Integer>(referent, referenceQueue);
        
        referent = null;
        System.gc();
        Reference<? extends Integer> oldReferent = referenceQueue.remove();
        System.out.println("old reference = " + oldReferent + ", value = " + oldReferent.get());
        
    }
}
