package org.misty.utils.limit;

public abstract class LongAbstractLimiter implements LongLimiter {

    private final LongLimitVerifierHandler verifier;

    private final long min;

    private final long max;

    protected LongAbstractLimiter(LongLimitVerifierHandler verifier, long min, long max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public LongLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public long getMin() {
        return this.min;
    }

    @Override
    public long getMax() {
        return this.max;
    }

}
