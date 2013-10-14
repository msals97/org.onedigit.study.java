package org.onedigit.study.convert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Converter
{
    private static final String fileName = "Units.txt";
    private Map<String, List<Unit>> map = new HashMap<String, List<Unit>>();
    
    public void readData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ( (line = br.readLine()) != null) {
                if (line.startsWith(";") || line.length() < 1) {
                    continue;
                }
                String[] parts = line.split(";");
                String key = parts[0];
                List<Unit> list = map.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(key, list);
                }
                list.add(new Unit(parts[1], Double.valueOf(parts[2])));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(map);
    }
    
    public Unit getUnit(String name)
    {
        Unit unit = null;
        Set<Entry<String, List<Unit>>> entries = map.entrySet();
        for (Entry<String, List<Unit>> entry : entries) {
            entry.getKey();
            List<Unit> values = entry.getValue();
            Unit unitToFind = new Unit(name, 0.0);
            if (values.contains(unitToFind)) {
                int index = values.indexOf(unitToFind);
                unit = values.get(index);
            }
        }
        return unit;
    }
    
    public List<Unit> getAvailableUnits(String key)
    {
        return Collections.unmodifiableList(map.get(key));
    }
    
    public double convert(String fromUnit, String toUnit, double value)
    {
        double result = 0.0;
        Unit from = getUnit(fromUnit);
        Unit to = getUnit(toUnit);
        System.out.println("fromUnit = " + from + ", toUnit = " + to);
        result = value * to.getFactor() / from.getFactor();
        System.out.println(value + " " + fromUnit + " = " + result + " " + toUnit);
        return result;
    }
    
    public static void main(String[] args)
    {
        Converter converter = new Converter();
        converter.readData();
        double result = converter.convert("Atmospheres", "Bars", 2.0);
        System.out.println(converter.getAvailableUnits("length"));
        result = converter.convert("Meters", "Yards", 2.0);
    }
}
