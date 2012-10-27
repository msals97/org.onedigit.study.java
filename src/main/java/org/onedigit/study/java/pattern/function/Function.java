package org.onedigit.study.java.pattern.function;

public interface Function<A, B, X extends Throwable>
{
    public B apply(A x) throws X;
}
