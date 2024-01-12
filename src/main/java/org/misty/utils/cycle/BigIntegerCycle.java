package org.misty.utils.cycle;

import java.math.BigInteger;

public interface BigIntegerCycle extends Cycle {

    BigInteger get();

    BigInteger getStep();

    BigInteger getMin();

    BigInteger getMax();

    BigInteger getAndNext();

    BigInteger nextAndGet();

}
