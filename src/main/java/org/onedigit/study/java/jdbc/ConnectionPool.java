package org.onedigit.study.java.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.PooledConnection;

/**
 * The ConnectionPool should perform the following tasks:
 *  Preallocate the connections.
 *  Manage available connections.
 *  Allocate new connections.
 *  Wait for connections to become available.
 *  Close connections when required.
 *  
 * @author ahmed
 *
 */

public class ConnectionPool implements AutoCloseable
{
    LinkedList<PooledConnection> connectionPool = new LinkedList<>();
    
    ConcurrentLinkedQueue<Connection> availableConnections = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<Connection> busyConnections = new ConcurrentLinkedQueue<>();
    
    private int maxConnections = 5;
    private AtomicInteger currentConnections = new AtomicInteger(0);
    
    private Semaphore semaphore = new Semaphore(maxConnections, true);
    
    private ExecutorService executorService;
    private int nThreads = 5;
    
    class ConnectionFactory implements Callable<PooledConnection>
    {
        @Override
        public PooledConnection call() throws Exception
        {
            return null;
        }
    }
    
    // Preallocate the connections.
    public ConnectionPool()
    {
        for (int i = 0; i < maxConnections; i++) {
            availableConnections.add(makeNewConnection());
        }
    }
    
    /**
     * If a connection is required and an idle connection is available, put it
     * in the list of busy connections and then return it.  The busy list is used to
     * check limits on the total number of connections as well as when the pool is
     * instructed to explicitly close all connections. 
     * Caveat: connections can time out, so before returning the connection, confirm that
     * it is still open, if not, discard the connection and repeat the process.
     * Discarding a connection opens up a slot that can be used by processes that needed
     * a connection when the connection limit had been reached, so use notifyAll to tell
     * all waiting threads to wake up and see if they can proceed (e.g. by allocating a 
     * new connection).
     * @throws SQLException 
     */
    public Connection getConnection(long timeoutMs) throws SQLException
    {
        try {
            if (!semaphore.tryAcquire(timeoutMs, TimeUnit.MILLISECONDS)) {
               throw new TimeoutException(); 
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while waiting for a database connection.", e); 
        }
        
        boolean ok = false;
        try {
            Connection conn = getConnection();
            ok = true;
            return conn;
        } finally {
            if (!ok) {
                semaphore.release();
            }
        }
    }
    
    public Connection getConnection() throws SQLException
    {
        Connection conn = null;
        while ( (conn = availableConnections.poll()) != null) {
            if (conn.isClosed()) {
                // Discard this connection and notify anyone waiting.
                notifyAll();
            } else { // found a free connection that is not closed.
                busyConnections.add(conn);
                break;
            }
        }
        
        // We don't have a free connection. 3 cases:
        // (1) We have not reached maxConnections limit. So, establish one in the
        // background, if there isn't already one pending, then wait for the next 
        // available connection (whether or not it was the newly establihed connection).
        // (2) We reached the maxConnections limit and waitIfBusy is false.  
        // Throw SQLException in that case.
        // (3) We reached the maxConnections limit and waitIfBusy is true.  Follow second
        // step of part (1).         
        if (conn == null) { // no free connections
            if (currentConnections.get() < maxConnections) {
                // open a new connection. 
            }
        }
        
        return conn;
    }    
    
    private Connection makeNewConnection()
    {
        Connection conn = null;
        
        // Create the connection in the background
        executorService = Executors.newFixedThreadPool(nThreads);
        Future<PooledConnection> future = executorService.submit(new ConnectionFactory());
        try {
            PooledConnection pooledConnection = future.get();
            conn = pooledConnection.getConnection();
        } catch (InterruptedException | ExecutionException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void close() throws Exception
    {
        // TODO Auto-generated method stub
        
    }

}
