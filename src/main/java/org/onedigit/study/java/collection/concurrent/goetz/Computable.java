package org.onedigit.study.java.collection.concurrent.goetz;

public interface Computable<A, V>
{
    V compute(A arg) throws InterruptedException;
}
