package org.onedigit.study.java.collection.concurrent.goetz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer
{
    private final int nThreads = 5; 
    private final ExecutorService executor = Executors.newFixedThreadPool(nThreads);
    
    public void start() throws IOException
    {
        ServerSocket socket = new ServerSocket(80);
        while (!executor.isShutdown()) {
            try {
               final Socket conn = socket.accept();
               executor.execute(new Runnable() {
                  public void run() {
                      handleRequest(conn);
                  }
               });
            } catch (RejectedExecutionException e) {
                if (!executor.isShutdown()) {
                    System.out.println("Task submission is rejected");
                }
            }
        }
    }
    
    public void stop()
    {
        executor.shutdown();
    }
    
    void handleRequest(Socket conn)
    {
        
    }
}
