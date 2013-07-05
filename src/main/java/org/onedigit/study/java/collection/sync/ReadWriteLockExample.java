package org.onedigit.study.java.collection.sync;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample
{
    static class RWDictionary {
        private final Map<String, String> map = new TreeMap<>();
        private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock rLock = rwLock.readLock();
        private final Lock wLock = rwLock.writeLock();
        
        public String get(String key)
        {
            rLock.lock();
            try {
                return map.get(key);
            } finally {
                rLock.unlock();
            }
        }
        
        public String[] allKeys() 
        {
            rLock.lock();
            try {
                return map.keySet().toArray(new String[]{});
            } finally {
                rLock.unlock();
            }
        }
        
        public void put(String key, String value)
        {
            wLock.lock();
            try {
                map.put(key, value);
            } finally {
                wLock.unlock();
            }
        }
        
    }
    
    public static void main(String[] args)
    {
        RWDictionary dict = new RWDictionary();
        dict.put("firstName", "ahmed");
        dict.put("lastName", "riza");
        System.out.println(Arrays.toString(dict.allKeys()));
    }
}
