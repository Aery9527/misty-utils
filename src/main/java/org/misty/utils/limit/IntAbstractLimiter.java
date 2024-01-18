package org.misty.utils.limit;

import java.util.Optional;

public abstract class IntAbstractLimiter implements IntLimiter {

    private final IntLimitVerifierHandler verifier;

    private final Integer min;

    private final Integer max;

    protected IntAbstractLimiter(IntLimitVerifierHandler verifier, Integer min, Integer max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public IntLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public Optional<Integer> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<Integer> getMax() {
        return Optional.ofNullable(this.max);
    }

}
