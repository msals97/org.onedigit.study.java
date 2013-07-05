package org.onedigit.study.java.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadFile
{
    public static void main(String[] args)
    {
        Map<String, Double> scores = new HashMap<>();
        
        File file = new File("test.txt");
        boolean fileExists = file.exists();
        System.out.println("File exists: " + fileExists);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            System.out.println(line);
            while ( (line = br.readLine()) != null) {
                String[] lineParts = line.split("\\s+");
                String team = lineParts[0];
                Double score = Double.valueOf(lineParts[1]);
                scores.put(team, score);
            }
        } catch (IOException e) {
            
        }
        
        System.out.println(scores);
    }
}
