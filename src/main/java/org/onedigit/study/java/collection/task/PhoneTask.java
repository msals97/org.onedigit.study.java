package org.onedigit.study.java.collection.task;

public final class PhoneTask extends Task
{
    public final String name;
    public final String number;
    
    public PhoneTask(String name, String number)
    {
        this.name = name;
        this.number = number;
    }
    
    @Override
    public String toString()
    {
        return "phone " + name;
    }
    
    public String getName() { return name; }

    public String getNumber() { return number; }
}
