package org.misty.utils.limit;

public abstract class ShortAbstractLimiter implements ShortLimiter {

    private final ShortLimitVerifierHandler verifier;

    private final short min;

    private final short max;

    protected ShortAbstractLimiter(ShortLimitVerifierHandler verifier, short min, short max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public ShortLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public short getMin() {
        return this.min;
    }

    @Override
    public short getMax() {
        return this.max;
    }

}
