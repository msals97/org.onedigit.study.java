package org.onedigit.study.ikm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class URLReader
{
    public static void main(String... args)
    {
        try {
            URL oracle = new URL("http://www.oneidigit.com");
            InputStream is = oracle.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ( (inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
            }
            br.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
