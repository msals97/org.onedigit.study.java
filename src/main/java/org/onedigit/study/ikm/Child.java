package org.onedigit.study.ikm;

class Parent
{
    
}

public class Child extends Parent
{
    protected static int count = 0;
    public Child()
    {
        count++;
    }
    static int getCount()
    {
        return count;

    }
    
    public static void main(String... args)
    {
        Child child = new Child();
        System.out.println(child.getCount());
    }
}
