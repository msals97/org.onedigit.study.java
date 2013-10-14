package org.onedigit.study.ikm;

import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.Formatter;
import java.util.Set;

public class Test
{

    public static void main(String[] args)
    {
        Integer before = new Integer(25);
        Integer after = ++before == 26 ? 5 : new Integer(10);
        System.out.println(after.intValue() - before.intValue());
    }
}
