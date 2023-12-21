package org.misty.utils.verify;

import java.math.BigInteger;

public class BigIntegerRangeVerifier extends BigIntegerRangeVerifierBase<IllegalArgumentException> {

    public BigIntegerRangeVerifier(String title, BigInteger min, BigInteger max) throws IllegalArgumentException {
        super(title, min, max, Verifier.INSTANCE);
    }

}
