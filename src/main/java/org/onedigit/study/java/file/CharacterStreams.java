package org.onedigit.study.java.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CharacterStreams
{
    public static void testOne()
    {
        try (FileReader fr = new FileReader("numbers.txt")) {
            int c;
            while ( (c = fr.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }        
    }
    
    public static void testTwo()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("numbers.txt"))) {
            String line;
            while ( (line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        testTwo();
    }
}
