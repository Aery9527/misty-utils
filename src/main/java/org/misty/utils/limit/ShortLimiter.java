package org.misty.utils.limit;

public interface ShortLimiter extends Limiter {

    short get();

    void set(short value);

    short plus(short plus);

    short minus(short minus);

    short getMin();

    short getMax();

    default short increment() {
        return plus((short) 1);
    }

    default short decrement() {
        return minus((short) 1);
    }

}
