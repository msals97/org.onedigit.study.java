package org.onedigit.study.java.algo.trading;


public interface StockListener
{
    /**
     * publish stock price event.
     * @param event
     */
    public void priceTick(StockPrice event);
}
