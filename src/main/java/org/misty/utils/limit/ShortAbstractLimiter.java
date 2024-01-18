package org.misty.utils.limit;

import java.util.Optional;

public abstract class ShortAbstractLimiter implements ShortLimiter {

    private final ShortLimitVerifierHandler verifier;

    private final Short min;

    private final Short max;

    protected ShortAbstractLimiter(ShortLimitVerifierHandler verifier, Short min, Short max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public ShortLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public Optional<Short> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<Short> getMax() {
        return Optional.ofNullable(this.max);
    }

}
