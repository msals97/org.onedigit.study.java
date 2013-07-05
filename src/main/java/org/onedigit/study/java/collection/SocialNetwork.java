package org.onedigit.study.java.collection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class SocialNetwork
{
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static  Map<String, Date> members = new HashMap<String, Date>();
    
    private static long msInYear = 1000L * 60 * 60 * 24 * 365; // milli seconds in a year
    
    static long getAgeFast(Date dob)
    {
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(dob);
        Calendar today = Calendar.getInstance();
        
        long msDiff = today.getTimeInMillis() - birthday.getTimeInMillis();
        return (msDiff / msInYear);
    }
    
    static int getAge(Date dob)
    {
        int age = 0;
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(dob);
        int month = birthday.get(Calendar.MONTH);
        int day = birthday.get(Calendar.DAY_OF_MONTH);

        Calendar today = Calendar.getInstance();
        int currentMonth = today.get(Calendar.MONTH);
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        
        age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if ( (currentMonth < month) || (currentMonth == month && currentDay < day)) {
            age--;
        }
        return age;
    }
    
    static NavigableMap<Integer, String> getMembersByAge()
    {
        // Order members by dob, descending
        NavigableMap<Integer, String> membersByAge = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2.compareTo(o1);
            }
        });
        
        Set<Entry<String, Date>> entries = members.entrySet();
        for (Entry<String, Date> entry : entries) {
            Date dob = entry.getValue();
            int age = getAge(dob);
            membersByAge.put(age, entry.getKey());
        }
        
        return membersByAge;
    }
    
    public static void findMembersAboveAge(int age)
    {
        NavigableMap<Integer, String> membersByAge = getMembersByAge();
        // This map is in descending order, highest age first.
        NavigableMap<Integer, String> view = membersByAge.headMap(age, true);
        Set<Entry<Integer, String>> entries = view.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.println(entry.getValue() + ", " + entry.getKey());
        }
    }
    
    public static void findMembersBelowAge(int age)
    {
        NavigableMap<Integer, String> membersByAge = getMembersByAge();
        // This map is in descending order, highest age first.
        NavigableMap<Integer, String> view = membersByAge.tailMap(age, true);
        Set<Entry<Integer, String>> entries = view.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.println(entry.getValue() + ", " + entry.getKey());
        }
    }    

    public static void findMembersBetween(int fromAge, int toAge)
    {
        NavigableMap<Integer, String> membersByAge = getMembersByAge();
        // This map is in descending order, highest age first.
        
        // Note that the map is reverse ordered, so we are passing the search keys in 
        // reverse order as well.
        NavigableMap<Integer, String> view = membersByAge.subMap(toAge, true, fromAge, true);
        
        Set<Entry<Integer, String>> entries = view.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.println(entry.getValue() + ", " + entry.getKey());
        }
    }    
    
    public static void main(String[] arg)
    {
        File file = new File("socialNetwork.txt");
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ( (line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String dobString = parts[1];
                Date dob = df.parse(dobString);
                members.put(name, dob);
                int age = getAge(dob);
                long ageFast = getAgeFast(dob);
                System.out.println(name + ", DOB: " + dob + ", Age: " + age + ", Age: " + ageFast);
            }
        } catch (IOException | ParseException e) {
            System.err.println(e.getMessage());
        }
        
        Map<Integer, String> membersByAge = getMembersByAge();
        Set<Entry<Integer, String>> ageEntries = membersByAge.entrySet();
        for (Entry<Integer, String> entry : ageEntries) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        
        int age = 30;
        System.out.println("--- Members above: " + age + " ---");
        findMembersAboveAge(age);
        
        age = 30;
        System.out.println("--- Members below: " + age + " ---");
        findMembersBelowAge(age);
        
        int fromAge = 30;
        int toAge = 40;
        System.out.println("--- Members between: " + fromAge + ":" + toAge + " ---");
        findMembersBetween(fromAge, toAge);
    }
}
