package org.misty.utils.limit;

import java.util.Optional;

public interface ShortLimiter extends Limiter {

    short get();

    void set(short value);

    short plus(short plus);

    short minus(short minus);

    Optional<Short> getMin();

    Optional<Short> getMax();

    default short increment() {
        return plus((short) 1);
    }

    default short decrement() {
        return minus((short) 1);
    }

}
