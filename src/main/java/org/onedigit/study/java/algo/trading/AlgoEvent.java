package org.onedigit.study.java.algo.trading;

public class AlgoEvent
{
    private final long timestamp;
    private final StockPrice event;
    
    public AlgoEvent(long timestamp, StockPrice event)
    {
        this.timestamp = timestamp;
        this.event = event;
    }
    
    public long getTimestamp()
    {
        return timestamp;
    }
    
    public StockPrice getEvent()
    {
        return event;
    }
    
    @Override
    public String toString()
    {
        return timestamp + ": " + event.toString();
    }
}
