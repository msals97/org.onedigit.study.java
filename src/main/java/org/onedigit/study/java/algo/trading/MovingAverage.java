package org.onedigit.study.java.algo.trading;

import java.awt.Color;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;


/**
 * Notify me when the difference between the current price of a stock and its 10
 * day moving average is greater than some threshold value.
 * 
 * @author ahmed
 * 
 */
public class MovingAverage implements StockListener
{
    public enum MovingAverageType 
    { 
        Simple, Exponential 
    }
    
    private final MovingAverageType movingAverageType;
    
    /**
     * If the current price and the moving average differ by threshold,
     * send a notification.
     */
    private final double threshold;
    
    private long lastTime;
    private double lastMovingAverage;
    private double movingAverage = -1.0;
    
    /**
     *  calculate moving average over how may days?
     */
    private final int movingAverageInterval;

    private LengthWindow lengthWindow;
    private final StockTicker stockTicker;
    private final Color lineColour;
    
    /**
     * Smoothing constant used in exponential moving average;
     */
    private double smoothingConstant;
    
    /**
     * MovingAverage calculator.
     * 
     * @param movingAverageInterval moving average interavl in days.
     * @param T number of days to simulate
     * @param dt time interval
     * @param s0 initial stock price
     * @param mu dift
     * @param sigma volatility
     */
    public MovingAverage(MovingAverageType movingAverageType,
            int movingAverageInterval, double threshold, StockTicker stockTicker, Color lineColor)
    {
        this.movingAverageType = movingAverageType;
        this.movingAverageInterval = movingAverageInterval;
        this.threshold = threshold;
        this.lengthWindow = new LengthWindow(movingAverageInterval);
        this.stockTicker = stockTicker;
        this.lineColour = lineColor;
        this.smoothingConstant = 2.0 / (movingAverageInterval + 1);
    }
    
    public void start()
    {
        stockTicker.addStockListener(this);
        Thread t = new Thread(stockTicker);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        
    @Override
    public void priceTick(StockPrice event)
    {
        if (movingAverageType == MovingAverageType.Simple) {
            movingAverage(event);
        } else if (movingAverageType == MovingAverageType.Exponential) {
            exponentialMovingAverage(event);
        }
    }    
    
    private void movingAverage(StockPrice event)
    {
        List<StockPrice> expired = lengthWindow.handleEvent(event);
        int expiredSize = expired.size();
        if (expiredSize == 0) {
            double sum = 0.0;
            for (StockPrice p : lengthWindow) {
                sum += p.getPrice();
            }
            movingAverage = sum / lengthWindow.size();
        } else {
            double expiredValue = expired.get(0).getPrice();
            movingAverage = movingAverage + (event.getPrice() - expiredValue) / movingAverageInterval;
        }
        if (movingAverage > -1) {
            System.out.println("\ttime = " + event.getTime() + ", current price = " + 
                    event.getPrice() + ", " + movingAverageInterval + 
                    " moving average = " + movingAverage);
            
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(event.getTime(), movingAverage);
            
            if (event.getTime() > 0) {
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(lineColour);
                StdDraw.line(lastTime, lastMovingAverage, event.getTime(), movingAverage);
                StdDraw.show();
            }
            
            // Monitor threshold breaches.
            double diff = Math.abs(event.getPrice() - movingAverage);
            if (diff > threshold) {
                /*
                System.out.println("\tThreshold breached: " + ", current price = " + price + 
                        ", moving average = " + movingAverage + ", diff = " + diff);
                        */
                StdDraw.setPenColor(Color.MAGENTA);
                StdDraw.setPenRadius(0.002);
                // StdDraw.point(day, movingAverage);
                StdDraw.line(event.getTime(), movingAverage, event.getTime(), event.getPrice());
            }                                   
        }    
        this.lastTime = event.getTime();
        this.lastMovingAverage = movingAverage;
    }
    
    private void exponentialMovingAverage(StockPrice event)
    {
        List<StockPrice> expired = lengthWindow.handleEvent(event);
        int expiredSize = expired.size();
        if (expiredSize == 0) {
            double sum = 0.0;
            for (StockPrice p : lengthWindow) {
                sum += p.getPrice();
            }
            movingAverage = sum / lengthWindow.size();
        } else {
            // double expiredValue = expired.get(0).getPrice();
            // movingAverage = movingAverage + (event.getPrice() - expiredValue) / movingAverageInterval;
            movingAverage = (event.getPrice() - lastMovingAverage) * smoothingConstant + lastMovingAverage;
        }
        if (movingAverage > -1) {
            System.out.println("\ttime = " + event.getTime() + ", current price = " + 
                    event.getPrice() + ", " + movingAverageInterval + 
                    " moving average = " + movingAverage);
            
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(event.getTime(), movingAverage);
            
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(lineColour);
            StdDraw.line(lastTime, lastMovingAverage, event.getTime(), movingAverage);
            StdDraw.show();
            
            // Monitor threshold breaches.
            double diff = Math.abs(event.getPrice() - movingAverage);
            if (diff > threshold) {
                /*
                System.out.println("\tThreshold breached: " + ", current price = " + price + 
                        ", moving average = " + movingAverage + ", diff = " + diff);
                        */
                StdDraw.setPenColor(Color.MAGENTA);
                StdDraw.setPenRadius(0.002);
                // StdDraw.point(day, movingAverage);
                StdDraw.line(event.getTime(), movingAverage, event.getTime(), event.getPrice());
            }                                   
        }    
        this.lastTime = event.getTime();
        this.lastMovingAverage = movingAverage;        
    }    

    public static void main(String[] args) throws InterruptedException
    {
        // ten day moving average
        StockTicker st = new StockTicker(30, 0.01, 20, 0.01, 0.01);
        MovingAverage ma = new MovingAverage(MovingAverageType.Simple, 10, 5.0, st, Color.BLUE);
        // MovingAverage ma = new MovingAverage(MovingAverageType.Exponential, 10, 5.0, st, Color.BLUE);
        ma.start();
    }
}
