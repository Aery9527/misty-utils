package org.misty.utils.limit;

public interface FloatLimiter extends Limiter {

    float get();

    void set(float value);

    float plus(float plus);

    float minus(float minus);

    default float increment() {
        return plus(1);
    }

    default float decrement() {
        return minus(1);
    }

}
