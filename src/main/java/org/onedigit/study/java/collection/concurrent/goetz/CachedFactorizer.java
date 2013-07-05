package org.onedigit.study.java.collection.concurrent.goetz;

import java.math.BigInteger;

public class CachedFactorizer
{
    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    
    public BigInteger[] service(BigInteger i)
    {
        BigInteger[] factors = null;
        
        synchronized(this) {
            if (i.equals(lastNumber)) {
                factors = lastFactors.clone();
            }
        }

        if (factors == null) {
            factors = factor(i);
            synchronized(this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        
        return factors;
    }
    
    public BigInteger[] factor(BigInteger i)
    {
        BigInteger[] result = new BigInteger[1];
        return result;
    }    
}
