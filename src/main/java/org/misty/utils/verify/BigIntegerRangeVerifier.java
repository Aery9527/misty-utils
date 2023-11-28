package org.misty.utils.verify;

import java.math.BigInteger;

public class BigIntegerRangeVerifier extends BigIntegerRangeVerifierBase<IllegalArgumentException> {

    public BigIntegerRangeVerifier(BigInteger min, BigInteger max) throws IllegalArgumentException {
        super(min, max, Verifier.INSTANCE);
    }

}
