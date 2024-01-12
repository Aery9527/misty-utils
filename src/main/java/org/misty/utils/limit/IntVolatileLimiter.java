package org.misty.utils.limit;

public class IntVolatileLimiter extends IntAbstractLimiter {

    private volatile int value;

    public IntVolatileLimiter(IntLimitVerifierHandler verifier, int min, int max, int initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public void set(int value) {
        super.getVerifier().verifySet(value);
        this.value = value;
    }

    @Override
    public int plus(int plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public int minus(int minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
