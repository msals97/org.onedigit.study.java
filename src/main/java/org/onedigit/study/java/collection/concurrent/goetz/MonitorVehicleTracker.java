package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Vehicle Tracker using Java monitors
 * @author ahmed
 *
 */
public class MonitorVehicleTracker
{
    private final Map<String, MutablePoint> locations;
    
    public MonitorVehicleTracker(Map<String, MutablePoint> locations)
    {
        this.locations = locations;
    }
    
    public synchronized Map<String, MutablePoint> getLocations()
    {
        return deepCopy(locations);
    }
    
    public synchronized MutablePoint getLocation(String id)
    {
        MutablePoint location = locations.get(id);
        return location == null ? null : new MutablePoint(location); 
    }
    
    public synchronized void setLocation(String id, int x, int y)
    {
        MutablePoint loc = locations.get(id);
        if (loc == null) {
            throw new IllegalArgumentException();
        }
        loc.x = x;
        loc.y = y;
    }
    
    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m)
    {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            result.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }
}
