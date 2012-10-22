package org.onedigit.study.java.collection.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TaskManager
{
    public TaskManager()
    {
        PhoneTask mikePhone = new PhoneTask("Mike", "123 44544");
        PhoneTask paulPhone = new PhoneTask("Paul", "243 83843");
        
        CodingTask dbCode = new CodingTask("db");
        CodingTask interfaceCode = new CodingTask("gui");
        CodingTask logicCode = new CodingTask("logic");
        
        Collection<PhoneTask> phoneTasks = new ArrayList<>();
        Collection<CodingTask> codingTasks = new ArrayList<>();
        
        Collection<Task> mondayTasks = new ArrayList<>();
        Collection<Task> tuesdayTasks = new ArrayList<>();
        
        Collections.addAll(phoneTasks, mikePhone, paulPhone);
        Collections.addAll(codingTasks, dbCode, interfaceCode, logicCode);
        
        Collections.addAll(mondayTasks, logicCode, mikePhone);
        Collections.addAll(tuesdayTasks, dbCode, interfaceCode, paulPhone);
        
        System.out.println("Phone Tasks = " + phoneTasks);
        System.out.println("Coding Tasks = " + codingTasks);
        System.out.println("Monday Tasks = " + mondayTasks);
        System.out.println("Tuesday Tasks = " + tuesdayTasks);
        
        // Which tasks other than phone calls are scheduled for Tuesday
        Collection<Task> tuesdayNoPhoneTasks = new ArrayList<>();
        tuesdayNoPhoneTasks.addAll(tuesdayTasks);
        tuesdayNoPhoneTasks.removeAll(phoneTasks);
        System.out.println("Tuesday No Phone Tasks = " + tuesdayNoPhoneTasks);
        
        // Which phone calls are scheduled for that day
        Collection<Task> phoneTuesdayTasks = new ArrayList<>(tuesdayTasks);
        phoneTuesdayTasks.retainAll(phoneTasks);
        System.out.println("Tuesday Phone Tasks = " + phoneTuesdayTasks);
    }
    
    public static void main(String... args)
    {
        new TaskManager();
    }
}
