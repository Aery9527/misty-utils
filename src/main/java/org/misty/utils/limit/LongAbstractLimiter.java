package org.misty.utils.limit;

import java.util.Optional;

public abstract class LongAbstractLimiter implements LongLimiter {

    private final LongLimitVerifierHandler verifier;

    private final Long min;

    private final Long max;

    protected LongAbstractLimiter(LongLimitVerifierHandler verifier, Long min, Long max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public LongLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public Optional<Long> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<Long> getMax() {
        return Optional.ofNullable(this.max);
    }

}
