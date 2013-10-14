package org.onedigit.study.convert;

public final class Unit
{
    private final String name;
    private final double factor;
    
    public Unit(String name, double factor)
    {
        this.name = name;
        this.factor = factor;
    }
    
    @Override
    public boolean equals(Object other)
    {
        boolean result = false;
        if (other instanceof Unit) {
            result = ((Unit)other).getName().equals(name);
        }
        return result;
    }
    
    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public String toString()
    { 
        return name + ":" + factor;
    }
    
    public String getName()
    {
        return name;
    }

    public double getFactor()
    {
        return factor;
    }
}
