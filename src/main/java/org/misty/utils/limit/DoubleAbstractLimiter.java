package org.misty.utils.limit;

import java.util.Optional;

public abstract class DoubleAbstractLimiter implements DoubleLimiter {

    private final DoubleLimitVerifierHandler verifier;

    private final Double min;

    private final Double max;

    protected DoubleAbstractLimiter(DoubleLimitVerifierHandler verifier, Double min, Double max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public DoubleLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public Optional<Double> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<Double> getMax() {
        return Optional.ofNullable(this.max);
    }

}
