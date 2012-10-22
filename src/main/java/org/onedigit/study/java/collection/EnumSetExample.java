package org.onedigit.study.java.collection;

import java.util.EnumSet;

public class EnumSetExample
{
    enum Colour
    {
        RED, GREEN, BLUE
    }
    
    private EnumSet<Colour> enumSet = EnumSet.allOf(Colour.class);
    
    public EnumSetExample()
    {
        for (Colour c: enumSet) {
            System.out.println(c);
        }
    }
    
    public static void main(String[] args)
    {
        EnumSetExample example = new EnumSetExample();
    }
}
