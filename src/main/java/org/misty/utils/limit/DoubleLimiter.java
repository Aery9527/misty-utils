package org.misty.utils.limit;

public interface DoubleLimiter extends Limiter {

    double get();

    void set(double value);

    double plus(double plus);

    double minus(double minus);

    double getMin();

    double getMax();

    default double increment() {
        return plus(1);
    }

    default double decrement() {
        return minus(1);
    }

}
