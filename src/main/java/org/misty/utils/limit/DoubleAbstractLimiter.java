package org.misty.utils.limit;

public abstract class DoubleAbstractLimiter implements DoubleLimiter {

    private final DoubleLimitVerifierHandler verifier;

    private final double min;

    private final double max;

    protected DoubleAbstractLimiter(DoubleLimitVerifierHandler verifier, double min, double max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public DoubleLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public double getMin() {
        return this.min;
    }

    @Override
    public double getMax() {
        return this.max;
    }

}
