package org.onedigit.study.java.algo.trading;

public class StockPrice
{
    private final String symbol = "AAPL";
    private final double price;
    private final long time;
    
    public StockPrice(long time, double price)
    {
        this.price = price;
        this.time = time;
    }
    
    public String getSymbol() { return symbol; }
    
    public double getPrice() { return price; }
    
    public long getTime() { return time; }
    
    @Override
    public String toString()
    {
        return "Price: " + price + " time: " + time;
    }
}