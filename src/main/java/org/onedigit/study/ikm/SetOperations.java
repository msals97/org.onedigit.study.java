package org.onedigit.study.ikm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetOperations
{
    public static Set<Integer> makeOne()
    {
        Set<Integer> setOne = new TreeSet<>();
        setOne.add(1);
        setOne.add(10);
        setOne.add(40);
        setOne.add(90);
        return setOne;
    }
    
    public static Set<Integer> makeTwo()
    {
        Set<Integer> setTwo = new TreeSet<>();
        setTwo.add(60);
        setTwo.add(30);
        setTwo.add(40);
        return setTwo;
    }
    
    // Find duplicates
    public static void findDups(List<String> words)
    {
        // which words in the argument list occur only once and which
        // occur more than once, but we do not want any duplicates
        // printed out repeatedly.
        Set<String> unique = new HashSet<>();
        Set<String> dups = new HashSet<>();
        
        for (String s : words) {
            boolean added = unique.add(s);
            if (!added) {
                dups.add(s);
            }
        }
        
        unique.removeAll(dups);
        System.out.println("Unique words: " + unique);
        System.out.println("Duplicate words: " + dups);
    }
    
    public static void main(String... args)
    {
        
        Set<Integer> setOne = makeOne();
        Set<Integer> setTwo = makeTwo();
        System.out.println("Set One: " + setOne);
        System.out.println("Set Two: " + setTwo);
        
        // Intersection
        Set<Integer> intersection = new TreeSet<>(setOne);
        intersection.retainAll(setTwo);
        System.out.println("Intersection: " + intersection);
        
        // Union
        Set<Integer> union = new TreeSet<>(setOne);
        union.addAll(setTwo);
        System.out.println("Union: " + union);
        
        // Asymmetric Difference
        Set<Integer> difference = new TreeSet<>(setOne);
        difference.removeAll(setTwo);
        System.out.println("setOne - setTwo: " + difference);
        
        // Symmetric Set Difference
        Set<Integer> symmetricDifference = new TreeSet<>(setOne);
        symmetricDifference.addAll(setTwo);
        symmetricDifference.removeAll(intersection);
        System.out.println("Symmetric Difference: " + symmetricDifference);
        
        List<String> words = Arrays.asList("i", "came", "i", "saw", "i", "left");
        findDups(words);
    }
}
