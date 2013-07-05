package org.onedigit.study.java.collection;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyReturn
{
    public List<Integer> getStuff()
    {
        return Collections.emptyList();
    }
    
    public void testOne()
    {
        Map<String, Integer> emptyMap = Collections.emptyMap();
        Set<Integer> emptySet = Collections.emptySet();
    }
}
