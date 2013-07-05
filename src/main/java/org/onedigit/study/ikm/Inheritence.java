package org.onedigit.study.ikm;

public class Inheritence
{
    static class A
    {
        protected String message;
        public A()
        {
            makeString();
        }
        protected void makeString()
        {
            message = "AAA";
        }
        protected String getString()
        {
            return message;
        }
    }
    
    static class B extends A
    {
        protected void makeString()
        {
            message = "BBB";
        }
    }
    
    public static void main(String[] args)
    {
        System.out.println(new B().getString());
    }
}
