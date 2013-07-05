package org.onedigit.study.ikm;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceExample
{
    private static int NUM_TASKS = 5;
    private Random random = new Random();
    private ExecutorService executor;
    
    class Worker implements Callable<Integer>
    {
        public Integer call()
        {
            int result = random.nextInt();
            System.out.println(Thread.currentThread().getName() + " generated " + result);
            return result;
        }
    }
    
    public CompletionServiceExample()
    {
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
    
    public void process()
    {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        for (int i = 0; i < NUM_TASKS; i++) {
            completionService.submit(new Worker());
        }
        
        // process the results;
        Integer finalResult = 0;
        for (int i = 0; i < NUM_TASKS; i++) {
            try {
                Future<Integer> f = completionService.take();
                finalResult += f.get();
            } catch (ExecutionException e) {
                System.err.println(e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("Final result = " + finalResult);
        executor.shutdown();
    }
    
    public static void main(String[] args)
    {
        CompletionServiceExample example = new CompletionServiceExample();
        example.process();
    }
}
