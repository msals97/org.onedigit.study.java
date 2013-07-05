package org.onedigit.study.java;

public class CloneTest implements Cloneable
{
    private int value;
    
    public CloneTest(int value)
    {
        this.value = value;
    }
    
    @Override
    public CloneTest clone() 
    {
        try {
            CloneTest result = (CloneTest)super.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    
    public void f()
    {
        
    }
    
    public static void main(String[] args)
    {
        CloneTest ct = new CloneTest(10);
        CloneTest clone = ct.clone();
        System.out.println(clone.value);
        System.out.println(ct.getClass() == clone.getClass());
    }
}
