package org.onedigit.study.java.collection.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.onedigit.study.java.collection.task.PriorityTask.Priority;

public class TaskManager
{
    public static Collection<PhoneTask> phoneTasks = new ArrayList<>();
    public static Collection<CodingTask> codingTasks = new ArrayList<>();
    public static Collection<Task> mondayTasks = new ArrayList<>();
    public static Collection<Task> tuesdayTasks = new ArrayList<>();
    public static PhoneTask mikePhone = new PhoneTask("Mike", "123 44544");
    public static PhoneTask paulPhone = new PhoneTask("Paul", "243 83843");
    public static CodingTask dbCode = new CodingTask("db");
    public static CodingTask interfaceCode = new CodingTask("gui");
    public static CodingTask logicCode = new CodingTask("logic");

    static {
        Collections.addAll(phoneTasks, mikePhone, paulPhone);
        Collections.addAll(codingTasks, dbCode, interfaceCode, logicCode);
        Collections.addAll(mondayTasks, logicCode, mikePhone);
        Collections.addAll(tuesdayTasks, dbCode, interfaceCode, paulPhone);
        // System.out.println("Phone Tasks = " + phoneTasks);
        // System.out.println("Coding Tasks = " + codingTasks);
        // System.out.println("Monday Tasks = " + mondayTasks);
        // System.out.println("Tuesday Tasks = " + tuesdayTasks);
        // Which tasks other than phone calls are scheduled for Tuesday
        Collection<Task> tuesdayNoPhoneTasks = new ArrayList<>();
        tuesdayNoPhoneTasks.addAll(tuesdayTasks);
        tuesdayNoPhoneTasks.removeAll(phoneTasks);
        // System.out.println("Tuesday No Phone Tasks = " + tuesdayNoPhoneTasks);

        // Which phone calls are scheduled for that day
        Collection<Task> phoneTuesdayTasks = new ArrayList<>(tuesdayTasks);
        phoneTuesdayTasks.retainAll(phoneTasks);
        // System.out.println("Tuesday Phone Tasks = " + phoneTuesdayTasks);
    }
    
    public static Collection<PhoneTask> getPhoneTasks()
    {
        return phoneTasks;
    }

    public static Collection<CodingTask> getCodingTasks()
    {
        return codingTasks;
    }

    public static Collection<Task> getMondayTasks()
    {
        return mondayTasks;
    }

    public static Collection<Task> getTuesdayTasks()
    {
        return tuesdayTasks;
    }

    public void priorityTasks()
    {
        NavigableSet<PriorityTask> set = new TreeSet<>();
        set.add(new PriorityTask(mikePhone, Priority.MEDIUM));
        set.add(new PriorityTask(paulPhone, Priority.HIGH));
        set.add(new PriorityTask(dbCode, Priority.MEDIUM));
        set.add(new PriorityTask(interfaceCode, Priority.LOW));
        System.out.println(set);
    }
    
    public static void main(String... args)
    {
        new TaskManager().priorityTasks();
    }
}
