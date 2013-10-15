package org.onedigit.study.java.algo.trading;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentLinkedHashMap<K, V>
{
    private Map<K, V> map;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rLock = rwLock.readLock();
    private final Lock wLock = rwLock.writeLock();
    
    public ConcurrentLinkedHashMap()
    {
        map = new LinkedHashMap<>();
    }
    
    public V put(K key, V value)
    {
        wLock.lock();
        try {
            return map.put(key, value);
        } finally {
            wLock.unlock();
        }
    }
    
    public V get(Object key)
    {
        rLock.lock();
        try {
            return map.get(key);
        } finally {
            rLock.unlock();
        }
    }
    
    public static void main(String[] args)
    {
    }
}
