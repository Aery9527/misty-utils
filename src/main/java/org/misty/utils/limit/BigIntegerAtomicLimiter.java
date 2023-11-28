package org.misty.utils.limit;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BigIntegerAtomicLimiter extends BigIntegerAbstractLimiter {

    private final AtomicReference<BigInteger> value = new AtomicReference<>();

    public BigIntegerAtomicLimiter(BigIntegerLimitVerifierHandler verifier, BigInteger initValue) {
        super(verifier);
        set(initValue);
    }

    @Override
    public BigInteger get() {
        return this.value.get();
    }

    @Override
    public void set(BigInteger value) {
        super.getVerifier().verifySet(value);
        this.value.set(value);
    }

    @Override
    public BigInteger plus(BigInteger plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public BigInteger minus(BigInteger minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
