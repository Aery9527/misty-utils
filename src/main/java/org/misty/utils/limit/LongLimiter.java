package org.misty.utils.limit;

import java.util.Optional;

public interface LongLimiter extends Limiter {

    long get();

    void set(long value);

    long plus(long plus);

    long minus(long minus);

    Optional<Long> getMin();

    Optional<Long> getMax();

    default long increment() {
        return plus(1);
    }

    default long decrement() {
        return minus(1);
    }

}
