package org.onedigit.study.java.collection;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.onedigit.study.java.collection.task.EmptyTask;
import org.onedigit.study.java.collection.task.PriorityTask;
import org.onedigit.study.java.collection.task.Task;
import org.onedigit.study.java.collection.task.TaskManager;
import org.onedigit.study.java.collection.task.PriorityTask.Priority;

public class SortedSetExample
{
    public void testInterface()
    {
        SortedSet<Integer> set = new TreeSet<>();
        set.add(10);
        set.add(20);
        set.add(30);
        set.add(40);
        set.add(2);
        
        for (Integer s : set) {
            System.out.println(s);
        }
        
        System.out.println(set.first()); // 2
        System.out.println(set.last()); // 40
        
        System.out.println(set.headSet(20)); // 2, 10 = interval (2, 20)
        System.out.println(set.tailSet(20)); // 20, 30, 40  = interval [20, 40]
        
        System.out.println(set.subSet(2, 40)); // interval [2, 40)        
        System.out.println(set.subSet(2, 40 + 1)); // interval [2, 40]        
    }
    
    public void testSubset()
    {
        SortedSet<String> set = new TreeSet<>();
        set.add("ahmed");
        set.add("imam");
        set.add("riza");
        set.add("ali");
        set.add("soli");
        System.out.println(set);
        System.out.println(set.subSet("ali", "riza"));
        System.out.println(set.subSet("ali", "riza" + "\0"));
    }
    
    public void testTasks()
    {
        // Merging two sets in O(nlog(n)), unlike merging two lists
        // which is O(n)
        SortedSet<Task> tasks = new TreeSet<>(TaskManager.getMondayTasks());
        tasks.addAll(TaskManager.getTuesdayTasks());
        System.out.println(tasks);
    }
    
    public void testPriorityTasks()
    {
        NavigableSet<PriorityTask> set = new TreeSet<>();
        set.add(new PriorityTask(TaskManager.mikePhone, Priority.MEDIUM));
        set.add(new PriorityTask(TaskManager.paulPhone, Priority.HIGH));
        set.add(new PriorityTask(TaskManager.dbCode, Priority.MEDIUM));
        set.add(new PriorityTask(TaskManager.interfaceCode, Priority.LOW));
        // System.out.println(set);
        
        PriorityTask firstLowPriorityTask = new PriorityTask(new EmptyTask(), Priority.LOW);
        // System.out.println(set.headSet(firstLowPriorityTask));
        
        PriorityTask firstMediumPriorityTask = new PriorityTask(new EmptyTask(), Priority.MEDIUM);
        SortedSet<PriorityTask> mediumPriorityTasks = set.subSet(firstMediumPriorityTask, firstLowPriorityTask);
        // System.out.println(mediumPriorityTasks);
        
        // Add a medium priority task
        PriorityTask logicCodeMedium = new PriorityTask(TaskManager.logicCode, Priority.MEDIUM);
        set.add(logicCodeMedium);
        // The view should reflect the change
        // System.out.println(mediumPriorityTasks);
        // Now change the view
        mediumPriorityTasks.remove(logicCodeMedium);
        System.out.println(mediumPriorityTasks);
    }
        
    public SortedSetExample()
    {
        // testInterface();
        // testTasks();
        // testSubset();
        testPriorityTasks();
    }
    
    public static void main(String... args)
    {
        new SortedSetExample();
    }
}
