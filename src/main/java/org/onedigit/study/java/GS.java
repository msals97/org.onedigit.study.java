package org.onedigit.study.java;

public class GS
{
    public void m1(String arg1)
    {
        arg1 = "Am I going to disappear?";
    }
    
    public static void main(String[] args)
    {
        GS gs = new GS();
        String str = "I am born new";
        gs.m1(str);
        System.out.println(str);
    }
}
