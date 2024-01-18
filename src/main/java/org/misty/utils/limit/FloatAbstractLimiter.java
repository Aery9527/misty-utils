package org.misty.utils.limit;

import java.util.Optional;

public abstract class FloatAbstractLimiter implements FloatLimiter {

    private final FloatLimitVerifierHandler verifier;

    private final Float min;

    private final Float max;

    protected FloatAbstractLimiter(FloatLimitVerifierHandler verifier, Float min, Float max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public FloatLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public Optional<Float> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<Float> getMax() {
        return Optional.ofNullable(this.max);
    }

}
