package org.onedigit.study.java.collection;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class LRUCache<K, V> extends LinkedHashMap<K, V>
{
    private static final long serialVersionUID = 1L;
    private final int maxEntries;

    public LRUCache(int maxEntries)
    {
        super(maxEntries, 1.0f, true);
        this.maxEntries = maxEntries;
    }
    
    public void add(K key, V value)
    {
        put(key, value);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Set<Entry<K, V>> entries = this.entrySet();
        for (Entry<K, V> e : entries) {
            sb.append(e.getKey()).append(",").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest)
    {
        return super.size() > maxEntries;
    }
    
    public static void main(String[] args)
    {
        LRUCache<Integer, String> cache = new LRUCache<>(5);
        for (int i = 0; i < 100; i++) {
            cache.add(i, String.valueOf(i));
        }
        System.out.println(cache);
    }
    
}
