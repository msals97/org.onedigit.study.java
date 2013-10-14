package org.onedigit.study.java.algo.trading;

import java.util.LinkedHashMap;
import java.util.Map;

public class BoundedBuffer<K, V> extends LinkedHashMap<K, V>
{
    private static final long serialVersionUID = 1L;
    private final int capacity;
    private V eldestValue;
    private BoundedBufferEvictionListener<V> evictionListener;
    
    public BoundedBuffer(int capacity)
    {
        super();
        this.capacity = capacity;
    }
    
    public BoundedBuffer(int capacity, BoundedBufferEvictionListener<V> evictionListener)
    {
        super();
        this.capacity = capacity;
        this.evictionListener = evictionListener;
    }
    
    public boolean removeEldestEntry(Map.Entry<K, V> eldest)
    {
        boolean result = super.size() > capacity;
        if (result) {
            eldestValue = eldest.getValue();
            if (evictionListener != null) {
                evictionListener.evicted(eldest.getValue());
            }
        } else {
            eldestValue = null;
        }
        return result;
    }
    
    public V eldestValue() { return eldestValue; }
}    
    
