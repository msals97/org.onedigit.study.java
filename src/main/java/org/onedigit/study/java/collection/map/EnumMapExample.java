package org.onedigit.study.java.collection.map;

import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Set;

public class EnumMapExample
{
    enum Colour
    {
        RED, GREEN, BLUE, YELLOW, WHITE, BLACK
    }
    
    EnumMap<Colour, String> map = new EnumMap<>(Colour.class);
    
    public EnumMapExample()
    {
        map.put(Colour.RED, "Red");
        map.put(Colour.BLUE, "Blue");
        map.put(Colour.GREEN, "Green");
        
        Set<Entry<Colour, String>> entries = map.entrySet();
        for (Entry<Colour, String> e: entries) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
    }
    
    public static void main(String[] args)
    {
        new EnumMapExample();
    }
}
