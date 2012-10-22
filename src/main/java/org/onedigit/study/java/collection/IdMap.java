package org.onedigit.study.java.collection;

import java.util.IdentityHashMap;

public class IdMap
{
    class Value
    {
        int item;
        public Value(int item) { this.item = item; }
        public void setItem(int item) { this.item = item; }
        @Override
        public String toString() { return String.valueOf(item); }
    }
    
    private IdentityHashMap<Value, Value> map = new IdentityHashMap<Value, Value>();
    
    public void f()
    {
        Value v1 = new Value(1);
        Value v2 = new Value(2);
        map.put(v1, v1);
        map.put(v2, v2);
        Value value = map.get(v1);
        System.out.println(value);
        value.setItem(10);
        System.out.println(map.get(v1));
    }
    
    public static void main(String[] args)
    {
        IdMap idMap = new IdMap();
        idMap.f();
    }
}
