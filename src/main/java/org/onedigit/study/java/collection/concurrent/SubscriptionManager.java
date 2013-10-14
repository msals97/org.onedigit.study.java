package org.onedigit.study.java.collection.concurrent;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SubscriptionManager
{
    class Listener
    {
        String name;
        public Listener(String name)
        {
            this.name = name;
        }
        void event() { }
        
        @Override
        public String toString()
        {
            return "Listener-" + name;
        }
    }
    
    ConcurrentMap<String, List<Listener>> subs = new ConcurrentHashMap<>();
    Lock lock = new ReentrantLock();
    
    public void subscribe(String key, Listener listener)
    {
        List<Listener> listeners = subs.get(key);
        if (listeners == null) {
            List<Listener> newListeners = new CopyOnWriteArrayList<>();
            listeners = subs.putIfAbsent(key, newListeners);
            if (listeners == null) {
                listeners = newListeners;
                // doSub for key
            }
        }
        // listeners could have been removed from subs at this point!
        if (subs.get(key) != listeners) {
            
        }
        listeners.add(listener);
    }
    
    public void unsubscribe(String key, Listener listener)
    {
        List<Listener> listeners = subs.get(key);
        if (listeners != null) {
            if (listeners.remove(listener)) {
                lock.lock();
                try {
                    if (listeners.isEmpty()) {
                        // TODO
                        // do not remove if a subscription is in progress
                        subs.remove(key);
                        // doUnsub for key
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    
    public void handleEvent(String key)
    {
        List<Listener> listeners = subs.get(key);
        for (Listener l : listeners) {
            l.event();
        }
    }
    
    public void test() throws InterruptedException
    {
        final Listener l1 = new Listener("L1");
        final Listener l2 = new Listener("L2");
        
        subscribe("A", l1);

        Thread t2 = new Thread(new Runnable() {
            public void run()
            {
                unsubscribe("A", l1);
            }
        });
        
        Thread t1 = new Thread(new Runnable() {
            public void run()
            {
                subscribe("A", l2);
            }
        });
        t2.start();
        t1.start();
        t2.join();
        t1.join();
        if (subs.size() != 1) {
            System.out.println("ERROR");
            System.exit(1);
        }
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        SubscriptionManager sm = new SubscriptionManager();
        for (int i = 0; i < 10000; i++) {
            sm.test();
        }
    }
}
