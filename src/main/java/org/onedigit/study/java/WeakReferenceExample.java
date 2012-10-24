package org.onedigit.study.java;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;


public class WeakReferenceExample
{
	private class RefCount extends WeakReference<String>
	{
		private int count;
		private String referent;
		public RefCount(String referent, ReferenceQueue<String> queue)
        {
	        super(referent, queue);
	        count++;
	        this.referent = referent;
        }
		
		public int getCount() { return count; }
	}
	
	public static void f()
	{
        String str = new String("Hello World");
        ReferenceQueue<String> rq = new ReferenceQueue<String>();
        WeakReference<String> wref = 
                new WeakReference<String>(str, rq);
        String sRef = wref.get();
        System.out.println(sRef);
        sRef = null;

        // Create some garbage
        for (int i = 0; i < 1_000_000; i++) {
            String s = new String(i + "");
        }
        
        // The weak reference would have probably disappeared by now.
        sRef = wref.get();
        System.out.println(sRef);
        System.out.println(rq.poll());
	}
	
	public void g()
	{
		ReferenceQueue<String> rq = new ReferenceQueue<String>();
		String str = new String("ABCDEFG");
		RefCount wRc = new RefCount(str, rq);
		// String sRc = wRc.get();
		// System.out.println(sRc);
		wRc.referent = null;
		// sRc = null;
		str = null;
		
        // Create some garbage
        for (int i = 0; i < 1_000_000; i++) {
            String s = new String(i + "");
        }
        // The weak reference would have probably disappeared by now.
        // sRc = wRc.get();
        // System.out.println(sRc);
        RefCount p = (RefCount)rq.poll();
        if (p != null) {
        	System.out.println("Reference has been GC'ed");
        	System.out.println(p.getCount());
        }
	}
	
    public static void main(String[] args)
    {
    	new WeakReferenceExample().g();
    }
}
