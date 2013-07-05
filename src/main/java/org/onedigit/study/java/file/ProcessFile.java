package org.onedigit.study.java.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessFile
{
    /**
     * Read the scores.txt data
     */
    private static String fileName = "scores.txt";
    private static String SEP = " ";
    private String[] columnHeaders;
    private List<List<Double>> scoreList = new ArrayList<>();
    
    public void readDataUsingScanner()
    {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (!s.startsWith("#")) {
                    System.out.println(s);
                }
            }
        } catch (IOException e) {
            
        }
    }
    
    public void readData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // lines starting with # are comments, so ignore them.
            String line;
            boolean columnHeadersRead = false;
            while ( (line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    if (columnHeadersRead == false) {
                        // read the column headers.
                        columnHeaders = line.split(SEP);
                        columnHeadersRead = true;
                    } else {
                        // We assume that each line has the same number of scores
                        String[] scores = line.split(SEP);
                        if (scores.length > 1) {
                            List<Double> list = new ArrayList<>();
                            for (int i = 0; i < scores.length; i++) {
                                list.add(Double.parseDouble(scores[i]));
                            }
                            scoreList.add(list);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void process()
    {
        for (List<Double> list : scoreList) {
            for (Double d : list) {
                System.out.format("%16.4f ", d);
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args)
    {
        ProcessFile pf = new ProcessFile();
        // pf.readDataUsingScanner();
        pf.readData();
        pf.process();
    }
}
