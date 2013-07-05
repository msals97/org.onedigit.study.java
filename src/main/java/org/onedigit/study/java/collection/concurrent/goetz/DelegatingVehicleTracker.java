package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatingVehicleTracker
{
    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;
    
    public DelegatingVehicleTracker(Map<String, Point> points)
    {
        locations = new ConcurrentHashMap<>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }
    
    public Map<String, Point> getLocations()
    {
        // return unmodifiableMap;
        // return a snapshot;
        return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
    }
    
    public Point getLocation(String id)
    {
        return locations.get(id);
    }
    
    public void setLocation(String id, int x, int y)
    {
        // Atomically update the new location of the location
        // refered to by id.
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("Invalid vechild id: " + id);
        }
    }
}
