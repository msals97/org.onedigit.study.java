package org.onedigit.study.java.collection.concurrent.goetz;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StatelessFactorizer
{
    // Access counter
    private AtomicLong count = new AtomicLong(0);
    
    public void service(BigInteger i)
    {
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
    }
    
    public BigInteger[] factor(BigInteger i)
    {
        BigInteger[] result = new BigInteger[1];
        return result;
    }
}
