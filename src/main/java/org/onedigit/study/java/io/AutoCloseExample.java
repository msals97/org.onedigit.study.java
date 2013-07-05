package org.onedigit.study.java.io;

public class AutoCloseExample
{
    class A implements AutoCloseable
    {
        @Override
        public void close() throws Exception
        {
            System.out.println("Closing A...");
        }
    }

    public AutoCloseExample()
    {
        try (A a = new A()) {
            System.out.println("We do something with a");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new AutoCloseExample();
    }
}
