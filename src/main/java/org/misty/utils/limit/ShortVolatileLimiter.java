package org.misty.utils.limit;

public class ShortVolatileLimiter extends ShortAbstractLimiter {

    private volatile short value;

    public ShortVolatileLimiter(ShortLimitVerifierHandler verifier, short min, short max, short initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public short get() {
        return this.value;
    }

    @Override
    public void set(short value) {
        super.getVerifier().verifySet(value);
        this.value = value;
    }

    @Override
    public short plus(short plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public short minus(short minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
