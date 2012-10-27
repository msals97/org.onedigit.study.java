package org.onedigit.study.java.collection.task;

import java.util.NavigableSet;
import java.util.TreeSet;

public class PriorityTask implements Comparable<PriorityTask>
{
    public enum Priority { HIGH, MEDIUM, LOW }
    
    private Task task;
    private Priority priority;
    
    public PriorityTask(Task task, Priority priority)
    {
        this.task = task;
        this.priority = priority;
    }
    
    public Task getTask() { return task; }
    
    public Priority getPriority() { return priority; }
    
    @Override
    public int compareTo(PriorityTask other)
    {
        int result;
        int c = priority.compareTo(other.priority);
        result = (c != 0) ? c : task.compareTo(other.task);
        return result;
    };

    @Override
    public boolean equals(Object other)
    {
        boolean result = false;
        if (other instanceof PriorityTask) {
            PriorityTask that = (PriorityTask)other;
            result = task.equals(that.task) &&
                    priority.equals(that.priority);
        }
        return result;
    }
    
    @Override
    public int hashCode()
    {
        return task.hashCode();
    }
    
    @Override
    public String toString() 
    {
        return task.toString() + ": " + priority.toString();
    }
    
    public static void main(String... args)
    {
    }
}
