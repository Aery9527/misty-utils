package org.misty.utils.limit;

import java.util.Optional;

public interface FloatLimiter extends Limiter {

    float get();

    void set(float value);

    float plus(float plus);

    float minus(float minus);

    Optional<Float> getMin();

    Optional<Float> getMax();

    default float increment() {
        return plus(1);
    }

    default float decrement() {
        return minus(1);
    }

}
