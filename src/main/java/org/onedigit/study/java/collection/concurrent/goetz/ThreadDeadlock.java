package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock
{
    class Task implements Callable<String>
    {
        public String call()
        {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "TASK" + Thread.currentThread().getId();
        }
    }
    
    ExecutorService exec = Executors.newFixedThreadPool(1);
    
    class RenderTask implements Callable<String>
    {
        public String call() throws Exception
        {
            System.out.println("Begin RenderTask...");
            String result = null;
            Future<String> header, footer;
            System.out.println("Header Task");
            header = exec.submit(new Task());
            System.out.println("Footer Task");
            footer = exec.submit(new Task());
            
            System.out.println("Get results");
            // This will deadlock as we have a single threaded executor.
            // Since the current task is not complete, as we're
            // inside the call method, the header task will
            // not have started.  The header and footer tasks
            // wait on the queue until the current task is done.
            //
            // If we increase the number of threads to 2,
            // this will work. 
            result = header.get() + "," + footer.get();
            
            return result;
        }
    }
    
    public Future<String> process()
    {
        return exec.submit(new RenderTask());
    }
    
    public void stop()
    {
        exec.shutdown();
    }
    
    public static void main(String[] args)
    {
        Executors.newScheduledThreadPool(1);
        
        ThreadDeadlock dl = new ThreadDeadlock();
        try {
            String result = dl.process().get();
            System.out.println("result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping...");
        dl.stop();
    }
}
