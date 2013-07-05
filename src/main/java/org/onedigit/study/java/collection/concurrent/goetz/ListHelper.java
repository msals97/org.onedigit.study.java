package org.onedigit.study.java.collection.concurrent.goetz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListHelper<E>
{
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());
    
    public ListHelper()
    {
        // Random r = new Random();
        // r.nextInt();
    }
    
    public boolean putIfAbset(E x)
    {
        synchronized(list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}
