package org.misty.utils.limit;

import java.math.BigInteger;
import java.util.Optional;

public interface BigIntegerLimiter extends Limiter {

    BigInteger get();

    void set(BigInteger value);

    BigInteger plus(BigInteger plus);

    BigInteger minus(BigInteger minus);

    Optional<BigInteger> getMin();

    Optional<BigInteger> getMax();

    default BigInteger increment() {
        return plus(BigInteger.ONE);
    }

    default BigInteger decrement() {
        return minus(BigInteger.ONE);
    }

}
