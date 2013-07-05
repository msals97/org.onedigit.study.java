package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest
{
    private int start = 10;
    
    static class OneDigitException extends Exception
    {
        String message;
        public OneDigitException(String message)
        {
            super(message);
            this.message = message;
        }
        
        public String getMessage()
        {
            return message;
        }
    }
    
    private final FutureTask<Integer> future = new FutureTask<Integer>(
            new Callable<Integer>() {
                @Override
                public Integer call() throws Exception
                {
                    int result = start * start;
                    throw new OneDigitException("OneDigitException");
                    // return result;
                }
    });
    
    public void process()
    {
        Thread t = new Thread(future);
        System.out.println("Starting thread " + t.getId());
        t.start();
    }
    
    public Integer getResult()
    {
        Integer result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        } 
        return result;
    }
    
    public static void main(String[] args)
    {
        FutureTaskTest test = new FutureTaskTest();
        test.process();
        System.out.println("Result = " + test.getResult());
    }
}
