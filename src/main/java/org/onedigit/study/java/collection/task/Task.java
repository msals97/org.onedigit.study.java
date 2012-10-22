package org.onedigit.study.java.collection.task;

public abstract class Task implements Comparable<Task>
{
    protected Task() {}
    
    @Override
    public boolean equals(Object other)
    {
        boolean result = false;
        if (other instanceof Task) {
            result = toString().equals(other.toString());
        }
        return result;
    }
    
    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
    
    @Override
    public int compareTo(Task other)
    {
        return toString().compareTo(other.toString());
    }
    
    @Override
    public abstract String toString();
}
