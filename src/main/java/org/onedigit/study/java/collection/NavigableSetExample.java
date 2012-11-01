package org.onedigit.study.java.collection;

import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.onedigit.study.java.collection.task.EmptyTask;
import org.onedigit.study.java.collection.task.PriorityTask;
import org.onedigit.study.java.collection.task.TaskManager;
import org.onedigit.study.java.collection.task.PriorityTask.Priority;

public class NavigableSetExample
{
    NavigableSet<Integer> ns = new TreeSet<>();
    
    public NavigableSetExample()
    {
        testInterface();
        testPriorityTasks();
    }
    
    public void testInterface()
    {
        ns.add(10);
        ns.add(70);
        ns.add(30);
        ns.add(2);
        ns.add(5);
        
        print();
        
        assert(ns.first() == 2); // does not remove; throws NoSuchElementException if empty
        assert(2 == ns.pollFirst()); // remove and return; returns null, if empty
        
        assert(70 == ns.last()); // does not remove; throws NoSuchElementException if empty
        assert(70 == ns.pollLast()); // remove and return; returns null, if empty
        
        assert(10 == ns.ceiling(10));
        assert(30 == ns.higher(10));
        
        assert(30 == ns.floor(70));
        assert(30 == ns.lower(70));
    }
    
    public void testPriorityTasks()
    {
        NavigableSet<PriorityTask> set = new TreeSet<>();
        PriorityTask mikePhone = new PriorityTask(TaskManager.mikePhone, Priority.MEDIUM);
        set.add(mikePhone);
        set.add(new PriorityTask(TaskManager.paulPhone, Priority.HIGH));
        set.add(new PriorityTask(TaskManager.dbCode, Priority.MEDIUM));
        set.add(new PriorityTask(TaskManager.interfaceCode, Priority.LOW));
        
        System.out.println(set.pollFirst());        
        
        PriorityTask firstMediumPriorityTask = new PriorityTask(new EmptyTask(), Priority.MEDIUM);
        // Subset of MEDIUM tasks up to and including mikePhone
        System.out.println(set.subSet(firstMediumPriorityTask, true, mikePhone, true));
        
        // Descending order
        NavigableSet<PriorityTask> desc = set.descendingSet();
        System.out.println(desc);
        
        NavigableSet<String> stringSet = new TreeSet<>();
        Collections.addAll(stringSet, "abc", "cde", "x-ray", "zed");
        String last = stringSet.floor("x-ray");
        System.out.println(last);
        String secondToLast = last == null ? null : 
            stringSet.lower(last);
        System.out.println(secondToLast);
        String thirdToLast = secondToLast == null ? null : 
            stringSet.lower(secondToLast);
        System.out.println(thirdToLast);
        System.out.println(stringSet.headSet("x-ray", true));
        System.out.println(stringSet.tailSet("x-ray", true));
    }
    
    private void print()
    {
        for (Integer i : ns) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public static void main(String... args)
    {
        new NavigableSetExample();
    }
}
