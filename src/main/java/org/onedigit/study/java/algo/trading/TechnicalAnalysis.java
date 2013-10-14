package org.onedigit.study.java.algo.trading;

import java.awt.Color;

import org.onedigit.study.java.algo.trading.MovingAverage.MovingAverageType;

public class TechnicalAnalysis
{
    public TechnicalAnalysis()
    {
        
    }
    
    private void addMovingAverage(MovingAverageType type, int movingAverageInterval, double threshold, 
            StockTicker ticker, Color color)
    {
        MovingAverage ma = new MovingAverage(type, movingAverageInterval, threshold, ticker, color);
        ticker.addStockListener(ma);
    }
    
    public void start()
    {
        StockTicker st = new StockTicker(30, 0.01, 20, 0.01, 0.01);

        // 3 day moving average
        addMovingAverage(MovingAverageType.Simple, 3, 5.0, st, Color.GREEN);

        // 3 day exp moving average
        // addMovingAverage(MovingAverageType.Exponential, 3, 5.0, st, Color.BLUE);
        
        // 5 day moving average
        addMovingAverage(MovingAverageType.Simple, 5, 5.0, st, Color.ORANGE);
        
        // 10 day moving average
        addMovingAverage(MovingAverageType.Simple, 10, 5.0, st, Color.BLUE);

        // 10 day exp moving average
        // addMovingAverage(MovingAverageType.Exponential, 10, 5.0, st, Color.RED);
        
        // 15 day moving average
        addMovingAverage(MovingAverageType.Simple, 15, 5.0, st, Color.RED);

        Thread t = new Thread(st);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        TechnicalAnalysis ta = new TechnicalAnalysis();
        ta.start();
    }
}
