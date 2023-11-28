package org.misty.utils.limit;

public class ShortBaseLimiter extends ShortAbstractLimiter {

    private short value;

    public ShortBaseLimiter(ShortLimitVerifierHandler verifier, short initValue) {
        super(verifier);
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
