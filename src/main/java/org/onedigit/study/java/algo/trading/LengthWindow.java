package org.onedigit.study.java.algo.trading;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LengthWindow implements Iterable<StockPrice>
{
    private final ArrayDeque<StockPrice> buffer;
    private final int size;
    
    public LengthWindow(int size)
    {
        this.buffer = new ArrayDeque<>();
        this.size = size;
    }

    /**
     * Add new event and return expired events.
     * @param event
     * @return expired events.
     */
    public List<StockPrice> handleEvent(StockPrice event)
    {
        buffer.add(event);

        // expire old events.        
        int expiredCount = buffer.size() - size;
        if (expiredCount > 0) {
           List<StockPrice> list = new ArrayList<>();
           for (int i = 0; i < expiredCount; i++) {
               list.add(buffer.removeFirst());
           }
           return list;
        } else {
            return Collections.emptyList();
        }
    }
    
    public int size()
    {
        return buffer.size();
    }

    @Override
    public Iterator<StockPrice> iterator()
    {
        return buffer.iterator();
    }
}
