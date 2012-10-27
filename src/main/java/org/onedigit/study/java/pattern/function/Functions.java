package org.onedigit.study.java.pattern.function;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Functions
{
    public static <A, B, X extends Throwable> List<B> applyAll(
            Function<A, B, X> f, List<A> list) throws X
    {
        List<B> result = new ArrayList<>(list.size());
        for (A x : list) {
            result.add(f.apply(x));
        }
        return result;
    }

    public static void main(String... args)
    {
        Function<String, Integer, Error> length = new Function<String, Integer, Error>() {
            @Override
            public Integer apply(String x) throws Error
            {
                return x.length();
            }
        };

        Function<String, Class<?>, ClassNotFoundException> forName = new Function<String, Class<?>, ClassNotFoundException>() {
            @Override
            public Class<?> apply(String x) throws ClassNotFoundException
            {
                return Class.forName(x);
            }
        };

        Function<String, Method, Exception> getRunMethod = new Function<String, Method, Exception>() {
            
            @Override
            public Method apply(String x) throws ClassNotFoundException, NoSuchMethodException
            {
                return Class.forName(x).getMethod("run");
            }
        };
        
        List<String> strings = Arrays.asList("java.lang.Thread", "java.util.List");
        System.out.println(applyAll(length, strings));
        
        try {
            System.out.println(applyAll(forName, strings));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            System.out.println(applyAll(getRunMethod, strings));
        } catch (ClassNotFoundException ce) {
            System.out.println("ClassNotFoundException: " + ce.getMessage());
        } catch (NoSuchMethodException nm) {            
            System.out.println("NoSuchMethodException: " + nm.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
