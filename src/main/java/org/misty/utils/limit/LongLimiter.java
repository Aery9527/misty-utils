package org.misty.utils.limit;

public interface LongLimiter extends Limiter {

    long get();

    void set(long value);

    long plus(long plus);

    long minus(long minus);

    long getMin();

    long getMax();

    default long increment() {
        return plus(1);
    }

    default long decrement() {
        return minus(1);
    }

}
