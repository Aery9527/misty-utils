package org.misty.utils.limit;

public interface IntLimiter extends Limiter {

    int get();

    void set(int value);

    int plus(int plus);

    int minus(int minus);

    default int increment() {
        return plus(1);
    }

    default int decrement() {
        return minus(1);
    }

}
