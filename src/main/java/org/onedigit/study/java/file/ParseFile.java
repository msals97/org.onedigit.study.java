package org.onedigit.study.java.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;

/**
 * Read a file containing numbers of the form:
 * 
 * 10 20 50
 * 100 2 90
 * 
 * Get the average of each line and output the result
 * in ascending order of the averages.
 * 
 * @author ahmed
 *
 */
public class ParseFile
{
    public static int[] toArray(String[] tokens)
    {
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            int value = Integer.valueOf(tokens[i]);
            result[i] = value;
        }
        return result;
    }
    
    public static int mean(int[] a)
    {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum / a.length;
    }
    
    public static void main(String... args)
    {
        Set<Integer> set = new TreeSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("numbers.txt")));
            String line = null;
            while ( (line = br.readLine()) != null && line != "") {
                String[] tokens = line.split(" ");
                set.add((mean(toArray(tokens))));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(set);
    }
}
