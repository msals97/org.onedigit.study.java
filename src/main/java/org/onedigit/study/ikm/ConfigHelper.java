package org.onedigit.study.ikm;

import java.util.Arrays;
import java.util.Date;

public class ConfigHelper
{
    private static ConfigHelper instance = new ConfigHelper();
    private String[] config;
    private long lastUpdateTime = 0;
    private static final int DELAY = 4 * 1000; // 1 minutes 
    private DBConnection dbConn;
    
    class DBConnection
    {
        public String[] getConfiguration()
        {
            return new String[]{"ABC", "XYZ"};
        }
    }
    
    private ConfigHelper()
    {
        dbConn = new DBConnection();
        config = dbConn.getConfiguration();
    }
    
    public static ConfigHelper getInstance()
    {
        return instance;
    }
    
    public String[] getConfiguration()
    {
        long now = new Date().getTime();
        String[] result = null;
        synchronized (this) {
            long delay = now - lastUpdateTime;
            System.out.println("delay = " + delay);
            if (delay > DELAY) {
                System.out.println("Stale data, calling DB");
                config = dbConn.getConfiguration();
                lastUpdateTime = now;
            }
            result = config.clone();
        }
        return result;
    }
    
    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++) {
            String[] config = ConfigHelper.getInstance().getConfiguration();
            System.out.println(Arrays.toString(config));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }
}
