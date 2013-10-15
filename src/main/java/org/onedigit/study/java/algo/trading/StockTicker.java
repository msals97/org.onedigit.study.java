package org.onedigit.study.java.algo.trading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.onedigit.study.util.Clock;

import edu.princeton.cs.introcs.StdDraw;


public class StockTicker implements Runnable
{
    // private final Random rand = new Random(System.nanoTime());
    private final Random rand = new Random(0);
    private final int T; // days.
    private final double dt; // = 0.01;
    private final int N;
    private final double s0;
    private final double mu;
    private final double sigma;
    private final double[] prices;
    private List<StockListener> stockListeners;

    private int currentMinYscale;
    private int currentMaxYscale;
    
    /**
     *  how frequently to send prices, in milliseconds
     */
    private int priceFrequency = 100; 
    

    
    public StockTicker(int T, double dt, double s0, double mu, double sigma)
    {
        this.T = T;
        this.dt = dt;
        this.s0 = s0;
        this.mu = mu;
        this.sigma = sigma;
        
        N = (int) (T / dt);
        prices = new double[N];
        
        stockListeners = new CopyOnWriteArrayList<>();
        
        currentMinYscale = (int)s0 - 5;
        currentMaxYscale = currentMinYscale + 60;
    }

    public void run()
    {
        try {
            generateStockPrices(s0, mu, sigma);
            /*
            for (int i = 0; i <= T; i++) {
                int time = i == 0 ? 0 : (int)(i / dt) - 1;
                
                // TODO
                // Is this efficient?
                publish(i, time, prices[time]);
                
                if (priceFrequency > 0) {
                    Thread.sleep(priceFrequency);
                }
            }
            */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Generated " + N + " prices");
    }
    
    public void publish(long day, int time, double price)
    {
        for (StockListener listener : stockListeners) {
            listener.priceTick(new StockPrice(time, price));
        }
    }
    
    public void setPriceFrequence(int priceFrequency)
    {
        this.priceFrequency = priceFrequency;
    }
    
    public double gaussian(double mean, double stddev)
    {
        return mean + stddev * rand.nextGaussian();
    }

    /**
     * Generate an increment of geometric Brownian motion.
     * 
     * @param mean
     * @param sigma
     * @param dt
     * @return
     */
    public double geometricBrownian(double mean, double sigma, double dt)
    {
        // generate a standard normal variate.
        double z = rand.nextGaussian();
        double incr = Math.exp((mean - (sigma * sigma / 2)) * dt + sigma
                * z);
        return incr;
    }

    /**
     * Generate stock price evolution using geometric Brownian motion.
     * 
     * @param s0
     *            initial stock price
     * @param mean
     *            drift
     * @param sigma
     *            volatility
     * @throws FileNotFoundException
     */
    public void generateStockPrices(double s0, double mean, double sigma)
            
    {
        prepareRealtimeDraw();
        prices[0] = s0;
        for (int i = 1; i < N; i++) {
            double s = s0 * geometricBrownian(mean, sigma, dt);
            prices[i] = s;
            
            // TODO
            drawRealtime(i);
            int time = i == 0 ? 0 : (int)(i / dt) - 1;
            publish(i, time, s);
            
            // System.out.println(i + "," + s);
            s0 = s;
        }
        // writePrices();
        if (priceFrequency > 0) {
            // drawPrices();
        }
    }
    
    private void writePrices() throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new File("stock.csv"));
        for (int i = 0; i <= T; i++) {
            int day = i == 0 ? 0 : (int)(i / dt) - 1;
            pw.print(i);
            pw.print(",");
            pw.println(prices[day]);
        }            
        pw.close();
    }

    public void drawPrices()
    {
        // find the min and max prices
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }
            if (prices[i] > max) {
                max = prices[i];
            }
        }
        // System.out.println("min = " + min + ", max = " + max);
        StdDraw.setCanvasSize(1680, 512);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(min, max);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.001);
        
        int ymin = (int)min;
        int ymax = (int)max;
        
        // Draw x-grid
        int dx = 20;
        for (int j = ymin; j < ymax; ) {
            for (int i = 1; i < N; ) {
                StdDraw.line(i, j, i + dx, j);
                i += dx;
            }
            j += 2;
        }
        
        // Draw y-grid
        for (int i = 0; i <= N; i++) {
            for (int j = ymin; j < ymax; ) {
                StdDraw.line(i, j, i, j + 2);
                j += 2;
            }
            i += 99;
        }

        // StdDraw.point(0, prices[0]);
        for (int i = 1; i < N; i++) {
            // StdDraw.point(i, prices[i]);
            StdDraw.line(i - 1, prices[i - 1], i, prices[i]);
        }
        StdDraw.show(0);
    }
    
    public void prepareRealtimeDraw()
    {
        StdDraw.setCanvasSize(1680, 512);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(currentMinYscale, currentMaxYscale);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.001);
        
        // Draw x-grid
        int dx = 20;
        for (int j = currentMinYscale; j < currentMaxYscale; ) {
            for (int i = 1; i < N; ) {
                StdDraw.line(i, j, i + dx, j);
                i += dx;
            }
            j += 2;
        }
        
        // Draw y-grid
        for (int i = 0; i <= N; i++) {
            for (int j = currentMinYscale; j < currentMaxYscale; ) {
                StdDraw.line(i, j, i, j + 2);
                j += 2;
            }
            i += 99;
        }        
    }
    
    public void drawRealtime(int time)
    {
        double min = (prices[time - 1] > prices[time]) ? prices[time] : prices[time - 1];
        double max = (prices[time - 1] > prices[time]) ? prices[time - 1] : prices[time];
        if (min < currentMinYscale) {
            StdDraw.setYscale(min, currentMaxYscale);
            currentMinYscale = (int)min;
        }
        if (max > currentMaxYscale) {
            StdDraw.setYscale(currentMinYscale, max);
            currentMaxYscale = (int)max;
        }
        StdDraw.show(0);
        
        // System.out.println("min = " + min + ", max = " + max);
        StdDraw.line(time - 1, prices[time - 1], time, prices[time]);
        StdDraw.show(0);        
    }
    
    public void testGaussian(double mean, double stddev)
    {
        double[] normals = new double[N];

        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            double r = gaussian(mean, stddev);
            normals[i] = r;
            sum += r;
        }
        double sampleMean = sum / N;
        System.out.println("Mean = " + sampleMean);

        sum = 0.0;
        for (int i = 0; i < N; i++) {
            double sq = (normals[i] - mean);
            sq *= sq;
            sum += sq;
        }
        double sampleVariance = sum / (N - 1);
        System.out.println("Sample stddev = " + Math.sqrt(sampleVariance));
    }   
    
    public void addStockListener(StockListener stockListener)
    {
        stockListeners.add(stockListener);
    }
    
    public void removeStockListener(StockListener stockListener)
    {
        stockListeners.remove(stockListener);
    }
    
    public static void main(String[] args)
    {
        StockTicker st = new StockTicker(90, 0.01, 20, 0.01, 0.01);
        // st.setPriceFrequence(-1);
        Thread t = new Thread(st);
        Clock clock = new Clock();
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }        
        long elapsed = clock.getElapsed();
        System.out.println("Elapsed = " + elapsed + " ms");
        double eventsPerSec = st.N / (elapsed/1000.0);
        System.out.println("price ticks per second = " + (int)eventsPerSec);
    }
}
