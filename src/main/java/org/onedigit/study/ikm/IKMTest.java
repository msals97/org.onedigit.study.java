package org.onedigit.study.ikm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class IKMTest
{
    static Map<String, Integer> names = 
            Calendar.getInstance().getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.JAPANESE);
    
    public static void main(String[] args)
    {
        String param2 = "UTF-64";
        
        try {
            FileOutputStream fos = new FileOutputStream("test.txt");
            Writer out = new OutputStreamWriter(fos, param2);
            out.write(names.toString());
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
