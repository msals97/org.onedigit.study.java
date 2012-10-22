package org.onedigit.study.java;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;


public class WeakReferenceExample
{
    public static void main(String[] args)
    {
        String str = new String("Hello World");
        ReferenceQueue<String> rq = new ReferenceQueue<String>();
        WeakReference<String> wref = 
                new WeakReference<String>(str, rq);
        String sRef = wref.get();
        System.out.println(sRef);
        sRef = null;

        // Create some garbage
        for (int i = 0; i < 1000000; i++) {
            String s = new String(i + "");
        }
        
        // The weak reference would have probably disappeared by now.
        sRef = wref.get();
        System.out.println(sRef);
        System.out.println(rq.poll());
    }
}
