package org.misty.utils.limit;

import java.math.BigInteger;
import java.util.Optional;

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
    public Optional<BigInteger> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<BigInteger> getMax() {
        return Optional.ofNullable(this.max);
    }

}
