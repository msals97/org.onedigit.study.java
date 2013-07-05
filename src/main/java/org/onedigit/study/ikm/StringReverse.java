package org.onedigit.study.ikm;

import java.util.Arrays;

public class StringReverse
{
    public static String reverseOne(String s)
    {
        char[] buf = s.toCharArray();
        int len = buf.length;
        for (int i = 0; i < len/2; i++) {
            char c = buf[i];
            buf[i] = buf[len - i - 1];
            buf[len - i -1] = c;
        }
        String result = new String(buf);
        return result;
    }
    
    public static String reverseTwo(String s)
    {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }
    
    /**
     * Rever the phrases of a string using {@link StringBuilder}
     * @param s
     * @return
     */
    public static String reversePhrase(String s)
    {
        StringBuilder result = new StringBuilder(s.length());
        String[] phrases = s.split(" ");
        for (String p : phrases) {
            String rp = reverseOne(p);
            result.append(rp).append(" ");
        }
        return result.toString();
    }
    
    /**
     * Reverse the phrases of a string in place.
     * @param s
     * @return
     */
    public static String reversePhraseTwo(String s)
    {
        char[] buf = s.toCharArray();
        int len = buf.length;
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (buf[i] == ' ' || i == (len-1)) {
                if (i == (len-1)) {
                    i = len;
                }
                int end = (start + i)/2;
                for (int j = start; j < end; j++) {
                    char c = buf[j];
                    int pos = (start + i) - j - 1;
                    buf[j] = buf[pos];
                    buf[pos] = c;
                }
                start = i + 1;    
            }
        }
        return new String(buf);
    }
    
    public static void main(String[] args)
    {
        String s = "ahmed riza is a good old boy";
        System.out.println(reverseOne(s));
        System.out.println(reverseTwo(s));
        System.out.println(reversePhrase(s));
        System.out.println(reversePhraseTwo(s));
    }
}
