package org.misty.utils.limit;

public class LongVolatileLimiter extends LongAbstractLimiter {

    private volatile long value;

    public LongVolatileLimiter(LongLimitVerifierHandler verifier, long initValue) {
        super(verifier);
        set(initValue);
    }

    @Override
    public long get() {
        return this.value;
    }

    @Override
    public void set(long value) {
        super.getVerifier().verifySet(value);
        this.value = value;
    }

    @Override
    public long plus(long plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public long minus(long minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
