package org.misty.utils.limit;

import java.math.BigInteger;

public interface BigIntegerLimiter extends Limiter {

    BigInteger get();

    void set(BigInteger value);

    BigInteger plus(BigInteger plus);

    BigInteger minus(BigInteger minus);

    BigInteger getMin();

    BigInteger getMax();

    default BigInteger increment() {
        return plus(BigInteger.ONE);
    }

    default BigInteger decrement() {
        return minus(BigInteger.ONE);
    }

}
