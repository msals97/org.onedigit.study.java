package org.onedigit.study.java.algo.trading;

public interface BoundedBufferEvictionListener<V>
{
    public void evicted(V value);
}
