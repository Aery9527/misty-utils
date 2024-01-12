package org.misty.utils.limit;

import java.math.BigInteger;

public abstract class BigIntegerAbstractLimiter implements BigIntegerLimiter {

    private final BigIntegerLimitVerifierHandler verifier;

    private final BigInteger min;

    private final BigInteger max;

    protected BigIntegerAbstractLimiter(BigIntegerLimitVerifierHandler verifier, BigInteger min, BigInteger max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public BigIntegerLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public BigInteger getMin() {
        return this.min;
    }

    @Override
    public BigInteger getMax() {
        return this.max;
    }

}
