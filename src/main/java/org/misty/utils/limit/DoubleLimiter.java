package org.misty.utils.limit;

import java.util.Optional;

public interface DoubleLimiter extends Limiter {

    double get();

    void set(double value);

    double plus(double plus);

    double minus(double minus);

    Optional<Double> getMin();

    Optional<Double> getMax();

    default double increment() {
        return plus(1);
    }

    default double decrement() {
        return minus(1);
    }

}
