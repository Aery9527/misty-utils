package org.misty.utils.limit;

import java.util.Optional;

public interface IntLimiter extends Limiter {

    int get();

    void set(int value);

    int plus(int plus);

    int minus(int minus);

    Optional<Integer> getMin();

    Optional<Integer> getMax();

    default int increment() {
        return plus(1);
    }

    default int decrement() {
        return minus(1);
    }

}
