package org.misty.utils.limit;

import java.math.BigInteger;

public class BigIntegerBaseLimiter extends BigIntegerAbstractLimiter {

    private BigInteger value;

    public BigIntegerBaseLimiter(BigIntegerLimitVerifierHandler verifier, BigInteger min, BigInteger max, BigInteger initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public BigInteger get() {
        return this.value;
    }

    @Override
    public void set(BigInteger value) {
        super.getVerifier().verifySet(value);
        this.value = value;
    }

    @Override
    public BigInteger plus(BigInteger plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public BigInteger minus(BigInteger minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
