package org.misty.utils.range;

import java.math.BigInteger;

public interface BigIntegerRange extends Range {

    BigInteger getLower();

    BigInteger getUpper();

    boolean inRange(BigInteger value);

    boolean outRange(BigInteger value);

    BigInteger random();

}
