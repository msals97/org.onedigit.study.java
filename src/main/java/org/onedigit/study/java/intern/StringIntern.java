package org.onedigit.study.java.intern;

public class StringIntern
{
    public static void main(String... args)
    {
        String s1 = "Good";
        s1 = s1 + "morning";
        System.out.println(s1.intern());

        String s2 = "Goodmorning";
        if (s1 == s2) {
            System.out.println("both are equal");
        }
    }
}
