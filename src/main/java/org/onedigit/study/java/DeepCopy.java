package org.onedigit.study.java;

public class DeepCopy
{
    class Entry implements Cloneable
    {
        Object key;
        Object value;
        Entry next;
        
        public Entry(Object key, Object value, Entry next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        // iterative deep copy
        public Entry deepCopy()
        {
            Entry result = new Entry(key, value, next);
            for (Entry p = result; p.next != null; p = p.next) {
                p.next = new Entry(p.next.key, p.next.value, p.next.next);
            }
            return result;
        }
        
        public Entry clone()
        {
            Entry result = null;
            try {
                result = (Entry)super.clone();
                result = this.deepCopy();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
            return result;
        }
        
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            Entry p = this;
            while (p != null) {
                sb.append("Key:").append(p.key).append(", Value:").append(p.value).append('\n');
                p = p.next;
            }
            return sb.toString();
        }
    }
    
    public void f()
    {
        Entry entry = new Entry(20, 20, new Entry(10, 10, null));
        System.out.println(entry);
        Entry clone = entry.clone();
        System.out.println(clone);
    }
    
    public static void main(String[] args)
    {
        new DeepCopy().f();
    }
}
